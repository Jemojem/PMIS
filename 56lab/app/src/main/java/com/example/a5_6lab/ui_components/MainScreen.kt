package com.example.a5_6lab.ui_components

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
import com.example.a5_6lab.ui_components.MainTopBar
import com.example.a5_6lab.utils.DrawerEvents
import com.example.a5_6lab.utils.IdArrayList
import com.example.a5_6lab.utils.ListItem
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(context: Context,onClick: (ListItem)->Unit) {
    val topBarTitle = remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val mainList= remember { mutableStateOf(getListItemsByIndex(0,context))
    }
    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {
            ModalDrawerSheet {
                DrawerMenu(){ event ->
                    when(event) {
                        is DrawerEvents.OnItemClick -> {
                            topBarTitle.value=event.title
                            mainList.value= getListItemsByIndex(
                                event.index,context)
                        }
                    }
                    scope.launch {
                        drawerState.close()
                    }
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    MainTopBar(title = topBarTitle.value, drawerState
                    = drawerState)
                }
            ) {innerPadding ->
                LazyColumn(modifier=
                Modifier.padding(innerPadding).fillMaxSize()){
                    items(mainList.value) {item ->
                        MainListItem(item = item){listItem -> onClick(listItem)}
                    }
                }
            }
        }
    )
}
private fun getListItemsByIndex(index: Int, context: Context): List<ListItem>{
    val list = ArrayList<ListItem>()
    val arrayList = context.resources.getStringArray(IdArrayList.listId[index])
    arrayList.forEach { item ->
        val itemArray = item.split("|")
        list.add(
            ListItem(
                itemArray[0],
                itemArray[1]
            )
        )
    }
    return list
}