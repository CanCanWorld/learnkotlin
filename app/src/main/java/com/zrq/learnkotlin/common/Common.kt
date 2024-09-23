package com.zrq.learnkotlin.common

import android.util.Log
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "http"

private val okHttpClient = okhttp3.OkHttpClient.Builder()
    .addInterceptor {
        val request = it.request().newBuilder()
            .build()
        Log.d(TAG, "request: $request")
        it.proceed(request)
    }
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()


val images = retrofit.create(Api::class.java)