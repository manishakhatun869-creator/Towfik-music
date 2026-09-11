package com.nikhil.yt.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikhil.yt.R

/** Compact branding and shortcuts only; search is owned by MainActivity. */
@Composable
fun TowfiiHeader(onLibrary: () -> Unit, onHistory: () -> Unit) {
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
