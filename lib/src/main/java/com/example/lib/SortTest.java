package com.example.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SortTest {
    public static void main(String[] a) {
        //java 乱序重排
        int size = 10;
        List<Integer> ori = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ori.add(i+1);
        }
        Collections.shuffle(ori);


        for (int j = 0; j < size; j++) {
            System.out.println("乱序："+ori.get(j));
        }

        List result = new ArrayList<Integer>();
        Random random = new Random();
        int n = random.nextInt(ori.size());

//        int radom =ori.get(n);
        Collections.sort(ori);

        for (int j = 0; j < size; j++) {
            System.out.println("乱序重排："+ori.get(j));
        }

    }
}
