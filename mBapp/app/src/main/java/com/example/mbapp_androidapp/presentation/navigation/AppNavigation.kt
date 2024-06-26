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
import com.example.mbapp_androidapp.common.classes.System
import com.example.mbapp_androidapp.data.AppDatabase
import com.example.mbapp_androidapp.presentation.screens.BuyScreen
import com.example.mbapp_androidapp.presentation.screens.EmployeeScreen
import com.example.mbapp_androidapp.presentation.screens.EmployeeSettingScreen
import com.example.mbapp_androidapp.presentation.screens.HomeScreen
import com.example.mbapp_androidapp.presentation.screens.ItemsScreen
import com.example.mbapp_androidapp.presentation.screens.MinStockScreen
import com.example.mbapp_androidapp.presentation.screens.MyShoppingScreen
import com.example.mbapp_androidapp.presentation.screens.SleepScreen
import com.example.mbapp_androidapp.presentation.screens.StockScreen
import com.example.mbapp_androidapp.presentation.viewmodels.ItemsViewModel
import com.example.mbapp_androidapp.presentation.viewmodels.ItemsViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * Archivo encargado de permitir y mantener la navegación entre pantallas de la aplicación
 */

@Composable
fun AppNavigation() {
    val navController = rememberNavController() //Controlador de navegación
    val hourStart by remember { mutableStateOf(System.getInstance().getStartHour()) }
    val hourFormat = DateTimeFormatter.ofPattern("hh:mm")
    val hour by remember { mutableStateOf(LocalDateTime.now().format(hourFormat)) }

    if (hourStart == hour) System.getInstance().clearInventory()

    //Se instancia al cliente
    System.getInstance()

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
            MyShoppingScreen(navController)
        }

        composable(route = AppScreens.ItemsScreen.route) {
            ItemsScreen(navController,itemsViewModel)
        }

        composable(route = AppScreens.EmployeeScreen.route) {
            EmployeeScreen(navController)
        }

        composable(route = AppScreens.EmployeeSettingScreen.route) {
            EmployeeSettingScreen(navController)
        }

        composable(route = AppScreens.BuyScreen.route) {
            BuyScreen(navController,System.getInstance().getTopWeight(),System.getInstance().getBotWeight(),itemsViewModel)
        }

        composable(route = AppScreens.StockScreen.route) {
            StockScreen(navController, itemsViewModel)
        }

        composable(route = AppScreens.MinStockScreen.route) {
            MinStockScreen(itemsViewModel)
        }
    }
}