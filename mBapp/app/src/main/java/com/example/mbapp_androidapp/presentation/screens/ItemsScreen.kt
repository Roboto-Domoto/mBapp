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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.mbapp_androidapp.common.classes.ItemClass
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.common.elements.TopElements
import com.example.mbapp_androidapp.presentation.navigation.AppScreens
import com.example.mbapp_androidapp.presentation.viewmodels.ItemsViewModel
import com.example.mbapp_androidapp.presentation.windows.NutritionalWindow
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@Composable
fun ItemsScreen(navController:NavController,itemsViewModel: ItemsViewModel) {
    val doorIsOpen = System.getInstance().doorIsOpen.observeAsState(false)
    if (doorIsOpen.value) navController.navigate(AppScreens.BuyScreen.route)
    else {
        val showInfo = remember { mutableStateOf(false) }
        val item: MutableState<ItemClass?> = remember { mutableStateOf(null) }
        val itemsList by itemsViewModel.allItems.observeAsState(emptyList())

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopElements()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.95f),
                    state = rememberLazyListState(),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    itemsIndexed(itemsList.distinctBy{it.name}.filter{itemsViewModel.getStock(it.name)>0}) { _, actualItem ->
                        //Representación del producto
                        Item(item = actualItem.toItemClass(), showInfo, item, itemsViewModel)
                        Spacer(modifier = Modifier.height(16.dp)) //Margen entre productos
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = "Info button",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(52.dp)
                )
            }

            if (showInfo.value && item.value != null) {
                NutritionalWindow(flag = showInfo, item = item.value!!)
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun Item(item: ItemClass, showInfo: MutableState<Boolean>,
                 itemToShow: MutableState<ItemClass?>, itemsViewModel: ItemsViewModel
) {
    val stock = itemsViewModel.getStock(item.name)

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
                        text = "$stock",
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

