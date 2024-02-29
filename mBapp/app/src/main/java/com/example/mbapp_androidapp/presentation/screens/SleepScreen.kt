package com.example.mbapp_androidapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mbapp_androidapp.ui.theme.amableFamily
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@Composable
fun SleepScreen(navController: NavHostController) {
    Box (
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopElements() //Elementos en la parte superior
        CenterElements() //Elementos de la parte central de la pantalla
        Icon(
            imageVector = Icons.Rounded.Info,
            contentDescription = "Info button",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .size(52.dp)
        )
    }
}

//En esta función se establecen los botones del menú principal y ajustes
@Composable
private fun TopElements() {
    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Rounded.Home,
            contentDescription = "Home button",
            modifier = Modifier.size(52.dp)
        )
        Icon(
            imageVector = Icons.Rounded.Settings,
            contentDescription = "Settings button",
            modifier = Modifier.size(52.dp)
        )
    }
}

//En esta función se representa la temperatura, hora, fecha y clima
@Composable
private fun CenterElements() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "4º",
            fontSize = 208.sp,
            fontFamily = amableFamily
        )
        Text(
            text = "10:00",
            fontSize = 60.sp,
            fontFamily = caviarFamily,
            letterSpacing = 0.sp
        )
        Text(
            text = "26/02/2024",
            fontSize = 16.sp,
            fontFamily = caviarFamily
        )
    }
}