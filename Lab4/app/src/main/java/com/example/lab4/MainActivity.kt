package com.example.lab4

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.lab4.ui.theme.Lab4Theme
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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import java.io.File


class ComposeFileProvider : FileProvider(R.xml.files_paths) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory,
            )
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4Theme() {
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
            composable("Camera") { Camera()}
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
        BarItem(title = "Animation",Icons.Filled.Favorite, route = "SucroseAnim"),
        BarItem(title = "Camera",Icons.Filled.Face, route = "Camera")
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
            fontSize = 18.sp
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
@Composable
fun Camera() {
    var imageUri by rememberSaveable {mutableStateOf<Uri?>(null)}
    var hasImage by rememberSaveable {mutableStateOf(false) }
    var currentUri by rememberSaveable {mutableStateOf<Uri?>(null)}

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult={ uri: Uri? ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
            if (success) {
                imageUri = currentUri
            }
        }
    )

    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
        onResult ={ isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Сэр есть сэр", Toast.LENGTH_SHORT).show()
                currentUri?.let { cameraLauncher.launch(it) }
            } else {
                Toast.makeText(context,"Аппокалипсис неминуем",Toast.LENGTH_SHORT).show()
            }
        }
    )
    Column (
        modifier = Modifier.padding(10.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        val size = remember { mutableStateOf(500.dp) }
        val configuration = LocalConfiguration.current

        LaunchedEffect(configuration.orientation) {
            size.value = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                200.dp
            } else {
                500.dp
            }
        }

        if (hasImage && imageUri != null) {
            AsyncImage(
                model = imageUri,
                modifier = Modifier.size(size.value),
                contentDescription = "Selected Image",
            )
        } else {
            Box(modifier = Modifier.size(size.value)
            ) {
                Text(text = "Тут мог быть красивый пустой экран, но вместо этого здесь бесполезная надпись, закрой её какой-нибудь пикчей",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center))
            }
        }

        Row (
            modifier = Modifier.padding(5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    imagePicker.launch("image/*")
                }
            ) {
                Text(text = "Выбрать из галереи")
            }

            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                        currentUri = ComposeFileProvider.getImageUri(context)
                        val permissionCheckResult = ContextCompat.checkSelfPermission(
                            context, android.Manifest.permission.CAMERA)
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(currentUri!!) }
                        else {permissionLauncher.launch(android.Manifest.permission.CAMERA)}

                }
            )
            {
                Text(text = "Открыть камеру")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4Theme() {
        Greeting()
    }
}