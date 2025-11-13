package com.hd.misaleawianegager.presentation.component.selected

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.R
import kotlinx.coroutines.launch

@Composable
fun SideList(selected: String, list: List<String>, scroll: (String) -> Unit) {

    val translationX = remember {
        Animatable(0f)
    }

    val showList = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val exitWidthInPx = with(LocalDensity.current) { 320.dp.toPx() }

    translationX.updateBounds(0f, exitWidthInPx)

    fun toggleListContent() {
        coroutineScope.launch {
            if (showList.value) {
                showList.value = false
                translationX.animateTo(0f)
            } else {
                showList.value = true
                translationX.animateTo(exitWidthInPx)
            }
        }
    }
    val density = LocalDensity.current
    val offsetX = animateDpAsState(
        targetValue = if (showList.value) 0.dp else (-10).dp,
        animationSpec = tween(durationMillis = 300)
    )

    val offsetXPx = with(density) { offsetX.value.toPx() }

  Box(modifier = Modifier
      .fillMaxSize()
      .background(Color.Transparent)
      .graphicsLayer(translationX = translationX.value)
   ) {
      IconButton(onClick = { toggleListContent() },
          Modifier
              .offset(x = 37.dp, y = 560.dp)
              .size(50.dp, 70.dp)
              .shadow(
                  elevation = 4.dp,
                  shape = RoundedCornerShape(20.dp),
                  clip = false
              )
              .background(
                  color = MaterialTheme.colorScheme.surface,
                  shape = RoundedCornerShape(20.dp)
              )
              .graphicsLayer(translationX = offsetXPx),
      ) {
          if (translationX.value == 0f) {
              Icon(
                  painterResource(R.drawable.arrow_forward_ios_24px),
                  null,
                  Modifier.offset((-10).dp, 0.dp)
              )
          } else {
              Icon(
                  painterResource(R.drawable.arrow_back_ios_24px),
                  null,
                  Modifier.offset((-10).dp, 0.dp)
              )
          }
      }
      LazyColumn(Modifier.offset(x = 60.dp, y = 0.dp).background(MaterialTheme.colorScheme.surface).fillMaxSize()) {
          items(
             items =  list,
              key = null,
              contentType = { null }
          ) {
              if (it == selected) {
                  Text(text = it, style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.secondary))
              } else {
                  Text(text = it, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.clickable { scroll.invoke(it) })
              }
          }
      }
   }
}

@Preview
@Composable
fun SideListPreview(){
    var j = 0
    val list = buildList<String>{
        repeat(30){
            add("I love watching football$j")
            j++
        }
    }
    MaterialTheme{
        Surface(color = Color.Gray, modifier = Modifier.fillMaxSize()) {
            SideList("I love watching football7", list) { }
        }
    }
}
