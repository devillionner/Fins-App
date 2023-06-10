package com.vist.fins.components.accordionComponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vist.fins.currencyFormatter
import com.vist.fins.data.OperationType
import com.vist.fins.ui.theme.Currency_expense_color_base
import com.vist.fins.ui.theme.Currency_income_color_base

@Composable
fun AccordionRowItem(
    value: Float,
    type: String,
    date: String,
    onClick: ()-> Unit = {}
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    radius = 250.dp,
                    bounded = false
                )
            ) { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
                ) {
                    append(currencyFormatter(value))
                }
                withStyle(
                    style = SpanStyle(
                        color = if (type == OperationType.expense) Currency_expense_color_base else Currency_income_color_base,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    append(" ₴")
                }
            })
        }

        Text(
            text = date,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White.copy(alpha = 0.15f)
        )
    }
}

