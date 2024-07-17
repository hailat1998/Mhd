package com.hd.misaleawianegager.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hd.misaleawianegager.presentation.component.home.HomeContent
import com.hd.misaleawianegager.presentation.component.home.HomeViewModel

@Composable
fun MisaleBodyContent(navHostController: NavHostController, modifier: Modifier){
    NavHost(navController = navHostController,
        startDestination = MisaleScreen.Home.route,
        modifier = modifier){
        composable(MisaleScreen.Home.route){
            val context = LocalContext.current
             val viewModel: HomeViewModel = hiltViewModel()
            viewModel.homeDataFeed(context)
            HomeContent(homeData = viewModel.homeStateFlow)
        }
        composable(MisaleScreen.Fav.route){

        }
        composable(MisaleScreen.Recent.route){

        }
        composable(MisaleScreen.Search.route){

        }
        composable(MisaleScreen.Selected.route){

        }
        composable(MisaleScreen.Setting.route){

        }

    }
}