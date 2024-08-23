package com.hd.misaleawianegager.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.hd.misaleawianegager.presentation.component.fav.FavScreen
import com.hd.misaleawianegager.presentation.component.fav.FavViewModel
import com.hd.misaleawianegager.presentation.component.home.HomeContent
import com.hd.misaleawianegager.presentation.component.home.HomeEvent
import com.hd.misaleawianegager.presentation.component.home.HomeViewModel
import com.hd.misaleawianegager.presentation.component.recent.Recent
import com.hd.misaleawianegager.presentation.component.recent.RecentViewModel
import com.hd.misaleawianegager.presentation.component.search.SearchScreen
import com.hd.misaleawianegager.presentation.component.search.SearchViewModel
import com.hd.misaleawianegager.presentation.component.selected.DetailEvent
import com.hd.misaleawianegager.presentation.component.selected.DetailViewModel
import com.hd.misaleawianegager.presentation.component.selected.Selected
import com.hd.misaleawianegager.presentation.component.setting.SettingEvent
import com.hd.misaleawianegager.utils.compose.LifeCycleObserver
import com.hd.misaleawianegager.utils.compose.favList

@Composable
fun MisaleBodyContent(navHostController: NavHostController, modifier: Modifier,
                      letterType: String , onSettingEvent: (SettingEvent) -> Unit){

    val viewModelHome: HomeViewModel = hiltViewModel()

    val viewModelRecent = hiltViewModel<RecentViewModel>()

    val viewModelFav: FavViewModel = hiltViewModel()

    NavHost(navController = navHostController,
        startDestination = MisaleScreen.Home.route,
        modifier = modifier){

        composable(MisaleScreen.Home.route){

            viewModelHome.onEvent(HomeEvent.LoadLetter(letterType))
            val list = viewModelHome.homeStateFlow.collectAsStateWithLifecycle()
            val scrollPos = viewModelHome.scrollValue.collectAsStateWithLifecycle()
            HomeContent(homeData = list,  onHomeEvent = viewModelHome::onEvent,  onSettingEvent, toDetail =   { home , arg, arg2 ->
                  viewModelHome.onEvent(HomeEvent.WriteText(arg))
                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$home/$arg/$arg2"))
            }, scrollIndex = scrollPos)
        }



        composable(MisaleScreen.Fav.route){
               viewModelFav.readFavList()
            val list = viewModelFav.favStateFlow.collectAsStateWithLifecycle()
            val scrollIndex = viewModelFav.scrollValue.collectAsStateWithLifecycle()
            FavScreen(list , toDetail =  { from, text, first->
                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
            }, setScroll = viewModelFav::setScroll, scrollIndex = scrollIndex)
        }

        composable(MisaleScreen.Recent.route){
            val context = LocalContext.current
             viewModelRecent.readText(context)
            val list = viewModelRecent.recentStateFlow.collectAsStateWithLifecycle()
            val scrollIndex = viewModelRecent.scrollValue.collectAsStateWithLifecycle()
            Recent(recentData = list , toDetail =  { from, text , first->
                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
            }, setScroll = viewModelRecent::setScroll, scrollIndex = scrollIndex)

        }

        composable(MisaleScreen.Search.route){
            val viewModel = hiltViewModel<SearchViewModel>()
            val list = viewModel.searchResult.collectAsStateWithLifecycle()
            SearchScreen(list = list, from = "home" , search = viewModel::search , toDest =  {
                navHostController.popBackStack()
            }, toDetail = { from, text , first ->
                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
              }
            )
        }



        composable(MisaleScreen.Detail.route.plus("/{from}/{arg2}/{arg3}"),
            arguments = listOf(navArgument("from") { type = NavType.StringType },
                               navArgument("arg2") { type = NavType.StringType },
                               navArgument("arg3") { type = NavType.StringType } ),
            deepLinks = listOf(
                navDeepLink { uriPattern = "misale://selected/{from}/{arg2}/{arg3}" }
            )){ backStackEntry ->
            val viewModel = hiltViewModel<DetailViewModel>()
            val arg1 = backStackEntry.arguments?.getString("from")
            val arg2 = if(arg1 == "search")backStackEntry.arguments?.getString("arg2")!!.replace('_', ' ')
                               else backStackEntry.arguments?.getString("arg2")
            val arg3 = backStackEntry.arguments?.getString("arg3")
            if(arg1 == "home"){
                viewModel.onEvent(DetailEvent.LoadLetter(arg3!!))
            }
            if(arg1 == "fav"){
                viewModel.onEvent(DetailEvent.LoadFav)
                }
            if(arg1 == "recent"){
                viewModel.onEvent(DetailEvent.LoadRecent)
            }

            val list = viewModel.detailStateFlow.collectAsStateWithLifecycle()
           Selected(list = list, text = arg2!! , arg1!!) {
             navHostController.popBackStack()
           }
        }
    }
 }



fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
    }