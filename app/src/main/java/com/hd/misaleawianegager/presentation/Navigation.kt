package com.hd.misaleawianegager.presentation

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
import com.hd.misaleawianegager.presentation.component.home.HomeEvent
import com.hd.misaleawianegager.presentation.component.home.HomeViewModel
import com.hd.misaleawianegager.presentation.component.home.HomeWrapper
import com.hd.misaleawianegager.presentation.component.onboard.MisaleAwiOnboardingScreen
import com.hd.misaleawianegager.presentation.component.recent.Recent
import com.hd.misaleawianegager.presentation.component.recent.RecentViewModel
import com.hd.misaleawianegager.presentation.component.search.SearchScreen
import com.hd.misaleawianegager.presentation.component.search.SearchViewModel
import com.hd.misaleawianegager.presentation.component.selected.DetailEvent
import com.hd.misaleawianegager.presentation.component.selected.DetailViewModel
import com.hd.misaleawianegager.presentation.component.selected.Selected
import com.hd.misaleawianegager.presentation.component.setting.SettingEvent
import com.hd.misaleawianegager.utils.DIRECTION
import com.hd.misaleawianegager.utils.compose.favList
import com.hd.misaleawianegager.utils.compose.recentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val ANIMATION_DURATION = 500

@Composable
fun MisaleBodyContent(navHostController: NavHostController,
                      modifier: Modifier,
                      letterType: String ,
                      onSettingEvent: (SettingEvent) -> Unit,
                      startDestination: String,
                      bottomBarShownDetails: State<Boolean>
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
                route = MisaleScreen.Home.route,
                enterTransition = {
                    // Use initialState (where we came from) and targetState (where we are going)
                    val initialIndex = getRouteIndex(initialState.destination.route)
                    val targetIndex = getRouteIndex(targetState.destination.route)

                    if (initialIndex < targetIndex) {
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIMATION_DURATION))
                    } else {
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIMATION_DURATION))
                    }
                },
                exitTransition = {
                    val targetIndex = getRouteIndex(targetState.destination.route)
                    val currentIndex = getRouteIndex(initialState.destination.route)

                    if (targetIndex > currentIndex) {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIMATION_DURATION))
                    } else {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIMATION_DURATION))
                    }
                }
            )
            {
                viewModelHome.onEvent(HomeEvent.LoadLetter(letterType))
                val list = viewModelHome.homeStateFlow.collectAsStateWithLifecycle()
                val scrollPos = viewModelHome.scrollValue.collectAsStateWithLifecycle()
                HomeWrapper(
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
                    },
                    drag = {
                        if (it == DIRECTION.LEFT) {
                            navHostController.navigateSingleTopTo(MisaleScreen.Fav.route)
                        } else {
                            navHostController.navigateSingleTopTo(MisaleScreen.Search.route)
                        }
                    }
                )
            }

            composable(MisaleScreen.Fav.route,
                enterTransition = {
                    // Use initialState (where we came from) and targetState (where we are going)
                    val initialIndex = getRouteIndex(initialState.destination.route)
                    val targetIndex = getRouteIndex(targetState.destination.route)

                    if (initialIndex < targetIndex) {
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIMATION_DURATION))
                    } else {
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIMATION_DURATION))
                    }
                },
                exitTransition = {
                    val targetIndex = getRouteIndex(targetState.destination.route)
                    val currentIndex = getRouteIndex(initialState.destination.route)

                    if (targetIndex > currentIndex) {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(ANIMATION_DURATION))
                    } else {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(ANIMATION_DURATION))
                    }
                }
            ) {
                viewModelDetailFav.readFavList()
                val list = viewModelDetailFav.favStateFlow.collectAsStateWithLifecycle()
                val scrollIndex = viewModelDetailFav.scrollValue.collectAsStateWithLifecycle()
                FavScreen(list, toDetail = { from, text, first ->
                    navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
                     },
                    setScroll = viewModelDetailFav::setScroll,
                    scrollIndex = scrollIndex,
                    drag = {
                        if (it == DIRECTION.LEFT) {
                            navHostController.navigateSingleTopTo(MisaleScreen.Recent.route)
                        } else {
                            navHostController.navigateSingleTopTo(MisaleScreen.Home.route)
                        }
                    }
                   )
            }

            composable(MisaleScreen.Recent.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(ANIMATION_DURATION)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(ANIMATION_DURATION)
                    )
                }
            ) {

                viewModelDetailRecent.readText()
                val list = viewModelDetailRecent.recentStateFlow.collectAsStateWithLifecycle()
                val scrollIndex = viewModelDetailRecent.scrollValue.collectAsStateWithLifecycle()
                Recent(recentData = list,
                    toDetail = { from, text, first ->
                    navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
                     },
                    setScroll = viewModelDetailRecent::setScroll,
                    scrollIndex = scrollIndex,
                    drag = {
                      navHostController.navigateSingleTopTo(MisaleScreen.Fav.route)
                    }
                  )
            }

            composable(MisaleScreen.Search.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(ANIMATION_DURATION)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(ANIMATION_DURATION)
                    )
                }
            ) {

                val list = viewModelSearch.searchResult.collectAsStateWithLifecycle()
                val word = viewModelSearch.wordResult

                SearchScreen(list = list, wordFlow = word, from = "ዋና", onSearchEvent = viewModelSearch::onEvent,
                    toDest = {
                    navHostController.popBackStack()
                },
                    toDetail = { from, text, first ->
                    navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
                 },
                 drag = {
                     navHostController.navigateSingleTopTo(MisaleScreen.Home.route)
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

                val scope = rememberCoroutineScope()

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
                    onNextPage = {

                        if (arg1 != "የቅርብ") {

                            while (recentList.size > 150) {
                                recentList.removeAt(0)
                            }

                            if (!recentList.contains(it)) {
                                recentList.add(it)
                            } else {
                                recentList.remove(it)
                                recentList.add(it)
                            }
                        }
                        viewModelDetail.onEvent(DetailEvent.LoadAIContent(it))
                    },
                    onFavoriteToggle = { item ->
                        if (favListHere.contains(item)) {
                            favListHere.remove(item)
                        } else {
                            favListHere.add(java.lang.String(item).toString())
                        }
                    },
                    getSingleText = {
                        viewModelDetail.onEvent(DetailEvent.LoadSingle)
                    },
                    goBack = {
                        val popped = navHostController.popBackStack()
                        if (!popped) {
                            navHostController.navigate(MisaleScreen.Home.route)
                        }
                    },
                    showBtmBarInDetail = bottomBarShownDetails
                )
             }
        }
    }

fun getRouteIndex(route: String?): Int = orderMapNav[route] ?: 2

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
    }