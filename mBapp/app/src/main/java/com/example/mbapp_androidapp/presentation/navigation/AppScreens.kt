package com.example.mbapp_androidapp.presentation.navigation

// Clase señada para almacenar las pantallas de la aplicación con sus rutas
sealed class AppScreens(val route: String) {
    object SleepScreen: AppScreens("sleep_screen")
    object HomeScreen: AppScreens("home_screen")
    object MyShoppingScreen: AppScreens("my_shopping_screen")
    object ItemsScreen: AppScreens("items_screen")
    object EmployeeScreen: AppScreens("employee_screen")
}
