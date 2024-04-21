package com.example.mbapp_androidapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@Composable
fun StockScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TextGuide()
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
            text = "1. Pulse y elija producto",
            fontFamily = caviarFamily,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "2. Pulse el botón cámara",
            fontFamily = caviarFamily,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "3. La cámara se activará",
            fontFamily = caviarFamily,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "4. Escanee el prducto",
            fontFamily = caviarFamily,
            fontSize = 26.sp,
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "5. Introdúzcalo",
            fontFamily = caviarFamily,
            fontSize = 26.sp,
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "6. Cuando termine pulse salir",
            fontFamily = caviarFamily,
            fontSize = 26.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    StockScreen()
}