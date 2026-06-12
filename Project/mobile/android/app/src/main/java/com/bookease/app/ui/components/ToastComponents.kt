package com.bookease.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookease.app.R
import com.bookease.app.ui.theme.BeColor
import com.bookease.app.ui.theme.BookEaseTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// ── Toast Types ────────────────────────────────────────────────────────────────

enum class ToastType { SUCCESS, ERROR, INFO, WARNING }

data class ToastMessage(
    val id: Long = System.currentTimeMillis(),
    val message: String,
    val title: String? = null,
    val type: ToastType
)

// ── Toast State ────────────────────────────────────────────────────────────────

class ToastState {
    var toasts by mutableStateOf<List<ToastMessage>>(emptyList())
        private set

    fun showToast(message: String, type: ToastType, title: String? = null) {
        val toast = ToastMessage(
            id = System.currentTimeMillis(),
            message = message,
            title = title,
            type = type
        )
        toasts = toasts + toast
    }

    fun dismiss(id: Long) {
        toasts = toasts.filter { it.id != id }
    }
}

@Composable
fun rememberToastState(): ToastState {
    return remember { ToastState() }
}

// ── Default title per type ─────────────────────────────────────────────────────

@Composable
private fun defaultTitle(type: ToastType): String = when (type) {
    ToastType.SUCCESS -> stringResource(R.string.toast_title_success)
    ToastType.ERROR   -> stringResource(R.string.toast_title_error)
    ToastType.INFO    -> stringResource(R.string.toast_title_info)
    ToastType.WARNING -> stringResource(R.string.toast_title_warning)
}

// ── Toast accent colors ────────────────────────────────────────────────────────

private data class ToastAccent(
    val iconBg: Color,
    val accentBar: Color,
    val iconTint: Color
)

private fun toastAccent(type: ToastType) = when (type) {
    ToastType.SUCCESS -> ToastAccent(
        iconBg    = Color(0xFFE7F8EE),
        accentBar = BeColor.successText,
        iconTint  = BeColor.successText
    )
    ToastType.ERROR   -> ToastAccent(
        iconBg    = Color(0xFFFCE4E9),
        accentBar = BeColor.dangerText,
        iconTint  = BeColor.dangerText
    )
    ToastType.INFO    -> ToastAccent(
        iconBg    = Color(0xFFEAEFFC),
        accentBar = BeColor.infoText,
        iconTint  = BeColor.infoText
    )
    ToastType.WARNING -> ToastAccent(
        iconBg    = Color(0xFFFEF3D9),
        accentBar = BeColor.warningText,
        iconTint  = BeColor.warningText
    )
}

private fun toastIcon(type: ToastType): ImageVector = when (type) {
    ToastType.SUCCESS -> Icons.Rounded.Check
    ToastType.INFO    -> Icons.Rounded.Info
    ToastType.ERROR   -> Icons.Rounded.Close
    ToastType.WARNING -> Icons.Rounded.Info
}

// ── Single Toast Chip ──────────────────────────────────────────────────────────

@Composable
private fun ToastChip(
    toast: ToastMessage,
    onDismiss: () -> Unit
) {
    val accent = toastAccent(toast.type)
    val scope  = rememberCoroutineScope()
    val resolvedDefaultTitle = defaultTitle(toast.type)
    val displayTitle = toast.title ?: resolvedDefaultTitle
    val dismissContentDesc = stringResource(R.string.toast_dismiss_content_desc)

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(toast.id) {
        visible = true
        delay(3500)
        visible = false
        delay(300)
        onDismiss()
    }

    AnimatedVisibility(
        visible = visible,
        enter   = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
        exit    = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
    ) {
        Row(
            modifier = Modifier
                .widthIn(min = 280.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(BeColor.surfaceWhite)
                .border(BorderStroke(1.dp, BeColor.border), RoundedCornerShape(12.dp))
                .semantics { contentDescription = toast.message },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 4dp left accent bar
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(64.dp)
                    .background(
                        color = accent.accentBar,
                        shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                    )
            )

            Row(
                modifier = Modifier
                    .background(BeColor.surfaceWhite)
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 32dp icon circle
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(accent.iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector        = toastIcon(toast.type),
                        contentDescription = null,
                        tint               = accent.iconTint,
                        modifier           = Modifier.size(16.dp)
                    )
                }

                // Title + subtitle column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text  = displayTitle,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize   = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color      = BeColor.ink900
                        )
                    )
                    Text(
                        text  = toast.message,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 13.sp,
                            color    = BeColor.ink300
                        )
                    )
                }

                Spacer(Modifier.width(4.dp))

                // Dismiss icon
                Icon(
                    imageVector        = Icons.Rounded.Close,
                    contentDescription = dismissContentDesc,
                    tint               = BeColor.ink300,
                    modifier           = Modifier
                        .size(16.dp)
                        .clickable {
                            scope.launch {
                                visible = false
                                delay(300)
                                onDismiss()
                            }
                        }
                )
            }
        }
    }
}

// ── Toast Host ─────────────────────────────────────────────────────────────────

@Composable
fun ToastHost(
    toastState: ToastState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.End
    ) {
        toastState.toasts.forEach { toast ->
            key(toast.id) {
                ToastChip(
                    toast     = toast,
                    onDismiss = { toastState.dismiss(toast.id) }
                )
            }
        }
    }
}

// ── Previews ───────────────────────────────────────────────────────────────────

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F8)
@Composable
private fun ToastChipSuccessPreview() {
    BookEaseTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            // Simulate visible chips by wrapping them in Box so they always show in preview
            Box {
                Row(
                    modifier = Modifier
                        .widthIn(min = 280.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(BeColor.surfaceWhite)
                        .border(BorderStroke(1.dp, BeColor.border), RoundedCornerShape(12.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(64.dp)
                            .background(
                                BeColor.successText,
                                RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                            )
                    )
                    Row(
                        modifier = Modifier
                            .background(BeColor.surfaceWhite)
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE7F8EE)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Rounded.Check, null, tint = BeColor.successText, modifier = Modifier.size(16.dp))
                        }
                        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text("Success", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = BeColor.ink900))
                            Text("Booking confirmed!", style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp, color = BeColor.ink300))
                        }
                        Icon(Icons.Rounded.Close, "Dismiss", tint = BeColor.ink300, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}
