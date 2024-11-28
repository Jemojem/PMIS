package com.example.a5_6lab

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.a5_6lab.ui.theme._56LabTheme
import com.example.a5_6lab.ui_components.DrawerMenu
import com.example.a5_6lab.ui_components.MainListItem
import com.example.a5_6lab.ui_components.MainScreen
import com.example.a5_6lab.ui_components.MainTopBar
import com.example.a5_6lab.utils.DrawerEvents
import com.example.a5_6lab.utils.IdArrayList
import com.example.a5_6lab.utils.ListItem
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _56LabTheme {
                MainScreen(context = this)
            }
        }
    }
}