package com.example.liyayu.myapplication.util

/**
 * Created by liyayu on 2018/5/29.
 */
class Calculator {
    fun divide(a: Int, b: Int): Double {
        if (b == 0) throw IllegalArgumentException("division by zero!")
        return (a.toDouble() / b)
    }
}