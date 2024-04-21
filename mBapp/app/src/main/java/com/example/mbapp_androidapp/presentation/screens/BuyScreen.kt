package com.example.mbapp_androidapp.presentation.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbapp_androidapp.common.classes.BarcodeScanner
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.ui.theme.caviarFamily

val system = System.getInstance()

@Composable
fun BuyScreen() {
    val weight = system.weight
    val failureConst = 0.1
    val barcodeScanner = BarcodeScanner.getBarcodeScanner(null)
    //Si el peso baja
    if (system.weight < (weight * (1 - failureConst))) {
        barcodeScanner.scan()
        val code = barcodeScanner.getLastCodeRead()
        /*
        if (code != "") {
            searchProduct(code)
            s
        }
         */

    }
    //Si el peso sube
    else if (system.weight > weight * (1 + failureConst)) {
        //NOTIFICAR ERROR
    }
    //Mientras no pase nada
    else {
        GuideScreen()
    }
}

@Composable
private fun GuideScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
            text = "Modo compra",
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
            text = "1. Retire el producto",
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
            text = "3. Escanee el producto",
            fontFamily = caviarFamily,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "4. Si desea seguir:",
            fontFamily = caviarFamily,
            fontSize = 26.sp,
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "4.1 Repita los pasos",
            fontFamily = caviarFamily,
            fontSize = 26.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "5. Si no:",
            fontFamily = caviarFamily,
            fontSize = 26.sp,
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "5.1 Cierre la puerta",
            fontFamily = caviarFamily,
            fontSize = 26.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    BuyScreen()
}