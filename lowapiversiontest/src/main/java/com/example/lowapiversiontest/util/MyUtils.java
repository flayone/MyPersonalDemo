package com.example.lowapiversiontest.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyayu on 2018/3/6.
 */

public class MyUtils {
    public static View[] getAllViews(Activity act) {
        List<View> list = getAllChildViews(act.getWindow().getDecorView());
        View[] allView = new View[list.size()];
        list.toArray(allView);
        return allView;
    }

    private static List<View> getAllChildViews(View view) {
        List<View> allchildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                if (!LocalContext.onlyNamedId) {
                    allchildren.add(viewchild);
                } else if (viewchild.getId() != View.NO_ID ) {
                    allchildren.add(viewchild);
                }

                //再次 调用本身（递归）
                allchildren.addAll(getAllChildViews(viewchild));
            }
        }
        return allchildren;
    }
}
