package com.example.liyayu.myapplication;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;

import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity;
import com.example.liyayu.myapplication.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by liyayu on 2017/12/8.
 */

public class TestTwoActivity extends BaseKotlinActivity {
    private ArrayList<String> mStringList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_two);
        setupWindowAnimations();
    }

    @Override
    protected void onResume() {
        mStringList = getIntent().getStringArrayListExtra("string_list");
        LogUtil.d("TestTwoActivity.mStringList.size()="+mStringList.size());
        setResult(RESULT_OK);
        finish();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
