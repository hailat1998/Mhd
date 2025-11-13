package com.hd.misaleawianegager.presentation.component.selected

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.utils.compose.ShimmerEffect



@Composable
fun ShimmerEffectWrapper() {
   Column {

       ShimmerEffect(
           modifier = Modifier
               .fillMaxWidth(0.76f)
               .height(100.dp)
               .padding(5.dp)
               .shadow(shape = RoundedCornerShape(20.dp), elevation = 0.dp, clip = false)
               .background(shape = RoundedCornerShape(20.dp), color = Color.Transparent),
                   cornerShapeDpValue = 10
       )

       ShimmerEffect(
           modifier = Modifier
               .fillMaxWidth(0.6f)
               .height(200.dp)
               .padding(5.dp)
               .shadow(shape = RoundedCornerShape(20.dp), elevation = 0.dp, clip = false)
               .background(shape = RoundedCornerShape(20.dp), color = Color.Transparent),
             widthOfShadowBrush = 280,
           cornerShapeDpValue = 15
       )

       ShimmerEffect(
           modifier = Modifier
               .fillMaxWidth(0.9f)
               .height(180.dp)
               .padding(5.dp)
               .shadow(shape = RoundedCornerShape(20.dp), elevation = 0.dp, clip = false)
               .background(shape = RoundedCornerShape(20.dp), color = Color.Transparent),
           cornerShapeDpValue = 20
       )
   }
}

@Preview
@Composable
fun ShimmerEffectWrapperPreview() {
    ShimmerEffectWrapper()
}


