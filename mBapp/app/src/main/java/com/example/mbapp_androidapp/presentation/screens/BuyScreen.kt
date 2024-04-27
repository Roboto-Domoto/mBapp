package com.example.mbapp_androidapp.presentation.screens

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mbapp_androidapp.common.classes.BarcodeScanner
import com.example.mbapp_androidapp.common.classes.Employee
import com.example.mbapp_androidapp.common.classes.MailSender
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.presentation.navigation.AppScreens
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@Composable
fun BuyScreen(navController: NavHostController, initialTW:Int, initialBW:Int) {
    val doorIsOpen = System.getInstance().doorIsOpen.observeAsState(initial = true)
    val barcodeScanner = System.getInstance().barcodeScanner.observeAsState()
    val mailSender = MailSender.getMailSender()

    if (!doorIsOpen.value) {
        val nProducts = barcodeScanner.value?.getCodeList()?.size
        if(nProducts!=0)
            Toast.makeText(LocalContext.current,"Has comprado un total de $nProducts!",Toast.LENGTH_SHORT).show()
        barcodeScanner.value?.cleanList()
        navController.navigate(AppScreens.SleepScreen.route)
    }
    else {
        val topWeight = System.getInstance().weightTop.observeAsState(0)
        val botWeight = System.getInstance().weightBot.observeAsState(0)
        val failTake = 0.9
        val failPut = 1.1
        //Sacar producto
        if (topWeight.value < (initialTW * failTake) || botWeight.value < (initialBW * failTake)) {
            //Activar cámara
            barcodeScanner.value?.scan()
            navController.navigate(AppScreens.BuyScreen.route)
        }
        //Meter un producto
        else if (topWeight.value > (initialTW * failPut) || botWeight.value > (initialBW * failPut)) {
            mailSender.send("Problema con el minibar ${System.barId}, se ha detectado una subida de peso sospechosa. Acuda a observar y cobrar si es necesario.",
                "Problema pesos minibar ${System.barId}",Employee.getInstance().getAdminEmail())
        }
        //Mientras el peso se mantenga constante mostrar la pantalla de guía
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
            style = TextStyle(color = Color.Black, fontSize = 40.sp)
        )
        Text(
            text = "Guía de uso",
            fontFamily = caviarFamily,
            style = TextStyle(color = Color.Black, fontSize = 40.sp)
        )
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "1. Retire el producto",
            fontFamily = caviarFamily,
            style = TextStyle(color = Color.Black, fontSize = 28.sp)
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "2. La cámara se activará",
            fontFamily = caviarFamily,
            style = TextStyle(color = Color.Black, fontSize = 28.sp)
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "3. Escanee el producto",
            fontFamily = caviarFamily,
            style = TextStyle(color = Color.Black, fontSize = 28.sp)
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "4. Si desea seguir:",
            fontFamily = caviarFamily,
            style = TextStyle(color = Color.Black, fontSize = 28.sp)
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "4.1 Repita los pasos",
            fontFamily = caviarFamily,
            style = TextStyle(color = Color.Black, fontSize = 28.sp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "5. Si no:",
            fontFamily = caviarFamily,
            style = TextStyle(color = Color.Black, fontSize = 28.sp)
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "5.1 Cierre la puerta",
            fontFamily = caviarFamily,
            style = TextStyle(color = Color.Black, fontSize = 28.sp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}