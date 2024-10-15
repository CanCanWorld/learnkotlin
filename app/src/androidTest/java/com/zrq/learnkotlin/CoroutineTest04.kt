package com.zrq.learnkotlin

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoroutineTest04 {

    @Test
    fun testDealWithTimeout() = runBlocking<Unit> {
        val result = withTimeoutOrNull(1300) {
            repeat(1000) {
                println("I'm sleeping $it")
                delay(500)
            }
            "Done"
        } ?: "Jack"
        println("Result is $result")
    }

    @Test
    fun testCoroutineContext() = runBlocking<Unit> {
        launch(Dispatchers.Default + CoroutineName("test")) {
            println("I'm working in thread ${Thread.currentThread().name}")
        }
    }

    @Test
    fun testCoroutineContextExtend() = runBlocking<Unit> {
        val scope = CoroutineScope(Job() + Dispatchers.IO + CoroutineName("test"))
        val job = scope.launch {
            println("${coroutineContext[Job]} ${Thread.currentThread().name}")
            val result = async {
                println("${coroutineContext[Job]} ${Thread.currentThread().name}")
                "OK"
            }.await()
        }
        job.join()
    }

    @Test
    fun testCoroutineContextExtend2() = runBlocking<Unit> {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        val scope = CoroutineScope(Job() + Dispatchers.Main + coroutineExceptionHandler)
        val job = scope.launch(Dispatchers.IO) {
            println("${coroutineContext[Job]} ${Thread.currentThread().name}")
        }
        job.join()
    }

    @Test
    fun testExceptionPropagation() = runBlocking<Unit> {
        val job = GlobalScope.launch {
            try {
                throw IndexOutOfBoundsException()
            } catch (e: Exception) {
                println("1")
            }
        }
        job.join()
        val deferred = GlobalScope.async {
            throw ArithmeticException()
        }
        try {
            deferred.await()
        } catch (e: Exception) {
            println("2")
        }
    }

    @Test
    fun testExceptionPropagation2() = runBlocking<Unit> {
        val scope = CoroutineScope(Job())
        val job = scope.launch {
            async {
                throw Exception("测试异常")
            }
        }
        job.join()
    }

    @Test
    fun testSupervisorJob() = runBlocking<Unit> {
        val scope = CoroutineScope(SupervisorJob())
        val job1 = scope.launch {
            delay(100)
            println("job1")
//            throw Exception("测试异常")
        }
        val job2 = scope.launch {
            delay(500)
            println("job2")
        }
        joinAll(job1, job2)
    }

    @Test
    fun testSupervisorScope() = runBlocking<Unit> {
        supervisorScope {
            launch {
                delay(100)
                println("job1")
                // 子协程抛出异常不会影响父协程
//                throw Exception("测试异常")
            }
        }
        delay(500)
        println("job2")
    }

    @Test
    fun testSupervisorScope2() = runBlocking<Unit> {
        supervisorScope {
            launch {
                delay(100)
                println("job1")
                throw Exception("测试异常")
            }
        }
        delay(500)
        println("job2")
        // 作用域抛出的异常会把子协程取消掉
//        throw Exception("测试异常2")
    }

}