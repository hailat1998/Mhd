package com.hd.misaleawianegager.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextCard(item: String){
    Card(modifier = Modifier.fillMaxWidth()
        .shadow(shape = RoundedCornerShape(20.dp),
                elevation = 5.dp)){
          Text(text = item, textAlign = TextAlign.Center)
    }
}