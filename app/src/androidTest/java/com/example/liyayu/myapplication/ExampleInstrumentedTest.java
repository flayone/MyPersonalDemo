package com.example.liyayu.myapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        int[] nums = new int[]{2, 7, 11, 15};
        System.out.println("addResult = " + Arrays.toString(twoSum(nums, 9)));


        assertEquals("com.example.liyayu.myapplication", appContext.getPackageName());
    }
    public int[] twoSum(int[] nums, int target) {
        for(int i = 0;i < nums.length; i++){
            int sup = target - nums[i];
            if(sup != nums[i]){
                for(int j = 0;j < nums.length; j++){
                    if(sup == nums[j]){
                        return new int[]{i,j};
                    }
                }
            }
        }
        return null;
    }
}
