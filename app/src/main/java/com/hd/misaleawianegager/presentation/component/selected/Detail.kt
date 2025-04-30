package com.hd.misaleawianegager.presentation.component.selected

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hd.misaleawianegager.R
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class) // Opt-in for Pager APIs
@Composable
fun Selected(
    viewModel: DetailViewModel,
    page: String,
    onNextPage: (String) -> Unit
) {

    val textAi = viewModel.detailsAITextStateFlow.collectAsStateWithLifecycle()
    val list = viewModel.detailStateFlow.collectAsStateWithLifecycle()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val isFavorite = remember{ mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000L)
        Log.i("DETAIL2", "${textAi.value}")
        Log.i("DETAIL2", "${textAi.value}")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.scrollable(rememberScrollState(), Orientation.Horizontal),
            ) {

            Column {
                if (list.value.isEmpty()) {
                    CircularProgressIndicator()
                } else {

                    val pager = rememberPagerState(
                        initialPage = 0,
                        pageCount = { list.value.size }
                    )

                    LaunchedEffect(Unit) {
                        pager.scrollToPage(list.value.indexOf(page))
                    }

                    LaunchedEffect(pager.currentPage) {
                        onNextPage.invoke(list.value[pager.targetPage])
                        Log.i("DETAIL3", list.value[pager.targetPage])
                    }

                    HorizontalPager(state = pager, Modifier.fillMaxWidth()) { page ->
                        Column {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = list.value[page],
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Center
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
                                            Text("Loading")
                                        } else if (uiState.isLoading != true && uiState.error != null) {
                                            Text("ERROR")
                                        } else if (uiState.isLoading != true && uiState.amMeaning != null) {
                                            MarkdownContent(uiState.amMeaning!!)
                                        }
                                    }
                                },
                                secondTabContent = {
                                    textAi.value.let { uiState ->
                                        if (uiState.isLoading == true) {
                                            Text("Loading")
                                        } else if (uiState.isLoading != true && uiState.error != null) {
                                            Text("ERROR")
                                        } else if (uiState.isLoading != true && uiState.enMeaning != null) {
                                            MarkdownContent(uiState.amMeaning!!)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
        Box(Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter){
            FootInteraction(isFavorite)
        }
    }
}


@Composable
fun MarkdownContent(markdownText: String) {
    MarkdownText(
        markdown = markdownText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}


@Composable
fun TwoTabLayout(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    firstTabTitle: String = "Tab 1",
    secondTabTitle: String = "Tab 2",
    firstTabContent: @Composable () -> Unit,
    secondTabContent: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {

            Tab(
                selected = selectedTabIndex == 0,
                onClick = { onTabSelected(0) },
                text = {
                    Text(
                        text = firstTabTitle,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            )


            Tab(
                selected = selectedTabIndex == 1,
                onClick = { onTabSelected(1) },
                text = {
                    Text(
                        text = secondTabTitle,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }

        // Content based on selected tab
        when (selectedTabIndex) {
            0 -> firstTabContent()
            1 -> secondTabContent()
        }
    }
}

@Composable
fun FootInteraction(isFavorite: MutableState<Boolean>) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            focusedElevation = 6.dp
        ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            InteractionButton(
                icon = if (isFavorite.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite",
                tintColor = if (isFavorite.value) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                onClick = { isFavorite.value = !isFavorite.value },
                label = "Like",
                isSelected = isFavorite.value
            )

            InteractionButton(
                icon = null,
                painter = painterResource(R.drawable.baseline_content_copy_24),
                contentDescription = "Copy",
                onClick = { /* Copy functionality */ },
                label = "Copy"
            )


            InteractionButton(
                icon = Icons.Default.Share,
                contentDescription = "Share",
                onClick = { /* Share functionality */ },
                label = "Share"
            )
        }
    }
}

@Composable
private fun InteractionButton(
    icon: ImageVector? = null,
    painter: Painter? = null,
    contentDescription: String,
    onClick: () -> Unit,
    label: String,
    tintColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    isSelected: Boolean = false
) {

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
        shape = RoundedCornerShape(16.dp),
        color = Color.Transparent
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(48.dp)
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = contentDescription,
                        tint = animatedTint,
                        modifier = Modifier
                            .size(28.dp)
                            .scale(scale)
                    )
                } else if (painter != null) {
                    Icon(
                        painter = painter,
                        contentDescription = contentDescription,
                        tint = animatedTint,
                        modifier = Modifier
                            .size(28.dp)
                            .scale(scale)
                    )
                }
            }

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = animatedTint,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TwoTabLayoutPreview() {
    var selectedTab by remember { mutableIntStateOf(0) }

    MaterialTheme {
        TwoTabLayout(
            selectedTabIndex = selectedTab,
            onTabSelected = { selectedTab = it },
            firstTabTitle = "Home",
            secondTabTitle = "Settings",
            firstTabContent = {
                Text(
                    text = "Home Content",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            secondTabContent = {
                Text(
                    text = "Settings Content",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
    }
}




//@Preview
//@Composable
//fun Mark() {
////    val modifier = Modifier
////    Column(
////        modifier = modifier.fillMaxSize(),
////        horizontalAlignment = Alignment.CenterHorizontally
////    ) {
////        ItemText("Hello world", "uri://cscscs/scscsc", modifier = Modifier)
////        Spacer(modifier = Modifier.height(20.dp))
////        ItemTextAI(modifier = modifier)
////    }
//}


fun getMarkText(): String {
    return "# \uD83D\uDCDA Virtual Book Store\n" +
            "\n" +
            "A modern digital library system built with Spring Boot that offers comprehensive book management features.\n" +
            "\n" +
            "## \uD83C\uDF1F Features\n" +
            "\n" +
            "- \uD83D\uDCD6 Search and view books\n" +
            "- \uD83D\uDED2 Purchase books online\n" +
            "- \uD83D\uDCF1 Borrow digital copies\n" +
            "- \uD83D\uDD10 Secure authentication system\n" +
            "- \uD83D\uDCBE Persistent data storage\n" +
            "- ⚡ Redis caching for improved performance\n" +
            "- \uD83D\uDD11 Role-based access control\n" +
            "\n" +
            "## \uD83C\uDFD7\uFE0F Architecture\n" +
            "\n" +
            "The project follows a modular architecture with the following components:\n" +
            "\n" +
            "- **App Module**: Contains the main class and also data initialization before loading the application context\n" +
            "- **Api Module**: Defines the endpoints\n" +
            "- **Commons Module**: Contains DTOs and exception class\n" +
            "- **REST Client Module**: Independent module for API testing and logging\n" +
            "- **Core Module**: Contains the main services throughout the app and application level configurations\n" +
            " - **Security Module**: Configurations for security\n" +
            " - **Domain Module**: Defines the Business logic of the app\n" +
            " - **Data Module**: Persistence  layer for recording entities\n" +
            " - **Web Module**: UI repository (Coming Soon)"
}
