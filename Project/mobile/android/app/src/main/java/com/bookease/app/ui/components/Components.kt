package com.bookease.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookease.app.R
import com.bookease.app.data.models.*
import com.bookease.app.ui.theme.*

// ── Primary Button ─────────────────────────────────────────────────────────────

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(52.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(BeRadius.lg),
                ambientColor = BeColor.primary.copy(alpha = 0.3f),
                spotColor = BeColor.primary.copy(alpha = 0.3f)
            ),
        shape = RoundedCornerShape(BeRadius.lg),
        colors = ButtonDefaults.buttonColors(
            containerColor = BeColor.primary,
            contentColor = Color.White,
            disabledContainerColor = BeColor.surface300,
            disabledContentColor = BeColor.ink300
        ),
        contentPadding = PaddingValues(horizontal = BeSp.xl)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

// ── Secondary Button ───────────────────────────────────────────────────────────

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF2F2F5),
            contentColor = BeColor.ink700
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = BeColor.ink700
            )
        )
    }
}

// ── Destructive Button ─────────────────────────────────────────────────────────

@Composable
fun DestructiveButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(54.dp),
        shape = RoundedCornerShape(BeRadius.pill),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = BeColor.dangerText
        ),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, BeColor.dangerText.copy(alpha = 0.4f))
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 15.sp,
                color = BeColor.dangerText
            )
        )
    }
}

// ── Search Bar ─────────────────────────────────────────────────────────────────

@Composable
fun BeSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String = "Search services or coaches…",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp)
            .clip(RoundedCornerShape(BeRadius.pill))
            .background(BeColor.surface200),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(BeSp.md))
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = BeColor.ink300,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(BeSp.sm))
        BasicTextField(
            query = query,
            onQueryChange = onQueryChange,
            placeholder = placeholder
        )
    }
}

@Composable
private fun BasicTextField(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String
) {
    Box(Modifier.weight(1f)) {
        if (query.isEmpty()) {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium.copy(color = BeColor.ink300)
            )
        }
        androidx.compose.foundation.text.BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = BeColor.ink900),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// ── Filter Chip ────────────────────────────────────────────────────────────────

@Composable
fun BeFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        if (selected) BeColor.primary else BeColor.surfaceWhite, label = "chip_bg"
    )
    val textColor by animateColorAsState(
        if (selected) Color.White else Color(0xFF545A66), label = "chip_text"
    )
    val borderColor by animateColorAsState(
        if (selected) BeColor.primary else BeColor.border, label = "chip_border"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(BeRadius.pill))
            .background(bgColor)
            .border(if (selected) 0.dp else 1.dp, borderColor, RoundedCornerShape(BeRadius.pill))
            .clickable { onClick() }
            .padding(horizontal = 15.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(
                color = textColor,
                fontSize = 13.sp,
                fontWeight = if (selected) androidx.compose.ui.text.font.FontWeight.Bold
                    else androidx.compose.ui.text.font.FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun FilterChipBar(
    labels: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(BeSp.sm)
    ) {
        labels.forEachIndexed { i, label ->
            BeFilterChip(
                label = label,
                selected = i == selectedIndex,
                onClick = { onSelect(i) }
            )
        }
    }
}

// ── Status Badge ───────────────────────────────────────────────────────────────

@Composable
fun StatusBadge(status: BookingStatus) {
    val (bg, fg) = when (status) {
        BookingStatus.CONFIRMED  -> BeColor.successBg  to BeColor.successText
        BookingStatus.PENDING    -> BeColor.warningBg  to BeColor.warningText
        BookingStatus.COMPLETED  -> BeColor.infoBg     to BeColor.infoText
        BookingStatus.CANCELLED  -> BeColor.dangerBg   to BeColor.dangerText
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(BeRadius.pill))
            .background(bg)
            .padding(horizontal = BeSp.md, vertical = 4.dp)
    ) {
        Text(
            text = status.label,
            style = MaterialTheme.typography.labelMedium.copy(color = fg, fontSize = 12.sp)
        )
    }
}

// ── Date Chip ─────────────────────────────────────────────────────────────────

