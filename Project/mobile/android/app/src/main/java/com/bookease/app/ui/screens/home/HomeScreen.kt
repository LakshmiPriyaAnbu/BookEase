package com.bookease.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bookease.app.R
import com.bookease.app.data.models.BookCategory
import com.bookease.app.data.models.Booking
import com.bookease.app.data.models.Service
import com.bookease.app.ui.components.ServicePopularCard
import com.bookease.app.ui.theme.*

@Composable
fun HomeScreen(
    onServiceClick: (Service) -> Unit,
    onSeeAllServices: () -> Unit,
    onCategoryClick: (BookCategory) -> Unit,
    onNavigateTo: (String) -> Unit = {},
    vm: HomeViewModel = viewModel()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BeColor.surface100),
        contentPadding = PaddingValues(bottom = BeSp.xxxl)
    ) {
        // Header
        item {
            HomeHeader(
                fullName = vm.user.fullName,
                avatarInitial = vm.user.fullName.first().toString()
            )
        }

        // Search bar
        item {
            SearchBarField(
                modifier = Modifier
                    .padding(horizontal = BeSp.lg)
                    .padding(bottom = BeSp.xl)
            )
        }

        // Upcoming session
        vm.upcomingBooking?.let { booking ->
            item {
                UpcomingSessionCard(booking = booking)
                Spacer(Modifier.height(BeSp.xl))
            }
        }

        // Popular Services
        item {
            Row(
                modifier = Modifier
                    .padding(horizontal = BeSp.lg)
                    .padding(bottom = BeSp.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.home_popular_services),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(R.string.common_see_all),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = BeColor.primary,
                        fontSize = 13.5.sp
                    ),
                    modifier = Modifier.clickable { onSeeAllServices() }
                )
            }
        }

        // Popular Services 2-column grid
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = BeSp.lg)
                    .padding(bottom = BeSp.xl),
                horizontalArrangement = Arrangement.spacedBy(BeSp.md)
            ) {
                vm.popularServices.take(2).forEach { service ->
                    Box(Modifier.weight(1f)) {
                        ServicePopularCard(
                            service = service,
                            onClick = { onServiceClick(service) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        // Categories header
        item {
            Text(
                text = stringResource(R.string.home_categories),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier
                    .padding(horizontal = BeSp.lg)
                    .padding(bottom = BeSp.md)
            )
        }

        // Categories grid
        item {
            CategoryGrid(
                categories = vm.categories,
                onCategoryClick = onCategoryClick,
                modifier = Modifier.padding(horizontal = BeSp.lg)
            )
        }

        // More section
        item {
            Spacer(Modifier.height(BeSp.xl))
            Text(
                text  = stringResource(R.string.home_more),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize   = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier
                    .padding(horizontal = BeSp.lg)
                    .padding(bottom = BeSp.md)
            )
            MoreLinksCard(onNavigateTo = onNavigateTo)
            Spacer(Modifier.height(BeSp.lg))
        }
    }
}

// ── Header ────────────────────────────────────────────────────────────────────

@Composable
private fun HomeHeader(fullName: String, avatarInitial: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = BeSp.lg)
            .padding(top = BeSp.lg, bottom = BeSp.lg),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.home_greeting),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = BeColor.ink300,
                    fontSize = 14.sp
                )
            )
            Text(
                text = fullName,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 23.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
        // Bell
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(BeColor.surfaceWhite)
                .border(1.dp, BeColor.border, CircleShape)
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = stringResource(R.string.home_notifications_content_desc),
                tint = BeColor.ink700,
                modifier = Modifier.size(19.dp)
            )
        }
        Spacer(Modifier.width(BeSp.md))
        // Avatar
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(BeColor.primarySoft)
                .border(1.dp, BeColor.border, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = avatarInitial,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = BeColor.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        }
    }
}

// ── Search Bar ────────────────────────────────────────────────────────────────

@Composable
private fun SearchBarField(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(BeRadius.lg))
            .background(BeColor.surfaceWhite)
            .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.lg))
            .padding(horizontal = BeSp.base),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BeSp.sm)
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = BeColor.ink300,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = stringResource(R.string.home_search_placeholder),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = BeColor.ink300,
                fontSize = 15.sp
            )
        )
    }
}

// ── Upcoming Session Card ─────────────────────────────────────────────────────

