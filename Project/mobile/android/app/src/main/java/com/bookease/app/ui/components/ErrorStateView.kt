package com.bookease.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookease.app.R
import com.bookease.app.ui.theme.BeColor
import com.bookease.app.ui.theme.BookEaseTheme

// ── ErrorStateView ─────────────────────────────────────────────────────────────

@Composable
fun ErrorStateView(
    title: String = "Something went wrong",
    message: String = "We hit a snag. Please try again in a moment.",
    errorCode: String? = null,
    onRetry: (() -> Unit)? = null,
    onContactSupport: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val tryAgainLabel = stringResource(R.string.errors_try_again)
    val contactSupportLabel = stringResource(R.string.errors_contact_support)
    val errorCodePrefix = stringResource(R.string.errors_error_code_prefix)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = title },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 84dp danger circle with warning icon
        Box(
            modifier = Modifier
                .size(84.dp)
                .clip(CircleShape)
                .background(BeColor.dangerBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = BeColor.dangerText,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = BeColor.ink900
            ),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                color = BeColor.ink500
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(Modifier.height(28.dp))

        if (onRetry != null) {
            PrimaryButton(
                text = tryAgainLabel,
                onClick = onRetry
            )
        }

        if (onContactSupport != null) {
            Spacer(Modifier.height(12.dp))
            TextButton(onClick = onContactSupport) {
                Text(
                    text = contactSupportLabel,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = BeColor.primary,
                        fontSize = 14.sp
                    )
                )
            }
        }

        if (errorCode != null) {
            Spacer(Modifier.height(20.dp))
            Text(
                text = "$errorCodePrefix$errorCode",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 12.sp,
                    color = BeColor.ink300
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

// ── Previews ───────────────────────────────────────────────────────────────────

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ErrorStateViewFullPreview() {
    BookEaseTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ErrorStateView(
                title = "Something went wrong",
                message = "We hit a snag. Please try again in a moment.",
                errorCode = "ERR_500",
                onRetry = {},
                onContactSupport = {}
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ErrorStateViewMinimalPreview() {
    BookEaseTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ErrorStateView(
                title = "Network error",
                message = "Check your connection and try again.",
                onRetry = {}
            )
        }
    }
}
