package com.hd.misaleawianegager.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hd.misaleawianegager.presentation.component.home.HomeContent
import com.hd.misaleawianegager.presentation.component.home.HomeViewModel
import com.hd.misaleawianegager.presentation.component.search.SearchScreen
import com.hd.misaleawianegager.presentation.component.search.SearchViewModel
import com.hd.misaleawianegager.presentation.component.selected.Selected

@Composable
fun MisaleBodyContent(navHostController: NavHostController, modifier: Modifier){
    NavHost(navController = navHostController,
        startDestination = MisaleScreen.Home.route,
        modifier = modifier){
        composable(MisaleScreen.Home.route){

             val viewModel: HomeViewModel = hiltViewModel()
            val list = viewModel.homeStateFlow.collectAsStateWithLifecycle()
            HomeContent(homeData = list, loadLetter = viewModel::onEvent ){ home , arg ->
                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$home/$arg"))
            }
        }
        composable(MisaleScreen.Fav.route){

        }
        composable(MisaleScreen.Recent.route){

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
           Selected(value = arg2!! , arg1!!) {
             navHostController.navigateSingleTopTo(arg1)
           }
        }
        composable(MisaleScreen.Setting.route){

        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }