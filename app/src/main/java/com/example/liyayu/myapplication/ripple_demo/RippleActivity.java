package com.example.liyayu.myapplication.ripple_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liyayu.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liyayu on 2018/2/8.
 */

public class RippleActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);
        ButterKnife.bind(this);
        Coloring.get().setButtonRipple(button);
        Coloring.get().setViewRipple(text, "" + ContextCompat.getColor(this, R.color.primary));
        Coloring.get().setLayoutRipple(linearLayout);
    }

    @OnClick({R.id.button, R.id.text, R.id.linear_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                break;
            case R.id.text:
                break;
            case R.id.linear_layout:
                break;
        }
    }
}
