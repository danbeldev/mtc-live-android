package ru.mtc.live.ui.screens.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun Segmented(
    items: List<String>,
    color: Color = Color.Red
) {
    val cornerRadius = 16.dp
    var selectedIndex by remember { mutableIntStateOf(-1) }

    items.forEachIndexed { index, item ->

        OutlinedButton(
            onClick = { selectedIndex = index },
            modifier = when (index) {
                0 ->
                    Modifier
                        .offset(0.dp, 0.dp)
                        .zIndex(if (selectedIndex == index) 1f else 0f)
                else ->
                    Modifier
                        .offset((-1 * index).dp, 0.dp)
                        .zIndex(if (selectedIndex == index) 1f else 0f)
            },
            shape = when (index) {
                0 -> RoundedCornerShape(
                    topStart = cornerRadius,
                    topEnd = 0.dp,
                    bottomStart = cornerRadius,
                    bottomEnd = 0.dp
                )
                items.size - 1 -> RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = cornerRadius,
                    bottomStart = 0.dp,
                    bottomEnd = cornerRadius
                )
                else -> RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            },
            border = BorderStroke(
                1.dp, if (selectedIndex == index) {
                    color
                } else {
                    color.copy(alpha = 0.75f)
                }
            ),
            colors = if (selectedIndex == index) {
                ButtonDefaults.outlinedButtonColors(
                    containerColor = color.copy(alpha = 0.1f),
                    contentColor = color
                )
            } else {
                ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = color
                )
            }
        ) {
            Text(text = item)
        }
    }
}