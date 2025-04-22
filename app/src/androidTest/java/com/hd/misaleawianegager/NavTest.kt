package com.hd.misaleawianegager

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hd.misaleawianegager.presentation.MisaleScreen
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
import com.hd.misaleawianegager.presentation.navigateSingleTopTo
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//
//@RunWith(AndroidJUnit4::class)
//class NavTest {
//
//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
//
//   lateinit var navController : NavHostController
//
//    @Before
//    fun setupNavHost() {
//        composeTestRule.setContent {
//           navController = rememberNavController()
//            TestNavHost(navController)
//        }
//    }
//
//    @Test
//    fun navTest(){
//
//        composeTestRule.onNodeWithText("ሀብታም በከብቱ ድሃ በጉልበቱ").performClick()
//
//        assertEquals(MisaleScreen.Detail.route.plus("/home/ሀብታም_በከብቱ_ድሃ_በጉልበቱ"), navController.currentDestination?.route)
//    }
//
//}
//
//
//@Composable
//fun TestNavHost(navHostController: NavHostController){
//    val list = MutableStateFlow( listOf(
//            "ሀምሳ ሎሚ ለአንድ ሰው ሸክሙ ነው",
//    "ሀ ሳይሉ ጥፈት ውል ሳይዙ ሙግት",
//    "ሀ ባሉ ተዝካር በሉ",
//    "ሀብታም ለሀብታም ይጠቃቀሱ ድሀ ለድሃ ይለቃቀሱ",
//    "ሀብታም ሊሰጥ የድሃ ሙርጥ አበጠ",
//    "ሀብታም ቢወድቅ ከሰገነት ድሃ ቢወድቅ ከመሬት",
//    "ሀብታም በምጽዋቱ፣ ድሀ በጸሎቱ",
//    "ሀብታም በከብቱ ድሃ በጉልበቱ",
//    "ሀብታም በወርቁ ድሃ በጨርቁ",
//    "ሀብታም በገንዘቡ ይኮራል ድሃ በጥበቡ ይከብራል",
//    "ሀብታም በገንዘቡ ድሃ በጉልበቱ",
//    "ሀብታም ቢሰጥ አበደረ እንጅ አልሰጠም",
//    "ሀብታም ገንዘብ ቢሰጥ በምትኩ",
//    "ሀብታም ነው መባል ያኮራል ድሃ ነው መባል ያሳፍራል",
//    "ሀብታም እንደሚበላለት፣ድሃ እንደሚከናወንለት",
//    "ሀብታም ያለ ድሃ አይኮራም፣ ድሃ ያለ ሀብታም አይበላም",
//    "ሀብታም ገንዘቡን ያስባል፣ ድሃ ቀኑን ይቆጥራል",
//    "ሀብትና ዕውቀት አይገኝ አንድነት",
//    "ሀብት የጠዋት ጤዛ ነው",
//    "ሁለተኛ ግፌ፣ ጫንቃዬን ተገርፌ ልብሴን መገፈፌ",
//    "ሁለተኛ ግፍ ልብሴን ይገፍ ጀርባዬን ይገረፍ",
//    "ሁለተኛ ጥፋት ቆሞ ማንቀላፋት",
//    "ሁለተኛ ጥፋት ከገበያ ማንቀላፋት"
//    ))
//
//    NavHost(navController = navHostController,
//        startDestination = MisaleScreen.Home.route,
//        modifier = Modifier){
//
//        composable(MisaleScreen.Home.route){
//
//            val list2 = list.collectAsStateWithLifecycle()
//            val scrollPos = remember {
//                mutableStateOf(0)
//            }
//            Log.i("NAV", "${scrollPos.value}")
//         //   HomeContent(homeData = list2,  onHomeEvent = {},  onSettingEvent = {  }, toDetail =   { home , arg, arg2 ->
//                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$home/$arg/$arg2"))
//            }, scrollIndex = scrollPos)
//        }
//
//
//
//        composable(MisaleScreen.Fav.route){
//
//            val list2 = list.collectAsStateWithLifecycle()
//            val scrollIndex = remember {
//                mutableStateOf(0)
//            }
//            FavScreen(favList = list2 , toDetail =  { from, text, first->
//                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
//            }, setScroll = {}, scrollIndex = scrollIndex)
//        }
//
//        composable(MisaleScreen.Recent.route){
//            val context = LocalContext.current
//
//            val list2 = list.collectAsStateWithLifecycle()
//            val scrollIndex = remember {
//                mutableStateOf(0)
//            }
//            Recent(recentData = list2 , toDetail =  { from, text , first->
//                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
//            }, setScroll = {}, scrollIndex = scrollIndex)
//
//        }
//
//        composable(MisaleScreen.Search.route){
//
//            val list2 = list.collectAsStateWithLifecycle()
//            SearchScreen(list = list as State<List<String>> , from = "home" , search = { } , toDest =  {
//                navHostController.popBackStack()
//            }, toDetail = { from, text , first ->
//                navHostController.navigateSingleTopTo(MisaleScreen.Detail.route.plus("/$from/$text/$first"))
//              }
//            )
//        }
//
//
//
//        composable(MisaleScreen.Detail.route.plus("/{from}/{arg2}/{arg3}"),
//            arguments = listOf(navArgument("from") { type = NavType.StringType },
//                navArgument("arg2") { type = NavType.StringType },
//                navArgument("arg3") { type = NavType.StringType } ),
//            deepLinks = listOf(
//                navDeepLink { uriPattern = "misale://selected/{from}/{arg2}/{arg3}" }
//            )){ backStackEntry ->
//           // val viewModel = hiltViewModel<DetailViewModel>()
//            val arg1 = backStackEntry.arguments?.getString("from")
//            val arg2 = if(arg1 == "search")backStackEntry.arguments?.getString("arg2")!!.replace('_', ' ')
//            else backStackEntry.arguments?.getString("arg2")
//            val arg3 = backStackEntry.arguments?.getString("arg3")
//            if(arg1 == "home"){
//               // viewModel.onEvent(DetailEvent.LoadLetter(arg3!!))
//            }
//            if(arg1 == "fav"){
//              //  viewModel.onEvent(DetailEvent.LoadFav)
//            }
//            if(arg1 == "recent"){
//               // viewModel.onEvent(DetailEvent.LoadRecent)
//            }
//
//            val list2 = list.collectAsStateWithLifecycle()
//            Selected(list = list as State<List<String>>, text = arg2!! , arg1!!) {
//                navHostController.popBackStack()
//            }
//        }
//    }
//}
//
