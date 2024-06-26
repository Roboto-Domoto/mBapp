package com.example.mbapp_androidapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.common.elements.MenuButton
import com.example.mbapp_androidapp.common.elements.TopElements
import com.example.mbapp_androidapp.presentation.navigation.AppScreens
import com.example.mbapp_androidapp.presentation.windows.PasswordWindow
import com.example.mbapp_androidapp.presentation.windows.guideWindows.HomeGuide
import com.example.mbapp_androidapp.presentation.windows.guideWindows.SleepGuide

@Composable
fun HomeScreen(navController: NavHostController) {
    val showPassWindow = remember { mutableStateOf(false) }
    val doorIsOpen = System.getInstance().doorIsOpen.observeAsState(false)
    if (doorIsOpen.value) navController.navigate(AppScreens.BuyScreen.route)
    else {
        val guideW = remember { mutableStateOf(false) }
        if (guideW.value) HomeGuide(flag = guideW)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            TopElements() //Hora y temperatura superior
            Buttons(navController, showPassWindow) //Los botones con las distintas opciones del menú
            IconButton(
                onClick = {guideW.value=!guideW.value},
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(52.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = "Info button",
                    modifier = Modifier.fillMaxSize()
                )
            }
            if (showPassWindow.value) {
                PasswordWindow(
                    navController = navController,
                    flag = showPassWindow,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(12.dp)
                )
            }
        }
    }
}

@Composable
private fun Buttons(navController: NavHostController, flag: MutableState<Boolean>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MenuButton(
            icon = Icons.Rounded.ShoppingCart,
            text = "Mis compras",
            onClick = { navController.navigate(AppScreens.MyShoppingScreen.route) }
        )
        Spacer(modifier = Modifier.height(60.dp))
        MenuButton(
            icon = ImageVector.vectorResource(id = R.drawable.local_cafe_24px),
            text = "Productos",
            onClick = { navController.navigate(AppScreens.ItemsScreen.route) }
        )
        Spacer(modifier = Modifier.height(60.dp))
        MenuButton(
            icon = Icons.Rounded.Person,
            text = "Soy empleado",
            onClick = { flag.value = !flag.value }
        )
    }
}




