package com.vist.fins.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vist.fins.Screen
import com.vist.fins.components.SelectableBoxListItem
import com.vist.fins.data.OperationViewModel
import com.vist.fins.data.OperationViewModelFactory
import com.vist.fins.data.entities.Category
import com.vist.fins.ui.theme.AppBG

@Composable
fun FinancialCategoryListScreen(
    navController: NavController,
    categoryName: String?
) {
    val context = LocalContext.current
    val mOperationViewModel: OperationViewModel = viewModel(
        factory = OperationViewModelFactory(context.applicationContext as Application)
    )
    val categoriesList by mOperationViewModel.readAllCategories.observeAsState(initial = emptyList())
    var selectedCategoryId by remember { mutableStateOf(0L) }



    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.CreateCategoryScreen.route)
                    },
                ) {
                    Text(
                        text = "+",
                        fontWeight = FontWeight.Light,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))
    
                ExtendedFloatingActionButton(
                    onClick = {
                        Log.i("MYTAG", categoriesList[selectedCategoryId.toInt() - 5].toString())

                        // Ось це треба колись переробити на шось адекватне бляха
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("selectedCategoryId", selectedCategoryId)

                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("selectedCategoryName", categoriesList[selectedCategoryId.toInt() - 5].categoryName)


                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("selectedCategoryRes", categoriesList[selectedCategoryId.toInt() - 5].imageRes)

                        navController.popBackStack()
//                        navController.navigate(Screen.CreateNewFinancialOperationScreen.route)
                    },
                    text = {
                        Text(
                            text = "Обрати",
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                )

            }
        },

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBG)
                .padding(padding)
                .padding(top = 20.dp)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize(),
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                items(categoriesList) { i ->
                    Box(
                        modifier = Modifier
                            .padding(0.dp)
                    ) {
                        SelectableBoxListItem(
                            title = i.categoryName,
                            iconName = i.imageRes,
                            isSelected = (selectedCategoryId == i.categoryId),
                        ) {
                            selectedCategoryId = i.categoryId
                        }
                    }
                }
            }
        }
    }
}