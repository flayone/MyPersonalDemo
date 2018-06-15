package com.example.liyayu.myapplication

import com.example.liyayu.myapplication.util.Calculator
import com.example.liyayu.myapplication.util.addResult
import org.junit.Assert
import org.junit.Test

/**
 * Created by liyayu on 2018/5/29.
 */
class CalculatorTest {
    @Test
    fun testDivide() {
        val calculator = Calculator()
        val result = calculator.divide(2, 1)
        val nor = addResult
        println("addResult = "+nor)

        val funs = mapOf("sum" to ::sums)
        val mapFun = funs["sum"]
        if (mapFun != null) {
            val result = mapFun(1,2)
            println("sum result -> $result")
        }
        val aa = 0.1*3
        println("浮点运算测试 = "+(aa == 0.3) +"运算小数=$aa")

        Assert.assertEquals(2.0, result, 0.0001)
    }

    fun sums(a: Int, b: Int): Int {
        return a + b
    }

}