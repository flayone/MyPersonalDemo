package com.example.liyayu.myapplication;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        int[] nums = new int[]{2, 7, 11, 15};
        System.out.println("addResult = " + Arrays.toString(twoSum(nums, 17)));


        assertEquals(4, 2 + 2);
    }
    public int[] twoSum(int[] nums, int target) {
        for(int i = 0;i < nums.length; i++){
            int sup = target - nums[i];
                for(int j = 0;j < nums.length; j++){
                    if(sup == nums[j]){
                        if (i != j){
                            return new int[]{i,j};
                        }
                    }
                }
        }
        return null;
    }
}