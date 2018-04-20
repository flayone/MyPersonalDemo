package com.example.liyayu.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyayu.myapplication.coordinatorlayout_demo.CoordinatorLayoutActivity;
import com.example.liyayu.myapplication.font_demo.FontActivity;
import com.example.liyayu.myapplication.imageview_tint_demo.TestImgTintActivity;
import com.example.liyayu.myapplication.recycle_demo.RecycleActivity;
import com.example.liyayu.myapplication.ripple_demo.RippleActivity;
import com.example.liyayu.myapplication.transition_demo.TransitionMainActivity;
import com.example.liyayu.myapplication.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liyayu on 2018/3/1.
 */

public class HomeActivity extends AppCompatActivity {

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
    @BindView(R.id.liner1)
    LinearLayout liner1;
    Intent intent;
    @BindView(R.id.text3)
    AppCompatTextView text3;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.text_test)
    TextView textTest;
    @BindView(R.id.button5)
    AppCompatButton button5;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.button6)
    AppCompatButton button6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.text1, R.id.text2, R.id.button1, R.id.button2, R.id.button4, R.id.liner1, R.id.text3, R.id.button3,
            R.id.button5, R.id.button6, R.id.button7, R.id.button_end, R.id.text_test})
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
            case R.id.text2:
                intent = new Intent(this, MainActivity.class);
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
                intent = new Intent(this, com.example.liyayu.myapplication.big_img_demo.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.button7:
                intent = new Intent(this, CoordinatorLayoutActivity.class);
                startActivity(intent);
            case R.id.button_end:
                intent = new Intent(this, FontActivity.class);
                startActivity(intent);
                break;
            case R.id.liner1:
                ToastUtil.showToast(this, "你点这里干ha？是不是傻?");
                break;
            case R.id.text3:
                break;
            case R.id.button3:
            case R.id.text_test:
                Toast.makeText(this, "2222222", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void startAct(Class activity) {
        intent = new Intent(this, activity);
        startActivity(intent);
    }
}