package com.example.mbapp_androidapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mbapp_androidapp.presentation.screens.EmployeeScreen
import com.example.mbapp_androidapp.presentation.screens.HomeScreen
import com.example.mbapp_androidapp.presentation.screens.ItemsScreen
import com.example.mbapp_androidapp.presentation.screens.SleepScreen


/**
 * Archivo encargado de permitir y mantener la navegación entre pantallas de la aplicación
 */

@Composable
fun AppNavigation() {
    val navController = rememberNavController() //Controlador de navegación

    // Almacenamiento y gestión de pantallas con NavHost
    NavHost(navController = navController, startDestination = AppScreens.SleepScreen.route) {
        composable(route = AppScreens.SleepScreen.route) {
            SleepScreen(navController)
        }
        composable(route = AppScreens.HomeScreen.route) {
            HomeScreen(navController)
        }

        composable(route = AppScreens.MyShoppingScreen.route) {

        }

        composable(route = AppScreens.ItemsScreen.route) {
            ItemsScreen()
        }

        composable(route = AppScreens.EmployeeScreen.route) {
            EmployeeScreen(navController)
        }
    }

}