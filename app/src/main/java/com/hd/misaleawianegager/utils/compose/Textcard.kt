package com.hd.misaleawianegager.utils.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextCard(item: String, from: String , first: String, toDetail: ( from: String, text: String, first: String) -> Unit){
    Card(modifier = Modifier.fillMaxWidth()
        .shadow(shape = RoundedCornerShape(2.dp), elevation = 0.dp)
        .clickable { toDetail.invoke(from, item, first) }.
    padding(top = 0.dp, bottom = 10.dp, end = 5.dp, start = 5.dp)){
          Text(text = item, textAlign = TextAlign.Center, modifier = Modifier.padding(start = 10.dp))
    }
}