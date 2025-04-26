package com.hd.misaleawianegager.presentation.component.selected

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.utils.compose.ZoomOutImageBackground
import com.hd.misaleawianegager.utils.compose.favList
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlin.random.Random

@Composable
fun Selected(list : State<List<String>> = mutableStateOf(emptyList()),
             text: String,
             from: String,
             toDest: (s : String) -> Unit,
             showModalBottomSheet: MutableState<Boolean>
) {

    val offsetX by remember { mutableFloatStateOf(-250f) }
    val animatedOffsetX by animateFloatAsState(targetValue = offsetX)

val res = remember {
    val randomIndex = Random.nextInt(from = 1, until = 8)
    when (randomIndex) {
        1 -> Pair(R.drawable.harar, " https://en.wikipedia.org/wiki/Harar")
        2 -> Pair(R.drawable.axum, "https://en.wikipedia.org/wiki/Obelisk_of_Axum")
        3 -> Pair(R.drawable.tiya, "https://en.wikipedia.org/wiki/Tiya_(archaeological_site)")
        4 -> Pair(R.drawable.gondar, "https://en.wikipedia.org/wiki/Fasil_Ghebbi")
        5 -> Pair(R.drawable.konso, "https://en.wikipedia.org/wiki/Konso_people")
        6 -> Pair(R.drawable.sofumer, "https://en.wikipedia.org/wiki/Sof_Omar_Caves")
        else -> Pair(R.drawable.lalibela, "https://en.wikipedia.org/wiki/Lalibela")
    }
}

    Scaffold {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {

            ZoomOutImageBackground(painter = painterResource(id = res.first))


            when (from) {
             "ምርጥ" , "ዋና", "የቅርብ" -> {
                    if (list.value.isEmpty()) {
                        CircularProgressIndicator()
                    } else {
                        val pagerState = rememberPagerState(
                            initialPage = 0,
                            0f
                        ) { list.value.size }

                        val index = remember { list.value.indexOf(text) }

                        LaunchedEffect(Unit) {
                            pagerState.scrollToPage(index)
                        }

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth().background(color = Color.Transparent)
                        ) { page ->
                            ItemText(item = list.value[page],
                                res.second ,
                                modifier = Modifier.graphicsLayer {  translationY = if(showModalBottomSheet.value) animatedOffsetX else 0f }
                            )
                        }
                    }
                }
                else -> {
                    ItemText(item = text, res.second, modifier = Modifier.graphicsLayer {  translationY = if(showModalBottomSheet.value) animatedOffsetX else 0f }
                    )
                }
            }
        }
    }
}


