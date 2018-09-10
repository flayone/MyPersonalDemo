package com.example.liyayu.myapplication;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.Explode;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity;

import java.util.ArrayList;

/**
 * Created by liyayu on 2017/12/8.
 */

public class TestOneActivity extends BaseKotlinActivity {
    public static final String KEY_LIST = "str_list";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_one);
        setupWindowAnimations();

        View one = (View) findViewById(R.id.one);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestOneActivity.this, TestTwoActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TestOneActivity.this).toBundle());
            }
        });

    }

    public static void lanch(Context context, ArrayList<String> stringArrayList){
        Intent intent = new Intent(context,TestOneActivity.class);
        intent.putStringArrayListExtra(KEY_LIST,stringArrayList);
        context.startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Explode explode = new Explode();
        explode.setDuration(1500);
        explode.setInterpolator(new FastOutSlowInInterpolator());
        getWindow().setEnterTransition(explode);

        Explode explode2 = new Explode();
        explode2.setDuration(1000);
        getWindow().setExitTransition(explode2);

        Explode explode3 = new Explode();
        explode3.setDuration(50);
        getWindow().setReturnTransition(explode3);

        Explode explode4 = new Explode();
        explode4.setDuration(4000);
        explode4.setInterpolator(new LinearInterpolator());
        getWindow().setReenterTransition(explode4);

    }
}
