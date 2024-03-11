package com.example.mbapp_androidapp.presentation.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mbapp_androidapp.R
import com.example.mbapp_androidapp.presentation.navigation.AppScreens
import com.example.mbapp_androidapp.presentation.windows.PasswordWindow
import com.example.mbapp_androidapp.ui.theme.amableFamily
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@Composable
fun HomeScreen(navController: NavHostController) {
    val showPassWindow = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopElements() //Hora y temperatura superior
        Buttons(navController, showPassWindow) //Los botones con las distintas opciones del menú
        Icon(
            imageVector = Icons.Rounded.Info,
            contentDescription = "Info button",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .size(52.dp)
        )
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

@Composable
private fun TopElements() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "10:00",
            fontSize = 36.sp,
            fontFamily = caviarFamily,
            letterSpacing = 0.sp
        )
        Text(
            text = "4º",
            fontSize = 32.sp,
            fontFamily = amableFamily
        )
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
            text = "Mis compras"
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

@Composable
private fun MenuButton (icon: ImageVector, text: String, onClick: () -> Unit = {}) {
    TextButton(
        onClick = { onClick.invoke() },
        colors = ButtonDefaults.buttonColors(Color.Black, Color.White),
        border = BorderStroke(
            width = 2.dp,
            brush = Brush.linearGradient(listOf(Color.Gray, Color.White))
        ),
        modifier = Modifier
            .size(192.dp, 60.dp)
    ) {
        ButtonContent(icon, text) //Icono y texto que contiene el botón
    }
}

@Composable
private fun ButtonContent(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            modifier = Modifier
                .size(52.dp)
                .alpha(0.25f)
        )
        Text(
            text = text,
            fontFamily = caviarFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}