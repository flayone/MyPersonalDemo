package com.example.lowapiversiontest.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lowapiversiontest.ripple_demo.Coloring;
import com.example.lowapiversiontest.util.MyUtils;

import static com.example.lowapiversiontest.util.LocalContext.isRippleOpen;

/**
 * Created by liyayu on 2018/3/7.
 */

public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        //设置涟漪效果
        if (isRippleOpen){
            Coloring.get().setViewRipple(MyUtils.getAllViews(this));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
