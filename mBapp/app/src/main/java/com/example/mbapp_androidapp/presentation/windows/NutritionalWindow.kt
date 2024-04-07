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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbapp_androidapp.common.classes.ItemClass
import com.example.mbapp_androidapp.ui.theme.caviarFamily

@Composable
fun NutritionalWindow(flag: MutableState<Boolean>, item: ItemClass, modifier: Modifier = Modifier) {
    AlertDialog(
        onDismissRequest = { flag.value = !flag.value },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { flag.value = !flag.value }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close button",
                        tint = Color.White,
                        modifier = Modifier.background(Color.Black, CircleShape)
                    )
                }
            }
        },
        title = { Title(item) },
        text = {
            NutritionInfo(item)
        },
        shape = RoundedCornerShape(5),
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(5))
    )
}

@Composable
private fun Title(item: ItemClass) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = item.pictureId),
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

@Composable
private fun NutritionInfo(item: ItemClass) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
    ) {
        Text(
            text = if (item.type == "Drink") "Cada 100 ml" else "Cada 100 g",
            fontWeight = FontWeight.Bold,
            fontFamily = caviarFamily,
            fontSize = 20.sp,
            color = Color.Black
        )

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            NutritionInfoText(element = "Calorías: ", quantity = item.nutritionInfo.getkCalText())
            NutritionInfoText(element = "Grasas: ", quantity = item.nutritionInfo.getFatText())
            NutritionInfoText(element = "Colesterol: ", quantity = item.nutritionInfo.getCholesterolText())
            NutritionInfoText(element = "Hidratos de carbono: ", quantity = item.nutritionInfo.getCarboHydrateText())
            NutritionInfoText(element = "Azúcar: ", quantity = item.nutritionInfo.getSugarText())
            NutritionInfoText(element = "Proteínas: ", quantity = item.nutritionInfo.getProteinText())
        }
    }

}

@Composable
private fun NutritionInfoText(element: String, quantity: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = element,
            fontWeight = FontWeight.Bold,
            fontFamily = caviarFamily,
        )
        Text(
            text = quantity
        )
    }
}