package com.vist.fins.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vist.fins.currencyFormatter
import com.vist.fins.data.OperationType
import com.vist.fins.ui.theme.Currency_expense_color
import com.vist.fins.ui.theme.Currency_income_color
import com.vist.fins.ui.theme.Dark_text_bg_rounded
import com.vist.fins.ui.theme.Text_color_white_half

@Composable
fun FinancialGroupView(title: String, value: Float, bgColor: Color = Dark_text_bg_rounded, textColor: Color = Text_color_white_half) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp,
                    color = if(title == OperationType.incomeUA) Currency_income_color else Currency_expense_color,
                    fontWeight = FontWeight.ExtraBold,
                ),
            ) {
                append(if(title == OperationType.incomeUA) "↙" else "↗" )
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp,
                    color = Text_color_white_half,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(" ${currencyFormatter(value)} ₴")
            }
        },
        modifier = Modifier
            .background(bgColor, shape = RoundedCornerShape(25.dp))
            .padding(vertical = 4.dp, horizontal = 12.dp),
    )
}

@Composable
fun FinancialBalanceView(title: String, value: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            fontSize = 18.sp,
            text = "$title:",
            color = MaterialTheme.colors.primary
        )
        
        Spacer(modifier = Modifier.width(10.dp))

        Text(
            modifier = Modifier
                .background(Dark_text_bg_rounded, shape = RoundedCornerShape(25.dp))
                .padding(vertical = 4.dp, horizontal = 12.dp),
            text = "${currencyFormatter(value)} ₴",
            fontSize = 18.sp,
            color = Text_color_white_half,
            fontWeight = FontWeight.Normal
        )
    }
}