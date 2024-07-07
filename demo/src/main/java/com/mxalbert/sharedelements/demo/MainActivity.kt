package com.mxalbert.sharedelements.demo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mxalbert.sharedelements.SharedElementsRoot
import com.mxalbert.sharedelements.demo.nav.BottomNavigationBar

sealed class AppRoute(val route: String) {
    object Home : AppRoute("home")
    object Search : AppRoute("search")
    object Notifications : AppRoute("notifications")
    object Profile : AppRoute("profile")
    data class DetailsRoute(val index: Int) : AppRoute("details/{index}")
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colors = if (isSystemInDarkTheme()) lightColors() else lightColors()) {

                Surface (
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background

                ){
                    // Initialize the NavController
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    val currentDestination = navBackStackEntry?.destination

                    Scaffold(bottomBar = {
                        if (currentDestination?.route in listOf(AppRoute.Home.route,AppRoute.Search.route,AppRoute.Profile.route,AppRoute.Profile.route,AppRoute.Notifications.route,"other_routes_to_show_bottom_nav")) {
                            BottomNavigationBar(navController = navController, currentDestination)
                        }
                    }) { paddingVal -> SharedElementsRoot {
                            NavHost(navController = navController, startDestination = AppRoute.Home.route, modifier = Modifier.padding(paddingVal)) {
                                composable(AppRoute.Home.route) {
                                    UserListScreen { i ->
                                        Log.e("TAG", "sending id: "+i )
                                        navController.navigate("details/$i")
                                    }
                                }
                                composable(AppRoute.DetailsRoute(-1).route,
                                    arguments = listOf(navArgument("index") { type = NavType.IntType })
                                ) { backStackEntry ->
                                    val index = backStackEntry.arguments?.getInt("index")!!
                                    Log.e("TAG", "rediving id "+index)
                                    UserDetailsScreen(index) {
                                        navController.navigateUp()
                                    }
                                }
                                composable(AppRoute.Search.route) {
                                    SearchScreen()
                                }
                                composable(AppRoute.Profile.route) {
                                    ProfileScreen()
                                }
                                composable(AppRoute.Notifications.route) {
                                    NotificationsScreen()
                                }


                            }
                    }

                }
            }
        }
    }

}

@Composable
fun ProfileScreen() {

}

@Composable
fun NotificationsScreen() {

}

@Composable
fun SearchScreen() {


}

@Composable
fun HomeScreen(navController: NavHostController) {
    /*var useCards by rememberSaveable { mutableStateOf(true) }
    Crossfade(useCards) {
        if (it) {
            UserListRoot(navController)
        } else {
            UserCardsRoot()
        }
    }*/
}

@Composable
private fun Demo() {
    var useCards by rememberSaveable { mutableStateOf(true) }
    Column {
       /* TopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },
            actions = {
                IconButton(onClick = { useCards = !useCards }) {
                    Text(text = "SWITCH", textAlign = TextAlign.Center)
                }
            },
            modifier = Modifier.zIndex(1f)
        )*/
/*        Crossfade(useCards) {
//            UserListRoot()
            if (it) {
                UserListRoot(navController)
            } else {
                UserCardsRoot()
            }
        }*/
    }
}
}
