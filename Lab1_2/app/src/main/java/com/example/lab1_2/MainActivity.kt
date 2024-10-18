package com.example.lab1_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab1_2.ui.theme.Lab1_2Theme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1_2Theme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
        Column(Modifier.padding(0.dp)) {

            NavHost(
                navController = navController,
                startDestination = "greetingScreen",
                modifier = Modifier.weight(1f)
            ) {
                composable("greetingScreen") { Greeting() }
                composable("listScreen") { ListScreen() }
            }
            BottomNavigationBar(navController = navController)
        }

    }

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavBarItems.BarItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(imageVector = navItem.image, contentDescription = navItem.title)
                },
                label = { Text(text = navItem.title) }
            )
        }
    }
}

object NavBarItems {
    val BarItems = listOf(
        BarItem(title = "Home", Icons.Filled.Home, route = "greetingScreen"),
        BarItem(title = "List", Icons.Filled.Menu, route = "listScreen")
    )
}

data class BarItem(val title: String, val image: ImageVector, val route: String)

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Column (modifier = modifier
        .fillMaxSize()
        .background(Color(0xFF00FFE3)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        var myName by rememberSaveable{mutableStateOf("")}
        val nameString = stringResource(id = R.string.My_FIO)
        Text(
            text = myName,
            modifier = modifier.padding(16.dp),
            fontSize = 22.sp
        )
        Row {
            Button(
                onClick =
                {
                    myName = nameString
                }
            ) {
                Text(text = "Вывести ФИО")
            }
            Button (
                onClick =
                {
                    myName = ""
                }
            ) {
                Text(text = "X")
            }
        }
    }
}

@Composable
fun ListScreen() {
    val text = "Любое текстовое поле :)"

    Column(Modifier.padding(16.dp)) {
        Text(text = "Текстовое поле:", fontSize = 30.sp)
        Text(
            text = text,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab1_2Theme {
        Greeting()
    }
}