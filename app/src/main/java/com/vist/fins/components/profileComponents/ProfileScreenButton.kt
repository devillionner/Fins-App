package com.vist.fins.components.profileComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vist.fins.R

@Composable
fun ProfileScreenButton(
    label: String = "Write a label",
    iconName: String = "categories",
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    val drawableId = remember(iconName) {
        context.resources.getIdentifier(
            iconName,
            "drawable",
            context.packageName
        )
    }

    Row(
        Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(10.dp)
            )
            .background(
                color = Color.White.copy(alpha = 0.05f),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onClick()
            }
            .padding(20.dp, 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = drawableId),
                tint = Color.White,
                contentDescription = ""
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = label,
                fontSize = 18.sp
            )
        }

        Icon(
            modifier = Modifier
                .size(13.dp)
                .rotate(-90f),
            painter = painterResource(id = R.drawable.down_arrow),
            tint = Color.White.copy(0.5f),
            contentDescription = ""
        )
    }
}