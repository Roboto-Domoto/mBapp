package com.example.mbapp_androidapp.presentation.windows

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.mbapp_androidapp.common.classes.ItemClass
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.ui.theme.caviarFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMinStockWindow(flag: MutableState<Boolean>, item: ItemClass) {
    val list = System.getInstance().getStockList()
    val itemList = list.firstOrNull { it.item.name == item.name }
    val minStock = remember {
        mutableStateOf(itemList?.minStock?.toString() ?: "")
    }

    AlertDialog(
        onDismissRequest = { flag.value = !flag.value },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {
                        if (itemList != null)
                            System.getInstance().changeStock(item, minStock.value.toInt())
                        else
                            System.getInstance().addMinStock(item, minStock.value.toInt())

                        flag.value = !flag.value
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Check button",
                        tint = Color.White,
                        modifier = Modifier.background(Color.Black, CircleShape)
                    )
                }
            }
        },
        title = { Title(item) },
        text = {
            Column {
                TextField(
                    value = minStock.value,
                    onValueChange = {
                        minStock.value = it
                    },
                    label = { Text(text = "Stock mínimo") },
                    singleLine = true,
                    modifier = Modifier.padding(4.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
        },
        shape = RoundedCornerShape(5),
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(5))
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun Title(item: ItemClass) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = if (item.pictureId!=null) painterResource(id = item.pictureId!!)
            else rememberImagePainter(item.pictureUri?.toUri()),
            contentDescription = "Product picture",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(72.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = item.name,
                fontFamily = caviarFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Text(
                text = item.getQuantityString(),
                fontFamily = caviarFamily,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
        }
    }
}