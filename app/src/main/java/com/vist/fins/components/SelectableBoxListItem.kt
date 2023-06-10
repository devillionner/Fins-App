package com.vist.fins.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.vist.fins.R
import com.vist.fins.conditional
import com.vist.fins.ui.theme.Dark_inputs_bg
import kotlinx.coroutines.launch

@Composable
fun SelectableBoxListItem(
    modifier: Modifier = Modifier,
    isRowStyle: Boolean = false,
    isSelected: Boolean,
    toUseSize: Boolean = false,
    size: Dp = 120.dp,
    isTitle: Boolean = true,
    toUseAspectRatio: Boolean = true,
    title: String = "Lorem Ipsum",
    titleColor: Color =
        if (isSelected) MaterialTheme.colors.primary.copy(alpha = 1f)
        else MaterialTheme.colors.onSurface.copy(alpha = 0.25f),
    titleSize: TextUnit = 12.sp,
    titleWeight: FontWeight = FontWeight.Medium,
    borderRadiusValue: Int = 10,
    borderWidth: Dp = 1.dp,
    borderColor: Color =
        if (isSelected) MaterialTheme.colors.primary.copy(alpha = 1f)
        else MaterialTheme.colors.onSurface.copy(alpha = 0.25f),
    iconName: String = "categories",
    iconSize: Dp = 50.dp,
    iconColor: Color =
        if (isSelected) MaterialTheme.colors.primary.copy(alpha = 1f)
        else MaterialTheme.colors.onSurface.copy(alpha = 0.25f),
    onClick: () -> Unit,
) {
    val scaleA = remember { Animatable(initialValue = 1f) }
    val scaleB = remember { Animatable(initialValue = 1f) }
    val duration = 50
    val borderRadius by animateIntAsState(
        targetValue = if (isSelected) 15 else borderRadiusValue,
        animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing),
    )

    val context = LocalContext.current
    val drawableId = remember(iconName) {
        context.resources.getIdentifier(
            iconName,
            "drawable",
            context.packageName
        )
    }
    
    LaunchedEffect(key1 = isSelected ) {
        if (isSelected) {
            launch {
                scaleA.animateTo(
                    targetValue = .85f,
                    animationSpec = tween(
                        durationMillis = duration
                    )
                )
                scaleA.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            launch {
                scaleB.animateTo(
                    targetValue = .95f,
                    animationSpec = tween(
                        durationMillis = duration
                    )
                )
                scaleB.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
        }
    }

    if (isRowStyle) {
        Row(
            modifier = Modifier
                .scale(scale = scaleB.value)
                .conditional(!toUseSize) {
                    modifier
                }
                .conditional(toUseSize) {
                    size(size)
                }
                .conditional(toUseAspectRatio) {
                    aspectRatio(1f)
                }
                .clip(
                    RoundedCornerShape(borderRadius)
                )
                .border(
                    width = borderWidth,
                    color =
                        if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0f)
                        else Color.Transparent,
                    //                shape = RoundedCornerShape(cornerRadius.value)
                    //                shape = borderShape
                    shape = RoundedCornerShape(borderRadius)
                )
                .clickable {
                    onClick()
                }
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            if (isTitle) {
                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .background(Color.Transparent)
                        .scale(scale = scaleA.value),
                    painter = painterResource(id = drawableId),
                    contentDescription = "",
                    tint = iconColor,
                )

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    modifier = Modifier
                        .background(Color.Transparent),
                    text = title,
                    style = TextStyle(
                        color = titleColor,
                        fontSize = titleSize,
                        fontWeight = titleWeight,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .background(Color.Transparent)
                        .scale(scale = scaleA.value),
                    painter = painterResource(id = drawableId),
                    contentDescription = "",
                    tint = iconColor,
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .scale(scale = scaleB.value)
                .conditional(toUseSize) {
                    size(size)
                }
                .background(Color.White.copy(alpha = 0.0f))
                .conditional(!toUseSize) {
                    modifier
                }
                .aspectRatio(1f)
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(borderRadius)
                )
                .clip(
                    RoundedCornerShape(borderRadius)
                )
                .clickable {
                    onClick()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isTitle) {
                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .background(Color.Transparent)
                        .scale(scale = scaleA.value),
                    painter = painterResource(id = drawableId),
                    contentDescription = "",
                    tint = iconColor,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier
                        .background(Color.Transparent),
                    text = title,
                    style = TextStyle(
                        color = titleColor,
                        fontSize = titleSize,
                        fontWeight = titleWeight,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .background(Color.Transparent)
                        .scale(scale = scaleA.value),
                    painter = painterResource(id = drawableId),
                    contentDescription = "",
                    tint = iconColor,
                )
            }
        }

    }
}