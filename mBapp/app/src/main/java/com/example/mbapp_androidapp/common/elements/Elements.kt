package com.example.mbapp_androidapp.common.elements

import android.annotation.SuppressLint
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbapp_androidapp.common.classes.BluetoothTerminal
import com.example.mbapp_androidapp.ui.theme.amableFamily
import com.example.mbapp_androidapp.ui.theme.caviarFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun Clock(fontSize: TextUnit, verticalAlignment: Alignment.Vertical,
          horizontalArrangement: Arrangement.Horizontal)
{
    val hourFormat = DateTimeFormatter.ofPattern("hh:mm")
    val hour = LocalDateTime.now().format(hourFormat)
    var isColonVisible by remember { mutableStateOf(true) }
    val infiniteTransition = rememberInfiniteTransition(label = "Clock")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1200
                0.5f at 600
            },
            repeatMode = RepeatMode.Reverse
        ), label = "Clock"
    )

    Row(
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {
        Text(
            text = hour.substring(0, 2),
            fontSize = fontSize,
            fontFamily = caviarFamily
        )
        Text(
            text = ":",
            fontSize = fontSize,
            fontFamily = caviarFamily,
            letterSpacing = 0.sp,
            color = Color.Black.copy(alpha = alpha)
        )
        Text(
            text = hour.substring(3),
            fontSize = fontSize,
            fontFamily = caviarFamily
        )
    }

    LaunchedEffect(alpha) {
        isColonVisible = alpha > 0.5f
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TopElements() {
    val sens = remember { mutableStateOf("NaN") }
    val thread = BluetoothTerminal.getBluetoothTerminal(null).createConnectedThread(sens)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Clock(
            fontSize = 36.sp,
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        Text(
            text = "${sens.value}º}",
            fontSize = 32.sp,
            fontFamily = amableFamily
        )
    }
    rememberCoroutineScope().launch(Dispatchers.Default) {
        thread.start()
        while(true){
            thread.write("C")
            delay(2500)
        }
    }
}

@Composable
fun MenuButton (icon: ImageVector, text: String, onClick: () -> Unit = {}) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color.Black, Color.White),
        border = BorderStroke(
            width = 2.dp,
            brush = Brush.linearGradient(listOf(Color.Gray, Color.White))
        ),
        modifier = Modifier
            .size(192.dp, 60.dp)
    ) {
        ButtonContent(icon, text) //Icono y texto que contiene el botón
    }
}

@Composable
private fun ButtonContent(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            modifier = Modifier
                .size(52.dp)
                .alpha(0.25f)
        )
        Text(
            text = text,
            fontFamily = caviarFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}