package com.nikhil.yt.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Towfik Music's midnight-violet identity. Custom user palettes remain supported.
val MidnightVioletColors = darkColorScheme(
    primary = Color(0xFFC4B5FD),
    onPrimary = Color(0xFF28134F),
    primaryContainer = Color(0xFF513580),
    onPrimaryContainer = Color(0xFFF0E7FF),
    secondary = Color(0xFFB7C4FF),
    onSecondary = Color(0xFF202C59),
    secondaryContainer = Color(0xFF303A60),
    onSecondaryContainer = Color(0xFFE0E5FF),
    tertiary = Color(0xFFF4B8DA),
    onTertiary = Color(0xFF482039),
    background = Color(0xFF0C0D19),
    onBackground = Color(0xFFF2EEFF),
    surface = Color(0xFF0C0D19),
    onSurface = Color(0xFFF2EEFF),
    surfaceVariant = Color(0xFF303047),
    onSurfaceVariant = Color(0xFFC7C2D9),
    surfaceContainerLowest = Color(0xFF080912),
    surfaceContainerLow = Color(0xFF121322),
    surfaceContainer = Color(0xFF191A2C),
    surfaceContainerHigh = Color(0xFF23243A),
    surfaceContainerHighest = Color(0xFF2E2E46),
    surfaceBright = Color(0xFF36354B),
    surfaceDim = Color(0xFF0C0D19),
    outline = Color(0xFF938BA8),
    outlineVariant = Color(0xFF36334D),
)

val TowfiiShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp),
)
