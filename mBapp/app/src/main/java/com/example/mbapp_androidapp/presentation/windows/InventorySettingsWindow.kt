package com.example.mbapp_androidapp.presentation.windows

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.presentation.navigation.AppScreens
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventorySettingsWindow(flag: MutableState<Boolean>, navHostController: NavHostController) {

    val system = System.getInstance()
    var start by remember { mutableStateOf(system.getStartHour()) }
    var end by remember { mutableStateOf(system.getEndHour()) }

    AlertDialog(
        onDismissRequest = { flag.value = !flag.value },
        confirmButton = {
            //Botón para confirmar
            IconButton(
                onClick = { flag.value = !flag.value }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Change the values of inventory enviroment",
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.Black, CircleShape)
                )
            }
        },
        title = {
            Text(
                text = "Ajustes de inventario",
                fontFamily = caviarFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(4.dp)
            )
        },
        text = {
            Column {
                TextField(
                    value = start,
                    onValueChange = {
                        start = it
                        if (it.matches(Regex("([01]?[0-9]|2[0-3]):[0-5][0-9]"))) {
                            system.setStartHour(start)
                        }
                    },
                    label = { Text(text = "Hora de inicio") },
                    singleLine = true,
                    modifier = Modifier.padding(4.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
                TextField(
                    value = end,
                    onValueChange = {
                        end = it
                        if (it.matches(Regex("([01]?[0-9]|2[0-3]):[0-5][0-9]"))) {
                            system.setEndHour(end)
                        }
                    },
                    label = { Text(text = "Hora de cierre") },
                    singleLine = true,
                    modifier = Modifier.padding(4.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
                Button(onClick = { navHostController.navigate(AppScreens.MinStockScreen.route) }) {
                    Text(text = "Ajustar stocks mínimos")
                }
            }
        },
        modifier = Modifier
            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
    )
}