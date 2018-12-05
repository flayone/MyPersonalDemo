package com.example.liyayu.myapplication

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by liyayu on 2018/11/7.
 */
class BaseTest {

    @Test
    fun a() {
        for (i in 1..8) {
            print(i)
        }
        val s = 3 shl 2

        val selStatus = Array(5, { false })
        for (i in selStatus) {
            println("$i 否：${!i}")
        }

        val arr = arrayOf(1, 1, 1, 3, 3, 4, 5, 6)
         for (i in 0 until arr.size) {
            when (arr[i]) {
                1 -> {
                    println("$i")
                    return
                }
                3 -> {
                    println("$i")
                }
            }
        }
        assertEquals(4, (2 + 2).toLong())

    }
}