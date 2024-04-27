package com.example.mbapp_androidapp.presentation.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mbapp_androidapp.R
import com.example.mbapp_androidapp.common.classes.Customer
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.common.elements.MenuButton
import com.example.mbapp_androidapp.common.elements.TopElements
import com.example.mbapp_androidapp.presentation.windows.AdminWindow
import com.example.mbapp_androidapp.presentation.windows.LogWindow
import com.example.mbapp_androidapp.ui.theme.caviarFamily
import kotlinx.coroutines.delay

@Composable
fun EmployeeSettingScreen() {
    val showMsg = remember { mutableStateOf(false) }
    val showAdminW = remember { mutableStateOf(false) }
    val showLogsW = remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopElements() //Hora y temperatura
        if (showAdminW.value) AdminWindow(flag = showAdminW)
        if (showLogsW.value) LogWindow(flag = showLogsW)
        Buttons(showMsg, showAdminW, showLogsW) //Los botones con las distintas opciones del menú
        Icon(
            imageVector = Icons.Rounded.Info,
            contentDescription = "Info button",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .size(52.dp)
        )
        if (showMsg.value) {
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(width = 220.dp, height = 60.dp)
                    .padding(bottom = 20.dp)
                    .animateContentSize(),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "¡Nuevo cliente listo!",
                    fontFamily = caviarFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            //Ocultar el mensaje después de 2 segundos
            LaunchedEffect(Unit) {
                delay(2000)
                showMsg.value = false
            }
        }

    }
}

@Composable
private fun Buttons(showMsg: MutableState<Boolean>, showAdminW: MutableState<Boolean>,
                    showLogsW: MutableState<Boolean>)
{
    val customer = System.getInstance().customer

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MenuButton(
            icon = Icons.Rounded.Person,
            text = "Nuevo cliente",
            onClick = {
                customer.resetList()
                System.getInstance().addLog("Nuevo cliente creado con éxito")
                showMsg.value = true
            }
        )
        Spacer(modifier = Modifier.height(60.dp))
        MenuButton(
            icon = Icons.Rounded.Face,
            text = "Cambiar administrador",
            onClick = { showAdminW.value = !showAdminW.value }

        )
        Spacer(modifier = Modifier.height(60.dp))
        MenuButton(
            icon = Icons.Rounded.ShoppingCart,
            text = "Inventario"
        )
        Spacer(modifier = Modifier.height(60.dp))
        MenuButton(
            icon = ImageVector.vectorResource(id = R.drawable.quick_reference_all_fill0_wght400_grad0_opsz24),
            text = "Logs",
            onClick = { showLogsW.value = !showLogsW.value }
        )
        Spacer(modifier = Modifier.height(60.dp))
        MenuButton(
            icon = Icons.Rounded.Build,
            text = "Rest. modo fábrica",
            onClick = { System.getInstance().factoryReset() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    EmployeeSettingScreen()
}