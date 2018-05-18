package com.example.liyayu.myapplication.bigImgDemo;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;

import com.example.liyayu.myapplication.demoViews.bigImgDemo.MainActivity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by liyayu on 2018/3/13.
 */
public class ViewPagerDemoActivityTest {
    @Test
    public void onCreate() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.startActivity(new Intent(appContext,MainActivity.class));
        assertEquals("com.example.liyayu.myapplication", appContext.getPackageName());
    }

}