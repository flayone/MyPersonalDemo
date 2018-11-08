package com.example.liyayu.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.liyayu.myapplication.baseFramework.BaseKotlinActivity;
import com.example.liyayu.myapplication.demoViews.bluetoothPrinterDemo.BluetoothActivity;
import com.example.liyayu.myapplication.demoViews.coordinatorlayoutDemo.CoordinatorLayoutActivity;
import com.example.liyayu.myapplication.demoViews.fontDemo.FontActivity;
import com.example.liyayu.myapplication.demoViews.hotfixRobustDemo.RobustMainActivity;
import com.example.liyayu.myapplication.demoViews.imageViewTintDemo.TestImgTintActivity;
import com.example.liyayu.myapplication.demoViews.kalle_demo.KalleLoginActivity;
import com.example.liyayu.myapplication.demoViews.recycleDemo.RecycleActivity;
import com.example.liyayu.myapplication.demoViews.rippleDemo.Coloring;
import com.example.liyayu.myapplication.demoViews.rippleDemo.RippleActivity;
import com.example.liyayu.myapplication.demoViews.transitionDemo.TransitionMainActivity;
import com.example.liyayu.myapplication.util.NormalUtilsKt;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.liyayu.myapplication.jpush.JPushUtilsKt.setJpushAlias;

/**
 * Created by liyayu on 2018/3/1.
 */

public class HomeActivity extends BaseKotlinActivity {

    @BindView(R.id.text1)
    AppCompatTextView text1;
    @BindView(R.id.text2)
    AppCompatTextView text2;
    @BindView(R.id.button1)
    AppCompatButton button1;
    @BindView(R.id.button2)
    AppCompatButton button2;
    @BindView(R.id.button4)
    AppCompatButton button4;
    @BindView(R.id.button5)
    AppCompatButton button5;
    @BindView(R.id.button6)
    AppCompatButton button6;
    @BindView(R.id.button7)
    AppCompatButton button7;
    @BindView(R.id.button8)
    AppCompatButton button8;
    @BindView(R.id.button9)
    AppCompatButton button9;
    @BindView(R.id.button_end)
    AppCompatButton buttonEnd;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setJpushAlias(this, NormalUtilsKt.getVersionName(this));
        Coloring.get().setViewRipple(text2, button1, button2, button4, button5, button6, button7, button8,button9, buttonEnd);
    }

    @OnClick({R.id.text1, R.id.text2, R.id.button1, R.id.button2, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text1:
                new AlertDialog.Builder(this)
                        .setTitle("Title")
                        .setMessage("This is message")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.button_end:
                intent = new Intent(this, MainActivity.class);
//                intent = new Intent(this, TestErr.class);
                startActivity(intent);
                break;
            case R.id.button1:
                intent = new Intent(this, RecycleActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                intent = new Intent(this, RippleActivity.class);
                startActivity(intent);
                break;
            case R.id.button4:
                intent = new Intent(this, TransitionMainActivity.class);
                startActivity(intent);
                break;
            case R.id.button5:
                intent = new Intent(this, TestImgTintActivity.class);
                startActivity(intent);
                break;
            case R.id.button6:
                intent = new Intent(this, com.example.liyayu.myapplication.demoViews.bigImgDemo.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.button7:
                intent = new Intent(this, CoordinatorLayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.button8:
                startAct(RobustMainActivity.class);
                break;
            case R.id.button9:
                startAct(BluetoothActivity.class);
                break;
            case R.id.button10:
                startAct(KalleLoginActivity.class);
                break;
            case R.id.text2:
                intent = new Intent(this, FontActivity.class);
                startActivity(intent);
                break;
        }
    }
}
