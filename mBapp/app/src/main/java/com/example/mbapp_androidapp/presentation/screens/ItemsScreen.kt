package com.example.mbapp_androidapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbapp_androidapp.R
import com.example.mbapp_androidapp.common.Drink
import com.example.mbapp_androidapp.common.ItemClass
import com.example.mbapp_androidapp.common.Snack
import com.example.mbapp_androidapp.presentation.windows.NutritionalWindow
import com.example.mbapp_androidapp.ui.theme.amableFamily
import com.example.mbapp_androidapp.ui.theme.caviarFamily

val cocacola = Drink(R.drawable.cocacola_lata, "Coca-Cola", 1.5f, 330f)
val cocacolazero = Drink(R.drawable.coca_cola_zero_lata, "Coca-Cola Zero", 1.5f, 330f)
val twix = Snack(R.drawable.twix, "Snickers", 1.25f, 50f)
val list = listOf(cocacola, cocacolazero, twix)
@Composable
fun ItemsScreen() {
    val showInfo = remember { mutableStateOf(false) }
    val item: MutableState<ItemClass?> = remember { mutableStateOf(null) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopElements()
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = rememberLazyListState(),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(list.size) { i ->
                    Item(item = list[i], showInfo, item) //Representación del producto
                    Spacer(modifier = Modifier.height(16.dp)) //Margen entre productos
                }
            }
        }
        Icon(
            imageVector = Icons.Rounded.Info,
            contentDescription = "Info button",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .size(52.dp)
        )
        if (showInfo.value && item.value != null) {
            NutritionalWindow(flag = showInfo, item = item.value!!)
        }
    }
}

@Composable
private fun Item(item: ItemClass, showInfo: MutableState<Boolean>,
                 itemToShow: MutableState<ItemClass?>
) {
    Column(
        modifier = Modifier.fillMaxWidth(0.95f),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = item.pictureId),
                contentDescription = "Product picture",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(108.dp)
            )
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = item.name,
                        fontFamily = caviarFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    IconButton(onClick = {
                        showInfo.value = !showInfo.value
                        itemToShow.value = item
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "More info",
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }
                Row {
                    Text(
                        text = "Precio:",
                        fontFamily = caviarFamily,
                        fontSize = 16.sp
                    )
                    val price = item.price
                    Text(
                        text = " $price €",
                        fontFamily = caviarFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 12.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Stock: ",
                        fontFamily = caviarFamily,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                    Text(
                        text = "2",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 48.sp
                    )
                }
            }
        }
        //Linea de separación entre productos
        Spacer(modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(1.dp)
            .background(Color.Gray)
            .align(Alignment.CenterHorizontally)
        )
    }

}

@Composable
private fun TopElements() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "10:00",
            fontSize = 36.sp,
            fontFamily = caviarFamily,
            letterSpacing = 0.sp
        )
        Text(
            text = "4º",
            fontSize = 32.sp,
            fontFamily = amableFamily
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ItemsScreen()
}
