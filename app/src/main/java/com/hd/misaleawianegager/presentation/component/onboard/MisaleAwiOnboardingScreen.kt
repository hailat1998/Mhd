package com.hd.misaleawianegager.presentation.component.onboard


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.presentation.component.setting.SettingEvent
import kotlinx.coroutines.launch
import mx.platacard.pagerindicator.PagerIndicator
import mx.platacard.pagerindicator.PagerIndicatorOrientation

@Composable
fun MisaleAwiOnboardingScreen(
    onSettingEvent: (SettingEvent) -> Unit,
    onOnboardingComplete: () -> Unit
) {

    val pageCount = OnboardingConstants.totalPages
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val scope = rememberCoroutineScope()


    val isLastPage by remember {
        derivedStateOf { pagerState.currentPage == pageCount - 1 }
    }

    val imageResList = listOf(
        R.drawable.harar,
        R.drawable.lalibela,
        R.drawable.axum
    )

    Scaffold(
        topBar = {
            if (!isLastPage) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clickable {
                                onSettingEvent.invoke(SettingEvent.ShowBoarding(true))
                                onOnboardingComplete.invoke()
                            }
                            .padding(13.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Skip",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier.shadow(shape = CircleShape, elevation = 4.dp, )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Skip",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.height(15.dp))
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()){
                    PagerIndicator(
                        pagerState = pagerState,
                        activeDotColor = MaterialTheme.colorScheme.primary,
                        dotColor = MaterialTheme.colorScheme.onPrimary,
                        dotCount = 3,
                        orientation = PagerIndicatorOrientation.Horizontal
                    )
                }
                Spacer(Modifier.height(15.dp))

               val text = if(isLastPage) "Finish/ጨርስ" else "Next/ቀጣይ"

                TextButton(text) {
                    if (isLastPage) {
                        onSettingEvent.invoke(SettingEvent.ShowBoarding(true))
                        onOnboardingComplete()
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { pageIndex ->
            StyledOnboardingPage(
                imageRes = imageResList[pageIndex]
            ) {
                when (pageIndex) {
                    0 -> Page1Content()
                    1 -> Page2Content()
                    2 -> Page3Content()
                }
            }
        }
    }
}

@Composable
fun TextButton(text: String, onClick: () -> Unit,) {
    Box(Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .clickable {
                    onClick.invoke()
                }
                .padding(vertical = 20.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000) // Preview with dark background
@Composable
fun MisaleAwiOnboardingScreenMultiPagePreview() {
    MaterialTheme {
        MisaleAwiOnboardingScreen(onOnboardingComplete = { }, onSettingEvent = {})
    }
}
