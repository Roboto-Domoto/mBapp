package com.example.mbapp_androidapp.presentation.windows

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.example.mbapp_androidapp.R
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordWindow(modifier: Modifier = Modifier) {
    var hidden by remember { mutableStateOf(true) }
    var password by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
            .wrapContentSize(Alignment.TopCenter)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
    ) {
        Text(
            text = "Contraseña",
            fontFamily = caviarFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier.padding(12.dp)
        )
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
        Buttons() //Botones de aceptar o cerrar
    }
}

@Composable
private fun Buttons() {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = "Close Window",
            modifier = Modifier.size(32.dp)
        )
        Icon(
            imageVector = Icons.Rounded.Check,
            contentDescription = "Validate password",
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    PasswordWindow()
}