@Composable
private fun UpcomingSessionCard(booking: Booking) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = BeSp.lg)
            .clip(RoundedCornerShape(BeRadius.xl))
            .background(
                Brush.linearGradient(
                    listOf(BeColor.primary, BeColor.primaryDark)
                )
            )
            .padding(BeSp.lg)
    ) {
        Column {
            // Top row: label + pill
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = BeSp.md),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.home_upcoming_session_label),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color(0xFFD9D3FA),
                        letterSpacing = 1.2.sp,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(BeRadius.pill))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(horizontal = 9.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.home_upcoming_session_eta),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Text(
                text = booking.serviceName,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = booking.formattedDateTime,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFD9D3FA),
                    fontSize = 13.5.sp
                )
            )
        }
    }
}

// ── Category Grid ─────────────────────────────────────────────────────────────

@Composable
private fun CategoryGrid(
    categories: List<BookCategory>,
    onCategoryClick: (BookCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val iconColors = listOf(BeColor.primary, BeColor.successText, BeColor.infoText, BeColor.warningText)
    val bgColors = listOf(BeColor.primarySoft, BeColor.successBg, BeColor.infoBg, BeColor.warningBg)

    val rows = categories.chunked(2)
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(BeSp.md)) {
        rows.forEachIndexed { rowIndex, row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(BeSp.md)
            ) {
                row.forEachIndexed { colIndex, cat ->
                    val idx = rowIndex * 2 + colIndex
                    val iconColor = iconColors[idx % iconColors.size]
                    val bgColor = bgColors[idx % bgColors.size]
                    CategoryTile(
                        category = cat,
                        iconColor = iconColor,
                        bgColor = bgColor,
                        onClick = { onCategoryClick(cat) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun CategoryTile(
    category: BookCategory,
    iconColor: Color,
    bgColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(BeRadius.lg))
            .background(BeColor.surfaceWhite)
            .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.lg))
            .clickable { onClick() }
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = categoryIcon(category.iconName),
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(18.dp)
            )
        }
        Text(
            text = category.name,
            style = MaterialTheme.typography.labelLarge.copy(
                color = BeColor.ink700,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

private fun categoryIcon(name: String) = when (name) {
    "FitnessCenter"   -> Icons.Default.FitnessCenter
    "Bolt"            -> Icons.Default.Bolt
    "SelfImprovement" -> Icons.Default.SelfImprovement
    "Restaurant"      -> Icons.Default.Restaurant
    else              -> Icons.Default.FitnessCenter
}

// ── More Links Card ───────────────────────────────────────────────────────────

@Composable
private fun MoreLinksCard(onNavigateTo: (String) -> Unit) {
    val howItWorksLabel = stringResource(R.string.home_how_it_works)
    val pricingLabel = stringResource(R.string.home_pricing)
    val contactLabel = stringResource(R.string.home_contact)
    val logInLabel = stringResource(R.string.home_log_in)

    val links = listOf(
        Triple(howItWorksLabel, Icons.Default.Info,       "how_it_works"),
        Triple(pricingLabel,    Icons.Default.Sell,       "pricing"),
        Triple(contactLabel,    Icons.Default.MailOutline, "contact"),
        Triple(logInLabel,      Icons.Default.Login,      "login")
    )

    Column(
        modifier = Modifier
            .padding(horizontal = BeSp.lg)
            .clip(RoundedCornerShape(BeRadius.xl))
            .background(BeColor.surfaceWhite)
            .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.xl))
    ) {
        links.forEachIndexed { index, (label, icon, route) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateTo(route) }
                    .padding(horizontal = BeSp.base, vertical = BeSp.md),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(BeSp.md)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(BeRadius.md))
                        .background(BeColor.primarySoft),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector        = icon,
                        contentDescription = null,
                        tint               = BeColor.primary,
                        modifier           = Modifier.size(17.dp)
                    )
                }
                Text(
                    text  = label,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector        = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint               = BeColor.ink300,
                    modifier           = Modifier.size(18.dp)
                )
            }
            if (index < links.lastIndex) {
                HorizontalDivider(
                    modifier  = Modifier.padding(horizontal = BeSp.base),
                    color     = BeColor.border,
                    thickness = 1.dp
                )
            }
        }
    }
}
