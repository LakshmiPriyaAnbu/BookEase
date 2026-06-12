package com.bookease.app.ui.screens.services

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bookease.app.data.models.Service
import com.bookease.app.ui.theme.*

@Composable
fun ServicesMarketingScreen(
    onBookService: (Service) -> Unit = {},
    vm: ServicesMarketingViewModel = viewModel()
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
                text  = "Services",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize   = 26.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text  = "24 services across 6 categories",
                style = MaterialTheme.typography.bodySmall.copy(
                    color      = BeColor.ink300,
                    fontSize   = 13.5.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )
        }

        // ── Search bar ───────────────────────────────────────────────────────
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(BeColor.surfaceWhite)
                    .border(1.dp, BeColor.border, RoundedCornerShape(13.dp))
                    .padding(horizontal = BeSp.md),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(BeSp.sm)
            ) {
                Icon(
                    imageVector        = Icons.Default.Search,
                    contentDescription = "Search",
                    tint               = BeColor.ink300,
                    modifier           = Modifier.size(18.dp)
                )
                Text(
                    text  = "Search services or coaches…",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color    = BeColor.ink300,
                        fontSize = 14.sp
                    )
                )
            }
            Spacer(Modifier.height(BeSp.xl))
        }

        // ── Browse by category header ────────────────────────────────────────
        item {
            Text(
                text  = "Browse by category",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        // ── Category 2-column grid ───────────────────────────────────────────
        val catRows = vm.categories.chunked(2)
        items(catRows) { row ->
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { cat ->
                    CategoryCard(
                        category = cat,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.height(12.dp))
        }

        item { Spacer(Modifier.height(BeSp.sm)) }

        // ── Featured header ──────────────────────────────────────────────────
        item {
            Text(
                text  = "Featured",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        // ── Featured service rows ────────────────────────────────────────────
        items(vm.featuredServices) { service ->
            FeaturedServiceRow(
                service   = service,
                onBookClick = { onBookService(service) }
            )
            Spacer(Modifier.height(12.dp))
        }

        item { Spacer(Modifier.height(BeSp.xl)) }
    }
}

// ── Category Card ─────────────────────────────────────────────────────────────

@Composable
private fun CategoryCard(
    category: ServiceCategory,
    modifier: Modifier = Modifier
) {
    val (iconBg, iconTint, icon) = categoryStyle(category.name)

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(BeColor.surfaceWhite)
            .border(1.dp, BeColor.border, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = icon,
                contentDescription = null,
                tint               = iconTint,
                modifier           = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text  = category.name,
            style = MaterialTheme.typography.labelLarge.copy(
                color      = BeColor.ink700,
                fontSize   = 15.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text  = "${category.count} services",
            style = MaterialTheme.typography.labelMedium.copy(
                color      = BeColor.ink300,
                fontSize   = 12.5.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

private data class CategoryStyle(
    val iconBg: Color,
    val iconTint: Color,
    val icon: ImageVector
)

private fun categoryStyle(name: String): CategoryStyle = when (name) {
    "Strength"  -> CategoryStyle(BeColor.primarySoft,  BeColor.primary,      Icons.Default.FitnessCenter)
    "HIIT"      -> CategoryStyle(BeColor.successBg,    BeColor.successText,  Icons.Default.Bolt)
    "Mobility"  -> CategoryStyle(BeColor.infoBg,       BeColor.infoText,     Icons.Default.Accessibility)
    "Nutrition" -> CategoryStyle(BeColor.warningBg,    BeColor.warningText,  Icons.Default.LocalFireDepartment)
    else        -> CategoryStyle(BeColor.surface200,   BeColor.ink300,       Icons.Default.FitnessCenter)
}

// ── Featured Service Row ───────────────────────────────────────────────────────

@Composable
private fun FeaturedServiceRow(
    service: Service,
    onBookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BeColor.surfaceWhite)
            .border(1.dp, BeColor.border, RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        // Placeholder image
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(BeColor.surface200),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.Default.FitnessCenter,
                contentDescription = null,
                tint               = BeColor.ink300,
                modifier           = Modifier.size(28.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text  = service.name,
                style = MaterialTheme.typography.labelLarge.copy(
                    color      = BeColor.ink700,
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1
            )
            Text(
                text  = "${service.formattedDuration} · ${service.coachName}",
                style = MaterialTheme.typography.labelMedium.copy(
                    color      = BeColor.ink300,
                    fontSize   = 12.5.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(top = 3.dp, bottom = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(BeSp.md)
            ) {
                Text(
                    text  = service.formattedPrice,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color      = BeColor.primary,
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(9.dp))
                        .background(BeColor.primarySoft)
                        .clickable(
                            onClick = onBookClick
                        )
                        .semantics { contentDescription = "Book ${service.name}" }
                        .padding(horizontal = 14.dp, vertical = 7.dp)
                ) {
                    Text(
                        text  = "Book",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color      = BeColor.primaryDark,
                            fontSize   = 12.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F8)
@Composable
private fun ServicesMarketingScreenPreview() {
    com.bookease.app.ui.theme.BookEaseTheme {
        ServicesMarketingScreen()
    }
}
