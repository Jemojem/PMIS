package com.example.a5_6lab.ui_components

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.a5_6lab.utils.DrawerEvents
import com.example.a5_6lab.utils.ListItem
import kotlinx.coroutines.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.a5_6lab.MainViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel(), onClick: (ListItem) -> Unit) {
    var currentCategory = rememberSaveable { mutableStateOf("Список желаемого") }
    var previousCategory = rememberSaveable { mutableStateOf("") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    LaunchedEffect(currentCategory.value) {
        if (currentCategory.value == "Список желаемого") {
            mainViewModel.getFavorites()
        } else {
            mainViewModel.getAllItemsByCategory(currentCategory.value)
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerMenu { event ->
                    when (event) {
                        is DrawerEvents.OnItemClick -> {
                            previousCategory.value = currentCategory.value
                            currentCategory.value = event.title
                        }
                    }
                    scope.launch { drawerState.close() }
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    MainTopBar(title = currentCategory.value, drawerState = drawerState) {
                        previousCategory.value = currentCategory.value
                        currentCategory.value = "Список желаемого"
                    }
                }
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    items(mainViewModel.mainList.value) { item ->
                        MainListItem(item = item) { listItem ->
                            onClick(listItem)
                        }
                    }
                }
            }
        }
    )
    BackHandler(enabled = currentCategory.value == "Список желаемого") {
        if (previousCategory.value.isNotEmpty()) {
            currentCategory.value = previousCategory.value
            previousCategory.value = ""
        }
    }
}