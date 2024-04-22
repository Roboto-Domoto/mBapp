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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mbapp_androidapp.R
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.presentation.navigation.AppScreens
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordWindow(navController: NavHostController, flag: MutableState<Boolean>,
                   modifier: Modifier = Modifier)
{
    val system = System.getInstance()

    var hidden by remember { mutableStateOf(true) }
    var password by remember { mutableStateOf("") }

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
                        contentDescription = "Validate password",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.Black, CircleShape)
                    )
                }
                //Botón para aceptar
                IconButton(
                    onClick = {
                        if (password == "Contraseña"){
                            system.addLog("Acceso a modo empleado")
                            navController.navigate(AppScreens.EmployeeScreen.route)
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
                text = "Contraseña",
                fontFamily = caviarFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(4.dp)
            )
        },
        text = {
            TextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(text = "Contraseña")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = if(hidden) PasswordVisualTransformation()
                else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = { hidden = !hidden }) {
                        Icon(
                            imageVector =
                            if(hidden) ImageVector.vectorResource(R.drawable.visibility_off)
                            else ImageVector.vectorResource(R.drawable.visibility_on),
                            contentDescription = "Visibility"
                        )
                    }
                },
                modifier = Modifier
                    .padding(12.dp)
                    .size(272.dp, 52.dp)
            )
        },
        modifier = modifier
            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
    )
}