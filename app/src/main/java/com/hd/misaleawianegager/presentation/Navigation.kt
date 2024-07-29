package com.hd.misaleawianegager.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.hd.misaleawianegager.presentation.component.fav.FavScreen
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
import com.hd.misaleawianegager.utils.compose.favList

@Composable
fun MisaleBodyContent(navHostController: NavHostController, modifier: Modifier,
                      letterType: String , onEvent: (SettingEvent) -> Unit){
    NavHost(navController = navHostController,
        startDestination = MisaleScreen.Home.route,
        modifier = modifier){
        composable(MisaleScreen.Home.route){
             val viewModel: HomeViewModel = hiltViewModel()
            viewModel.onEvent(HomeEvent.LoadLetter(letterType))
            val list = viewModel.homeStateFlow.collectAsStateWithLifecycle()
            HomeContent(homeData = list,  loadLetter = viewModel::onEvent, onEvent,  ){ home , arg, arg2 ->
                  viewModel.onEvent(HomeEvent.WriteText(arg))
                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$home/$arg/$arg2"))
            }
        }



        composable(MisaleScreen.Fav.route){
            FavScreen(favList) { from, text, first->
                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
            }
        }



        composable(MisaleScreen.Recent.route){
            val viewModel = hiltViewModel<RecentViewModel>()
            val list = viewModel.recentStateFlow.collectAsStateWithLifecycle()
            Recent(recentData = list ) { from, text , first->
                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
            }
        }



        composable(MisaleScreen.Search.route){
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(list = viewModel.searchResult, from = "home" , search = viewModel::search , toDest =  {
                navHostController.navigateSingleTopTo(MisaleScreen.Home.route)
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
            val arg2 = backStackEntry.arguments?.getString("arg2")
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
             navHostController.navigateSingleTopTo(arg1)
           }
        }
    }
 }



fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }