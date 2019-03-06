package com.example.liyayu.myapplication.baseFramework

import kotlin.reflect.KClass


@Target(AnnotationTarget.CLASS,AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestInject(val value: KClass<*>)