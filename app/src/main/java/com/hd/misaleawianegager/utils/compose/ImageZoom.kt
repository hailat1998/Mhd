package com.hd.misaleawianegager.utils.compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun RememberZoomOutImageAnimation(): Float {

    val infiniteTransition = rememberInfiniteTransition(label = "ZoomAnimation")

    val scale by infiniteTransition.animateFloat(
        initialValue = 2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                delayMillis = 2000,
                durationMillis = 10000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ZoomScale"
    )

    return scale
}

@Composable
fun ZoomOutImageBackground(painter: Painter) {

    val scale = RememberZoomOutImageAnimation()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .scale(scale),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        )
    }
}


@Preview
@Composable
fun SS(){
    Box(Modifier.fillMaxSize()) {
        MarkdownText("\n" +
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
                "### Thank you for checking out our project\n", Modifier.fillMaxSize())
    }
}