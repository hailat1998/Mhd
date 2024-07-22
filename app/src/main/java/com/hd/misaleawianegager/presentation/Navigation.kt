package com.hd.misaleawianegager.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hd.misaleawianegager.presentation.component.fav.FavScreen
import com.hd.misaleawianegager.presentation.component.fav.FavViewModel
import com.hd.misaleawianegager.presentation.component.home.HomeContent
import com.hd.misaleawianegager.presentation.component.home.HomeEvent
import com.hd.misaleawianegager.presentation.component.home.HomeViewModel
import com.hd.misaleawianegager.presentation.component.recent.Recent
import com.hd.misaleawianegager.presentation.component.recent.RecentViewModel
import com.hd.misaleawianegager.presentation.component.search.SearchScreen
import com.hd.misaleawianegager.presentation.component.search.SearchViewModel
import com.hd.misaleawianegager.presentation.component.selected.Selected
import com.hd.misaleawianegager.utils.favList
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay

@Composable
fun MisaleBodyContent(navHostController: NavHostController, modifier: Modifier){
    NavHost(navController = navHostController,
        startDestination = MisaleScreen.Home.route,
        modifier = modifier){
        composable(MisaleScreen.Home.route){
              val scope = rememberCoroutineScope()
             val viewModel: HomeViewModel = hiltViewModel()
            val list = viewModel.homeStateFlow.collectAsStateWithLifecycle()
            HomeContent(homeData = list, loadLetter = viewModel::onEvent ){ home , arg ->
                  viewModel.onEvent(HomeEvent.WriteText(arg))
                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$home/$arg"))
            }
        }
        composable(MisaleScreen.Fav.route){
            val viewModel = hiltViewModel<FavViewModel>()
            val list = viewModel.favStateFlow.collectAsStateWithLifecycle()
           // Log.i("FROM NAV" , list.size.toString())
            LaunchedEffect(Unit) {
                delay(1000L)
                //Log.i("FROM NAVLA" , list.size.toString())
            }
            FavScreen(favList = list) { from, text ->
                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text"))
            }
        }
        composable(MisaleScreen.Recent.route){
            val viewModel = hiltViewModel<RecentViewModel>()
            val list = viewModel.recentStateFlow.collectAsStateWithLifecycle()
            Recent(recentData = list ) {
                navHostController.navigateSingleTopTo(it)
            }
        }

        composable(MisaleScreen.Search.route){
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(list = viewModel.searchResult, from = "home" , search = viewModel::search) {
            }
        }
        composable(MisaleScreen.Detail.route.plus("/{from}/{arg2}"),
            arguments = listOf(navArgument("from") { type = NavType.StringType }, navArgument("arg2")
            { type = NavType.StringType } ) ){ backStackEntry ->
            val arg1 = backStackEntry.arguments?.getString("from")
            val arg2 = backStackEntry.arguments?.getString("arg2")
           Selected(text = arg2!! , arg1!!) {
             navHostController.navigateSingleTopTo(arg1)
           }
        }
        composable(MisaleScreen.Setting.route){

        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }