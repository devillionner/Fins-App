package com.vist.fins.screens

import android.annotation.SuppressLint
import android.app.Application
import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.title
import com.vist.fins.*
import com.vist.fins.R
import com.vist.fins.components.InputField
import com.vist.fins.components.InputFieldBG
import com.vist.fins.components.SelectableBoxListItem
import com.vist.fins.data.FinanceDatabase
import com.vist.fins.data.entities.Operation
import com.vist.fins.data.OperationViewModel
import com.vist.fins.data.OperationViewModelFactory
import com.vist.fins.data.OperationType
import com.vist.fins.data.entities.Category
import com.vist.fins.ui.theme.*
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateNewFinancialOperationScreen(
    navController: NavController
) {
    val inputViewModel = viewModel<InputViewModel>()
    val context = LocalContext.current

    val mOperationViewModel: OperationViewModel = viewModel(
        factory = OperationViewModelFactory(context.applicationContext as Application)
    )

    fun getOperationExpenseValuesFromDB(): List<Category> {
        val testDao = FinanceDatabase.getInstance(context).financeDao
        return testDao.getUpdatedAllCategories()
    }

    val categoriesList by mOperationViewModel.readAllCategories.observeAsState(initial = emptyList())


    val selectedCategoryResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<Long>("selectedCategoryId")?.observeAsState()
    selectedCategoryResult?.value?.let {
        // do something with the result
        inputViewModel.onInputCategoryChange(it)
    }

    val selectedCategoryNameResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("selectedCategoryName")?.observeAsState()
    selectedCategoryNameResult?.value?.let {
        inputViewModel.onInputCategoryNameChange(it)
    }

    val selectedCategoryResResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("selectedCategoryRes")?.observeAsState()
    selectedCategoryResResult?.value?.let {
        inputViewModel.onInputCategoryResChange(it)
    }

    val todayDate by remember { mutableStateOf(LocalDate.now()) }
    val formatter = DateTimeFormatter.ofPattern("dd MMMM");
    val formattedString = todayDate.format(formatter);
    val operationDate: String by inputViewModel.operationDate.observeAsState("")


    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = {
                        // TODO()
                    },
                    contentColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp,0.dp)
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
                        if (
                            inputViewModel.operationValue.value.toString().isEmpty() ||
                            inputViewModel.operationType.value.toString().isEmpty() ||
                            inputViewModel.selectedCategoryId.value?.toInt()!! <= 0
                        ) {
                            Toast.makeText(context, "Будь ласка, заповніть всі поля", Toast.LENGTH_SHORT).show()
                        } else if (
                            inputViewModel.operationValue.value.toString().isNotEmpty() &&
                            inputViewModel.operationType.value.toString().isNotEmpty() &&
                            inputViewModel.selectedCategoryId.value?.toInt()!! > 0
                        ) {
                            insertFinancialOperationInDB(
                                inputViewModel.operationValue.value.toString(),
                                inputViewModel.operationType.value.toString(),
                                categoriesList[inputViewModel.selectedCategoryId.value?.toInt()!! - 5].categoryName,
                                if (operationDate == "") formattedString else operationDate,
                                mOperationViewModel
                            )

                            navController.popBackStack()
                            Toast.makeText(context, "Операцію додано успішно", Toast.LENGTH_SHORT).show()
                        }
                    },
                    icon = {
                           Icon(Icons.Filled.Add, contentDescription = "Add")
                    },
                    text = {
                        Text(
                            text = "Додати",
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBG)
                .padding(20.dp),

        ) {
            item {
                InputFieldState(inputViewModel, navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun InputFieldState(inputViewModel: InputViewModel, navController: NavController) {
    val operationValue: String by inputViewModel.operationValue.observeAsState("")
    val operationType: String by inputViewModel.operationType.observeAsState("")
    val selectedCategoryId: Long by inputViewModel.selectedCategoryId.observeAsState(0)
    val selectedCategoryName: String by inputViewModel.selectedCategoryName.observeAsState("")
    val selectedCategoryRes: String by inputViewModel.selectedCategoryRes.observeAsState("")
    val itemsIndent = 30.dp
    
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Text(
            text = "Сума",
            color = Text_color_white_half
        )
        Spacer(modifier = Modifier.height(5.dp))
        InputFieldBG(
            operationValue,
            keyboardType = KeyboardType.Number,
            onValChange = { inputViewModel.onInputValueChange(getValidatedNumber(it)) },
            visualTransformation = CurrencyTransformation(operationType),

        )

        Spacer(modifier = Modifier.height(itemsIndent))
        
        Text(
            text = "Категорія та дата",
            color = Text_color_white_half
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SelectableBoxListItem(
                isSelected = selectedCategoryId != 0L,
                title = if (selectedCategoryId == 0L) "Категорія" else selectedCategoryName,
                iconName = if (selectedCategoryId == 0L) "categories" else selectedCategoryRes,
                toUseAspectRatio = false,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.75f)
                    .background(Dark_inputs_bg, shape = RoundedCornerShape(10)),
                isRowStyle = true,
                iconSize = 35.dp,
                titleWeight = FontWeight.Normal,
                titleSize = 22.sp,
                borderRadiusValue = 10
            ) {
                navController.navigate(Screen.FinancialCategoryListScreen.route)
            }
            DatePickerButtonComponent(inputViewModel)
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Тип операції",
            color = Text_color_white_half
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            RadioButton(
                selected = operationType == OperationType.income,
                onClick = { inputViewModel.onInputTypeChangeToIncome() },
                colors = RadioButtonDefaults.colors(Color.White)
            )
            Text(OperationType.incomeUA)

            Spacer(modifier = Modifier.width(10.dp))

            RadioButton(
                selected = operationType == OperationType.expense,
                onClick = { inputViewModel.onInputTypeChangeToExpense() },
                colors = RadioButtonDefaults.colors(Color.White)
            )
            Text(OperationType.expenseUA)
        }
        
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerButtonComponent(
    inputViewModel: InputViewModel
) {
    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("$day/${month + 1}/$year") }
    var dateTran by remember { mutableStateOf(LocalDate.now()) }

    // FORMAT DATE
    val formatter = DateTimeFormatter.ofPattern("dd MMMM");
    val formattedString = dateTran.format(formatter);

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        R.style.my_dialog_theme,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            date.value = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            dateTran = LocalDate.of(selectedYear, selectedMonth + 1, selectedDayOfMonth)
            inputViewModel.onInputDateChange((LocalDate.of(selectedYear, selectedMonth + 1, selectedDayOfMonth)).format(formatter))
        }, year, month, day
    )

    Column(
        Modifier
    ) {
        ColumnSelectableDateWindow(formattedString) { datePickerDialog.show() }
    }
}

