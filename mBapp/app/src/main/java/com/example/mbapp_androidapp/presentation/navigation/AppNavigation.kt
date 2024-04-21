package com.example.mbapp_androidapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mbapp_androidapp.R
import com.example.mbapp_androidapp.common.classes.Customer
import com.example.mbapp_androidapp.common.classes.ItemClass
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.data.AppDatabase
import com.example.mbapp_androidapp.data.entities.ItemEntity
import com.example.mbapp_androidapp.presentation.screens.BuyScreen
import com.example.mbapp_androidapp.presentation.screens.EmployeeScreen
import com.example.mbapp_androidapp.presentation.screens.EmployeeSettingScreen
import com.example.mbapp_androidapp.presentation.screens.HomeScreen
import com.example.mbapp_androidapp.presentation.screens.ItemsScreen
import com.example.mbapp_androidapp.presentation.screens.MyShoppingScreen
import com.example.mbapp_androidapp.presentation.screens.SleepScreen
import com.example.mbapp_androidapp.presentation.viewmodels.ItemsViewModel
import com.example.mbapp_androidapp.presentation.viewmodels.ItemsViewModelFactory


/**
 * Archivo encargado de permitir y mantener la navegación entre pantallas de la aplicación
 */

@Composable
fun AppNavigation() {
    val navController = rememberNavController() //Controlador de navegación
    //Se instancia al cliente
    val system = System.getInstance()
    val snicker = ItemClass(
        name = "Snicker",
        pictureId = R.drawable.twix,
        price = 1.25f,
        type = "Snack",
        quantity = 50f
    )
    val cocaColaZero = ItemClass(
        name = "Coca-Cola Zero",
        pictureId = R.drawable.coca_cola_zero_lata,
        price = 1.5f,
        type = "Drink",
        quantity = 330f
    )
    val water = ItemClass(
        name = "Agua",
        pictureId = R.drawable.botella_agua,
        price = 1.5f,
        type = "Drink",
        quantity = 500f
    )
    system.customer.addProduct(snicker)
    system.customer.addProduct(snicker)
    system.customer.addProduct(cocaColaZero)
    system.customer.addProduct(water)
    system.customer.addProduct(snicker)

    //Room y DAO
    val appDatabase = AppDatabase.getDatabase(context = LocalContext.current.applicationContext)
    val itemDao = appDatabase.itemDao()

    //ViewModels
    val itemsViewModel: ItemsViewModel = viewModel(factory = ItemsViewModelFactory(itemDao))

    // Almacenamiento y gestión de pantallas con NavHost
    NavHost(navController = navController, startDestination = AppScreens.SleepScreen.route) {
        composable(route = AppScreens.SleepScreen.route) {
            SleepScreen(navController)
        }
        composable(route = AppScreens.HomeScreen.route) {
            HomeScreen(navController)
        }

        composable(route = AppScreens.MyShoppingScreen.route) {
            MyShoppingScreen()
        }

        composable(route = AppScreens.ItemsScreen.route) {
            ItemsScreen(itemsViewModel)
        }

        composable(route = AppScreens.EmployeeScreen.route) {
            EmployeeScreen(navController)
        }

        composable(route = AppScreens.EmployeeSettingScreen.route) {
            EmployeeSettingScreen()
        }

        composable(route = AppScreens.BuyScreen.route) {
            BuyScreen()
        }
    }
}