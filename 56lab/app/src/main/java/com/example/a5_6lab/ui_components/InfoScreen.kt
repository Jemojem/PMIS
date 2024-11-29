package com.example.a5_6lab.ui_components


import android.content.res.Configuration
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.a5_6lab.utils.ListItem

@Composable
fun InfoScreen(item: ListItem) {
    val url: MutableState<String?> = rememberSaveable { mutableStateOf(null) }
    Card(
        modifier = Modifier.fillMaxSize().padding(5.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        val configuration = LocalConfiguration.current
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                HtmlLoader(htmlName = item.htmlName, curUrl = url, modifier = Modifier.weight(1f))
                AssetImage(
                    imageName = item.imageName,
                    contentDescription = item.title,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(300.dp)
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AssetImage(
                    imageName = item.imageName,
                    contentDescription = item.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                HtmlLoader(htmlName = item.htmlName, curUrl = url)
            }
        }
    }
}

@Composable
fun HtmlLoader(htmlName: String, curUrl: MutableState<String?>, modifier: Modifier = Modifier) {
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    val context = LocalContext.current
    val assetManager = context.assets
    val inputStream = assetManager.open("html/$htmlName")
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    val htmlString = String(buffer)

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            WebView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                        backEnabled = view.canGoBack()
                        curUrl.value = view.url
                    }
                }
                settings.javaScriptEnabled = true
                if (curUrl.value == null)
                    loadData(htmlString, "text/html", "utf-8")
                else
                    loadUrl(curUrl.value!!)
                webView = this
            }
        },
        update = {
            webView = it
        }
    )

    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }
}






