package com.zrq.learnkotlin.entity

data class DataType<T>(
    val code: Int,
    val msg: String,
    val res: Res<T>
)