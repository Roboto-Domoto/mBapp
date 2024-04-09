package com.example.mbapp_androidapp.presentation.windows

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@Composable
fun LogWindow(flag: MutableState<Boolean>)
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
                text = "LOGS",
                fontFamily = caviarFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(4.dp)
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.95f),
                state = rememberLazyListState(),
                contentPadding = PaddingValues(12.dp)
            ) {
                itemsIndexed(logs) { _, log ->
                    LogEvent(hour = log.hour, event = log.event)
                    Spacer(modifier = Modifier.height(16.dp)) //Margen
                }
            }
        },
        modifier = Modifier
            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
    )
}

@Composable
private fun LogEvent(hour: String, event: String) {
    Row {
        Text(
            text = hour,
            fontFamily = caviarFamily,
            fontSize = 12.sp,
            modifier = Modifier.padding(4.dp)
        )

        Text(
            text = event,
            fontFamily = caviarFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(4.dp)
        )
    }
}