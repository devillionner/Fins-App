package com.vist.fins

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)
//{
//    object Home: BottomBarScreen(
//        route = Screen.HomeScreen.route,
//        title = "Home",
//        icon = Icons.Default.Home
//    )
//    object Profile: BottomBarScreen(
//        route = Screen.ProfileScreen.route,
//        title = "Profile",
//        icon = Icons.Default.Person
//    )
//}
