package com.zrq.learnkotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import com.zrq.learnkotlin.common.images
import com.zrq.learnkotlin.entity.DataType
import com.zrq.learnkotlin.entity.Vertical
import com.zrq.learnkotlin.ui.theme.LearnkotlinTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    val paths = mutableStateListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnkotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LazyColumn {
                        items(paths.size) {
                            NetworkImage(paths[it])
                        }
                    }
                }
            }
        }
        getImages2()

        lifecycleScope.launch {
        }
    }



    private fun getImages() {
        images.getImages(10, 2000, true, 1, "new").enqueue(object : Callback<DataType<Vertical>> {
            override fun onResponse(call: Call<DataType<Vertical>>?, response: Response<DataType<Vertical>>?) {
                Log.d(TAG, "onResponse: ${response?.body()}")
            }

            override fun onFailure(call: Call<DataType<Vertical>>?, t: Throwable?) {
            }

        })
    }

    private fun getImages2() = CoroutineScope(Dispatchers.Main).launch {
        val random = Random(Date().time).nextInt(1, 1000)
        val res = images.getImages2(10, random, true, 1, "new")
        Log.d(TAG, "getImages: $res")
        res.res.vertical.forEach {
            Log.d(TAG, "images: ${it.img}")
            paths.add(it.img)
        }
    }
}

const val TAG = "Learnkotlin"

@Composable
fun NetworkImage(url: String) {
    val painter = rememberImagePainter(url)
    Image(
        painter = painter, contentDescription = null, modifier = Modifier
            .fillMaxWidth()
            .height(700.dp)
    )
}