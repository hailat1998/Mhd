package com.hd.misaleawianegager.utils.compose

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextCard(
    item: String,
    from: String,
    first: String,
    toDetail: (from: String, text: String, first: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { toDetail.invoke(from, item, first) }
            .padding(vertical = 8.dp, horizontal = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background( Brush.verticalGradient(
                    colors = listOf(
                       MaterialTheme.colorScheme.surfaceContainer,
                      MaterialTheme.colorScheme.surface
                    )
                ))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}


@Composable
fun TextCardAnnotated(   item: AnnotatedString,
                         from: String,
                         first: String,
                         toDetail: (from: String, text: String, first: String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { toDetail.invoke(from, item.text, first) }
            .padding(vertical = 8.dp, horizontal = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background( Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surfaceContainer,
                        MaterialTheme.colorScheme.surface
                    )
                ))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
fun HD() {
    MaterialTheme {

    }
}