package com.example.lib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class myClass {

    public static void main(String[] args) {
//        int i = 1929902%(1000*60*60)/(1000*60);
        List<List<EasyModel>> mList = new ArrayList<>();
        List<EasyModel> mChildList = new ArrayList<>();
        int i = 2 << 3;
        int s = 13 >> 1;
        int y = 13 >>> 1;
        for (int k = 0;k<6;k++){
            mChildList.add(new EasyModel());
        }
        for (int k = 0;k<6;k++) {

            mList.add(mChildList);
        }
        System.out.println(i );
        System.out.println(s );
        System.out.println(y );
        List list = new ArrayList();

        List<EasyModel> j = mList.get(3);
        j.get(3).key = "test3";
        j.get(2).key = "test2";

        System.out.println("mList.get(3).get(3):" + mList.get(3).get(3).key);
        System.out.println("mList.get(3).get(2):" + mList.get(3).get(2).key);
        list.add("LYY");
        list.add(100);
        System.out.println("list:" + list);
    }

    static class EasyModel implements Serializable{
        public String key;

    }
}
