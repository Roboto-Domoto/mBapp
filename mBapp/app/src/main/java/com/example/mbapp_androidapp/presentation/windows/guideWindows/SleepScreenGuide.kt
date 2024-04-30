package com.example.mbapp_androidapp.presentation.windows.guideWindows

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@Composable
fun SleepGuide(flag: MutableState<Boolean>)
{
    val logs = System.getInstance().getLogs()

    AlertDialog(
        onDismissRequest = { flag.value = !flag.value },
        confirmButton = {
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
        },
        title = {
            Text(
                text = "Guia",
                fontFamily = caviarFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(4.dp)
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.5f),
                state = rememberLazyListState(),
                contentPadding = PaddingValues(12.dp)
            ) {
                item {
                    Guide()
                }
            }
        },
        modifier = Modifier
            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
    )
}

@Composable
private fun Guide() {
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
    }
}