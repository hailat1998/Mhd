package com.hd.misaleawianegager.presentation.component.selected

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hd.misaleawianegager.R
import kotlinx.coroutines.launch

@Composable
fun SideList(selected: String, list: List<String>, scroll: (String) -> Unit) {

    val config = LocalConfiguration.current

    val exitWidthInPx = with(LocalDensity.current) { if (config.orientation == ORIENTATION_PORTRAIT) 320.dp.toPx() else 768.dp.toPx() }

    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = if (list.isNotEmpty() && list.size > 1) list.indexOf(selected) else 0)

    val translationX = remember {
        Animatable(exitWidthInPx)
    }

    LaunchedEffect(selected) {
        lazyListState.scrollToItem(if (list.isNotEmpty() && list.size > 1) list.indexOf(selected) else 0)
    }

    val showList = remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()

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
        animationSpec = tween(durationMillis = 300), label = "internal"
    )

    val offsetXPx = with(density) { offsetX.value.toPx() }

  Box(modifier = Modifier
      .fillMaxSize()
      .background(Color.Transparent)
      .graphicsLayer(translationX = translationX.value)
      .pointerInput(Unit) {
          detectDragGestures { _, dragAmount ->
              if (translationX.value == 0f && dragAmount.x > 20.dp.toPx()) {
                  toggleListContent()
              }
          }
      }
   ) {
      Box(
          Modifier
             .offset(x = 37.dp, y = if (config.orientation == ORIENTATION_PORTRAIT) 560.dp else 200.dp)
              .graphicsLayer(translationX = offsetXPx)
              .clickable {  }
      ) {
      IconButton(
          onClick = { toggleListContent() },
          Modifier
             .size(50.dp, 70.dp)
              .shadow(
                  elevation = 4.dp,
                  shape = RoundedCornerShape(topStart = 27.dp, topEnd = 27.dp),
                  clip = false
              )
              .background(
                  color = MaterialTheme.colorScheme.surface,
                  shape = RoundedCornerShape(topStart = 27.dp, bottomStart = 27.dp)
              )
      ) {
          if (translationX.value == 0f) {
              Icon(
                  painterResource(R.drawable.arrow_forward_ios_24px),
                  null,
                    Modifier.offset((-9).dp, 0.dp)
              )
          } else {
              Icon(
                  painterResource(R.drawable.arrow_back_ios_24px),
                  null,
                   Modifier.offset((-9).dp, 0.dp)
              )
          }
      }
  }
      LazyColumn(
          modifier = Modifier.offset(x = 60.dp, y = 0.dp)
              .fillMaxSize()
              .graphicsLayer(
                  scaleY = 0.95f,
                  shadowElevation = 32f,
                  clip = true,
                  shape = RoundedCornerShape(20.dp)
              )
              .background(MaterialTheme.colorScheme.surface),
          verticalArrangement = Arrangement.Center,
         state = lazyListState
      ) {
          items(
             items =  list,
              key = { it },
              contentType = { null }
          ) { s ->
              TextWrapper(s, { s == selected }) {
                  toggleListContent()
                  scroll.invoke(s)
              }
              HorizontalDivider(
                  Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceContainer).padding(start = 8.dp),
                  thickness = 1.dp
                  )
          }
      }
   }
}

@Composable
fun TextWrapper(s: String, isSelected: () -> Boolean, scroll: (String) -> Unit) {
    if (isSelected.invoke()) {
        Text(
            text = s,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.surfaceContainer, fontSize = 20.sp),
            modifier = Modifier.padding(start = 8.dp)
        )
    } else {
        Text(
            text = s,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge.copy( fontSize = 20.sp ),
            modifier = Modifier.padding(start = 8.dp).clickable { scroll.invoke(s) }
        )
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
