package com.zrq.learnkotlin

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.FileReader
import java.util.concurrent.CancellationException

@RunWith(AndroidJUnit4::class)
class CoroutineTest03 {

    @Test
    fun testCancelCpuTaskByYield() = runBlocking<Unit> {
        val job = launch {
            try {
                repeat(1000) {
                    println("job: I'm sleeping $it")
                    delay(500)
                }
            } finally {
                println("job: I'm running finally.")
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }

    @Test
    fun testWithFunction() = runBlocking<Unit> {
        val br = BufferedReader(FileReader("D:\\study\\test.txt"))
        with(br) {
            var line: String
            while (true) {
                line = readLine() ?: break
                println(line)
            }
            close()
        }
    }

    /**
     * use 函数自动关闭文件对象
     */
    @Test
    fun testUseFunction() = runBlocking<Unit> {
        val br = BufferedReader(FileReader("D:\\study\\test.txt"))
        br.use {
            var line: String
            while (true) {
                line = it.readLine() ?: break
                println(line)
            }
        }
    }

    @Test
    fun testCancelWithNonCancelable() = runBlocking<Unit> {
        val job = launch {
            try {
                repeat(1000) {
                    println("job: I'm sleeping $it")
                    delay(500)
                }
            } finally {
                withContext(NonCancellable) {
                    println("job: I'm running finally.")
                    delay(1000)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }


}