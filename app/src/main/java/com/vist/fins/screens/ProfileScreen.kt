package com.vist.fins.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vist.fins.R
import com.vist.fins.components.profileComponents.ProfileScreenButton
import com.vist.fins.ui.theme.*

@Composable
fun ProfileScreen(
    navController: NavController
) {
    val uriHandler = LocalUriHandler.current


    Column(
        Modifier
            .background(AppBG)
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.user),
                tint = Color.White,
                contentDescription = ""
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(

            ) {
                Text(
                    text = "Акаунт Fins",
                    fontSize = 28.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Made with 🤍 by @devillionner",
                    fontSize = 10.sp,
                    color = Color.White.copy(0.25f)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ProfileScreenButton(
            label = "Підтримати автора",
            iconName = "heart"
        ) {
            uriHandler.openUri("https://www.patreon.com/devillionner")
        }

        Spacer(modifier = Modifier.height(10.dp))

        ProfileScreenButton(
            label = "Інстаграм автора",
            iconName = "instagram"
        ) {
            uriHandler.openUri("https://www.instagram.com/devillionner/")
        }

        Spacer(modifier = Modifier.height(10.dp))

        ProfileScreenButton(
            label = "Оцінити додаток",
            iconName = "like_button"
        ) {
            uriHandler.openUri("https://forms.gle/TaCPzCCMxaiTP1QJ8")
        }
    }
}