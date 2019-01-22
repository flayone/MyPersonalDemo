package com.example.lib;

import java.util.ArrayList;
import java.util.List;

public class myClass {
    public static void main(String[] args) {
//        int i = 1929902%(1000*60*60)/(1000*60);
        int i = 2 << 3;
        int s = 13 >> 1;
        int y = 13 >>> 1;
        System.out.println(i );
        System.out.println(s );
        System.out.println(y );
        List list = new ArrayList();
        list.add("LYY");
        list.add(100);
        System.out.println("list:" + list);
    }
}
