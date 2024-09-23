package com.zrq.learnkotlin.common

import com.zrq.learnkotlin.entity.DataType
import com.zrq.learnkotlin.entity.Vertical
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://service.picasso.adesk.com"

interface Api {
    @GET("/v1/vertical/category/4e4d610cdf714d2966000003/vertical")
    fun getImages(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("adult") adult: Boolean,
        @Query("first") first: Int,
        @Query("order") order: String,
    ): Call<DataType<Vertical>>


    @GET("/v1/vertical/category/4e4d610cdf714d2966000003/vertical")
    suspend fun getImages2(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("adult") adult: Boolean,
        @Query("first") first: Int,
        @Query("order") order: String,
    ): DataType<Vertical>
}
