package com.hd.misaleawianegager.presentation.component.selected

import android.content.ClipData
import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.utils.compose.AnimatedPreloader
import com.hd.misaleawianegager.utils.compose.favList
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.platacard.pagerindicator.PagerIndicator
import mx.platacard.pagerindicator.PagerIndicatorOrientation


@Composable
fun Selected(
    listFlow: StateFlow<List<String>>,
    textAiFlow: StateFlow<DetailUiState>,
    page: String,
    from: String,
    onNextPage: (String) -> Unit,
    favListHere: List<String>,
    onFavoriteToggle: (String) -> Unit,
    getSingleText:() -> Unit,
    goBack: () -> Unit,
    showBtmBarInDetail: State<Boolean>
) {

    val textAi = textAiFlow.collectAsStateWithLifecycle()
    var str by remember { mutableStateOf("") }

    var currentPage by remember { mutableStateOf(page) }

    var checkAssignment by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        onDispose {
            favList = favListHere as MutableList<String>
        }
    }

    val clipboardManager = LocalClipboard.current
    val context = LocalContext.current
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    var list = if (from == "search") {
        remember { mutableStateListOf<String>().apply { add(page) } }
    } else {
        listFlow.collectAsStateWithLifecycle().value
    }

    val isFavorite by remember(currentPage, favListHere, list) {
        derivedStateOf { if (list.size == 1) favListHere.contains(list[0]) else favListHere.contains(currentPage) }
    }

    if (checkAssignment) {
        list = listFlow.collectAsStateWithLifecycle().value
    }

    val pager = rememberPagerState(
        initialPage = 0,
        pageCount = { if (list.isNotEmpty())list.size else 0 }
    )


 SelectionContainer {
   Box(Modifier.fillMaxSize()
   ) {
       Column(
           modifier = Modifier.fillMaxSize()
       ) {
           Box(
               contentAlignment = Alignment.Center,
               modifier = Modifier
                   .weight(1f, fill = true)
                   .fillMaxWidth()
                   .background(Color.Transparent)
           ) {
               if (list.isEmpty()) {
                   CircularProgressIndicator()
               } else {

                   LaunchedEffect(Unit) {
                       pager.scrollToPage(list.indexOf(page))
                   }

                   LaunchedEffect(pager.currentPage) {
                       val newPage = list[pager.targetPage]
                       onNextPage.invoke(newPage)
                       currentPage = newPage
                   }

                   LaunchedEffect(list) {
                       if (list.isNotEmpty() && list.size == 1) {
                           onNextPage.invoke(list[0])
                           currentPage = list[0]
                       }
                   }

                   HorizontalPager(
                       state = pager,
                       modifier = Modifier.fillMaxWidth(),
                   ) { page ->

                       str = list[page]

                       Column(
                           modifier = Modifier
                               .fillMaxSize()
                               .verticalScroll(rememberScrollState())
                               .padding(
                                   bottom = 8.dp,
                                   top = if (showBtmBarInDetail.value) 20.dp else 40.dp
                               )
                               .background(Color.Transparent)
                       ) {
                           Card(
                               modifier = Modifier
                                   .fillMaxWidth().padding(5.dp),
                               elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                               colors = CardDefaults.cardColors(
                                   containerColor = MaterialTheme.colorScheme.surface.copy(
                                       alpha = 0.95f
                                   )
                               )
                           ) {
                               Box(
                                   modifier = Modifier.fillMaxWidth().padding(10.dp),
                                   contentAlignment = Alignment.Center
                               ) {
                                   Text(
                                       text = list[page],
                                       fontSize = 21.sp,
                                       style = MaterialTheme.typography.bodyLarge,
                                       textAlign = TextAlign.Center,
                                       color = MaterialTheme.colorScheme.onPrimary
                                   )
                               }
                           }

                           TwoTabLayout(
                               selectedTabIndex = selectedTabIndex,
                               onTabSelected = { selectedTabIndex = it },
                               firstTabTitle = "አማርኛ",
                               secondTabTitle = "English",
                               firstTabContent = {
                                   textAi.value.let { uiState ->
                                       if (uiState.isLoading == true) {
                                           ShimmerEffectWrapper()
                                       } else if (uiState.isLoading != true && uiState.error != null) {
                                           Box(
                                               Modifier.fillMaxSize(),
                                               contentAlignment = Alignment.Center
                                           ) {
                                               Column(modifier = Modifier.fillMaxSize()) {
                                                   AnimatedPreloader(
                                                       Modifier.fillMaxWidth().height(480.dp)
                                                           .shadow(
                                                               shape = RoundedCornerShape(20.dp),
                                                               elevation = 0.dp
                                                           ).padding(5.dp),
                                                       R.raw.animation_error
                                                   )
                                                   Text(
                                                       text = uiState.error!!,
                                                       fontSize = 10.sp,
                                                       color = Color.Red.copy(red = 0.9f),
                                                       modifier = Modifier.padding(top = 4.dp)
                                                           .fillMaxWidth(),
                                                       textAlign = TextAlign.Right
                                                   )
                                               }
                                               Button(
                                                   onClick = {
                                                       onNextPage.invoke(currentPage)
                                                   },
                                                   border = BorderStroke(
                                                       1.dp,
                                                       MaterialTheme.colorScheme.onPrimary
                                                   )
                                               ) {
                                                   Text(text = "refresh")
                                               }
                                           }
                                       } else if (uiState.isLoading != true && uiState.amMeaning != null) {
                                           MarkdownContent(uiState.amMeaning!!)
                                       }
                                   }
                               },
                               secondTabContent = {
                                   textAi.value.let { uiState ->
                                       if (uiState.isLoading == true) {
                                           ShimmerEffectWrapper()
                                       } else if (uiState.isLoading != true && uiState.error != null) {
                                           Box(
                                               Modifier.fillMaxSize(),
                                               contentAlignment = Alignment.Center
                                           ) {
                                               Column(modifier = Modifier.fillMaxSize()) {
                                                   AnimatedPreloader(
                                                       Modifier.fillMaxWidth().height(480.dp)
                                                           .shadow(
                                                               shape = RoundedCornerShape(20.dp),
                                                               elevation = 0.dp
                                                           ).padding(5.dp),
                                                       R.raw.animation_error
                                                   )
                                                   Text(
                                                       text = uiState.error!!,
                                                       fontSize = 10.sp,
                                                       color = Color.Red.copy(red = 0.9f),
                                                       modifier = Modifier.padding(top = 4.dp)
                                                           .fillMaxWidth(),
                                                       textAlign = TextAlign.Right
                                                   )
                                               }
                                               Button(
                                                   onClick = {
                                                       onNextPage.invoke(currentPage)
                                                   },
                                                   border = BorderStroke(
                                                       1.dp,
                                                       MaterialTheme.colorScheme.onPrimary
                                                   )
                                               ) {
                                                   Text(text = "refresh")
                                               }
                                           }
                                       } else if (uiState.isLoading != true && uiState.enMeaning != null) {
                                           MarkdownContent(uiState.enMeaning!!)
                                       }
                                   }
                               }
                           )

                       }
                   }
               }
           }


       FloatingInteraction(
           isFavorite,
           onFavChanged = {
               onFavoriteToggle(currentPage)
           },
           onCopy = {
               val annotatedString = buildAnnotatedString {
                   withStyle(style = SpanStyle(textDecoration = TextDecoration.None)) {
                       append(str)
                   }
               }
               val clipEntry = ClipEntry(
                   ClipData.newPlainText("label", annotatedString)
               )
               scope.launch {
                   clipboardManager.setClipEntry(clipEntry)
               }
           },
           onShare = {
               val shareText = Intent(Intent.ACTION_SEND).apply {
                   type = "text/plain"  // Fixed content type
                   putExtra(Intent.EXTRA_TEXT, str)
               }
               val chooserIntent = Intent.createChooser(shareText, "Misaleawi Anegager")
               context.startActivity(chooserIntent)
           },
           getRandom = {
               if (from == "search") {
                   checkAssignment = true
               }
               getSingleText.invoke()
           },
       )
           Box(
               Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp),
               contentAlignment = Alignment.Center
           ) {
               PagerIndicator(
                   pagerState = pager,
                   activeDotColor = MaterialTheme.colorScheme.primary,
                   dotColor = MaterialTheme.colorScheme.onPrimary,
                   dotCount = 6,
                   space = 12.dp,
                   orientation = PagerIndicatorOrientation.Horizontal
               )
           }
   }
         if (list.isNotEmpty()) {
             SideList(currentPage, list) {
                 scope.launch {
                     pager.scrollToPage(list.indexOf(it))
                 }
             }
         }
         if (!showBtmBarInDetail.value) {
             Back(goBack = { goBack.invoke() })
         }
      }
   }
}


