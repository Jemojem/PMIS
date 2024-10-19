package com.example.lab1_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp


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
                composable("SucroseAnim") { SucroseAnim() }
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
        BarItem(title = "List", Icons.Filled.Menu, route = "listScreen"),
        BarItem(title = "Animation",Icons.Filled.Favorite, route = "SucroseAnim")
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListScreen() {
    val students = listOf(
        Student("Адамович Богдан константинович", "224401"), Student("Вербицкий Александр Олегович", "224401"),
        Student("Солодуха Александра Ивановна", "224401"), Student("Бубен Всеволод Дмитриевич", "224401"),
        Student("Король Елизавета Романовна", "224401"), Student("Булыня Александр Валерьевич", "224401"),
        Student("Царюк Владислав Олегович", "224401"), Student("Павловский Даниил Дмитриевич", "224401"),
        Student("Скурат Дмитрий Евгеньевич", "224401"),Student("Цыкман Роман Валерьевич", "224401"),
        Student("Зубенко Михаил Петрович", "224402"), Student("Шатилова Ольга Олеговна", "224402"),
        Student("Кукин Дмитрий Петрович", "224402"), Student("Коршикова Дарья Валерьевна", "224402"),
        Student("Гуревич Ольга Викторовна", "224402"),Student("Канаш Алексей Васильевич", "224402"),
        Student("Бузенкова Ксения Геннадьевна", "224403"), Student("Веретин Артём Павлович", "224403"),
        Student("Денисов Кирилл Артемович", "224403"), Student("Дудко Валерия Сергеевна", "224403"),
        Student("Климович Максим Дмитриевич", "224403"),Student("Королёв Владислав Владимирович", "224403")
    )

    val groupedStudents = students.groupBy { it.group }

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        Button(
            onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(students.size - 1)
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("Прокрутить к последнему студенту")
        }

        LazyColumn(
            contentPadding = PaddingValues(5.dp),
            state = scrollState
        ) {
            groupedStudents.forEach { (group, studentList) ->
                stickyHeader {
                    Text(
                        text = group,
                        fontSize = 28.sp,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color.Gray)
                            .padding(5.dp)
                            .fillMaxWidth()
                    )
                }
                items(studentList) { student ->
                        Text(student.name, Modifier.padding(5.dp), fontSize =
                        22.sp)
                }
            }
        }
    }
}
data class Student(val name:String, val group: String)
@Composable
fun SucroseAnim(){
    val circleHeight = 150
    val endOffset = 10
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val navigationBarHeight = 110


    val startOffset = screenHeight - circleHeight - navigationBarHeight

    var circleOffset by remember { mutableStateOf(startOffset) }
    val offset: Dp by animateDpAsState(
        targetValue = circleOffset.dp,
        animationSpec = spring(
            dampingRatio = 0.3f,
            stiffness = 100f
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = BitmapPainter(ImageBitmap.imageResource(R.drawable.sucrose)),
            contentDescription = "Animated Image",
            modifier = Modifier
                .offset(y = offset)
                .size(circleHeight.dp)
                .clip(CircleShape)
                .clickable {
                    circleOffset = if (circleOffset == startOffset) endOffset else startOffset
                }
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