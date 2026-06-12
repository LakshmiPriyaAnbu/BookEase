package com.bookease.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookease.app.ui.theme.BeColor
import com.bookease.app.ui.theme.BeRadius
import com.bookease.app.ui.theme.BookEaseTheme

// ── Empty State Variant ────────────────────────────────────────────────────────

sealed class EmptyStateVariant {
    object Bookings : EmptyStateVariant()
    data class Generic(val icon: ImageVector) : EmptyStateVariant()
}

// ── EmptyStateView ─────────────────────────────────────────────────────────────

@Composable
fun EmptyStateView(
    variant: EmptyStateVariant,
    title: String,
    message: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = title },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon container
        val (containerBg, iconTint, iconVector) = when (variant) {
            is EmptyStateVariant.Bookings -> Triple(
                BeColor.primarySoft,
                BeColor.primary,
                Icons.Default.CalendarMonth
            )
            is EmptyStateVariant.Generic -> Triple(
                BeColor.surface200,
                BeColor.ink300,
                variant.icon
            )
        }

        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(containerBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = iconVector,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = BeColor.ink900
            ),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 15.5.sp,
                color = BeColor.ink500
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        if (actionLabel != null && onAction != null) {
            Spacer(Modifier.height(24.dp))
            PrimaryButton(
                text = actionLabel,
                onClick = onAction
            )
        }
    }
}

// ── Previews ───────────────────────────────────────────────────────────────────

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F8)
@Composable
private fun EmptyStateBookingsPreview() {
    BookEaseTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            EmptyStateView(
                variant = EmptyStateVariant.Bookings,
                title = "No upcoming bookings",
                message = "Book a session to get started",
                actionLabel = "Browse services",
                onAction = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F8)
@Composable
private fun EmptyStateGenericPreview() {
    BookEaseTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            EmptyStateView(
                variant = EmptyStateVariant.Generic(Icons.Default.CalendarMonth),
                title = "No completed sessions",
                message = "Your finished sessions will appear here"
            )
        }
    }
}
