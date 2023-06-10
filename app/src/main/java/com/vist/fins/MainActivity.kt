package com.vist.fins

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.vist.fins.data.FinanceDatabase
import com.vist.fins.data.entities.Category
import com.vist.fins.data.entities.Operation
import com.vist.fins.data.entities.Subcategory
import com.vist.fins.data.entities.relations.OperationSubcategoryCrossRef
import com.vist.fins.ui.theme.AppBG
import com.vist.fins.ui.theme.FinsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinsTheme {
                val navController = rememberAnimatedNavController()
                val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                when (navBackStackEntry?.destination?.route) {
                    Screen.HomeScreen.route -> {
                        // Show BottomBar
                        bottomBarState.value = true
                    }
                    Screen.ProfileScreen.route -> {
                        // Show BottomBar
                        bottomBarState.value = true
                    }
                    Screen.CreateNewFinancialOperationScreen.route -> {
                        // Hide BottomBar
                        bottomBarState.value = false
                    }
                    Screen.FinancialCategoryListScreen.route -> {
                        // Hide BottomBar
                        bottomBarState.value = false
                    }
                    Screen.CreateCategoryScreen.route -> {
                        // Hide BottomBar
                        bottomBarState.value = false
                    }
                }

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .drawBehind {
                                    val borderSize = 1.dp.toPx()
                                    drawLine(
                                        color = Color.White.copy(alpha = 0.1f),
                                        start = Offset(0f, 0f),
                                        end = Offset(size.width, 0f),
                                        strokeWidth = borderSize
                                    )
                                },
                            items = listOf(
                                BottomNavItem(
                                    route = Screen.HomeScreen.route,
                                    title = "Головна",
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    route = Screen.ProfileScreen.route,
                                    title = "Акаунт",
                                    icon = Icons.Default.Person
                                )
                            ),
                            navController = navController,
                            bottomBarState = bottomBarState,
                            onItemClick = {
                                navController.navigate(it.route) {
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                ) {
//                    innerPadding ->
//                    Box(modifier = Modifier.padding(innerPadding)) {
                        Navigation(navController = navController)
//                    }
                }
            }
        }
    }
}

