package com.example.a5_6lab.utils

sealed class Routes(val route: String) {
    object MAIN_SCREEN : Routes("main_screen")
    object INFO_SCREEN : Routes("info_screen")
}