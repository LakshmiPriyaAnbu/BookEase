package com.bookease.app.ui.screens.pricing

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bookease.app.ui.theme.*

@Composable
fun PricingScreen(
    vm: PricingViewModel = viewModel()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BeColor.surface100),
        contentPadding = PaddingValues(
            horizontal = BeSp.lg,
            vertical   = BeSp.lg
        )
    ) {
        // ── Title block ──────────────────────────────────────────────────────
        item {
            Text(
                text  = "Pricing",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize   = 26.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text  = "Start free or save with a plan.",
                style = MaterialTheme.typography.bodySmall.copy(
                    color      = BeColor.ink300,
                    fontSize   = 13.5.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
            )
        }

        // ── Billing toggle ───────────────────────────────────────────────────
        item {
            BillingToggle(
                billingCycle = vm.billingCycle,
                onToggle     = vm::toggleBillingCycle
            )
            Spacer(Modifier.height(BeSp.xl))
        }

        // ── Plan cards ───────────────────────────────────────────────────────
        item {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                // Starter
                StarterPlanCard()
                // Pro
                ProPlanCard(billingCycle = vm.billingCycle)
                // Elite
                ElitePlanCard(billingCycle = vm.billingCycle)
            }
        }

        item { Spacer(Modifier.height(BeSp.xxxl)) }
    }
}

// ── Billing Toggle ────────────────────────────────────────────────────────────

@Composable
private fun BillingToggle(
    billingCycle: BillingCycle,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BeColor.surface200)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        BillingTab(
            label    = "Monthly",
            selected = billingCycle == BillingCycle.MONTHLY,
            onClick  = { if (billingCycle != BillingCycle.MONTHLY) onToggle() },
            modifier = Modifier.weight(1f)
        )
        BillingTab(
            label    = "Yearly  (save 20%)",
            selected = billingCycle == BillingCycle.YEARLY,
            onClick  = { if (billingCycle != BillingCycle.YEARLY) onToggle() },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun BillingTab(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(9.dp))
            .then(
                if (selected) Modifier
                    .shadow(
                        elevation    = 2.dp,
                        shape        = RoundedCornerShape(9.dp),
                        ambientColor = Color.Black.copy(alpha = 0.08f),
                        spotColor    = Color.Black.copy(alpha = 0.08f)
                    )
                    .background(BeColor.surfaceWhite)
                else Modifier.background(Color.Transparent)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text  = label,
            style = MaterialTheme.typography.labelLarge.copy(
                color      = if (selected) BeColor.ink700 else BeColor.ink300,
                fontSize   = 13.5.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.SemiBold
            )
        )
    }
}

// ── Starter Plan ──────────────────────────────────────────────────────────────

@Composable
private fun StarterPlanCard() {
    PlanCard(
        name        = "Starter",
        nameColor   = BeColor.ink300,
        price       = "$0",
        billingNote = "Pay per session",
        features    = listOf(
            "Book any service",
            "Reminders & calendar sync",
            "In-app payments"
        ),
        borderColor = BeColor.border,
        borderWidth = 1.dp,
        button      = {
            Button(
                onClick  = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .semantics { contentDescription = "Get started with Starter plan" },
                shape    = RoundedCornerShape(12.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor = BeColor.surface200,
                    contentColor   = BeColor.ink700
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text(
                    text  = "Get started",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize   = 14.5.sp,
                        fontWeight = FontWeight.Bold,
                        color      = BeColor.ink700
                    )
                )
            }
        }
    )
}

// ── Pro Plan ──────────────────────────────────────────────────────────────────

