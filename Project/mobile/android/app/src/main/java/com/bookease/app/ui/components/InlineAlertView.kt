package com.bookease.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookease.app.ui.theme.BookEaseTheme

// ── Alert Variant ──────────────────────────────────────────────────────────────

enum class AlertVariant { SUCCESS, ERROR, INFO, WARNING }

// ── InlineAlertView ────────────────────────────────────────────────────────────

@Composable
fun InlineAlertView(
    variant: AlertVariant,
    title: String,
    message: String? = null,
    modifier: Modifier = Modifier
) {
    val bg: Color
    val borderColor: Color
    val titleColor: Color
    val messageColor: Color
    val icon: ImageVector

    when (variant) {
        AlertVariant.SUCCESS -> {
            bg           = Color(0xFFE7F8EE)
            borderColor  = Color(0xFFBBE9CD)
            titleColor   = Color(0xFF15803D)
            messageColor = Color(0xFF166534)
            icon         = Icons.Default.CheckCircle
        }
        AlertVariant.ERROR -> {
            bg           = Color(0xFFFCE4E9)
            borderColor  = Color(0xFFF6C6D1)
            titleColor   = Color(0xFFBE123C)
            messageColor = Color(0xFF9F1239)
            icon         = Icons.Default.Info
        }
        AlertVariant.INFO -> {
            bg           = Color(0xFFEAEFFC)
            borderColor  = Color(0xFFC5D6F7)
            titleColor   = Color(0xFF1D4ED8)
            messageColor = Color(0xFF1E40AF)
            icon         = Icons.Default.Info
        }
        AlertVariant.WARNING -> {
            bg           = Color(0xFFFEF3D9)
            borderColor  = Color(0xFFF5DCA0)
            titleColor   = Color(0xFFB45309)
            messageColor = Color(0xFF92400E)
            icon         = Icons.Default.Warning
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 17.dp, vertical = 15.dp)
            .semantics { contentDescription = title },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = titleColor,
            modifier = Modifier.size(19.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = titleColor
                )
            )
            if (message != null) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 13.sp,
                        color = messageColor
                    )
                )
            }
        }
    }
}

// ── Previews ───────────────────────────────────────────────────────────────────

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun InlineAlertSuccessPreview() {
    BookEaseTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InlineAlertView(
                variant = AlertVariant.SUCCESS,
                title = "Booking confirmed!",
                message = "Your session has been scheduled for Tuesday at 10am."
            )
            InlineAlertView(
                variant = AlertVariant.ERROR,
                title = "Payment failed",
                message = "Your card was declined. Please try a different payment method."
            )
            InlineAlertView(
                variant = AlertVariant.INFO,
                title = "Reminder",
                message = "You have a session starting in 30 minutes."
            )
            InlineAlertView(
                variant = AlertVariant.WARNING,
                title = "Session almost full",
                message = "Only 2 spots remaining. Book now to secure your slot."
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun InlineAlertTitleOnlyPreview() {
    BookEaseTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InlineAlertView(variant = AlertVariant.SUCCESS, title = "Profile updated successfully.")
            InlineAlertView(variant = AlertVariant.ERROR, title = "Something went wrong.")
        }
    }
}
