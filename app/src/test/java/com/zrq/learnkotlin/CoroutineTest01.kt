package com.zrq.learnkotlin

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CoroutineTest01 {

    // 返回了多个值，但不是异步
    fun simpleList(): List<Int> = listOf(1, 2, 3)

    // 返回了多个值，但是同步
    fun simpleSequence(): Sequence<Int> = sequence {
        for (i in 1..3) {
            Thread.sleep(1000)  // 阻塞
//            delay(1000) // 不能使用，只能使用限制的挂起函数
            yield(i)
        }
    }

    suspend fun simpleList2(): List<Int> {
        val list = mutableListOf<Int>()
        for (i in 1..3) {
            delay(1000)
            list.add(i)
        }
        return list
    }

    suspend fun simpleFlow() = flow<Int> {
        for (i in 1..3) {
            println("emit: $i")
            delay(1000)
            emit(i)
        }
    }

    @Test
    fun `test multiple values`() = runBlocking {
//        simpleList2().forEach(::println)
        simpleFlow().collect(::println)
    }

    @Test
    fun `test flow is cold`() = runBlocking {
        val flow = simpleFlow()
        println("①")
        flow.collect(::println)
        println("②")
        flow.collect(::println)
    }

}