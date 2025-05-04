package com.hd.misaleawianegager.presentation.component.onboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.R
import kotlinx.coroutines.launch
import mx.platacard.pagerindicator.PagerIndicator
import mx.platacard.pagerindicator.PagerIndicatorOrientation


@Composable
fun MisaleAwiOnboardingScreen(
    onOnboardingComplete: () -> Unit
) {
    val pageCount = OnboardingConstants.totalPages // Now 3
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
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                PagerIndicator(
                    pagerState = pagerState,
                    activeDotColor = Color.Green,
                    dotColor = Color.LightGray,
                    dotCount = 5,
                    orientation = PagerIndicatorOrientation.Horizontal
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (!isLastPage) {
                        CoolFinishButton(modifier = Modifier, onClick = {
                            onOnboardingComplete.invoke()
                        })
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    CoolNextButton(isLastPage = isLastPage, modifier = Modifier , onClick = {
                        if (isLastPage) {
                            onOnboardingComplete()
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    })
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
fun CoolNextButton(
    isLastPage: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val amharicText = if (isLastPage) "ጨርስ" else "ቀጣይ"
    val englishText = if (isLastPage) "Finish" else "Next"
    val icon = if (isLastPage) Icons.Default.Check else Icons.Default.ArrowForward

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                )
            )
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            elevation = null,
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(icon, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = amharicText, style = MaterialTheme.typography.labelLarge)
                    Text(text = englishText, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}


@Composable
fun CoolFinishButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val amharicText = "አቇርጥ"
    val englishText = "Skip"


    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                )
            )
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            elevation = null,
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.width(12.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = amharicText, style = MaterialTheme.typography.labelLarge)
                    Text(text = englishText, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}



@Preview(showBackground = true, backgroundColor = 0xFF000000) // Preview with dark background
@Composable
fun MisaleAwiOnboardingScreenMultiPagePreview() {
    MaterialTheme {
        MisaleAwiOnboardingScreen(onOnboardingComplete = { })
    }
}
