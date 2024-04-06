package com.example.mbapp_androidapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mbapp_androidapp.common.classes.Customer
import com.example.mbapp_androidapp.data.AppDatabase
import com.example.mbapp_androidapp.presentation.screens.EmployeeScreen
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
    val customer = Customer()

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
            MyShoppingScreen(customer)
        }

        composable(route = AppScreens.ItemsScreen.route) {
            ItemsScreen(itemsViewModel)
        }

        composable(route = AppScreens.EmployeeScreen.route) {
            EmployeeScreen(navController)
        }
    }
}