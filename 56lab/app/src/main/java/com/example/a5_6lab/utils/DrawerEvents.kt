package com.example.a5_6lab.utils

sealed class DrawerEvents {
    data class OnItemClick(val title: String,val index:Int): DrawerEvents()
}