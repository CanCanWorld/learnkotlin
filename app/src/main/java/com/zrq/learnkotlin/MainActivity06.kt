package com.zrq.learnkotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.zrq.learnkotlin.common.images
import com.zrq.learnkotlin.ui.theme.LearnkotlinTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity06 : ComponentActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var text by mutableStateOf("")
        setContent {
            LearnkotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LazyColumn {
                        item {
                            Button(onClick = {
                                launch {
                                    try {
                                        delay(10000)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }) {
                                Text(text = "START")
                            }
                        }
                        item {
                            Button(onClick = {
                                cancel()
                            }) {
                                Text(text = "CANCEL")
                            }
                        }
                        item {
                            Text(text = text)
                        }
                    }
                }
            }
        }
    }

}

