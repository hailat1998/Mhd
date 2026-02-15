package com.hd.misaleawianegager.presentation.component.selected

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
            if (translationX.value == exitWidthInPx) {
                showList.value = false
                translationX.animateTo(0f)
            } else {
                showList.value = true
                translationX.animateTo(exitWidthInPx)
            }
        }
    }

    val draggableState = rememberDraggableState(onDelta = { dragAmount ->
        coroutineScope.launch {
            translationX.snapTo(translationX.value + dragAmount)
        }
    })

    val rotation by animateFloatAsState(
        targetValue = if (translationX.value == 0f) 180f else 0f, label = "rotate"
    )

    val decay = rememberSplineBasedDecay<Float>()

  Box(modifier = Modifier
      .fillMaxSize()
      .background(Color.Transparent)
      .graphicsLayer(translationX = translationX.value)
      .draggable(
          state = draggableState,
          orientation =  Orientation.Horizontal,
          onDragStopped = { velocity ->
              val targetOffsetX = decay.calculateTargetValue(
                  translationX.value,
                  velocity,
              )
              coroutineScope.launch {
                  val actualTargetX = if (targetOffsetX > exitWidthInPx * 0.5) {
                      exitWidthInPx
                  } else {
                      0f
                  }
                  // checking if the difference between the target and actual is + or -
                  val targetDifference = (actualTargetX - targetOffsetX)
                  val canReachTargetWithDecay =
                      (
                              targetOffsetX > actualTargetX &&
                                      velocity > 0f &&
                                      targetDifference > 0f
                              ) ||
                              (
                                      targetOffsetX < actualTargetX &&
                                              velocity < 0 &&
                                              targetDifference < 0f
                                      )
                  if (canReachTargetWithDecay) {
                      translationX.animateDecay(
                          initialVelocity = velocity,
                          animationSpec = decay,
                      )
                  } else {
                      translationX.animateTo(actualTargetX, initialVelocity = velocity)
                  }
              }
          }
      )
   ) {
      Box(
          Modifier
             .offset(x = 37.dp, y = if (config.orientation == ORIENTATION_PORTRAIT) 560.dp else 200.dp)
              .background(
                  color = MaterialTheme.colorScheme.background,
                  shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
              )
      ) {
          IconButton(
              onClick = { toggleListContent() },
              )  {
              Icon(
                  painter = painterResource(R.drawable.arrow_back_ios_24px),
                  contentDescription = null,
                  modifier = Modifier
                      .offset((-9).dp, 0.dp)
                      .graphicsLayer(rotationZ = rotation)
              )
      }
  }
      LazyColumn(
          modifier = Modifier.offset(x = 64.dp, y = 0.dp)
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
                  Modifier.padding(start = 8.dp).fillMaxWidth().background(MaterialTheme.colorScheme.surfaceContainer),
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
    val list = buildList{
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