@Composable
private fun ProPlanCard(billingCycle: BillingCycle) {
    val price = if (billingCycle == BillingCycle.MONTHLY) "$49/mo" else "$39/mo"

    Box {
        PlanCard(
            name        = "Pro",
            nameColor   = BeColor.primary,
            price       = price,
            billingNote = "8 sessions / month",
            features    = listOf(
                "Everything in Starter",
                "Priority slots",
                "Free rescheduling",
                "Progress tracking"
            ),
            borderColor = BeColor.primary,
            borderWidth = 2.dp,
            shadowColor = BeColor.primary.copy(alpha = 0.16f),
            button      = {
                Button(
                    onClick  = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .shadow(
                            elevation    = 6.dp,
                            shape        = RoundedCornerShape(12.dp),
                            ambientColor = BeColor.primary.copy(alpha = 0.3f),
                            spotColor    = BeColor.primary.copy(alpha = 0.3f)
                        )
                        .semantics { contentDescription = "Get started with Pro plan" },
                    shape    = RoundedCornerShape(12.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor = BeColor.primary,
                        contentColor   = Color.White
                    )
                ) {
                    Text(
                        text  = "Get started",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize   = 14.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        )

        // "MOST POPULAR" badge
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 22.dp, y = (-12).dp)
                .clip(RoundedCornerShape(BeRadius.pill))
                .background(BeColor.primary)
                .padding(horizontal = 11.dp, vertical = 5.dp)
        ) {
            Text(
                text  = "MOST POPULAR",
                style = MaterialTheme.typography.labelSmall.copy(
                    color      = Color.White,
                    fontSize   = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.4.sp
                )
            )
        }
    }
}

// ── Elite Plan ────────────────────────────────────────────────────────────────

@Composable
private fun ElitePlanCard(billingCycle: BillingCycle) {
    val price = if (billingCycle == BillingCycle.MONTHLY) "$99/mo" else "$79/mo"

    PlanCard(
        name        = "Elite",
        nameColor   = BeColor.ink300,
        price       = price,
        billingNote = "Unlimited sessions",
        features    = listOf(
            "Everything in Pro",
            "Dedicated coach",
            "Custom nutrition plan"
        ),
        borderColor = BeColor.border,
        borderWidth = 1.dp,
        button      = {
            Button(
                onClick  = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .semantics { contentDescription = "Get started with Elite plan" },
                shape    = RoundedCornerShape(12.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor = BeColor.surface200,
                    contentColor   = BeColor.ink700
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text(
                    text  = "Get started",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize   = 14.5.sp,
                        fontWeight = FontWeight.Bold,
                        color      = BeColor.ink700
                    )
                )
            }
        }
    )
}

// ── Generic Plan Card ─────────────────────────────────────────────────────────

@Composable
private fun PlanCard(
    name: String,
    nameColor: Color,
    price: String,
    billingNote: String,
    features: List<String>,
    borderColor: Color,
    borderWidth: androidx.compose.ui.unit.Dp,
    shadowColor: Color = Color.Transparent,
    button: @Composable () -> Unit
) {
    val cardModifier = if (shadowColor != Color.Transparent) {
        Modifier
            .fillMaxWidth()
            .shadow(
                elevation    = 14.dp,
                shape        = RoundedCornerShape(BeRadius.xl),
                ambientColor = shadowColor,
                spotColor    = shadowColor
            )
            .clip(RoundedCornerShape(BeRadius.xl))
            .background(BeColor.surfaceWhite)
            .border(borderWidth, borderColor, RoundedCornerShape(BeRadius.xl))
    } else {
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BeRadius.xl))
            .background(BeColor.surfaceWhite)
            .border(borderWidth, borderColor, RoundedCornerShape(BeRadius.xl))
    }

    Column(
        modifier = cardModifier.padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Plan name + price
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text  = name,
                style = MaterialTheme.typography.labelLarge.copy(
                    color      = nameColor,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text  = price,
                style = MaterialTheme.typography.displayMedium.copy(
                    color      = BeColor.ink900,
                    fontSize   = 34.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text  = billingNote,
                style = MaterialTheme.typography.labelMedium.copy(
                    color    = BeColor.ink300,
                    fontSize = 12.5.sp
                )
            )
        }

        // Feature list
        Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
            features.forEach { feature ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(BeSp.sm),
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector        = Icons.Default.Check,
                        contentDescription = null,
                        tint               = BeColor.successText,
                        modifier           = Modifier.size(16.dp)
                    )
                    Text(
                        text  = feature,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color      = Color(0xFF545A66),
                            fontSize   = 13.5.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }

        // CTA button
        button()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F8)
@Composable
private fun PricingScreenPreview() {
    com.bookease.app.ui.theme.BookEaseTheme {
        PricingScreen()
    }
}
