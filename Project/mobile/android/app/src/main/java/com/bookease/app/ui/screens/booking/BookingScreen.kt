package com.bookease.app.ui.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookease.app.R
import com.bookease.app.data.models.Booking
import com.bookease.app.data.models.DatePill
import com.bookease.app.data.models.TimeSlot
import com.bookease.app.ui.components.PrimaryButton
import com.bookease.app.ui.theme.*

@Composable
fun BookingScreen(
    vm: BookingViewModel,
    onBack: () -> Unit,
    onConfirmed: (Booking) -> Unit
) {
    val service = vm.service

    Box(
        Modifier
            .fillMaxSize()
            .background(BeColor.surface100)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Title
            item {
                Text(
                    text = stringResource(R.string.booking_title),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier
                        .padding(horizontal = BeSp.lg)
                        .padding(top = BeSp.sm, bottom = BeSp.lg)
                )
            }

            // Date selector
            item {
                SectionLabel(stringResource(R.string.booking_select_date))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = BeSp.lg)
                        .padding(bottom = BeSp.xl),
                    horizontalArrangement = Arrangement.spacedBy(BeSp.sm)
                ) {
                    vm.datePills.forEachIndexed { index, pill ->
                        BookingDatePillDesign(
                            pill = pill,
                            selected = index == vm.selectedDateIndex,
                            onClick = { vm.selectDate(index) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Time slots
            item {
                SectionLabel(stringResource(R.string.booking_select_time))
                TimeSlotGridDesign(
                    slots = vm.timeSlots,
                    selectedSlot = vm.selectedSlot,
                    onSlotSelect = vm::selectSlot,
                    modifier = Modifier
                        .padding(horizontal = BeSp.lg)
                        .padding(bottom = BeSp.xl)
                )
            }

            // Your details
            item {
                SectionLabel(stringResource(R.string.booking_your_details))
                Column(
                    modifier = Modifier
                        .padding(horizontal = BeSp.lg)
                        .padding(bottom = BeSp.lg),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Name field
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .clip(RoundedCornerShape(BeRadius.md))
                            .background(BeColor.surfaceWhite)
                            .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.md))
                            .padding(horizontal = BeSp.md),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val fullNamePlaceholder = stringResource(R.string.booking_placeholder_full_name)
                        androidx.compose.foundation.text.BasicTextField(
                            value = vm.fullName,
                            onValueChange = { vm.fullName = it },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = BeColor.ink700,
                                fontSize = 14.5.sp
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            decorationBox = { inner ->
                                Box {
                                    if (vm.fullName.isEmpty()) {
                                        Text(
                                            text = fullNamePlaceholder,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = BeColor.ink300,
                                                fontSize = 14.5.sp
                                            )
                                        )
                                    }
                                    inner()
                                }
                            }
                        )
                    }

                    // Phone field
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .clip(RoundedCornerShape(BeRadius.md))
                            .background(BeColor.surfaceWhite)
                            .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.md))
                            .padding(horizontal = BeSp.md),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val phonePlaceholder = stringResource(R.string.booking_placeholder_phone)
                        androidx.compose.foundation.text.BasicTextField(
                            value = vm.phone,
                            onValueChange = { vm.phone = it },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = BeColor.ink700,
                                fontSize = 14.5.sp
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            decorationBox = { inner ->
                                Box {
                                    if (vm.phone.isEmpty()) {
                                        Text(
                                            text = phonePlaceholder,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = BeColor.ink300,
                                                fontSize = 14.5.sp
                                            )
                                        )
                                    }
                                    inner()
                                }
                            }
                        )
                    }

                    // Notes field
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 64.dp)
                            .clip(RoundedCornerShape(BeRadius.md))
                            .background(BeColor.surfaceWhite)
                            .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.md))
                            .padding(horizontal = BeSp.md, vertical = 12.dp)
                    ) {
                        val notesPlaceholder = stringResource(R.string.booking_placeholder_notes)
                        androidx.compose.foundation.text.BasicTextField(
                            value = vm.notes,
                            onValueChange = { vm.notes = it },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                color = BeColor.ink700,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            decorationBox = { inner ->
                                Box {
                                    if (vm.notes.isEmpty()) {
                                        Text(
                                            text = notesPlaceholder,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = BeColor.ink300,
                                                fontSize = 14.sp
                                            )
                                        )
                                    }
                                    inner()
                                }
                            }
                        )
                    }
                }
            }
        }

        // Bottom bar
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(BeColor.surfaceWhite)
                .border(
                    1.dp, BeColor.border,
                    RoundedCornerShape(topStart = BeRadius.xxl, topEnd = BeRadius.xxl)
                )
                .padding(horizontal = BeSp.lg, vertical = BeSp.md)
                .padding(bottom = 16.dp)
        ) {
            PrimaryButton(
                text = "Confirm booking · ${service.formattedPrice}",
                onClick = {
                    val booking = vm.buildConfirmedBooking()
                    onConfirmed(booking)
                },
                enabled = vm.isValid,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp
        ),
        modifier = Modifier
            .padding(horizontal = BeSp.lg)
            .padding(bottom = BeSp.md)
    )
}

@Composable
private fun BookingDatePillDesign(
    pill: DatePill,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = if (selected)
        Brush.verticalGradient(listOf(BeColor.primary, BeColor.primaryDark))
    else
        Brush.verticalGradient(listOf(BeColor.surfaceWhite, BeColor.surfaceWhite))

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(13.dp))
            .background(bg)
            .border(
                width = if (selected) 0.dp else 1.dp,
                color = if (selected) Color.Transparent else BeColor.border,
                shape = RoundedCornerShape(13.dp)
            )
            .then(
                if (selected) Modifier.shadow(elevation = 6.dp, shape = RoundedCornerShape(13.dp), ambientColor = BeColor.primary.copy(alpha = 0.3f), spotColor = BeColor.primary.copy(alpha = 0.3f))
                else Modifier
            )
            .clickable { onClick() }
            .padding(vertical = 11.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = pill.weekdayShort,
            style = MaterialTheme.typography.labelSmall.copy(
                color = if (selected) Color(0xFFD9D3FA) else BeColor.ink300,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = pill.dayNumber.toString(),
            style = MaterialTheme.typography.labelLarge.copy(
                color = if (selected) Color.White else BeColor.ink700,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold
            )
        )
    }
}

@Composable
private fun TimeSlotGridDesign(
    slots: List<TimeSlot>,
    selectedSlot: TimeSlot?,
    onSlotSelect: (TimeSlot) -> Unit,
    modifier: Modifier = Modifier
) {
    val rows = slots.chunked(3)
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                row.forEach { slot ->
                    val isSelected = slot.id == selectedSlot?.id
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                            .clip(RoundedCornerShape(11.dp))
                            .background(
                                when {
                                    isSelected          -> BeColor.primarySoft
                                    !slot.isAvailable   -> BeColor.surface200
                                    else                -> BeColor.surfaceWhite
                                }
                            )
                            .border(
                                width = if (isSelected) 1.5.dp else 1.dp,
                                color = if (isSelected) BeColor.primary else BeColor.border,
                                shape = RoundedCornerShape(11.dp)
                            )
                            .clickable(enabled = slot.isAvailable) { onSlotSelect(slot) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = slot.label,
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = when {
                                    isSelected        -> BeColor.primaryDark
                                    !slot.isAvailable -> BeColor.ink300
                                    else              -> BeColor.ink700
                                },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}
