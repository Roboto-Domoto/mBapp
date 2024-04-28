package com.example.mbapp_androidapp.presentation.screens

import android.util.Log
import android.widget.Toast
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
import androidx.navigation.NavController
import com.example.mbapp_androidapp.common.classes.BarcodeScanner
import com.example.mbapp_androidapp.common.classes.Employee
import com.example.mbapp_androidapp.common.classes.MailSender
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.data.entities.ItemEntity
import com.example.mbapp_androidapp.presentation.navigation.AppScreens
import com.example.mbapp_androidapp.presentation.viewmodels.ItemsViewModel
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@Composable
fun PressureScreen(
    navController: NavController,
    initialTW: Int,
    initialBW: Int,
    itemsViewModel: ItemsViewModel
) {
    val doorIsOpen = System.getInstance().doorIsOpen.observeAsState(initial = true)
    val barcodeScanner = BarcodeScanner.getBarcodeScanner()
    barcodeScanner.setNav(navController)
    val mailSender = MailSender.getMailSender()

    if (!doorIsOpen.value) {
        navController.navigate(AppScreens.EmployeeScreen.route)
    }else{
        val topWeight = System.getInstance().weightTop.observeAsState(0)
        val botWeight = System.getInstance().weightBot.observeAsState(0)
        Log.d("EEEE","$topWeight - $initialTW, $botWeight - $initialBW")
        //Sacar producto (posible robo)
        if (topWeight.value < (initialTW - System.pressureErrorTop) || botWeight.value < (initialBW - System.pressureErrorBot)) {
            mailSender.send(
                "Problema con el minibar ${System.barId}, se ha detectado una bajada de peso sospechosa. Acuda a observar y regañar al empleado.",
                "Problema pesos admin minibar ${System.barId}", Employee.getInstance().getAdminEmail()
            )
            System.getInstance()
                .addLog("Bajada de peso anómala, se aconseja comprobar productos(T:$initialTW->${topWeight.value} B:$initialBW->${botWeight.value})")
            Toast.makeText(LocalContext.current,"Error en la introducción de objetos",Toast.LENGTH_SHORT).show()
            navController.navigate(AppScreens.EmployeeScreen.route)
        }
        //Meter un producto
        else if (topWeight.value > (initialTW + System.pressureErrorTop) || botWeight.value > (initialBW + System.pressureErrorBot)) {
            val example = System.getInstance().lastItemAdd!!
            val item = ItemEntity(
                name = example.name,
                pictureId = example.pictureId,
                quantity = example.quantity,
                price = example.price,
                type = example.type,
                barcode = barcodeScanner.getLastCodeRead(),
                nutritionInfo = example.nutritionInfo
            )
            itemsViewModel.addItem(item)
            System.getInstance()
                .addLog("Añadido ${item.name} con codigo: ${item.barcode}")
            navController.navigate(AppScreens.EmployeeScreen.route)
        }
        else GuideScreen()
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
            text = "Ingresar objeto",
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
            text = "Ingrese el objeto dentro del minibar",
            fontFamily = caviarFamily,
            style = TextStyle(color = Color.Black, fontSize = 28.sp)
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "*Aviso*",
            fontFamily = caviarFamily,
            style = TextStyle(color = Color.Black, fontSize = 28.sp)
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "No se activara la camara hasta que guarde el producto",
            fontFamily = caviarFamily,
            style = TextStyle(color = Color.Black, fontSize = 28.sp)
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "No guardara el producto a menos que se introduzca",
            fontFamily = caviarFamily,
            style = TextStyle(color = Color.Black, fontSize = 28.sp)
        )
        Spacer(modifier = Modifier.height(lineSpacing))
        Text(
            text = "Tiene que cerrar la puerta para cancelar",
            fontFamily = caviarFamily,
            style = TextStyle(color = Color.Black, fontSize = 28.sp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}