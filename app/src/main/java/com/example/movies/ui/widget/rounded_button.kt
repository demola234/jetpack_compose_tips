package com.example.movies.ui.widget

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.unit.*

val IconButtonSizeModifier = Modifier.size(40.dp)

@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black,
    backgroundColor: Color = Color.Black.copy(alpha = 0.5f),
    elevation: Dp = 4.dp
) {
    Card(
        modifier = modifier
            .padding(all = 4.dp)
            .clickable { onClick() }
            .then(IconButtonSizeModifier),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = "Round Button",
                tint = tint
            )
        }
    }
}