package com.vist.fins.components.accordionComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vist.fins.data.OperationViewModel
import com.vist.fins.data.entities.Category
import com.vist.fins.data.entities.Operation
import com.vist.fins.data.listOfMonths
import com.vist.fins.data.listOfMonthsShort

@Composable
fun AccordionMonthGroup(listOfOperations: List<Operation>, listOfCategories: List<Category>, mOperationViewModel: OperationViewModel) {
    LazyColumn() {
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

        itemsIndexed(listOfMonths.reversed()) { index, item ->
            // Список місяців в яких присутні операції
            val listMonthOpers = listOfOperations.filter {
                it.operationDate.contains(listOfMonthsShort.reversed()[index], ignoreCase = true)
            }

            // Якщо
            if (listMonthOpers.isNotEmpty()) {
                Column(
//                    Modifier.animateItemPlacement()
                    Modifier.padding(horizontal = 20.dp)
                ) {
                    //Назва місяця
                    Text(
                        text = item,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(1f)
                    )

                    // Список категорій з витратами
                    listOfCategories.forEach() { i ->
                        val listOp: List<Operation> = listOfOperations.filter() {
                            it.categoryName == i.categoryName &&
                                    it.operationDate.contains(listOfMonthsShort.reversed()[index], ignoreCase = true)
                        }

                        val valueOfAllOperationsVByCategory = listOp.map {
                            it.operationValue
                        }.sum()

                        if (listOp.isNotEmpty()) {
                            Accordion(
                                categoryName = i.categoryName,
                                categoryValue = valueOfAllOperationsVByCategory,
                                categoryIcon = i.imageRes,
                                operations = listOp,
                                mOperationViewModel = mOperationViewModel
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}