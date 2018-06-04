package com.example.liyayu.myapplication

import com.example.liyayu.myapplication.util.LogUtil
import com.example.liyayu.myapplication.util.addResult
import org.junit.Assert
import org.junit.Test

/**
 * Created by liyayu on 2018/5/29.
 */
class KotlinTest {

    @Test(expected = IllegalArgumentException::class)
    fun testDivide() {
        LogUtil.d("add result ==" + addResult)
        Assert.assertEquals(4, (2 + 2).toLong())
    }
}