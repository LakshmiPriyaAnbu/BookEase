package com.bookease.app.ui.screens.bookings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bookease.app.R
import com.bookease.app.data.models.Booking
import com.bookease.app.data.models.BookingStatus
import com.bookease.app.ui.components.BookingCard
import com.bookease.app.ui.components.EmptyStateVariant
import com.bookease.app.ui.components.EmptyStateView
import com.bookease.app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BookingsScreen(
    vm: BookingsViewModel = viewModel(),
    onBrowseServices: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BeColor.surface100)
    ) {
        // Title
        Text(
            text = stringResource(R.string.bookings_title),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier
                .padding(horizontal = BeSp.lg)
                .padding(top = BeSp.sm, bottom = BeSp.base)
        )

        // Segmented control
        val segments = listOf(
            stringResource(R.string.bookings_segment_upcoming),
            stringResource(R.string.bookings_segment_completed),
            stringResource(R.string.bookings_segment_cancelled)
        )
        SegmentedPicker(
            segments = segments,
            selectedIndex = vm.selectedSegment,
            onSelect = { vm.selectSegment(it) },
            modifier = Modifier
                .padding(horizontal = BeSp.lg)
                .padding(bottom = BeSp.lg)
        )

        // List
        val bookings = vm.filteredBookings
        if (bookings.isEmpty()) {
            val emptyTitle = when (vm.selectedSegment) {
                0    -> stringResource(R.string.bookings_empty_upcoming_title)
                1    -> stringResource(R.string.bookings_empty_completed_title)
                2    -> stringResource(R.string.bookings_empty_cancelled_title)
                else -> stringResource(R.string.bookings_empty_generic_title)
            }
            val emptyMessage = when (vm.selectedSegment) {
                0    -> stringResource(R.string.bookings_empty_upcoming_message)
                1    -> stringResource(R.string.bookings_empty_completed_message)
                2    -> stringResource(R.string.bookings_empty_cancelled_message)
                else -> stringResource(R.string.bookings_empty_generic_message)
            }
            EmptyStateView(
                variant = if (vm.selectedSegment == 0)
                    EmptyStateVariant.Bookings
                else
                    EmptyStateVariant.Generic(Icons.Default.CalendarToday),
                title       = emptyTitle,
                message     = emptyMessage,
                actionLabel = if (vm.selectedSegment == 0) stringResource(R.string.common_browse_services) else null,
                onAction    = if (vm.selectedSegment == 0) onBrowseServices else null,
                modifier    = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = BeSp.lg, vertical = BeSp.xs),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(bookings, key = { it.id.toString() }) { booking ->
                    SwipeToCancel(
                        booking = booking,
                        onCancel = { vm.cancelBooking(booking.id) }
                    ) {
                        BookingCardRow(booking)
                    }
                }
                item { Spacer(Modifier.height(BeSp.xxl)) }
            }
        }
    }
}

// ── Segmented Picker ──────────────────────────────────────────────────────────

@Composable
private fun SegmentedPicker(
    segments: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFECECEF))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        segments.forEachIndexed { index, label ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .then(
                        if (isSelected) Modifier
                            .shadow(elevation = 2.dp, shape = RoundedCornerShape(9.dp), ambientColor = Color.Black.copy(alpha = 0.08f))
                            .background(Color.White)
                        else Modifier
                    )
                    .clickable { onSelect(index) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = if (isSelected) BeColor.ink700 else BeColor.ink300,
                        fontSize = 13.5.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

// ── Booking Card Row ──────────────────────────────────────────────────────────

@Composable
private fun BookingCardRow(booking: Booking) {
    val monthFmt = SimpleDateFormat("MMM", Locale.getDefault())
    val dayFmt   = SimpleDateFormat("d",   Locale.getDefault())
    val timeFmt  = SimpleDateFormat("h:mm a", Locale.getDefault())
    val date     = Date(booking.startsAt)

    BookingCard(
        booking           = booking,
        formattedMonth    = monthFmt.format(date),
        formattedDay      = dayFmt.format(date),
        formattedDateTime = "${timeFmt.format(date)} · ${booking.coachName}",
        onClick           = {}
    )
}

// ── Swipe to Cancel ───────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeToCancel(
    booking: Booking,
    onCancel: () -> Unit,
    content: @Composable () -> Unit
) {
    val canCancel = booking.status == BookingStatus.CONFIRMED ||
            booking.status == BookingStatus.PENDING

    if (!canCancel) {
        content()
        return
    }

    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onCancel()
                true
            } else false
        }
    )

    SwipeToDismissBox(
        state = state,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BeColor.dangerBg, RoundedCornerShape(BeRadius.lg))
                    .padding(horizontal = BeSp.xl),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = stringResource(R.string.bookings_swipe_cancel),
                    style = MaterialTheme.typography.labelLarge.copy(color = BeColor.dangerText)
                )
            }
        }
    ) {
        content()
    }
}
