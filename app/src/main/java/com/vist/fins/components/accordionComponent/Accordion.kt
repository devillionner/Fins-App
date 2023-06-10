package com.vist.fins.components.accordionComponent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.vist.fins.data.AccordionModel
import com.vist.fins.data.OperationViewModel
import com.vist.fins.data.entities.Operation

@Composable
fun Accordion(
    modifier: Modifier = Modifier,
    categoryName: String = "CategoryName",
    categoryValue: Float = 0f,
    categoryIcon: String,
    operations: List<Operation>,
    mOperationViewModel: OperationViewModel
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        AccordionHeader(categoryName = categoryName, categoryValue = categoryValue, categoryIcon = categoryIcon, operationsSize = operations.size.toString(), isExpanded = expanded) {
            expanded = !expanded
        }

        AnimatedVisibility(visible = expanded) {
            Column(
                Modifier
                    .graphicsLayer {
                        transformOrigin = TransformOrigin(0.5f, 0f)
                    }
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                operations.forEach { i ->
                    AccordionRowItem(
                        value = i.operationValue,
                        type = i.operationType,
                        date = i.operationDate
                    ) {
                        mOperationViewModel.deleteFinancialOperation(i)
                    }
                }
            }
        }

    }
}