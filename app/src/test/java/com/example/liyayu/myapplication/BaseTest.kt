package com.example.liyayu.myapplication

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by liyayu on 2018/11/7.
 */
class BaseTest{

    @Test
    fun a(){
        for (i in 1..8) {
            print(i)
        }

        assertEquals(4, (2 + 2).toLong())

    }
}