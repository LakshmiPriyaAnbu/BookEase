package com.bookease.app.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookease.app.data.models.TimeSlot
import com.bookease.app.ui.components.PrimaryButton
import com.bookease.app.ui.components.ServiceThumbnail
import com.bookease.app.ui.theme.*

@Composable
fun ServiceDetailScreen(
    vm: ServiceDetailViewModel,
    onBack: () -> Unit,
    onBookNow: () -> Unit
) {
    val service = vm.service

    Box(Modifier.fillMaxSize().background(BeColor.surfaceWhite)) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Hero image
            item {
                Box {
                    ServiceThumbnail(
                        size = 1000,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                    // Back button
                    Box(
                        modifier = Modifier
                            .padding(start = BeSp.base, top = 54.dp)
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.92f))
                            .clickable { onBack() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = BeColor.ink700,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Title & price
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = BeSp.lg)
                        .padding(top = BeSp.lg),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = service.name,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(BeSp.md))
                    Text(
                        text = service.formattedPrice,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = BeColor.primary,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
            }

            // Meta row
            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = BeSp.lg, vertical = BeSp.md),
                    horizontalArrangement = Arrangement.spacedBy(BeSp.base),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⏱ ${service.formattedDuration}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF545A66),
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = "★ ${String.format("%.1f", service.rating)}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF545A66),
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = service.coachName,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF545A66),
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            // Description
            item {
                Text(
                    text = service.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF545A66),
                        lineHeight = 22.sp
                    ),
                    modifier = Modifier
                        .padding(horizontal = BeSp.lg)
                        .padding(bottom = BeSp.lg)
                )
            }

            // What's included
            if (service.included.isNotEmpty()) {
                item {
                    Text(
                        text = "What's included",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier
                            .padding(horizontal = BeSp.lg)
                            .padding(bottom = BeSp.md)
                    )
                    Column(
                        modifier = Modifier
                            .padding(horizontal = BeSp.lg)
                            .padding(bottom = BeSp.lg),
                        verticalArrangement = Arrangement.spacedBy(11.dp)
                    ) {
                        service.included.forEach { item ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(BeColor.successBg),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = BeColor.successText,
                                        modifier = Modifier.size(11.dp)
                                    )
                                }
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                                )
                            }
                        }
                    }
                }
            }

            // Available today
            item {
                Text(
                    text = "Available today",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier
                        .padding(horizontal = BeSp.lg)
                        .padding(bottom = BeSp.md)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = BeSp.lg),
                    horizontalArrangement = Arrangement.spacedBy(9.dp),
                    modifier = Modifier.padding(bottom = BeSp.lg)
                ) {
                    items(vm.timeSlots.take(4)) { slot ->
                        val isSelected = vm.selectedSlot?.id == slot.id
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) BeColor.primarySoft else BeColor.surfaceWhite)
                                .border(
                                    width = if (isSelected) 1.5.dp else 1.dp,
                                    color = if (isSelected) BeColor.primary else BeColor.border,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable(enabled = slot.isAvailable) { vm.selectSlot(slot) }
                                .padding(horizontal = 15.dp, vertical = 9.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = slot.label,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = if (isSelected) BeColor.primaryDark else BeColor.ink700,
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }

        // Bottom action bar
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(BeColor.surfaceWhite)
                .border(
                    width = 1.dp,
                    color = BeColor.border,
                    shape = RoundedCornerShape(topStart = BeRadius.xxl, topEnd = BeRadius.xxl)
                )
                .padding(horizontal = BeSp.lg, vertical = BeSp.md)
                .padding(bottom = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(BeSp.lg)
            ) {
                Column {
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = BeColor.ink300,
                            fontSize = 11.5.sp
                        )
                    )
                    Text(
                        text = service.formattedPrice,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
                PrimaryButton(
                    text = "Book Now",
                    onClick = onBookNow,
                    enabled = vm.canProceed,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
