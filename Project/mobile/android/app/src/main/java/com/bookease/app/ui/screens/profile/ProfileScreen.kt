package com.bookease.app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookease.app.data.mock.MockData
import com.bookease.app.ui.theme.*

@Composable
fun ProfileScreen(
    onMyBookingsClick: () -> Unit
) {
    val user = MockData.currentUser
    var notificationsOn by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BeColor.surface100),
        contentPadding = PaddingValues(bottom = BeSp.xxxl)
    ) {
        // Title
        item {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier
                    .padding(horizontal = BeSp.lg)
                    .padding(top = BeSp.sm, bottom = BeSp.lg)
            )
        }

        // Profile card
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = BeSp.lg)
                    .clip(RoundedCornerShape(BeRadius.xl))
                    .background(BeColor.surfaceWhite)
                    .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.xl))
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(BeColor.primarySoft),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.fullName.first().toString(),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = BeColor.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )
                }

                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = user.fullName,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp
                        )
                    )
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = BeColor.ink300,
                            fontSize = 13.sp
                        )
                    )
                    Text(
                        text = user.phone,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = BeColor.ink300,
                            fontSize = 13.sp
                        )
                    )
                }
            }
        }

        // Settings card
        item {
            Spacer(Modifier.height(BeSp.xl))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = BeSp.lg)
                    .clip(RoundedCornerShape(BeRadius.lg))
                    .background(BeColor.surfaceWhite)
                    .border(1.dp, BeColor.border, RoundedCornerShape(BeRadius.lg))
            ) {
                ProfileSettingsRow(
                    icon = Icons.Default.CalendarToday,
                    iconBg = BeColor.primarySoft,
                    iconTint = BeColor.primary,
                    title = "My bookings",
                    onClick = onMyBookingsClick
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = BeSp.base),
                    color = Color(0xFFF4F4F6)
                )
                ProfileSettingsRow(
                    icon = Icons.Default.CreditCard,
                    iconBg = BeColor.infoBg,
                    iconTint = BeColor.infoText,
                    title = "Payment methods",
                    onClick = {}
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = BeSp.base),
                    color = Color(0xFFF4F4F6)
                )
                NotificationsRow(
                    checked = notificationsOn,
                    onCheckedChange = { notificationsOn = it }
                )
            }
        }

        // Log out
        item {
            Spacer(Modifier.height(BeSp.xl))
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = BeSp.lg)
                    .height(52.dp),
                shape = RoundedCornerShape(BeRadius.lg),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = BeColor.dangerText
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, BeColor.border)
            ) {
                Text(
                    text = "Log out",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = BeColor.dangerText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
private fun ProfileSettingsRow(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 17.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(17.dp)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            ),
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color(0xFFC4C4CC),
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
private fun NotificationsRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 17.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(BeColor.warningBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = BeColor.warningText,
                modifier = Modifier.size(17.dp)
            )
        }
        Text(
            text = "Notifications",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            ),
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = BeColor.primary
            )
        )
    }
}
