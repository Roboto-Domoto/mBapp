package com.example.mbapp_androidapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbapp_androidapp.common.classes.BarcodeScanner
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.presentation.navigation.AppScreens
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@Composable
fun StockProcesScreen() {
    val doorIsOpen = System.getInstance().doorIsOpen.observeAsState(initial = false)
    val barcodeScanner = BarcodeScanner.getBarcodeScanner()

    if (doorIsOpen.value) {
        //Activar cámara
        barcodeScanner.scan()
        //navController.navigate(AppScreens.BuyScreen.route)
    }
    GuideScreen()
}

@Composable
private fun GuideScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TextGuide()
        Button(onClick = { /*navController.navigate(AppScreens.StockScreen.route)*/ }) {
            Text(
                text = "Salir",
                fontFamily = caviarFamily,
                fontSize = 24.sp,
            )
        }
    }
}

// Muestra la guía de como usar el modo compra al usuario
@Composable
private fun TextGuide() {
    val lineSpacing = 16.dp

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Modo Stock",
            fontFamily = caviarFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
        )
        Text(
            text = "Guía de uso",
            fontFamily = caviarFamily,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "1. Abra la puerta",
            fontFamily = caviarFamily,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "2. La cámara se activará",
            fontFamily = caviarFamily,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "3. Escanee el prducto",
            fontFamily = caviarFamily,
            fontSize = 26.sp,
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "4. Introdúzcalo",
            fontFamily = caviarFamily,
            fontSize = 26.sp,
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "5. Cuando termine cierre la puerta",
            fontFamily = caviarFamily,
            fontSize = 26.sp,
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "6. Pulse el botón para salir",
            fontFamily = caviarFamily,
            fontSize = 26.sp,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    StockProcesScreen()
}