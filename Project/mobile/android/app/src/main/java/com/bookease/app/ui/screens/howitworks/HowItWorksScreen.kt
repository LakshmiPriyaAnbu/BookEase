package com.bookease.app.ui.screens.howitworks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookease.app.R
import com.bookease.app.ui.theme.*

private data class Step(
    val number: Int,
    val title: String,
    val description: String
)

private data class Guarantee(
    val icon: ImageVector,
    val label: String
)

@Composable
fun HowItWorksScreen(
    onGetStarted: () -> Unit = {}
) {
    val steps = listOf(
        Step(
            number      = 1,
            title       = stringResource(R.string.how_it_works_step1_title),
            description = stringResource(R.string.how_it_works_step1_desc)
        ),
        Step(
            number      = 2,
            title       = stringResource(R.string.how_it_works_step2_title),
            description = stringResource(R.string.how_it_works_step2_desc)
        ),
        Step(
            number      = 3,
            title       = stringResource(R.string.how_it_works_step3_title),
            description = stringResource(R.string.how_it_works_step3_desc)
        )
    )

    val guarantees = listOf(
        Guarantee(Icons.Default.CheckCircle, stringResource(R.string.how_it_works_guarantee_instant_confirmation)),
        Guarantee(Icons.Default.Refresh,     stringResource(R.string.how_it_works_guarantee_free_rescheduling)),
        Guarantee(Icons.Default.Lock,        stringResource(R.string.how_it_works_guarantee_secure_payments))
    )

    Scaffold(
        containerColor = BeColor.surface100,
        bottomBar = {
            BottomBar(onGetStarted = onGetStarted)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                horizontal = BeSp.lg,
                vertical   = BeSp.lg
            )
        ) {
            // ── Eyebrow ──────────────────────────────────────────────────────
            item {
                Text(
                    text  = stringResource(R.string.how_it_works_eyebrow),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color         = BeColor.primary,
                        fontSize      = 12.sp,
                        fontWeight    = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // ── Title ────────────────────────────────────────────────────────
            item {
                Text(
                    text  = stringResource(R.string.how_it_works_title),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize    = 26.sp,
                        fontWeight  = FontWeight.ExtraBold,
                        lineHeight  = (26 * 1.1).sp
                    ),
                    modifier = Modifier.padding(top = 6.dp)
                )
            }

            // ── Subtitle ─────────────────────────────────────────────────────
            item {
                Text(
                    text  = stringResource(R.string.how_it_works_subtitle),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color      = BeColor.ink300,
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = (14 * 1.55).sp
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 22.dp)
                )
            }

            // ── Step cards ───────────────────────────────────────────────────
            item {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    steps.forEach { step ->
                        StepCard(step = step)
                    }
                }
            }

            item { Spacer(Modifier.height(BeSp.xl)) }

            // ── Guarantees row ───────────────────────────────────────────────
            item {
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    guarantees.forEach { guarantee ->
                        GuaranteeItem(
                            guarantee = guarantee,
                            modifier  = Modifier.weight(1f)
                        )
                    }
                }
            }

            item { Spacer(Modifier.height(BeSp.xl)) }
        }
    }
}

// ── Step Card ─────────────────────────────────────────────────────────────────

@Composable
private fun StepCard(step: Step) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BeRadius.xl))
            .background(BeColor.surfaceWhite)
            .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.xl))
            .padding(22.dp)
    ) {
        // Number badge + title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .shadow(
                        elevation    = 6.dp,
                        shape        = RoundedCornerShape(13.dp),
                        ambientColor = BeColor.primary.copy(alpha = 0.25f),
                        spotColor    = BeColor.primary.copy(alpha = 0.25f)
                    )
                    .clip(RoundedCornerShape(13.dp))
                    .background(BeColor.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text  = step.number.toString(),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color      = Color.White,
                        fontSize   = 19.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            Text(
                text  = step.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color      = BeColor.ink700,
                    fontSize   = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }

        // Description
        Text(
            text  = step.description,
            style = MaterialTheme.typography.bodyMedium.copy(
                color      = Color(0xFF545A66),
                fontSize   = 14.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = (14 * 1.6).sp
            )
        )
    }
}

// ── Guarantee Item ────────────────────────────────────────────────────────────

@Composable
private fun GuaranteeItem(
    guarantee: Guarantee,
    modifier: Modifier = Modifier
) {
    Column(
        modifier              = modifier,
        horizontalAlignment   = Alignment.CenterHorizontally,
        verticalArrangement   = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector        = guarantee.icon,
            contentDescription = null,
            tint               = BeColor.primary,
            modifier           = Modifier.size(22.dp)
        )
        Text(
            text      = guarantee.label,
            style     = MaterialTheme.typography.labelMedium.copy(
                color      = BeColor.ink700,
                fontSize   = 12.sp,
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center
        )
    }
}

// ── Bottom Bar ────────────────────────────────────────────────────────────────

@Composable
private fun BottomBar(onGetStarted: () -> Unit) {
    val getStartedContentDesc = stringResource(R.string.how_it_works_get_started_content_desc)
    val getStartedLabel = stringResource(R.string.common_get_started)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BeColor.surfaceWhite)
            .border(
                width  = 1.dp,
                color  = BeColor.border,
                shape  = RoundedCornerShape(topStart = BeRadius.xxl, topEnd = BeRadius.xxl)
            )
            .padding(horizontal = BeSp.lg, vertical = 14.dp)
            .padding(bottom = 8.dp)
    ) {
        Button(
            onClick  = onGetStarted,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .shadow(
                    elevation    = 6.dp,
                    shape        = RoundedCornerShape(14.dp),
                    ambientColor = BeColor.primary.copy(alpha = 0.3f),
                    spotColor    = BeColor.primary.copy(alpha = 0.3f)
                )
                .semantics { contentDescription = getStartedContentDesc },
            shape  = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BeColor.primary,
                contentColor   = Color.White
            )
        ) {
            Text(
                text  = getStartedLabel,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize   = 15.5.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F8)
@Composable
private fun HowItWorksScreenPreview() {
    com.bookease.app.ui.theme.BookEaseTheme {
        HowItWorksScreen()
    }
}
