package com.vist.fins.screens

//import com.vist.fins.components.FinancialOperationListItemsColumn
import android.app.Application
import android.util.Log
import android.widget.Space
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.vist.fins.Screen
import com.vist.fins.components.*
import com.vist.fins.components.accordionComponent.AccordionMonthGroup
import com.vist.fins.data.*
import com.vist.fins.ui.theme.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val mOperationViewModel: OperationViewModel = viewModel(
        factory = OperationViewModelFactory(context.applicationContext as Application)
    )

    val operationsList = mOperationViewModel.readAllData.observeAsState(initial = emptyList()).value
    val categoriesList = mOperationViewModel.readAllCategories.observeAsState(initial = emptyList()).value
    val incomeValue = mOperationViewModel.readAllIncomeValues.observeAsState().value ?: 0f
    val expenseValue = mOperationViewModel.readAllExpenseValues.observeAsState().value ?: 0f

//    val categoryWithOperations = mOperationViewModel.getCategoryWithOperations("Living")
    val dao = FinanceDatabase.getInstance(context.applicationContext).financeDao

    val categoriesListWithOperations = categoriesList.map {
        mOperationViewModel.getCategoryWithOperations(it.categoryName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBG)
            .padding(top = 20.dp, start = 0.dp, end = 0.dp, bottom = 50.dp)
    ) {
        TopView(
            mOperationViewModel = mOperationViewModel,
            navController,
            incomeValue = incomeValue,
            expenseValue = expenseValue
        )
        Spacer(modifier = Modifier.height(20.dp))

        AccordionMonthGroup(operationsList, categoriesList, mOperationViewModel)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopView(
    mOperationViewModel: OperationViewModel,
    navController: NavController,
    incomeValue: Float,
    expenseValue: Float
) {
    data class FinancialGroupViewItem(val title: String, val value: Float, val id: Int)

    val listOfGroups = listOf(
        FinancialGroupViewItem(OperationType.incomeUA, incomeValue, 0),
        FinancialGroupViewItem(OperationType.expenseUA, expenseValue, 1),
    )
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            FinancialBalanceView("Баланс", incomeValue-expenseValue)
            
            Spacer(modifier = Modifier.height(15.dp))
            
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(0.75f),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(listOfGroups,  key = { it.id }) {
                    Box(
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(
                                durationMillis = 300
                            )
                        )
                    ) {
                        FinancialGroupView(
                            it.title,
                            it.value
                        )
                    }
                }
            }

        }

        CreateNewFinanceOperationBtn(
            onClick = {
                navController.navigate(Screen.CreateNewFinancialOperationScreen.route) {
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun CreateNewFinanceOperationBtn(onClick: ()-> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .size(60.dp)
    ) {
        Text(
            text = "+",
            fontWeight = FontWeight.Light,
            fontSize = 34.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
        )
    }
}