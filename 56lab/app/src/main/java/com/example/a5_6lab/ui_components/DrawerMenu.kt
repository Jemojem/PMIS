package com.example.a5_6lab.ui_components
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a5_6lab.R
import com.example.a5_6lab.ui.theme.BgTransp
import com.example.a5_6lab.ui.theme.MyBlue
import com.example.a5_6lab.utils.DrawerEvents
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration


@Composable
fun DrawerMenu(onEvent: (DrawerEvents) -> Unit) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.steamback),
            contentDescription = "Main Bg Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Header(isLandscape)
            Body(isLandscape) { event -> onEvent(event) }
        }
    }
}

@Composable
fun Header(isLandscape: Boolean) {
    val headerHeight = if (isLandscape) 130.dp else 170.dp
    Card(
        modifier = Modifier
            .fillMaxWidth() // Всегда заполняет всю ширину
            .height(headerHeight)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, MyBlue)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.steamheader),
                contentDescription = "Header Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Величайшие игры доступные в Steam",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyBlue)
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun Body(isLandscape: Boolean, onEvent: (DrawerEvents) -> Unit) {
    val list = stringArrayResource(id = R.array.Genres)

    if (isLandscape) {
        // Альбомная ориентация: горизонтальный список, занимающий часть высоты
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f) // Панель занимает 70% высоты экрана
                .padding(8.dp), // Отступы
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            itemsIndexed(list) { index, title ->
                GenreCard(title, index, onEvent, isLandscape = true)
            }
        }
    } else {
        // Портретная ориентация: вертикальный список, занимающий часть ширины
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.7f) // Панель занимает 70% ширины экрана
                .padding(8.dp), // Отступы
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            itemsIndexed(list) { index, title ->
                GenreCard(title, index, onEvent, isLandscape = false)
            }
        }
    }
}


@Composable
fun GenreCard(title: String, index: Int, onEvent: (DrawerEvents) -> Unit, isLandscape: Boolean) {
    val modifier = if (isLandscape) {
        Modifier
            .wrapContentWidth() // Компактная ширина для альбомной ориентации
            .padding(3.dp)
    } else {
        Modifier
            .fillMaxWidth() // Полная ширина для портретной ориентации
            .padding(3.dp)
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = BgTransp)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onEvent(DrawerEvents.OnItemClick(title, index)) }
                .padding(10.dp),
            textAlign = if (isLandscape) TextAlign.Start else TextAlign.Center, // Выравниваем текст
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

