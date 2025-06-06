package com.hd.misaleawianegager.presentation

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.hd.misaleawianegager.presentation.DataProvider.orderMapNav
import com.hd.misaleawianegager.presentation.component.fav.FavScreen
import com.hd.misaleawianegager.presentation.component.fav.FavViewModel
import com.hd.misaleawianegager.presentation.component.home.HomeContent
import com.hd.misaleawianegager.presentation.component.home.HomeEvent
import com.hd.misaleawianegager.presentation.component.home.HomeViewModel
import com.hd.misaleawianegager.presentation.component.onboard.MisaleAwiOnboardingScreen
import com.hd.misaleawianegager.presentation.component.recent.Recent
import com.hd.misaleawianegager.presentation.component.recent.RecentViewModel
import com.hd.misaleawianegager.presentation.component.search.SearchScreen
import com.hd.misaleawianegager.presentation.component.search.SearchViewModel
import com.hd.misaleawianegager.presentation.component.selected.DetailEvent
import com.hd.misaleawianegager.presentation.component.selected.DetailViewModel
import com.hd.misaleawianegager.presentation.component.selected.Selected
import com.hd.misaleawianegager.presentation.component.setting.SettingEvent
import com.hd.misaleawianegager.utils.compose.favList

const val ANIMATION_DURATION = 500

