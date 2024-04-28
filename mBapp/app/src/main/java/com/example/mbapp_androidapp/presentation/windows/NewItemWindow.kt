package com.example.mbapp_androidapp.presentation.windows

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.mbapp_androidapp.common.classes.NutritionInfoClass
import com.example.mbapp_androidapp.common.classes.PhotoManager
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.data.entities.ItemEntity
import com.example.mbapp_androidapp.presentation.viewmodels.ItemsViewModel
import com.example.mbapp_androidapp.ui.theme.caviarFamily
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewItemWindow(flag: MutableState<Boolean>, itemsViewModel: ItemsViewModel) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Drink") }
    var calories by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var cholesterol by remember { mutableStateOf("") }
    var carbohydrate by remember { mutableStateOf("") }
    var sugar by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }

    var uri by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = { flag.value = !flag.value },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                //Botón para confirmar
                IconButton(
                    onClick = { 
                        val q = if (quantity == "") 0f else quantity.toFloat()
                        val p = if (price == "") 0f else price.toFloat()
                        val c = if (calories == "") 0f else calories.toFloat()
                        val f = if (fat == "") 0f else fat.toFloat()
                        val ch = if (cholesterol == "") 0f else cholesterol.toFloat()
                        val car = if (carbohydrate == "") 0f else carbohydrate.toFloat()
                        val s = if (sugar == "") 0f else sugar.toFloat()
                        val pro = if (protein == "") 0f else protein.toFloat()
                        
                        if (p > 0 && q > 0 && uri != "") {
                            val item = ItemEntity(
                                name = name,
                                price = p,
                                pictureUri = uri,
                                quantity = q,
                                type = type,
                                nutritionInfo = NutritionInfoClass(
                                    calories = c,
                                    cholesterol = ch,
                                    fat = f,
                                    carbohydrate = car,
                                    sugar = s,
                                    protein = pro
                                )
                            )
                            itemsViewModel.addItem(item)
                        }
                        flag.value = !flag.value
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Change the telephone number of admin",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.Black, CircleShape)
                    )
                }
            }
        },
        title = {
            Text(
                text = "Nuevo producto",
                fontFamily = caviarFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(4.dp)
            )
        },
        text = {
            LazyColumn {
                item {
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "Nombre") },
                        singleLine = true,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                item {
                    TextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text(text = "Precio") },
                        singleLine = true,
                        modifier = Modifier.padding(4.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
                    )
                }
                item {
                    TextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = { Text(text = "Cantidad") },
                        singleLine = true,
                        modifier = Modifier.padding(4.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
                    )
                }
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = type == "Drink",
                            onClick = { type = "Drink" }
                        )
                        Text(text = "Bebida")

                        RadioButton(
                            selected = type == "Snack",
                            onClick = { type = "Snack" }
                        )
                        Text(text = "Snack")
                    }
                }
                item {
                    TextField(
                        value = calories,
                        onValueChange = { calories = it },
                        label = { Text(text = "Calorias") },
                        singleLine = true,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                item {
                    TextField(
                        value = fat,
                        onValueChange = { fat = it },
                        label = { Text(text = "Grasas") },
                        singleLine = true,
                        modifier = Modifier.padding(4.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
                    )
                }
                item {
                    TextField(
                        value = cholesterol,
                        onValueChange = { cholesterol = it },
                        label = { Text(text = "Colesterol") },
                        singleLine = true,
                        modifier = Modifier.padding(4.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
                    )
                }
                item {
                    TextField(
                        value = carbohydrate,
                        onValueChange = { carbohydrate = it },
                        label = { Text(text = "Carbohidratos") },
                        singleLine = true,
                        modifier = Modifier.padding(4.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
                    )
                }
                item {
                    TextField(
                        value = sugar,
                        onValueChange = { sugar = it },
                        label = { Text(text = "Azúcar") },
                        singleLine = true,
                        modifier = Modifier.padding(4.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
                    )
                }
                item {
                    TextField(
                        value = protein,
                        onValueChange = { protein = it },
                        label = { Text(text = "Proteinas") },
                        singleLine = true,
                        modifier = Modifier.padding(4.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
                    )
                }
                item {
                    Button(
                        onClick = {
                            PhotoManager.getInstance().selectPhotoFromDevice()
                            uri = PhotoManager.getInstance().getUri().toString()
                        },
                        colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                        Text(text = "Añadir imagen")
                    }
                }
            }
        },

        modifier = Modifier
            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
    )
}