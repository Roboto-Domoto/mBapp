package com.example.mbapp_androidapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mbapp_androidapp.R
import com.example.mbapp_androidapp.common.elements.MenuButton
import com.example.mbapp_androidapp.common.elements.TopElements
import com.example.mbapp_androidapp.presentation.navigation.AppScreens
import com.example.mbapp_androidapp.presentation.windows.guideWindows.SleepGuide
import com.example.mbapp_androidapp.presentation.windows.InventoryWindow
import com.example.mbapp_androidapp.presentation.windows.guideWindows.EmployeeGuide

@Composable
fun EmployeeScreen(navController: NavHostController) {
    val guideW = remember { mutableStateOf(false) }
    if (guideW.value) EmployeeGuide(flag = guideW)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopElements() //Hora y temperatura
        Buttons(navController) //Los botones con las distintas opciones del menú
        BottomElements(navController,guideW) //Botones de cierre de sesión e información
    }
}

@Composable
private fun BottomElements(navController: NavHostController,guideW:MutableState<Boolean>) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Rounded.ExitToApp,
                contentDescription = "Info button",
                modifier = Modifier
                    .size(52.dp)
            )
        }
        IconButton(
            onClick = {guideW.value=!guideW.value},
            modifier = Modifier
                .padding(8.dp)
                .size(52.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Info,
                contentDescription = "Info button",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun Buttons(navController: NavHostController) {
    val showInvW = remember { mutableStateOf(false) }
    if(showInvW.value) InventoryWindow(flag = showInvW)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MenuButton(
            icon = Icons.Rounded.Settings,
            text = "Ajustes",
            onClick = { navController.navigate(AppScreens.EmployeeSettingScreen.route)
            }
        )
        Spacer(modifier = Modifier.height(60.dp))
        MenuButton(
            icon = ImageVector.vectorResource(id = R.drawable.local_cafe_24px),
            text = "Stock",
            onClick = { navController.navigate(AppScreens.StockScreen.route) }
        )
        Spacer(modifier = Modifier.height(60.dp))
        MenuButton(
            icon = ImageVector.vectorResource(id = R.drawable.inventory_24px),
            text = "Inventario",
            onClick = { showInvW.value = !showInvW.value }
        )
    }
}