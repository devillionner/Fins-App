package com.vist.fins.components.accordionComponent

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vist.fins.R
import com.vist.fins.currencyFormatter

@SuppressLint("DiscouragedApi")
@Composable
fun AccordionHeader(
    categoryName: String = "Header",
    categoryValue: Float = 0f,
    categoryIcon: String,
    operationsSize: String = "2",
    isExpanded: Boolean = false,
    onTapped: () -> Unit = {}
) {
    val context = LocalContext.current
    val degrees = if (isExpanded) 180f else 0f

    val drawableId = remember(categoryIcon) {
        context.resources.getIdentifier(
            categoryIcon,
            "drawable",
            context.packageName
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .padding(end = 20.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    radius = 300.dp,
                    bounded = false
                )
            ) { onTapped() },

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                Modifier
                    .clip(shape = RoundedCornerShape(30.dp))
                    .background(MaterialTheme.colors.primary)
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = drawableId),
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(28.dp)
                        .background(Color.Transparent),
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = categoryName,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    modifier = Modifier
                        .offset(y = (-9).dp, x = 2.dp)
                        .background(Color.Black, shape = RoundedCornerShape(20.dp))
                        .padding(horizontal = 7.dp, vertical = 2.dp),
                    text = operationsSize,
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Normal
                    )
                ) {
//                    append(currencyFormatter(allOperationsByCategoryValue))
                    append(currencyFormatter(categoryValue))
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    append(" ₴")
                }
            })
        }

        Icon(
            painter = painterResource(id = R.drawable.down_arrow),
            contentDescription = "Open or close the drop down",
            tint = MaterialTheme.colors.primary.copy(0.5f),
            modifier = Modifier
                .size(13.dp)
                .rotate(degrees)
        )
    }
}