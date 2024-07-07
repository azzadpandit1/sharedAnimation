package com.mxalbert.sharedelements.demo.nav

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mxalbert.sharedelements.demo.AppColor
import com.mxalbert.sharedelements.demo.AppRoute
import com.mxalbert.sharedelements.demo.R

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentDestination: NavDestination?
) {
    val items = listOf(
        AppRoute.Home, AppRoute.Search, AppRoute.Profile, AppRoute.Notifications
    )
val local = LocalContext.current
    val activeColor = AppColor().Orange
    val inactiveColor = Color.LightGray

    BottomNavigation(
        elevation = 10.dp,
        backgroundColor = Color.White
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    when (screen) {
                        AppRoute.Home -> Icon(
                            Icons.Default.Home, contentDescription = null, tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) activeColor else inactiveColor, modifier = Modifier.size(30.dp)
                        )
                        AppRoute.Search -> Icon(
                            Icons.Default.List, contentDescription = null, tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) activeColor else inactiveColor, modifier = Modifier.size(30.dp)
                        )
                        AppRoute.Profile -> Icon(
                            Icons.Default.Star, contentDescription = null, tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) activeColor else inactiveColor, modifier = Modifier.size(30.dp)
                        )
                        AppRoute.Notifications -> Icon(
                            Icons.Default.Menu, contentDescription = null, tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) activeColor else inactiveColor, modifier = Modifier.size(30.dp)
                        )
                        else -> {}
                    }
                },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                selectedContentColor = activeColor,
                unselectedContentColor = inactiveColor
            )
        }
    }
}
