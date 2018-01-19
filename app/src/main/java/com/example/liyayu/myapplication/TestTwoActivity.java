package com.example.liyayu.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;

/**
 * Created by liyayu on 2017/12/8.
 */

public class TestTwoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_two);
        setupWindowAnimations();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setEnterTransition(explode);

        Explode explode2 = new Explode();
        explode2.setDuration(50);
        getWindow().setExitTransition(explode2);

        Explode explode3 = new Explode();
        explode3.setDuration(50);
        getWindow().setReturnTransition(explode3);
    }

}