@Composable
fun Back(goBack: () -> Unit) {
    IconButton(
        onClick = { goBack.invoke() },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 32.dp)
            .size(36.dp)
            .offset(0.dp, (-6).dp)
            .background(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = null
        )
    }
}

@Composable
fun MarkdownContent(
    markdownText: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Column {
        MarkdownText(
            markdown = markdownText,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start,
            fontSize = 17.sp,
            style = MaterialTheme.typography.bodyLarge,
            onClick = onClick,
        )
        Spacer(Modifier.height(10.dp))

        Row(Modifier.fillMaxWidth()) {
            Text(
                text = "ℹ\uFE0F ",
                fontSize = 23.sp,
                color = Color.Gray,
                modifier = modifier.padding(end = 4.dp, top = 5.dp)
            )
            Column {
                Text(
                    text = "AI-generated content—may contain errors. Verify important info.",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = modifier.padding(top = 4.dp).fillMaxWidth()
                )

                Text(
                    text = "በአርቴፊሻል ኢንተለጀንስ የተፈጠረ ይዘት — ስህተቶች ሊኖሩት ይችላል። አስፈላጊ መረጃን ያረጋግጡ።",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = modifier.padding(top = 4.dp).fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun TwoTabLayout(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    firstTabTitle: String = "Tab 1",
    secondTabTitle: String = "Tab 2",
    firstTabContent: @Composable () -> Unit,
    secondTabContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {


        SecondaryTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,

            indicator = {
                SecondaryIndicator(

                    modifier = Modifier
                        .tabIndicatorOffset(selectedTabIndex)
                        .padding(horizontal = 24.dp),
                    height = 3.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ) {

            Tab(
                selected = selectedTabIndex == 0,
                onClick = { onTabSelected(0) },
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .animateContentSize(),
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                text = {
                    Text(
                        text = firstTabTitle,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )

            Tab(
                selected = selectedTabIndex == 1,
                onClick = { onTabSelected(1) },
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .animateContentSize(),
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                text = {
                    Text(
                        text = secondTabTitle,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }


        AnimatedContent(
            targetState = selectedTabIndex,
            transitionSpec = {
                val direction = if (targetState > initialState) 1 else -1
                val slideIn = slideInHorizontally(
                    animationSpec = tween(durationMillis = 300),
                    initialOffsetX = { fullWidth -> direction * fullWidth }
                ) + fadeIn(animationSpec = tween(durationMillis = 300))

                val slideOut = slideOutHorizontally(
                    animationSpec = tween(durationMillis = 300),
                    targetOffsetX = { fullWidth -> -direction * fullWidth }
                ) + fadeOut(animationSpec = tween(durationMillis = 300))

                slideIn togetherWith slideOut
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            label = "TabContentAnimation"
        ) { index ->
            when (index) {
                0 -> firstTabContent()
                1 -> secondTabContent()
            }
        }
    }
}

@Composable
fun FloatingInteraction(
    isFavorite: Boolean,
    onFavChanged: () -> Unit,
    onCopy: () -> Unit,
    onShare: () -> Unit,
    getRandom: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var interacted by remember { mutableStateOf(false) }

    val rememberedOnFavChanged by rememberUpdatedState(onFavChanged)
    val rememberedOnCopy by rememberUpdatedState(onCopy)
    val rememberedOnShare by rememberUpdatedState(onShare)
    val rememberedGetRandom by rememberUpdatedState(getRandom)

        Row(
            modifier = Modifier.fillMaxWidth().padding(start  = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InteractionButton(
                icon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite",
                tintColor = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary,
                onClick = {
                    interacted = !interacted
                    rememberedOnFavChanged.invoke()
                },
                label = null,
                isSelected = isFavorite
            )

            InteractionButton(
                icon = Icons.Default.Share,
                contentDescription = "Share",
                onClick = {
                    interacted = !interacted
                    rememberedOnShare.invoke()
                },
                label = null,
                tintColor = MaterialTheme.colorScheme.onPrimary
            )

            InteractionButton(
                icon = null,
                painter = painterResource(R.drawable.baseline_content_copy_24),
                contentDescription = "Copy",
                onClick = {
                    interacted = !interacted
                    rememberedOnCopy.invoke()
                },
                label = null,
                tintColor = MaterialTheme.colorScheme.onPrimary
            )

            InteractionButton(
                icon = null,
                painter = painterResource(R.drawable.baseline_shuffle_24),
                contentDescription = "GetRandom",
                onClick = {
                    interacted = !interacted
                    rememberedGetRandom.invoke()
                },
                label = null,
                tintColor = MaterialTheme.colorScheme.onPrimary
            )
                    }

              }




@Composable
private fun InteractionButton(
    icon: ImageVector? = null,
    painter: Painter? = null,
    contentDescription: String,
    onClick: () -> Unit,
    label: String?,
    tintColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    isSelected: Boolean = false
) {

    val rememberedOnClick = rememberUpdatedState(onClick)

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )

    val animatedTint by animateColorAsState(
        targetValue = tintColor,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "tint"
    )

    Surface(
        modifier = Modifier.padding(8.dp),
        color = Color.Transparent
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
        ) {
            IconButton(
                onClick = { rememberedOnClick.value() },
                modifier = Modifier.size(30.dp)
            ) {
                when {
                    icon != null -> Icon(
                        imageVector = icon,
                        contentDescription = contentDescription,
                        tint = animatedTint,
                        modifier = Modifier
                            .size(25.dp)
                            .scale(scale)
                    )
                    painter != null -> Icon(
                        painter = painter,
                        contentDescription = contentDescription,
                        tint = animatedTint,
                        modifier = Modifier
                            .size(25.dp)
                            .scale(scale)
                    )
                }
            }
            label?.let{
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = animatedTint,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FloatingButtonPreview(){
    FloatingInteraction(false, {}, {}, {}, {})
}


//
//@Preview(showBackground = true)
//@Composable
//fun TwoTabLayoutPreview() {
//    var selectedTab by remember { mutableIntStateOf(0) }
//
//    MaterialTheme {
//        TwoTabLayout(
//            selectedTabIndex = selectedTab,
//            onTabSelected = { selectedTab = it },
//            firstTabTitle = "Home",
//            secondTabTitle = "Settings",
//            firstTabContent = {
//                Text(
//                    text = "Home Content",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    textAlign = TextAlign.Center,
//                    style = MaterialTheme.typography.bodyLarge
//                )
//            },
//            secondTabContent = {
//                Text(
//                    text = "Settings Content",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    textAlign = TextAlign.Center,
//                    style = MaterialTheme.typography.bodyLarge
//                )
//            }
//        )
//    }
//}
//
//
//@Preview(showBackground = true)
//@Composable
//fun MarkDown(){
//    MarkdownContent(markdownText = getMarkDown())
//}
//
//fun getMarkDown() = "# Contributing to Virtual Book Store\n" +
//        "\n" +
//        "Thank you for considering contributing to Virtual Book Store! We welcome all contributions, whether it's bug fixes, new features, documentation improvements, or other enhancements.\n" +
//        "\n" +
//        "## Getting Started\n" +
//        "\n" +
//        "### 1. Fork the Repository\n" +
//        "Click the **Fork** button on the top right of the repository page to create a copy of the repository under your GitHub account.\n" +
//        "\n" +
//        "### 2. Clone the Repository\n" +
//        "Clone your forked repository to your local machine:\n" +
//        "```sh\n"
//
//
//@Preview(showBackground = true)
//@Composable
//fun SelectedPreview() {
//  //  Selected(MutableStateFlow(emptyList()), MutableStateFlow(DetailUiState()), "","", {}, emptyList(), {}, {}, {})
//}





