package com.vist.fins.screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.vist.fins.components.InputField
import com.vist.fins.components.SelectableBoxListItem
import com.vist.fins.data.OperationType
import com.vist.fins.data.OperationViewModel
import com.vist.fins.data.OperationViewModelFactory
import com.vist.fins.data.baseCategoriesIconsList
import com.vist.fins.data.entities.Category
import com.vist.fins.ui.theme.AppBG

@Composable
fun CreateCategoryScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val inputViewModel = viewModel<InputViewModelCreateCategory>()
    val mOperationViewModel: OperationViewModel = viewModel(
        factory = OperationViewModelFactory(context.applicationContext as Application)
    )

    val categoryName: String by inputViewModel.categoryName.observeAsState("")
    var selectedCategoryIcon by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (
                        inputViewModel.categoryName.value.toString().isEmpty() ||
                        selectedCategoryIcon == ""
                    ) {
                        Toast.makeText(context, "Будь ласка, заповніть всі поля", Toast.LENGTH_SHORT).show()
                    } else {
                        insertCategoryInDB(
                            categoryName = categoryName,
                            imageRes = selectedCategoryIcon,
                            mOperationViewModel = mOperationViewModel
                        )
                        navController.popBackStack()

                        Toast.makeText(context, "Категорія успішно створена", Toast.LENGTH_SHORT).show()
                    }
                },
                icon = {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                },
                text = {
                    Text(
                        text = "Створити",
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBG)
                .padding(padding)
                .padding(20.dp)
        ) {
            InputField(
                name = categoryName,
                label = "Назва категорії",
                keyboardType = KeyboardType.Text,
                onValChange = { inputViewModel.onInputValueChange(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize(),
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(baseCategoriesIconsList) {i ->
                    Box(
                        modifier = Modifier
                            .padding(0.dp)
                    ) {
                        SelectableBoxListItem(
                            iconName = i,
                            isTitle = false,
                            isSelected = (selectedCategoryIcon == i),
                        ) {
                            selectedCategoryIcon = i
                        }
                    }
                }
            }
        }
    }
}

private fun insertCategoryInDB(categoryName: String, imageRes: String, mOperationViewModel: OperationViewModel) {
    val category = Category(
        categoryName = categoryName,
        imageRes = imageRes,
    )

    mOperationViewModel.insertCategory(category)
}

class InputViewModelCreateCategory(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val categoryName: LiveData<String> = savedStateHandle.getLiveData("categoryName", "")
    val categoryType: LiveData<String> = savedStateHandle.getLiveData("categoryType", "")

    fun onInputValueChange(newValue: String) {
        savedStateHandle["categoryName"] = newValue
    }
}