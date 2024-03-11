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
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mbapp_androidapp.R
import com.example.mbapp_androidapp.ui.theme.amableFamily
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@Composable
fun EmployeeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopElements() //Hora y temperatura
        Buttons() //Los botones con las distintas opciones del menú
        BottomElements(navController) //Botones de cierre de sesión e información
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
private fun BottomElements(navController: NavHostController) {
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

        Icon(
            imageVector = Icons.Rounded.Info,
            contentDescription = "Info button",
            modifier = Modifier
                .padding(8.dp)
                .size(52.dp)
        )
    }
}

@Composable
private fun Buttons() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MenuButton(
            icon = Icons.Rounded.Settings,
            text = "Ajustes"
        )
        Spacer(modifier = Modifier.height(60.dp))
        MenuButton(
            icon = ImageVector.vectorResource(id = R.drawable.local_cafe_24px),
            text = "Stock"
        )
        Spacer(modifier = Modifier.height(60.dp))
        MenuButton(
            icon = ImageVector.vectorResource(id = R.drawable.inventory_24px),
            text = "Inventario"
        )
    }
}

@Composable
private fun MenuButton (icon: ImageVector, text: String) {
    TextButton(
        onClick = { /*TODO*/ },
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