@Composable
fun MisaleBodyContent(navHostController: NavHostController,
                      modifier: Modifier,
                      letterType: String ,
                      onSettingEvent: (SettingEvent) -> Unit,
                      startDestination: String
                      ) {

            val viewModelHome: HomeViewModel = hiltViewModel()

            val viewModelDetailRecent: RecentViewModel = hiltViewModel()

            val viewModelDetailFav: FavViewModel = hiltViewModel()

            val viewModelSearch: SearchViewModel = hiltViewModel()

        NavHost(
            navController = navHostController,
            startDestination = startDestination,
            modifier = modifier
        ) {

            composable(MisaleScreen.Onboarding.route) {
                MisaleAwiOnboardingScreen(onSettingEvent) { navHostController.navigateSingleTopTo(MisaleScreen.Home.route) }
            }

            composable(
                MisaleScreen.Home.route,
                enterTransition = {
                    val prevRoute = navHostController.previousBackStackEntry?.destination?.route
                    val prevIndex = if (prevRoute != null) orderMapNav[prevRoute] ?: -1 else -1
                    val currentIndex = orderMapNav[MisaleScreen.Home.route] ?: 2 // Default to its known position

                    if (prevIndex >= currentIndex) {

                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(ANIMATION_DURATION)
                        )
                    } else {

                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(ANIMATION_DURATION)
                        )
                    }
                },
                exitTransition = {
                    val targetRoute = targetState.destination.route
                    val targetIndex = if (targetRoute != null) orderMapNav[targetRoute] ?: -1 else -1
                    val currentIndex = orderMapNav[MisaleScreen.Home.route] ?: 2

                    if (targetIndex > currentIndex) {

                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(ANIMATION_DURATION)
                        )
                    } else if (targetIndex < currentIndex) {

                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(ANIMATION_DURATION)
                        )
                    } else {

                        ExitTransition.None
                    }
                }
            )
            {
                viewModelHome.onEvent(HomeEvent.LoadLetter(letterType))
                val list = viewModelHome.homeStateFlow.collectAsStateWithLifecycle()
                val scrollPos = viewModelHome.scrollValue.collectAsStateWithLifecycle()
                HomeContent(
                    homeData = list,
                    onHomeEvent = viewModelHome::onEvent,
                    onSettingEvent,
                    toDetail = { home, arg, arg2 ->
                        viewModelHome.onEvent(HomeEvent.WriteText(arg))
                        navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$home/$arg/$arg2"))
                    },
                    scrollIndex = scrollPos,
                    toBoarding = {
                        navHostController.navigateSingleTopTo(MisaleScreen.Onboarding.route)
                    }
                )
            }

            composable(MisaleScreen.Fav.route,
                enterTransition = {
                    val prevRoute = navHostController.previousBackStackEntry?.destination?.route
                    val prevIndex = if (prevRoute != null) orderMapNav[prevRoute] ?: -1 else -1
                    val currentIndex = orderMapNav[MisaleScreen.Fav.route] ?: 1 // Default to its known position

                    if (prevIndex > currentIndex) {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(ANIMATION_DURATION)
                        )
                    } else if (prevIndex < currentIndex) {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(ANIMATION_DURATION)
                        )
                    } else {
                        EnterTransition.None
                    }
                },
                exitTransition = {
                    val targetRoute = targetState.destination.route
                    val targetIndex = if (targetRoute != null) orderMapNav[targetRoute] ?: -1 else -1
                    val currentIndex = orderMapNav[MisaleScreen.Home.route] ?: 2

                    if (targetIndex > currentIndex) {

                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(ANIMATION_DURATION)
                        )
                    } else if (targetIndex < currentIndex) {

                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(ANIMATION_DURATION)
                        )
                    } else {

                        ExitTransition.None
                    }
                }
            ) {
                viewModelDetailFav.readFavList()
                val list = viewModelDetailFav.favStateFlow.collectAsStateWithLifecycle()
                val scrollIndex = viewModelDetailFav.scrollValue.collectAsStateWithLifecycle()
                FavScreen(list, toDetail = { from, text, first ->
                    navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
                     }, setScroll = viewModelDetailFav::setScroll, scrollIndex = scrollIndex,
                   )
            }

            composable(MisaleScreen.Recent.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(ANIMATION_DURATION)
                    )
                }) {
                val context = LocalContext.current
                viewModelDetailRecent.readText(context)
                val list = viewModelDetailRecent.recentStateFlow.collectAsStateWithLifecycle()
                val scrollIndex = viewModelDetailRecent.scrollValue.collectAsStateWithLifecycle()
                Recent(recentData = list, toDetail = { from, text, first ->
                    navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
                }, setScroll = viewModelDetailRecent::setScroll, scrollIndex = scrollIndex,
                  )
            }

            composable(MisaleScreen.Search.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(ANIMATION_DURATION)
                    )
                }) {

                val list = viewModelSearch.searchResult.collectAsStateWithLifecycle()
                val word = viewModelSearch.wordResult

                SearchScreen(list = list, wordFlow = word, from = "ዋና", onSearchEvent = viewModelSearch::onEvent, toDest = {
                    navHostController.popBackStack()
                }, toDetail = { from, text, first ->
                    navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
                 }
               )
            }

            composable(MisaleScreen.Detail.route.plus("/{from}/{arg2}/{arg3}"),
                arguments = listOf(navArgument("from") { type = NavType.StringType },
                    navArgument("arg2") { type = NavType.StringType },
                    navArgument("arg3") { type = NavType.StringType }),
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern = "misale://selected/{from}/{arg2}/{arg3}"
                    }
                ),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(ANIMATION_DURATION)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(ANIMATION_DURATION)
                    )
                }
            ) { backStackEntry ->

                val viewModelDetail: DetailViewModel = hiltViewModel()

                val arg1 = backStackEntry.arguments?.getString("from")
                val arg2 = if (arg1 == "search") backStackEntry.arguments?.getString("arg2")!!
                    .replace('_', ' ')
                else backStackEntry.arguments?.getString("arg2")
                val arg3 = backStackEntry.arguments?.getString("arg3")
                if (arg1 == "ዋና") {
                    viewModelDetail.onEvent(DetailEvent.LoadLetter(arg3!!))
                }
                if (arg1 == "ምርጥ") {
                    viewModelDetail.onEvent(DetailEvent.LoadFav)
                }
                if (arg1 == "የቅርብ") {
                    viewModelDetail.onEvent(DetailEvent.LoadRecent)
                }


                val favListHere = remember { favList.toMutableStateList() }

                Selected(
                    viewModelDetail.detailStateFlow,
                    viewModelDetail.detailsAITextStateFlow,
                    page = arg2!!,
                    from = arg1!!,
                    favListHere = favListHere,
                     onNextPage =  {
                    viewModelDetail.onEvent(DetailEvent.LoadAIContent(it))
                   },
                    onFavoriteToggle = { item ->
                        if (favListHere.contains(item)) {
                            favListHere.remove(item)
                        } else {
                            favListHere.add(item)
                        }
                    })
            }
        }
    }


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
    }