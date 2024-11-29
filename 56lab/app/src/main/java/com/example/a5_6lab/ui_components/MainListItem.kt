package com.example.a5_6lab.ui_components
import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a5_6lab.ui.theme.BgTranp1
import com.example.a5_6lab.ui.theme.MyBlue
import com.example.a5_6lab.utils.ListItem

@Composable
fun MainListItem(item: ListItem, onClick: (ListItem) -> Unit) {
    val isDarkTheme = isSystemInDarkTheme() // Проверка, используется ли тёмная тема

    // Выбор цветов в зависимости от темы
    val borderColor = if (isDarkTheme) MyBlue else BgTranp1
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val backgroundColor = if (isDarkTheme) MyBlue else BgTranp1 // Цвет фона текста

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(5.dp)
            .clickable { onClick(item) },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, borderColor) // Применяем цвет границы
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AssetImage(
                imageName = item.imageName,
                contentDescription = item.title,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor) // Применяем цвет фона для текста
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = textColor // Применяем цвет текста
            )
        }
    }
}
@Composable
fun AssetImage(imageName:String,contentDescription: String, modifier: Modifier
){
    val context= LocalContext.current
    val assetManager=context.assets
    val inputStream=assetManager.open(imageName)
    val bitMap= BitmapFactory.decodeStream(inputStream)
    Image(bitmap = bitMap.asImageBitmap(), contentDescription =contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}