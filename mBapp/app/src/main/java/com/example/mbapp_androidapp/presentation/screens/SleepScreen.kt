package com.example.mbapp_androidapp.presentation.screens

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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.common.elements.Clock
import com.example.mbapp_androidapp.presentation.navigation.AppScreens
import com.example.mbapp_androidapp.ui.theme.amableFamily
import com.example.mbapp_androidapp.ui.theme.caviarFamily
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SleepScreen(navController: NavHostController) {
    val doorIsOpen by remember {
        mutableStateOf(System.getInstance().doorIsOpen)
    }
    Box (
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopElements(navController) //Elementos en la parte superior
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
    if (doorIsOpen) navController.navigate(AppScreens.BuyScreen.route)
}

//En esta función se establecen los botones del menú principal y ajustes
@Composable
private fun TopElements(navController: NavHostController) {
    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { navController.navigate(AppScreens.HomeScreen.route) }) {
            Icon(
                imageVector = Icons.Rounded.Home,
                contentDescription = "Home button",
                modifier = Modifier.size(52.dp)
            )
        }
        IconButton(onClick = {
            /* TODO */
        }) {
            Icon(
                imageVector = Icons.Rounded.Settings,
                contentDescription = "Settings button",
                modifier = Modifier.size(52.dp)
            )
        }
    }
}

//En esta función se representa la temperatura, hora, fecha y clima
@Composable
private fun CenterElements() {
    val temperature = System.getInstance().temperature.observeAsState("0")
    val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val date = LocalDateTime.now().format(dateFormat)

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = temperature.value + "º",
            fontSize = 208.sp,
            fontFamily = amableFamily
        )
        Clock(
            fontSize = 60.sp,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        Text(
            text = date,
            fontSize = 16.sp,
            fontFamily = caviarFamily
        )
    }

}