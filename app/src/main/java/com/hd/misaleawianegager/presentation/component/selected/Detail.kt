package com.hd.misaleawianegager.presentation.component.selected

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalClipboardManager
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
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.utils.compose.HorizontalShimmer
import com.hd.misaleawianegager.utils.compose.ZoomOutImageBackground
import com.hd.misaleawianegager.utils.compose.favList
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class) // Opt-in for Pager APIs
@Composable
fun Selected(
    list: State<List<String>>, // List of items for the pager
    favList: SnapshotStateList<String>, // <<< ADDED: Pass the favourites list state
    textAi: DetailUiState, // State for AI-generated text (meanings)
    text: String, // The initially selected text item
    from: String, // Source identifier ("ምርጥ", "ዋና", "የቅርብ" for pager)
    onPageChanged: (String) -> Unit, // Callback when pager page changes
    modifier: Modifier = Modifier // Optional modifier for the whole Selected composable
    // Removed unused parameters: toDest, showModalBottomSheet
) {
    // Removed unused offsetX, animatedOffsetX

    // Calculate random background info once per composition of Selected
    val res = remember(from, text) { // Remember based on key inputs if background should change
        val randomIndex = Random.nextInt(1, 8)
        when (randomIndex) {
            1 -> Pair(R.drawable.harar, "https://en.wikipedia.org/wiki/Harar")
            2 -> Pair(R.drawable.axum, "https://en.wikipedia.org/wiki/Obelisk_of_Axum")
            3 -> Pair(R.drawable.tiya, "https://en.wikipedia.org/wiki/Tiya_(archaeological_site)")
            4 -> Pair(R.drawable.gondar, "https://en.wikipedia.org/wiki/Fasil_Ghebbi")
            5 -> Pair(R.drawable.konso, "https://en.wikipedia.org/wiki/Konso_people")
            6 -> Pair(R.drawable.sofumer, "https://en.wikipedia.org/wiki/Sof_Omar_Caves")
            else -> Pair(R.drawable.lalibela, "https://en.wikipedia.org/wiki/Lalibela")
        }
    }

    // Use Scaffold for basic Material structure (can be customized further)
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent // Make Scaffold background transparent if needed over image
    ) { paddingValues -> // Use paddingValues from Scaffold
        Box(
            modifier = Modifier
                .padding(paddingValues) // Apply Scaffold padding
                .fillMaxSize()
        ) {

            // Background Image (Assumed Composable)
            ZoomOutImageBackground(painter = painterResource(id = res.first))

            when (from) {
                "ምርጥ", "ዋና", "የቅርብ" -> { // Pager mode
                    val currentList = list.value
                    if (currentList.isEmpty()) {
                        // Show centered loading indicator if list is empty
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = Color.White) // Adjust color for background
                        }
                    } else {
                        // Calculate initial page, handle case where text might not be in the list
                        val initialIndex = remember(currentList, text) {
                            val index = currentList.indexOf(text)
                            if (index == -1) 0 else index // Default to 0 if not found
                        }

                        val pagerState = rememberPagerState(
                            initialPage = initialIndex,
                            pageCount = { currentList.size }
                        )

                        // Effect to call onPageChanged when pager settles on a new page
                        LaunchedEffect(pagerState) {
                            snapshotFlow { pagerState.currentPage }
                                .distinctUntilChanged() // Avoid multiple calls for the same page
                                .collect { currentPage ->
                                    // Ensure index is valid before accessing list
                                    if (currentPage >= 0 && currentPage < currentList.size) {
                                        Log.d("SelectedPager", "Page changed to: $currentPage, Item: ${currentList[currentPage]}")
                                        onPageChanged(currentList[currentPage])
                                    }
                                }
                        }

                        // Effect to scroll to the correct initial page when the key changes
                        LaunchedEffect(initialIndex) {
                            if (pagerState.currentPage != initialIndex) {
                                Log.d("SelectedPager", "Scrolling to initial index: $initialIndex")
                                pagerState.scrollToPage(initialIndex)
                            }
                        }

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize() // Pager should fill the available space
                                .background(color = Color.Transparent) // Transparent over the background image
                        ) { page ->
                            // Ensure page index is valid
                            if (page >= 0 && page < currentList.size) {
                                // --- Use NeatItemDisplay ---
                                NeatItemDisplay(
                                    item = currentList[page],
                                    info = res.second, // Same info URL for all pages
                                    textAi = textAi,
                                    favList = favList // Pass favList state
                                    // Add onNavigateBack callback if needed
                                    // onNavigateBack = { ... }
                                )
                            } else {
                                // Optional: Placeholder if page index is somehow invalid
                                Box(modifier=Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text("Invalid Page: $page", color = Color.Red)
                                }
                            }
                        }
                    }
                }
                else -> { // Single item view
                    // --- Use NeatItemDisplay ---
                    NeatItemDisplay(
                        item = text,
                        info = res.second,
                        textAi = textAi,
                        favList = favList // Pass favList state
                        // Add onNavigateBack callback if needed
                        // onNavigateBack = { ... }
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class) // For CenterAlignedTopAppBar
@Composable
fun NeatItemDisplay(
    item: String,
    info: String, // URL for info icon
    textAi: DetailUiState,
    favList: SnapshotStateList<String>,
    // Optional: Add callbacks for actions if navigation/ViewModel interaction is needed
    onNavigateBack: (() -> Unit)? = null, // Example callback
    modifier: Modifier = Modifier // Allow passing external modifiers
) {
    // --- State Variables ---
    val listContains by remember(item, favList.size) { derivedStateOf { favList.contains(item) } }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) } // Save tab index across config changes
    val tabs = listOf("አማርኛ", "English")
    val scrollState = rememberScrollState()
    val currentState = textAi // Read state once per composition
    LaunchedEffect(Unit) {

        delay(5000L)

        Log.i("DETAIL SCREEN", "${textAi.amMeaning}")
        Log.i("DETAIL SCREEN22", "${textAi.isLoading}")

    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar( // Or use TopAppBar for start alignment
                title = {
                    Text(
                        item, // Show item in title, ellipsized if too long
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    if (onNavigateBack != null) {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    // Info Icon
                    IconButton(onClick = { /* Handle Info click */
                        try {
                            val view = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(info) }
                            context.startActivity(view)
                        } catch (e: Exception) { Log.e("NeatItemDisplay", "Failed to open info URL", e) }
                    }) {
                        Icon(Icons.Default.Info, contentDescription = "More Info")
                    }
                    // Favourite Icon
                    IconButton(onClick = { /* Handle Favourite click */
                        if (listContains) favList.remove(item) else favList.add(item)
                    }) {
                        Icon(
                            imageVector = if (listContains) Icons.Sharp.Favorite else Icons.Sharp.FavoriteBorder,
                            contentDescription = if (listContains) "Remove Favourite" else "Add Favourite",
                            tint = if (listContains) Color.Red else LocalContentColor.current
                        )
                    }
                    // Share Icon
                    IconButton(onClick = { /* Handle Share click */
                        val shareText = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "$item\n\n${currentState.amMeaning ?: currentState.enMeaning ?: ""}") // Share item and current meaning
                        }
                        context.startActivity(Intent.createChooser(shareText, "Share Proverb"))
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    // Copy Icon
                    IconButton(onClick = { /* Handle Copy click */
                        clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(item))
                        // Optional Toast
                    }) {
                        Icon(painterResource(id = R.drawable.baseline_content_copy_24), contentDescription = "Copy")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f) // Slightly transparent
                )
            )
        }
    ) { paddingValues -> // Content area with padding from Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply padding from Scaffold
                .verticalScroll(scrollState) // Make content scrollable
                .padding(horizontal = 16.dp), // Horizontal padding for the content column
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp)) // Space below app bar

            // --- Display Item Text ---
            Text(
                text = item,
                style = MaterialTheme.typography.headlineMedium, // Prominent style
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp) // Space below item text
            )

            // --- Tabs ---
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(), // Let TabRow take full width
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                indicator = { tabPositions ->
                    if (tabPositions.isNotEmpty() && selectedTabIndex < tabPositions.size) {
                        TabRowDefaults.PrimaryIndicator( // Use standard indicator
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            height = 3.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        modifier = Modifier.padding(vertical = 14.dp) // Increase tab height slightly
                    )
                }
            } // End of TabRow

            Spacer(modifier = Modifier.height(16.dp)) // Space below tabs

            // --- Loading / Error / Markdown Content Area ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 200.dp), // Ensure minimum space for content
                contentAlignment = Alignment.TopCenter
            ) {
                if (currentState.isLoading == true) {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
                } else if (currentState.error != null) {
                    Text(
                        text = "Error loading details: ${currentState.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                } else {
                    // Show content based on selected tab
                    when (selectedTabIndex) {
                        0 -> currentState.amMeaning?.takeIf { it.isNotBlank() }?.let { MarkdownContent(it) }
                            ?: Text("ትርጉም አልተገኘም", modifier = Modifier.padding(16.dp), textAlign = TextAlign.Center)

                        1 -> currentState.enMeaning?.takeIf { it.isNotBlank() }?.let { MarkdownContent(it) }
                            ?: Text("Meaning not available", modifier = Modifier.padding(16.dp), textAlign = TextAlign.Center)
                    }
                }
            } // End of Content Box

            Spacer(modifier = Modifier.height(16.dp)) // Padding at the very bottom

        } // End of Content Column
    } // End of Scaffold
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
