package com.example.mbapp_androidapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.mbapp_androidapp.common.classes.Customer
import com.example.mbapp_androidapp.common.classes.ItemClass
import com.example.mbapp_androidapp.common.elements.TopElements
import com.example.mbapp_androidapp.ui.theme.caviarFamily
import java.util.Locale

@Composable
fun MyShoppingScreen() {
    val customer = Customer.getInstance()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopElements()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.91f),
                state = rememberLazyListState(),
                contentPadding = PaddingValues(12.dp)
            ) {
                itemsIndexed(customer.getShoppingList()) { _, actualItem ->
                    //Representación del producto
                    Item(item = actualItem)
                    Spacer(modifier = Modifier.height(16.dp)) //Margen entre productos
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Total a pagar: ",
                    fontFamily = caviarFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
                Text(
                    text = "${String.format(Locale.US, "%.2f", customer.getAccount())}€",
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun Item(item: ItemClass) {
    Column(
        modifier = Modifier.fillMaxWidth(0.95f),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = if (item.pictureId!=null) painterResource(id = item.pictureId!!)
                else rememberImagePainter(item.pictureUri?.toUri()),
                contentDescription = "Product picture",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(108.dp)
                    .padding(end = 8.dp)
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
                        fontSize = 28.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 12.dp, top = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Precio: ",
                        fontFamily = caviarFamily,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(end = 12.dp, bottom = 8.dp)
                            .align(Alignment.Bottom)
                    )
                    Text(
                        text = "${String.format(Locale.US, "%.2f", item.price)}€",
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )
                }
            }
        }
        //Linea de separación entre productos
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(1.dp)
                .background(Color.Gray)
                .align(Alignment.CenterHorizontally)
        )
    }
}