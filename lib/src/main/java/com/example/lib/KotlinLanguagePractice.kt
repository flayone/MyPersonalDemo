package com.example.lib

/**
 * Created by liyayu on 2018/5/28.
 */
var add = { x:Int,y:Int ,z:Int->x+y+z }

val addResult = add(1,2,3)
object CodeTest{
    @JvmStatic
    fun main() {
        println("add ==="+add(1,2,3))
    }
}

fun printAdd() {
    println("add ==="+add(1,2,3))
}