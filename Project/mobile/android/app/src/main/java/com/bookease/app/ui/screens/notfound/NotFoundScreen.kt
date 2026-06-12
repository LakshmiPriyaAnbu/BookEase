package com.bookease.app.ui.screens.notfound

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookease.app.R
import com.bookease.app.ui.components.PrimaryButton
import com.bookease.app.ui.theme.BeColor
import com.bookease.app.ui.theme.BeRadius
import com.bookease.app.ui.theme.BookEaseTheme

// ── NotFoundScreen ─────────────────────────────────────────────────────────────

@Composable
fun NotFoundScreen(
    onBackToHome: () -> Unit,
    onBrowseServices: () -> Unit
) {
    val notFoundContentDesc = stringResource(R.string.not_found_404_content_desc)
    val backContentDesc = stringResource(R.string.not_found_back_content_desc)
    val browseContentDesc = stringResource(R.string.not_found_browse_content_desc)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BeColor.surfaceWhite)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Spacer(Modifier.height(24.dp))

        // ── Logo row ──────────────────────────────────────────────────────────
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(
                        Brush.linearGradient(listOf(BeColor.primary, BeColor.primaryDark))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint               = Color.White,
                    modifier           = Modifier.size(20.dp)
                )
            }
            Text(
                text  = stringResource(R.string.common_app_name),
                style = MaterialTheme.typography.headlineSmall.copy(
                    color      = BeColor.ink900,
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // ── Push centre content down with a flexible spacer ───────────────────
        Spacer(Modifier.weight(1f))

        // ── Centre column ─────────────────────────────────────────────────────
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 404 gradient text
            Text(
                text = "404",
                style = TextStyle(
                    brush = Brush.linearGradient(
                        listOf(Color(0xFF6B5BF0), Color(0xFF4A3BD0))
                    ),
                    fontSize   = 96.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.semantics { contentDescription = notFoundContentDesc }
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.not_found_title),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize   = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color      = BeColor.ink900
                ),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(14.dp))

            Text(
                text = stringResource(R.string.not_found_message),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.5.sp,
                    color    = BeColor.ink300
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(max = 300.dp)
            )

            Spacer(Modifier.height(16.dp))

            PrimaryButton(
                text     = stringResource(R.string.common_back_to_home),
                onClick  = onBackToHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = backContentDesc }
            )

            Spacer(Modifier.height(12.dp))

            // Outlined "Browse services" button
            OutlinedButton(
                onClick  = onBrowseServices,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .semantics { contentDescription = browseContentDesc },
                shape    = RoundedCornerShape(BeRadius.lg),
                border   = BorderStroke(1.dp, BeColor.border),
                colors   = ButtonDefaults.outlinedButtonColors(
                    containerColor = BeColor.surfaceWhite,
                    contentColor   = BeColor.ink700
                )
            ) {
                Text(
                    text  = stringResource(R.string.common_browse_services),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = BeColor.ink700
                    )
                )
            }
        }

        Spacer(Modifier.weight(1f))
        Spacer(Modifier.height(32.dp))
    }
}

// ── Preview ────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NotFoundScreenPreview() {
    BookEaseTheme {
        NotFoundScreen(
            onBackToHome     = {},
            onBrowseServices = {}
        )
    }
}
