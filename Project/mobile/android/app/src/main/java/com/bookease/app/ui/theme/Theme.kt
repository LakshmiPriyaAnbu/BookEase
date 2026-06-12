package com.bookease.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Brand Colors ──────────────────────────────────────────────────────────────

object BeColor {
    // Primary purple
    val primary        = Color(0xFF5B4BE3)
    val primaryDark    = Color(0xFF4A3BD0)
    val primarySoft    = Color(0xFFEEEBFD)

    // Accent lime
    val accent         = Color(0xFFC8F25C)
    val accentInk      = Color(0xFF1B2906)

    // Ink / text
    val ink900         = Color(0xFF0E0F14)
    val ink700         = Color(0xFF3A3D4A)
    val ink500         = Color(0xFF5E6370)
    val ink300         = Color(0xFF8A909C)

    // Surface
    val surfaceWhite   = Color(0xFFFFFFFF)
    val surface100     = Color(0xFFF5F5F8)
    val surface200     = Color(0xFFEEEEF2)
    val surface300     = Color(0xFFE4E4EA)
    val border         = Color(0xFFE8E8EE)

    // Status
    val successText    = Color(0xFF16A34A)
    val successBg      = Color(0xFFE7F8EE)
    val warningText    = Color(0xFFD97706)
    val warningBg      = Color(0xFFFEF3D9)
    val dangerText     = Color(0xFFE11D48)
    val dangerBg       = Color(0xFFFCE4E9)
    val infoText       = Color(0xFF2563EB)
    val infoBg         = Color(0xFFEAEFFC)
}

// ── Typography ────────────────────────────────────────────────────────────────

// Using system default fonts to avoid needing custom font assets
// (Space Grotesk / Plus Jakarta Sans equivalent)
val BeTypography = androidx.compose.material3.Typography(
    displayLarge = TextStyle(
        fontSize = 34.sp, fontWeight = FontWeight.Bold,
        color = BeColor.ink900, letterSpacing = (-0.5).sp
    ),
    displayMedium = TextStyle(
        fontSize = 30.sp, fontWeight = FontWeight.Bold,
        color = BeColor.ink900, letterSpacing = (-0.5).sp
    ),
    headlineLarge = TextStyle(
        fontSize = 26.sp, fontWeight = FontWeight.ExtraBold,
        color = BeColor.ink900
    ),
    headlineMedium = TextStyle(
        fontSize = 22.sp, fontWeight = FontWeight.ExtraBold,
        color = BeColor.ink900
    ),
    headlineSmall = TextStyle(
        fontSize = 20.sp, fontWeight = FontWeight.Bold,
        color = BeColor.ink900
    ),
    titleLarge = TextStyle(
        fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
        color = BeColor.ink900
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp, fontWeight = FontWeight.SemiBold,
        color = BeColor.ink900
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
        color = BeColor.ink700
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp, fontWeight = FontWeight.Normal,
        color = BeColor.ink700
    ),
    bodyMedium = TextStyle(
        fontSize = 14.5.sp, fontWeight = FontWeight.Normal,
        color = BeColor.ink700
    ),
    bodySmall = TextStyle(
        fontSize = 13.sp, fontWeight = FontWeight.Normal,
        color = BeColor.ink500
    ),
    labelLarge = TextStyle(
        fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold,
        color = BeColor.ink900
    ),
    labelMedium = TextStyle(
        fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold,
        color = BeColor.ink500
    ),
    labelSmall = TextStyle(
        fontSize = 11.sp, fontWeight = FontWeight.Medium,
        color = BeColor.ink300
    )
)

// ── Spacing ───────────────────────────────────────────────────────────────────

object BeSp {
    val xs: Dp    = 4.dp
    val sm: Dp    = 8.dp
    val md: Dp    = 12.dp
    val base: Dp  = 16.dp
    val lg: Dp    = 20.dp
    val xl: Dp    = 24.dp
    val xxl: Dp   = 32.dp
    val xxxl: Dp  = 48.dp
}

// ── Corner Radius ─────────────────────────────────────────────────────────────

object BeRadius {
    val sm: Dp   = 8.dp
    val md: Dp   = 11.dp
    val lg: Dp   = 14.dp
    val xl: Dp   = 18.dp
    val xxl: Dp  = 22.dp
    val pill: Dp = 999.dp
}

// ── Color Scheme ──────────────────────────────────────────────────────────────

private val BookEaseColorScheme = lightColorScheme(
    primary          = BeColor.primary,
    onPrimary        = Color.White,
    primaryContainer = BeColor.primarySoft,
    onPrimaryContainer = BeColor.primaryDark,
    secondary        = BeColor.accent,
    onSecondary      = BeColor.accentInk,
    background       = BeColor.surface100,
    onBackground     = BeColor.ink900,
    surface          = BeColor.surfaceWhite,
    onSurface        = BeColor.ink900,
    outline          = BeColor.border,
    error            = BeColor.dangerText
)

@Composable
fun BookEaseTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BookEaseColorScheme,
        typography  = BeTypography,
        content     = content
    )
}