@Composable
fun DateChip(
    month: String,
    day: String,
    status: BookingStatus,
    modifier: Modifier = Modifier
) {
    val (bg, fg) = when (status) {
        BookingStatus.CONFIRMED  -> BeColor.primarySoft to BeColor.primary
        BookingStatus.PENDING    -> BeColor.warningBg   to BeColor.warningText
        BookingStatus.COMPLETED  -> BeColor.surface200  to BeColor.ink500
        BookingStatus.CANCELLED  -> BeColor.dangerBg    to BeColor.dangerText
    }
    Column(
        modifier = modifier
            .size(50.dp)
            .clip(RoundedCornerShape(BeRadius.md))
            .background(bg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = month.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(color = fg, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        )
        Text(
            text = day,
            style = MaterialTheme.typography.labelLarge.copy(color = fg, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        )
    }
}

// ── Service Thumbnail ─────────────────────────────────────────────────────────

@Composable
fun ServiceThumbnail(
    size: Int = 64,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(RoundedCornerShape(BeRadius.lg))
            .background(
                Brush.verticalGradient(
                    listOf(BeColor.primarySoft, BeColor.primary.copy(alpha = 0.6f))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.FitnessCenter,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size((size * 0.4f).dp)
        )
    }
}

// ── Service Row ───────────────────────────────────────────────────────────────

@Composable
fun ServiceRow(
    service: com.bookease.app.data.models.Service,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BeRadius.lg))
            .background(BeColor.surfaceWhite)
            .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.lg))
            .clickable { onClick() }
            .padding(BeSp.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BeSp.md)
    ) {
        ServiceThumbnail(size = 64)

        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(
                text = service.name,
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 15.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${service.formattedDuration} · ★ ${String.format("%.1f", service.rating)}",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = BeColor.ink300,
                    fontSize = 12.5.sp
                ),
                modifier = Modifier.padding(top = 3.dp, bottom = BeSp.sm)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = service.formattedPrice,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = BeColor.primary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(9.dp))
                        .background(BeColor.primarySoft)
                        .padding(horizontal = 14.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = stringResource(R.string.common_book),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = BeColor.primaryDark,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

// ── Booking Card ──────────────────────────────────────────────────────────────

@Composable
fun BookingCard(
    booking: Booking,
    formattedMonth: String,
    formattedDay: String,
    formattedDateTime: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BeRadius.xl))
            .background(BeColor.surfaceWhite)
            .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.xl))
            .clickable { onClick() }
            .padding(BeSp.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BeSp.md)
    ) {
        DateChip(
            month = formattedMonth,
            day = formattedDay,
            status = booking.status
        )

        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = booking.serviceName,
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 15.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = formattedDateTime,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = BeColor.ink300,
                    fontSize = 12.5.sp
                )
            )
        }

        StatusBadge(status = booking.status)
    }
}

// ── Settings Row ──────────────────────────────────────────────────────────────

@Composable
fun SettingsRow(
    icon: ImageVector,
    iconBg: Color,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = BeSp.base, vertical = BeSp.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BeSp.md)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(BeRadius.md))
                .background(iconBg.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconBg,
                modifier = Modifier.size(18.dp)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = BeColor.ink300,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun SettingsToggleRow(
    icon: ImageVector,
    iconBg: Color,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = BeSp.base, vertical = BeSp.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BeSp.md)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(BeRadius.md))
                .background(iconBg.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconBg,
                modifier = Modifier.size(18.dp)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = BeColor.primary
            )
        )
    }
}

// ── Time Slot Chip ─────────────────────────────────────────────────────────────

@Composable
fun TimeSlotChip(
    slot: TimeSlot,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = when {
        selected          -> BeColor.primary
        !slot.isAvailable -> BeColor.surface200
        else              -> BeColor.surfaceWhite
    }
    val textColor = when {
        selected          -> Color.White
        !slot.isAvailable -> BeColor.ink300
        else              -> BeColor.ink900
    }
    val borderColor = when {
        selected          -> BeColor.primary
        !slot.isAvailable -> BeColor.border
        else              -> BeColor.border
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(BeRadius.lg))
            .background(bg)
            .border(1.dp, borderColor, RoundedCornerShape(BeRadius.lg))
            .clickable(enabled = slot.isAvailable) { onSelect() }
            .padding(vertical = BeSp.sm),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = slot.label,
            style = MaterialTheme.typography.labelLarge.copy(
                color = textColor,
                fontSize = 13.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}

// ── Date Pill (booking flow) ───────────────────────────────────────────────────

@Composable
fun BookingDatePill(
    pill: com.bookease.app.data.models.DatePill,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = if (selected)
        Brush.verticalGradient(listOf(BeColor.primaryDark, BeColor.primary))
    else
        Brush.verticalGradient(listOf(BeColor.surfaceWhite, BeColor.surfaceWhite))

    Column(
        modifier = modifier
            .width(48.dp)
            .clip(RoundedCornerShape(BeRadius.xl))
            .background(bg)
            .border(
                width = if (selected) 0.dp else 1.dp,
                color = if (selected) Color.Transparent else BeColor.border,
                shape = RoundedCornerShape(BeRadius.xl)
            )
            .clickable { onClick() }
            .padding(vertical = BeSp.sm),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = pill.weekdayShort,
            style = MaterialTheme.typography.labelSmall.copy(
                color = if (selected) Color.White.copy(alpha = 0.75f) else BeColor.ink300,
                fontSize = 10.sp
            )
        )
        Text(
            text = pill.dayNumber.toString(),
            style = MaterialTheme.typography.labelLarge.copy(
                color = if (selected) Color.White else BeColor.ink900,
                fontSize = 15.sp
            )
        )
    }
}

// ── Service Popular Card ────────────────────────────────────────────────────────

@Composable
fun ServicePopularCard(
    service: com.bookease.app.data.models.Service,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(BeRadius.lg))
            .background(BeColor.surfaceWhite)
            .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.lg))
            .clickable { onClick() }
    ) {
        ServiceThumbnail(size = 160, modifier = Modifier.fillMaxWidth().height(84.dp))
        Column(
            modifier = Modifier.padding(horizontal = 13.dp, vertical = 11.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = service.name,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${service.formattedDuration} · ${service.formattedPrice}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = BeColor.ink300,
                    fontSize = 12.sp
                )
            )
        }
    }
}
