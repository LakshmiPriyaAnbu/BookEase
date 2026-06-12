package com.bookease.app.ui.screens.confirmation

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookease.app.data.models.Booking
import com.bookease.app.ui.components.PrimaryButton
import com.bookease.app.ui.components.SecondaryButton
import com.bookease.app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ConfirmationScreen(
    booking: Booking,
    onBackToHome: () -> Unit
) {
    var started by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (started) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "checkmark_scale"
    )
    LaunchedEffect(Unit) { started = true }

    val dateFormatter = SimpleDateFormat("MMM d · h:mm a", Locale.getDefault())
    val date = Date(booking.startsAt)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BeColor.surface100)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = BeSp.lg)
                .padding(bottom = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(BeSp.xl))

            // Success icon
            Box(
                modifier = Modifier
                    .scale(scale)
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(BeColor.successBg),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(BeColor.successText),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            Text(
                text = "Booking confirmed",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Your session is booked. We've sent the details to your email.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = BeColor.ink300,
                    lineHeight = 22.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(BeSp.xl))

            // Detail card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(BeRadius.xl))
                    .background(BeColor.surfaceWhite)
                    .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.xl))
                    .padding(horizontal = 22.dp, vertical = BeSp.lg)
            ) {
                // Booking ID row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = BeSp.md),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Booking ID",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = BeColor.ink300,
                            fontSize = 12.5.sp
                        )
                    )
                    Text(
                        text = "#${booking.ref}",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = BeColor.primary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }

                // Dashed divider
                androidx.compose.foundation.Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(bottom = BeSp.md)
                ) {
                    drawLine(
                        color = Color(0xFFE2E3E9),
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                        strokeWidth = 2f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(18f, 12f), 0f)
                    )
                }

                Spacer(Modifier.height(BeSp.md))

                ConfirmDetailRow(label = "Service",      value = booking.serviceName)
                ConfirmDetailRow(label = "Date & time",  value = dateFormatter.format(date))
                ConfirmDetailRow(label = "Coach",        value = booking.coachName)
            }
        }

        // Bottom buttons
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
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PrimaryButton(
                text = "Add to Calendar",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
            SecondaryButton(
                text = "Back to Home",
                onClick = onBackToHome,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ConfirmDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = BeColor.ink300,
                fontSize = 14.sp
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = BeColor.ink700,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
