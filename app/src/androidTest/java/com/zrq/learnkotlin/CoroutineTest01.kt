package com.zrq.learnkotlin

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import kotlin.system.measureTimeMillis

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CoroutineTest01 {

    @Test
    fun testCoroutineBuilder() = runBlocking {
        val job1 = launch {
            delay(200)
            println("job1 finish")
        }
        val job2 = async {
            delay(100)
            println("job2 finish")
            "job2 result"
        }
        println(job2.await())
    }

    @Test
    fun testCoroutineJoin() = runBlocking {
        val job1 = launch {
            delay(300)
            println("job1 finish")
        }
        job1.join()
        val job2 = async {
            delay(200)
            println("job2 finish")
        }
        val job3 = async {
            delay(100)
            println("job3 finish")
        }
    }

    @Test
    fun testCoroutineAwait() = runBlocking {
        val job1 = async {
            delay(300)
            println("job1 finish")
        }
        job1.await()
        val job2 = async {
            delay(200)
            println("job2 finish")
        }
        val job3 = async {
            delay(100)
            println("job3 finish")
        }
    }

    @Test
    fun testSync() = runBlocking<Unit> {
        val time1 = measureTimeMillis {
            val result = doOne() + doTwo()
            println(result)
        }
        println(time1)    // 耗时200ms
        val time2 = measureTimeMillis {
            val result = async { doOne() }.await() + async { doTwo() }.await()
            println(result)
        }
        println(time2)    // 耗时200ms
        val time3 = measureTimeMillis {
            val result = async { doOne() + doTwo() }.await()
            println(result)
        }
        println(time3)    // 耗时200ms
        val time4 = measureTimeMillis {
            val result1 = async { doOne() }
            val result2 = async { doTwo() }
            println(result1.await() + result2.await())
        }
        println(time4)    // 耗时100ms
    }

    private suspend fun doOne(): Int {
        delay(100)
        return 14
    }

    private suspend fun doTwo(): Int {
        delay(100)
        return 25
    }

    @Test
    fun testCoroutineScopeBuilder() = runBlocking {
        coroutineScope {
            val job1 = async {
                delay(200)
                println("job1 finish")
            }
            val job2 = async {
                delay(100)
                println("job2 finish")
                throw Exception("job2 error")
            }
        }
    }

    @Test
    fun testSupervisorScopeBuilder() = runBlocking {
        supervisorScope {
            val job1 = async {
                delay(200)
                println("job1 finish")
            }
            val job2 = async {
                delay(100)
                println("job2 finish")
                throw Exception("job2 error")
            }
        }
    }
}