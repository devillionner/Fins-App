@file:OptIn(ExperimentalAnimationApi::class)

package com.vist.fins

import android.widget.RemoteViews.RemoteCollectionItems
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.vist.fins.screens.*
import com.vist.fins.ui.theme.AppBG
import com.vist.fins.ui.theme.DialogBG

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController
) {
//    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(500, easing = FastOutSlowInEasing))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(500, easing = FastOutSlowInEasing))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(500, easing = FastOutSlowInEasing))
            }
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.ProfileScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(500, easing = FastOutSlowInEasing))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(500, easing = FastOutSlowInEasing))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(500, easing = FastOutSlowInEasing))
            }
        ) {
            ProfileScreen(navController = navController)
        }
        composable(
            route = Screen.CreateNewFinancialOperationScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(500, easing = FastOutSlowInEasing))
            },
//            exitTransition = {},
            popExitTransition = {
                fadeOut(animationSpec = tween(500, easing = FastOutSlowInEasing))
            }
        ) {backStackEntry ->
            CreateNewFinancialOperationScreen(navController = navController)
        }
        composable(
            route = Screen.FinancialCategoryListScreen.route + "?categoryName={categoryName}",
            arguments = listOf(
                navArgument("categoryName") {
                    type = NavType.StringType
                    nullable = true
                }
            ),
            enterTransition = {
                fadeIn(animationSpec = tween(500, easing = FastOutSlowInEasing))
            },
//            exitTransition = {},
            popExitTransition = {
                fadeOut(animationSpec = tween(500, easing = FastOutSlowInEasing))
            }
        ) {backStackEntry ->
            val categoryName = requireNotNull(backStackEntry.arguments).getString("categoryName")
            FinancialCategoryListScreen(navController = navController, categoryName = categoryName)
        }
        composable(
            route = Screen.CreateCategoryScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(500, easing = FastOutSlowInEasing))
            },
//            exitTransition = {},
            popExitTransition = {
                fadeOut(animationSpec = tween(500, easing = FastOutSlowInEasing))
            }
        ) {backStackEntry ->
//            val categoryName = requireNotNull(backStackEntry.arguments).getString("categoryName")
            CreateCategoryScreen(navController = navController)
        }
    }
}

//@Composable
//fun CourseText() {
//    Column() {
//        Text(
//            text = "То не так складно, як здається",
//            fontSize = 18.sp
//        )
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = "Просто дій. Більше практики - краще розуміння 🔥",
//            fontSize = 18.sp
//        )
//    }
//}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    bottomBarState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)),
        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(durationMillis = 0, easing = LinearOutSlowInEasing)),
        content = {
            BottomNavigation(
                modifier = modifier,
                backgroundColor = AppBG,
                contentColor = Color.White,
            ) {
                val backStackEntry = navController.currentBackStackEntryAsState()

                items.forEach { item ->
                    val selected = item.route == backStackEntry.value?.destination?.route
                    BottomNavigationItem(
                        selected = selected,
                        onClick = { onItemClick(item) },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.White.copy(alpha = 0.2f),
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 10.sp
                            )
                        },
                        alwaysShowLabel = true,
                    )
                }
            }
        }
    )
}
