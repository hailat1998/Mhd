package com.hd.misaleawianegager.presentation.component.selected

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.utils.compose.ZoomOutImageBackground
import com.hd.misaleawianegager.utils.compose.favList
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class) // Opt-in for Pager APIs
@Composable
fun Selected(
    viewModel: DetailViewModel,
    page: String,
    onNextPage: (String) -> Unit
) {

                    val textAi = viewModel.detailsAITextStateFlow.collectAsStateWithLifecycle()
                    val list = viewModel.detailStateFlow.collectAsStateWithLifecycle()

                Log.i("DETAIL", "${textAi.value.amMeaning}")

                Log.i("DETAIL", "${textAi.value.isLoading}")

                LaunchedEffect(Unit) {
                    delay(3000L)
                    Log.i("DETAIL2", "${textAi.value}")
                    Log.i("DETAIL2", "${textAi.value}")
                }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {

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

                HorizontalPager(state = pager) { page ->
                    Column {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = list.value[page],
                                style = MaterialTheme.typography.displayLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    textAi.value.let { uiState ->
                        if (uiState.isLoading == true) {
                            Text("Loading")
                        } else if (uiState.isLoading != true && uiState.error != null) {
                            Text("ERROR")
                        } else if (uiState.isLoading != true && uiState.amMeaning != null) {
                            MarkdownContent(uiState.amMeaning!!)
                        }
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class) // For CenterAlignedTopAppBar
@Composable
fun NeatItemDisplay(

) {

}

// Use the previously fixed MarkdownContent composable
@Composable
fun MarkdownContent(markdownText: String) {
    MarkdownText(
        markdown = markdownText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Vertical padding for markdown
    )
}




@Preview
@Composable
fun Mark() {
//    val modifier = Modifier
//    Column(
//        modifier = modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        ItemText("Hello world", "uri://cscscs/scscsc", modifier = Modifier)
//        Spacer(modifier = Modifier.height(20.dp))
//        ItemTextAI(modifier = modifier)
//    }
}


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
