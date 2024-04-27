package com.example.mbapp_androidapp.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mbapp_androidapp.common.classes.System

@Composable
fun CameraScreen(navController: NavHostController) {
    val system = System.getInstance()
    system.barcodeScanner.value?.scan()
    navController.navigateUp()
}