@Composable
fun ItemText(item: String,
             info: String,
             modifier: Modifier,
             ){
    var listContains by remember { mutableStateOf(favList.contains(item)) }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Box(contentAlignment = Alignment.Center, modifier =  modifier.fillMaxSize()
        .background(Color.Transparent)) {

        Column {
            Row {

                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.Info, null, modifier = Modifier.clickable {
                    val view = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(info)
                    }
                    context.startActivity(view)
                }.padding(20.dp),
                    tint = Color.Black
                )
                if (listContains) {
                    Icon(Icons.Sharp.Favorite, null,
                        modifier = Modifier
                            .clickable {
                                listContains = false
                                favList.remove(item)
                            }
                            .padding(20.dp), tint = Color.Black
                       )
                } else {
                    Icon(Icons.Sharp.FavoriteBorder, null,
                        modifier = Modifier
                            .clickable {
                                listContains = true
                                favList.add(item)
                            }
                            .padding(20.dp), tint = Color.Black
                     )
                }
                Icon(Icons.Default.Share, null, modifier = Modifier
                    .clickable {
                        val shareText = Intent(Intent.ACTION_SEND).apply {
                            type = "drawable/text/plain"
                            putExtra(Intent.EXTRA_TEXT, item)
                        }
                        val chooserIntent = Intent.createChooser(shareText, "Misaleawi Anegager")
                        context.startActivity(chooserIntent)
                    }
                    .padding(20.dp), tint = Color.Black)
                Icon(painterResource(id = R.drawable.baseline_content_copy_24),
                    null,
                    modifier = Modifier
                        .clickable {
                            val annotatedString = buildAnnotatedString {
                                withStyle(style = SpanStyle(textDecoration = TextDecoration.None)) {
                                    append(item)
                                }
                            }
                            clipboardManager.setText(annotatedString)
                        }
                        .padding(20.dp),
                    tint = Color.Black
                )
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = item,
                    modifier = Modifier.padding(
                        top = 20.dp,
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    ) ,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}



@Preview
@Composable
fun Mark(){
    Box(Modifier.fillMaxSize()
        .verticalScroll(rememberScrollState())) {

        MarkdownText(markdown = "\n" +
                "# \uD83D\uDCDA Virtual Book Store\n" +
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
                " - **Web Module**: UI repository (Coming Soon)\n" +
                "\n" +
                "## \uD83D\uDD0C API Endpoints\n" +
                "\n" +
                "### Authentication Endpoints\n" +
                "```http\n" +
                "POST    /api/auth/signup     # Create new user account\n" +
                "POST    /api/auth/login      # Authenticate user\n" +
                "POST    /api/auth/refresh    # Refresh authentication token\n" +
                "POST    /api/auth/logout     # End user session\n" +
                "```\n" +
                "\n" +
                "### Book Management Endpoints\n" +
                "```http\n" +
                "GET     /api/book/all           # Retrieve all books\n" +
                "GET     /api/book/{id}          # Get book by ID\n" +
                "POST    /api/book/save          # Add new book (ADMIN only)\n" +
                "GET     /api/book/search        # Search books\n" +
                "GET     /api/book/countByAuthor # Get books count by author\n" +
                "GET     /api/book/countByAuthors # Get books count by multiple authors\n" +
                "```\n" +
                "\n" +
                "### Borrowing Endpoints\n" +
                "```http\n" +
                "POST    /api/borrow/set        # Borrow a book (Authenticated users)\n" +
                "PUT     /api/borrow/update     # Update borrowing status (ADMIN only)\n" +
                "```\n" +
                "\n" +
                "## \uD83D\uDE80 Getting Started\n" +
                "\n" +
                "### Prerequisites\n" +
                "\n" +
                "- Java 11 or higher\n" +
                "- Gradle\n" +
                "- Redis Server\n" +
                "\n" +
                "### Installation\n" +
                "\n" +
                "1. **Redis Setup**\n" +
                "   ```bash\n" +
                "   # For Ubuntu/Debian\n" +
                "   sudo apt-get install redis-server\n" +
                "   sudo systemctl start redis\n" +
                "\n" +
                "   # For MacOS\n" +
                "   brew install redis\n" +
                "   brew services start redis\n" +
                "   ```\n" +
                "\n" +
                "2. **Build the Project**\n" +
                "   ```bash\n" +
                "   ./gradlew build\n" +
                "   ```\n" +
                "\n" +
                "3. **Run the Application**\n" +
                "   ```bash\n" +
                "   ./gradlew :app:bootRun\n" +
                "   ```\n" +
                "\n" +
                "## \uD83E\uDDEA Testing\n" +
                "\n" +
                "The project includes a dedicated REST client module for testing API endpoints. You can find it in the [restclient](restclient) repository.\n" +
                "\n" +
                "\n" +
                "## \uD83D\uDD12 Security\n" +
                "\n" +
                "- JWT-based authentication\n" +
                "- Role-based access control (ADMIN, USER)\n" +
                "- Secure endpoint protection\n" +
                "- Token refresh mechanism\n" +
                "\n" +
                "## \uD83D\uDEE0\uFE0F Built With\n" +
                "\n" +
                "- Spring Boot\n" +
                "- Spring Security\n" +
                "- Redis\n" +
                "- Gradle\n" +
                "- JPA/Hibernate\n" +
                "\n" +
                "\n" +
                "## Features to be added in the future\n" +
                "\n" +
                "- Payment integration for buying books\n" +
                "- Recommendation system\n" +
                "\n" +
                "\n" +
                "## License\n" +
                "\n" +
                "This project is licensed under [MIT](LICENSE.md)  License.\n" +
                "\n" +
                "\n" +
                "\n" +
                "## Contribution\n" +
                "For contribution see [CONTRIBUTION](CONTRIBUTION.md)\n" +
                "\n" +
                "\n" +
                "\n" +
                "### Thank you for checking out our project\n",
             Modifier.fillMaxSize())
    }
}