@Composable
fun ColumnSelectableDateWindow(
    dateTitle: String,
    onClick: () -> Unit,
) {
    val todayDate by remember { mutableStateOf(LocalDate.now()) }

    // FORMAT DATE
    val formatter = DateTimeFormatter.ofPattern("dd MMMM");
    val formattedTodayDate = todayDate.format(formatter);


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(10)
                )
                .background(Dark_inputs_bg, shape = RoundedCornerShape(10))
                .clickable {
                    onClick()
                }
                .padding(horizontal = 20.dp, vertical = 20.dp),
        ) {
            Icon(
                modifier = Modifier
                    .size(35.dp)
                    .background(Color.Transparent),
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "",
                tint = MaterialTheme.colors.primary,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = if (dateTitle == formattedTodayDate) "Сьогодні" else dateTitle,
            fontSize = 16.sp
        )
    }
}

private fun insertFinancialOperationInDB(operationValue: String, operationType: String, categoryName: String, operationDate: String, mOperationViewModel: OperationViewModel) {
    val operation = Operation(
        operationValue = operationValue.toFloat(),
        operationType = operationType,
        categoryName = categoryName,
        operationDate = operationDate
    )

    mOperationViewModel.insertOperation(operation)
}


class InputViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val operationValue: LiveData<String> = savedStateHandle.getLiveData("operationValue", "")
    val operationType: LiveData<String> = savedStateHandle.getLiveData("operationType", "")
    val operationDate: LiveData<String> = savedStateHandle.getLiveData("operationDate", "")
    val selectedCategoryId: LiveData<Long> = savedStateHandle.getLiveData("selectedCategoryId", 0)
    val selectedCategoryName: LiveData<String> = savedStateHandle.getLiveData("selectedCategoryName", "")
    val selectedCategoryRes: LiveData<String> = savedStateHandle.getLiveData("selectedCategoryRes", "")

    fun onInputValueChange(newValue: String) {
        savedStateHandle["operationValue"] = newValue
    }

    fun onInputTypeChangeToExpense() {
        savedStateHandle["operationType"] = OperationType.expense
    }

    fun onInputTypeChangeToIncome() {
        savedStateHandle["operationType"] = OperationType.income
    }

    fun onInputDateChange(newValue: String) {
        savedStateHandle["operationDate"] = newValue
    }

    fun onInputCategoryChange(newValue: Long) {
        savedStateHandle["selectedCategoryId"] = newValue
    }

    fun onInputCategoryNameChange(newValue: String) {
        savedStateHandle["selectedCategoryName"] = newValue
    }

    fun onInputCategoryResChange(newValue: String) {
        savedStateHandle["selectedCategoryRes"] = newValue
    }
}