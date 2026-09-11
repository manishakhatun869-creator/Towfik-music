package com.nikhil.yt.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikhil.yt.R

/** Adaptive home dashboard. All actions use the existing navigation destinations. */
@Composable
fun TowfiiHeader(onSearch: () -> Unit, onLibrary: () -> Unit, onHistory: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Column(
        Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painterResource(R.drawable.ic_towfik_logo), null,
                Modifier.size(48.dp).clip(RoundedCornerShape(16.dp)))
            Column(Modifier.weight(1f).padding(start = 12.dp)) {
                Text(stringResource(R.string.app_name), style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold)
                Text(stringResource(R.string.towfii_owner), style = MaterialTheme.typography.labelSmall,
                    color = colors.onSurfaceVariant, letterSpacing = 1.sp)
            }
            FilledTonalIconButton(onClick = onSearch, modifier = Modifier.size(48.dp)) {
                Icon(painterResource(R.drawable.search), stringResource(R.string.search))
            }
        }
        Surface(onClick = onSearch, shape = RoundedCornerShape(18.dp),
            color = colors.surfaceContainer, modifier = Modifier.fillMaxWidth()) {
            Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(painterResource(R.drawable.search), null, tint = colors.primary)
                Text(stringResource(R.string.towfik_search_hint),
                    style = MaterialTheme.typography.bodyMedium, color = colors.onSurfaceVariant)
            }
        }
        Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(30.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF382467), Color(0xFF191A35))))
            .border(1.dp, Color(0xFF8B74C5).copy(alpha = 0.3f), RoundedCornerShape(30.dp))) {
            // Decorative record artwork has no accessibility semantics or network dependency.
            Canvas(Modifier.matchParentSize()) {
                val center = Offset(size.width * 0.96f, size.height * 0.40f)
                val radius = size.width * 0.37f
                drawCircle(Color(0xFFBBA1FF).copy(alpha = 0.08f), radius, center)
                for (ring in 1..7) {
                    drawCircle(Color(0xFFC4B5FD).copy(alpha = 0.12f), radius * ring / 7f,
                        center, style = Stroke(1.dp.toPx()))
                }
                drawCircle(Color(0xFF67E8F9).copy(alpha = 0.18f), radius * 0.23f, center)
                drawCircle(Color(0xFF151326), radius * 0.075f, center)
            }
            Column(Modifier.padding(26.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(stringResource(R.string.towfik_daily_selection), color = Color(0xFFD4C5FF),
                    style = MaterialTheme.typography.labelSmall, letterSpacing = 2.sp)
                Text(stringResource(R.string.towfik_hero_title),
                    style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold,
                    color = Color.White, modifier = Modifier.fillMaxWidth(0.8f))
                Text(stringResource(R.string.towfik_hero_subtitle),
                    style = MaterialTheme.typography.bodyMedium, color = Color(0xFFD1CCE4),
                    modifier = Modifier.fillMaxWidth(0.8f))
                Button(onClick = onLibrary, shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD5C4FF), contentColor = Color(0xFF29184D))) {
                    Icon(painterResource(R.drawable.library_outlined), null, Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.towfik_open_library))
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ShortcutCard(stringResource(R.string.filter_library), R.drawable.library_outlined, onLibrary, Modifier.weight(1f))
            ShortcutCard(stringResource(R.string.history), R.drawable.history, onHistory, Modifier.weight(1f))
        }
    }
}

@Composable
private fun ShortcutCard(title: String, icon: Int, onClick: () -> Unit, modifier: Modifier) {
    Surface(onClick = onClick, modifier = modifier, shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Icon(painterResource(icon), null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        }
    }
}
