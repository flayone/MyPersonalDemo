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

//        val funs = mapOf("sum" to ::sums)
//        val mapFun = funs["sum"]
//        if (mapFun != null) {
//            val result = mapFun(1, 2)
//            println("sum result -> $result")
//        }
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

        var base  = 100.00
        for (i in 1..10){
            base *= 1.05
        }
        println("n年后变化 = " + base.toInt())

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

        //lambda 表达式测试
        val lambda = {
            left: Int, right: Int
            ->
            left + right
        }
        println(lambda(2, 3))
        println(lambda.invoke(2, 3))

        val jsonString = "{\"l1\":\"demo\",\"l2\":2}"
        val jsonArrayString = "[{\"l1\":\"demo\",\"l2\":2},{\"l1\":\"demo\",\"l2\":2}]"
//        println("fastJSON = " +JSON.toJSONString(jsonArrayString))

//        var a = JSON.parseObject(jsonString) as A1
//        println("a == ${a.l1} + ${a.l2}")

        Assert.assertEquals(2.0, result, 0.0001)
    }

    private fun sums(a: Int, b: Int): Int {
        return a + b
    }
    data class A1(
            val l1: String? ,
            val l2: Int?
    )
}