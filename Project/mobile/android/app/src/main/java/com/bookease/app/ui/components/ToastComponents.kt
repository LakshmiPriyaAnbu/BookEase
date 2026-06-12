package com.bookease.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// ── Toast Types ────────────────────────────────────────────────────────────────

enum class ToastType { SUCCESS, ERROR, INFO, WARNING }

data class ToastMessage(
    val id: Long = System.currentTimeMillis(),
    val message: String,
    val type: ToastType
)

// ── Toast State ────────────────────────────────────────────────────────────────

class ToastState {
    var toasts by mutableStateOf<List<ToastMessage>>(emptyList())
        private set

    fun showToast(message: String, type: ToastType) {
        val toast = ToastMessage(
            id = System.currentTimeMillis(),
            message = message,
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

// ── Toast Colors ───────────────────────────────────────────────────────────────

private data class ToastColors(
    val background: Color,
    val border: Color,
    val text: Color,
    val accent: Color
)

private fun toastColors(type: ToastType) = when (type) {
    ToastType.SUCCESS -> ToastColors(
        background = Color(0xFFE7F8EE),
        border     = Color(0xFF16A34A),
        text       = Color(0xFF15803D),
        accent     = Color(0xFF16A34A)
    )
    ToastType.ERROR   -> ToastColors(
        background = Color(0xFFFCE4E9),
        border     = Color(0xFFE11D48),
        text       = Color(0xFFBE123C),
        accent     = Color(0xFFE11D48)
    )
    ToastType.INFO    -> ToastColors(
        background = Color(0xFFEAEFFC),
        border     = Color(0xFF2563EB),
        text       = Color(0xFF1D4ED8),
        accent     = Color(0xFF2563EB)
    )
    ToastType.WARNING -> ToastColors(
        background = Color(0xFFFEF3D9),
        border     = Color(0xFFD97706),
        text       = Color(0xFFB45309),
        accent     = Color(0xFFD97706)
    )
}

private fun toastIcon(type: ToastType) = when (type) {
    ToastType.SUCCESS -> Icons.Rounded.Check
    ToastType.INFO    -> Icons.Rounded.Info
    ToastType.ERROR   -> Icons.Rounded.Close
    ToastType.WARNING -> Icons.Rounded.Close
}

// ── Single Toast Chip ──────────────────────────────────────────────────────────

@Composable
private fun ToastChip(
    toast: ToastMessage,
    onDismiss: () -> Unit
) {
    val colors = toastColors(toast.type)
    val scope  = rememberCoroutineScope()

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(toast.id) {
        visible = true
        delay(3500)
        visible = false
        delay(300) // allow exit animation
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
                .clip(RoundedCornerShape(12.dp))
                .background(colors.background)
                // 1dp outer border
                .then(
                    Modifier.padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
                )
                .semantics { contentDescription = toast.message },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 4dp left accent bar
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(48.dp)
                    .background(
                        color = colors.accent,
                        shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                    )
            )

            Row(
                modifier = Modifier
                    .background(
                        color  = colors.background,
                        shape  = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                    )
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector        = toastIcon(toast.type),
                    contentDescription = null,
                    tint               = colors.accent,
                    modifier           = Modifier.size(18.dp)
                )
                Text(
                    text  = toast.message,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color      = colors.text,
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(4.dp))
                Icon(
                    imageVector        = Icons.Rounded.Close,
                    contentDescription = "Dismiss",
                    tint               = colors.text,
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
