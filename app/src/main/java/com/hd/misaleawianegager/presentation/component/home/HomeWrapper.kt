package com.hd.misaleawianegager.presentation.component.home

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.presentation.DataProvider
import com.hd.misaleawianegager.presentation.component.setting.SettingEvent
import com.hd.misaleawianegager.utils.DIRECTION
import com.hd.misaleawianegager.utils.compose.Chip
import com.hd.misaleawianegager.utils.compose.ChipDefaultsM3
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeWrapper(homeData: State<List<String>>,
                onHomeEvent: (HomeEvent) -> Unit,
                onSettingEvent: (SettingEvent) -> Unit,
                scrollIndex: State<Int>,
                toDetail: ( from: String, text: String, first: String) -> Unit,
                toBoarding: () -> Unit,
                drag: (DIRECTION) -> Unit) {

    val openDialog = remember { mutableStateOf(false) }

    val showBottomSheet = remember { mutableStateOf(false) }

    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = scrollIndex.value)

    var showFloatButton by remember { mutableStateOf(true) }

    val firstVisibleItemIndex = remember { mutableIntStateOf(0) }

    val context = LocalContext.current as Activity

    val sheetHeightInPx = with(LocalDensity.current) { 100.dp.toPx() }

    val translationY = remember {
        Animatable(0f)
    }

    val coroutineScope = rememberCoroutineScope()

    translationY.updateBounds(0f, sheetHeightInPx)

    BackHandler {
        context.finish()
    }

    val draggableState = rememberDraggableState(onDelta = { dragAmount ->
        coroutineScope.launch {
            translationY.snapTo(translationY.value + dragAmount)
        }
    }
    )

    var xAmount by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(xAmount) {
        if (abs(xAmount) > 10 ) {
            if (xAmount > 0) {
                drag.invoke(DIRECTION.LEFT)
            } else {
                drag.invoke(DIRECTION.RIGHT)
            }
        }
    }

    val decay = rememberSplineBasedDecay<Float>()

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }.distinctUntilChanged()
            .collect { currentIndex ->
                onHomeEvent.invoke(HomeEvent.ScrollPos(currentIndex))
                if (lazyListState.isScrollInProgress) {
                    if (currentIndex > firstVisibleItemIndex.intValue) {
                        showFloatButton = false
                    } else {
                        delay(200)
                        showFloatButton = true
                    }
                }
                firstVisibleItemIndex.intValue = currentIndex
            }
     }

    val floatLetter = remember { mutableStateOf("") }

    LaunchedEffect (homeData.value ){
      if (homeData.value.isNotEmpty()) {
          floatLetter.value =   if (homeData.value[0][0].toString() == "ኃ") "ኀ"
          else if (homeData.value[0][0].toString() == "ጳ") "ጰ"
          else homeData.value[0][0].toString()
      }
    }

    fun toggleHomeContent() {
        val prevLetter =  floatLetter.value
        coroutineScope.launch {

            if (showBottomSheet.value) {

                translationY.animateTo(0f)
                showBottomSheet.value = false
            } else {
                showBottomSheet.value = true
                translationY.animateTo(sheetHeightInPx)
            }
            if (prevLetter != floatLetter.value) {
                lazyListState.scrollToItem(0)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ዋና",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = { openDialog.value = true },
                        Modifier.padding(end = 13.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "INFO",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.height(45.dp)
            )
        },

        floatingActionButton = {
            AnimatedVisibility(
                visible = showFloatButton,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }) {
                FloatingActionButton(
                    onClick = { toggleHomeContent() }, modifier = Modifier.testTag("FLOAT")
                ) {
                    Text(
                        floatLetter.value,
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    var xAmountLocal = 0f
                    detectDragGestures(onDragEnd = { xAmount = xAmountLocal })  { _, dragAmount ->
                        xAmountLocal = dragAmount.x
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (homeData.value.isEmpty()) {
                CircularProgressIndicator()
            } else {
                 val list = remember { derivedStateOf { homeData.value.distinct() } }
                HomeContent(
                    homeData = list,
                    toDetail = toDetail,
                    lazyListState = lazyListState,
                    arg3 = floatLetter,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .graphicsLayer{
                            this.translationY = 0 - translationY.value
                            val scale = lerp(1f, 0.6f, translationY.value / sheetHeightInPx)
                            this.scaleX = scale
                            this.scaleY = scale
                            val roundedCorners = lerp(0f, 20.dp.toPx(), translationY.value / sheetHeightInPx)
                            this.shape = RoundedCornerShape(roundedCorners)
                        }
                        .draggable(
                            state = draggableState,
                            orientation = Orientation.Vertical,
                            onDragStopped = { velocity ->
                                val targetOffsetY = decay.calculateTargetValue(
                                    translationY.value,
                                    velocity
                                )
                                coroutineScope.launch {
                                    val actualTargetY = if (targetOffsetY > sheetHeightInPx * 0.5) {
                                        sheetHeightInPx
                                    } else {
                                        0f
                                    }

                                    val targetDifference = (actualTargetY - targetOffsetY)
                                    val canReachTargetWithDecay =
                                        (
                                                targetOffsetY > actualTargetY && velocity > 0f &&
                                                        targetDifference > 0f
                                                ) ||
                                                (
                                                        targetOffsetY < actualTargetY && velocity < 0 &&
                                                                targetDifference < 0f
                                                        )
                                    if (canReachTargetWithDecay) {
                                        translationY.animateDecay(
                                            initialVelocity = velocity,
                                            animationSpec = decay
                                        )
                                    } else {
                                        translationY.animateTo(
                                            actualTargetY,
                                            initialVelocity = velocity
                                        )
                                    }
                                }
                            }
                        )
                )

                if (openDialog.value) {
                    AppInfoDialog(openDialog = openDialog, toBoarding = toBoarding)
                }
                if (showBottomSheet.value) {
                    HomeBottomSheet(
                       ::toggleHomeContent,
                        onHomeEvent,
                        onSettingEvent
                    )
                }
            }
        }
    }
}

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    fun HomeBottomSheet(
        dismissReq: () -> Unit,
        loadLetter: (HomeEvent) -> Unit,
        onSettingEvent: (SettingEvent) -> Unit
    ) {
        ModalBottomSheet(
            onDismissRequest = {
                dismissReq.invoke()
            },
            dragHandle = null
        ) {
            Box(
                modifier = Modifier.heightIn(max = 238.dp).fillMaxSize()
            ) {
                Image(
                    painterResource(id = R.drawable.drawing_dun),
                    null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.4f), BlendMode.SrcOver),
                    modifier = Modifier.fillMaxWidth()
                )

                FlowRow(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.Transparent)
                ) {
                    val borderLetters = remember { mutableStateListOf("ረ", "ዐ", "ቸ", "ጠ") }
                    DataProvider.letterMap.keys.forEach { it ->
                        Row {
                        Chip(
                            onClick = {

                                onSettingEvent.invoke(SettingEvent.LetterType(DataProvider.letterMap[it]!!))

                                loadLetter.invoke(HomeEvent.LoadLetter(DataProvider.letterMap[it]!!))

                                dismissReq.invoke()

                            },
                            colors = ChipDefaultsM3.chipColors(containerColor = MaterialTheme.colorScheme.background),

                            border = BorderStroke(2.dp, Color(0xFFFFD700)),
                        ) {
                            Box(
                                modifier = Modifier.size(34.dp).padding(end = 4.dp, start = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    if (!borderLetters.contains(it)) {
                        Spacer(Modifier.width(3.dp))
                     }
                    }
                  }
                }
            }
        }
    }

