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
        println("addResult = " + nor)

        val funs = mapOf("sum" to ::sums)
        val mapFun = funs["sum"]
        if (mapFun != null) {
            val result = mapFun(1, 2)
            println("sum result -> $result")
        }
        //区间归属测试
        val cou = 20
        when (cou) {
            in 1..20 -> println("cou在1-20范围内")
            in 20..30 -> println("cou在20-30范围内")
            else -> println("cou不在范围内")
        }
//        二进制转换失真测试
        val aa = 0.1 * 3
        println("浮点运算测试 = " + (aa == 0.3) + "运算小数=$aa")
//        阶乘测试
        var jie = 10
        for (i in 1..jie) {
            jie *= i
        }
        println("阶乘结果 = " + jie)
//        测试list相关操作
        val level = mutableListOf<Int>()
        val levelString = mutableListOf<String>()
        level.add(1)
        level.add(3)
        level.add(5)
        println("list结果 = " + level.toString())

        level.addAll(arrayListOf(1, 2, 3))
        levelString.addAll(arrayListOf("1"))
        level[1] = 5
        println("list结果 = " + level.toString() + levelString)

        Assert.assertEquals(2.0, result, 0.0001)
    }

    fun sums(a: Int, b: Int): Int {
        return a + b
    }

}