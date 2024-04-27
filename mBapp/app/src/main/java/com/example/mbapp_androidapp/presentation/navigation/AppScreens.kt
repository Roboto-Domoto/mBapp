package com.example.mbapp_androidapp.presentation.navigation

// Clase señada para almacenar las pantallas de la aplicación con sus rutas
sealed class AppScreens(val route: String) {
    data object SleepScreen: AppScreens("sleep_screen")
    data object HomeScreen: AppScreens("home_screen")
    data object MyShoppingScreen: AppScreens("my_shopping_screen")
    data object ItemsScreen: AppScreens("items_screen")
    data object EmployeeScreen: AppScreens("employee_screen")
    data object EmployeeSettingScreen: AppScreens("employee_settings_screen")
    data object BuyScreen: AppScreens("buy_screen")
    data object StockScreen: AppScreens("stock_screen")
    data object StockProcesScreen: AppScreens("stock_process_screen")
    data object CameraScreen: AppScreens("camera_screen")
}
