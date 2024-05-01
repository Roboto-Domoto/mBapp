package com.example.mbapp_androidapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.example.mbapp_androidapp.common.classes.BarcodeScanner
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.mbapp_androidapp.common.classes.ItemClass
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.common.elements.MenuButton
import com.example.mbapp_androidapp.common.elements.TopElements
import com.example.mbapp_androidapp.data.entities.ItemEntity
import com.example.mbapp_androidapp.presentation.navigation.AppScreens
import com.example.mbapp_androidapp.presentation.viewmodels.ItemsViewModel
import com.example.mbapp_androidapp.presentation.windows.NewItemWindow
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@Composable
fun StockScreen(navController: NavHostController, itemsViewModel: ItemsViewModel) {
    val itemsList by itemsViewModel.allItems.observeAsState(emptyList())
    val showNewItemWindow = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopElements()
            Text(
                text = "Seleccione el producto a insertar",
                fontFamily = caviarFamily,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.95f),
                state = rememberLazyListState(),
                contentPadding = PaddingValues(12.dp)
            ) {
                itemsIndexed(itemsList.distinctBy{it.name}) { _, actualItem ->
                    //Representación del producto
                    Item(navController, itemsViewModel, actualItem.toItemClass())
                    Spacer(modifier = Modifier.height(16.dp)) //Margen entre productos
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            MenuButton(
                icon = Icons.Rounded.Add,
                text = "Añadir nuevo",
                onClick = {showNewItemWindow.value = !showNewItemWindow.value}
            )
        }
        if (showNewItemWindow.value) {
            NewItemWindow(flag = showNewItemWindow, itemsViewModel)
        }
    }
}



@OptIn(ExperimentalCoilApi::class)
@Composable
private fun Item(navController: NavHostController,itemsViewModel: ItemsViewModel, item: ItemClass) {
    val barcodeScanner = BarcodeScanner.getBarcodeScanner()
    val sys = System.getInstance()
    if(barcodeScanner.getCodeList().isNotEmpty()){
        for(code in barcodeScanner.getCodeList()){
            val newItem = ItemEntity(
                name = sys.lastItemAdd!!.name,
                pictureId = sys.lastItemAdd!!.pictureId,
                pictureUri = sys.lastItemAdd!!.pictureUri,
                quantity = sys.lastItemAdd!!.quantity,
                price = sys.lastItemAdd!!.price,
                type = sys.lastItemAdd!!.type,
                barcode = code,
                nutritionInfo = sys.lastItemAdd!!.nutritionInfo
            )
            itemsViewModel.addItem(newItem)
            System.getInstance().reItemToInventory(newItem.toItemClass())
            sys.addLog("Añadido nuevo articulo ${newItem.name} con codigo ${newItem.barcode}")
        }
        barcodeScanner.cleanList()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .clickable {
                sys.lastItemAdd=item
                barcodeScanner.scan()
                navController.navigate(AppScreens.StockScreen.route)
            },
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = if (item.pictureId!=null) painterResource(id = item.pictureId!!)
                else rememberImagePainter(item.pictureUri!!.toUri()),
                contentDescription = "Product picture",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(92.dp)
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
                        text = "Stock: ${itemsViewModel.getStock(item.name)}",
                        fontFamily = caviarFamily,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(end = 12.dp, bottom = 8.dp)
                            .align(Alignment.Bottom)
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