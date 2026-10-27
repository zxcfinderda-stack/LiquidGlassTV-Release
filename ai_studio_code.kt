package com.liquidglass.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liquidglass.tv.data.GlassConfig
import com.liquidglass.tv.ui.glass.liquidGlass

@Composable
fun SettingsScreen(
    currentConfig: GlassConfig,
    onSave: (GlassConfig) -> Unit,
    onClose: () -> Unit
) {
    var opacity by remember { mutableStateOf(currentConfig.opacity) }
    var blur by remember { mutableStateOf(currentConfig.blur) }
    var corners by remember { mutableStateOf(currentConfig.cornerRadius) }
    var columns by remember { mutableStateOf(currentConfig.gridColumns) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.88f))
            .padding(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(600.dp)
                .liquidGlass(
                    isFocused = false,
                    cornerRadius = 32.dp,
                    glassOpacity = 0.35f,
                    blurRadius = 50f
                )
                .padding(36.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Настройки Liquid Glass",
                fontSize = 26.sp,
                color = Color.White
            )

            SettingRow(
                title = "Прозрачность стекла: ${(opacity * 100).toInt()}%",
                onDecrease = { opacity = (opacity - 0.05f).coerceAtLeast(0.05f) },
                onIncrease = { opacity = (opacity + 0.05f).coerceAtMost(0.85f) }
            )

            SettingRow(
                title = "Интенсивность Blur: ${blur.toInt()} px",
                onDecrease = { blur = (blur - 5f).coerceAtLeast(0f) },
                onIncrease = { blur = (blur + 5f).coerceAtMost(90f) }
            )

            SettingRow(
                title = "Скругление углов: ${corners} dp",
                onDecrease = { corners = (corners - 4).coerceAtLeast(8) },
                onIncrease = { corners = (corners + 4).coerceAtMost(48) }
            )

            SettingRow(
                title = "Количество колонок сетки: $columns",
                onDecrease = { columns = (columns - 1).coerceAtLeast(3) },
                onIncrease = { columns = (columns + 1).coerceAtMost(8) }
            )

            Spacer(Modifier.height(8.dp))

            var isSaveFocused by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isSaveFocused = it.isFocused }
                    .liquidGlass(
                        isFocused = isSaveFocused,
                        cornerRadius = 16.dp,
                        glassOpacity = 0.5f
                    )
                    .focusable()
                    .clickable {
                        onSave(
                            currentConfig.copy(
                                opacity = opacity,
                                blur = blur,
                                cornerRadius = corners,
                                gridColumns = columns
                            )
                        )
                        onClose()
                    }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Применить и закрыть",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
private fun SettingRow(
    title: String,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused }
            .liquidGlass(isFocused = isFocused, cornerRadius = 14.dp, glassOpacity = 0.2f)
            .focusable()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = Color.White, fontSize = 16.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(
                modifier = Modifier
                    .focusable()
                    .clickable { onDecrease() }
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(text = "◄", color = Color(0xFF38BDF8), fontSize = 18.sp)
            }
            Box(
                modifier = Modifier
                    .focusable()
                    .clickable { onIncrease() }
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(text = "►", color = Color(0xFF38BDF8), fontSize = 18.sp)
            }
        }
    }
}