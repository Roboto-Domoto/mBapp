package com.example.mbapp_androidapp.presentation.windows

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
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
import com.example.mbapp_androidapp.common.classes.Employee
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminWindow(flag: MutableState<Boolean>)
{
    val employee = System.getInstance().employee
    var mail by remember { mutableStateOf(employee.getAdminEmail()) }

    AlertDialog(
        onDismissRequest = { flag.value = !flag.value },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //Botón para cerrar
                IconButton(
                    onClick = { flag.value = !flag.value }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Change the telephone number of admin",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.Black, CircleShape)
                    )
                }
                //Botón para aceptar
                IconButton(
                    onClick = {
                        if (mail.length == 9) {
                            employee.changeAdminEmail(mail)
                            flag.value = !flag.value
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Validate password",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.Black, CircleShape)
                    )
                }
            }
        },
        title = {
            Text(
                text = "Correo del administrador",
                fontFamily = caviarFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(4.dp)
            )
        },
        text = {
            TextField(
                value = mail,
                onValueChange = { mail = it },
                label = {
                    Text(text = "Email del admin")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .padding(12.dp)
                    .size(272.dp, 52.dp)
            )
        },
        modifier = Modifier
            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
    )
}