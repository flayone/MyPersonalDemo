package com.example.lib

/**
 * Created by liyayu on 2018/5/28.
 */
object TestLocalCode {
    @JvmStatic
    fun main(args: Array<String>) {
        val i = 1929902 % (1000 * 60 * 60) / (1000 * 60)
        print(i)
    }
}