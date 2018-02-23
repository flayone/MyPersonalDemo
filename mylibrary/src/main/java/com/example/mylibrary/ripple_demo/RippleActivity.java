package com.example.mylibrary.ripple_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mylibrary.R;


/**
 * Created by liyayu on 2018/2/8.
 */

public class RippleActivity extends AppCompatActivity {
    Button button;
    TextView text;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);
//        ButterKnife.bind(this);
        button = findViewById(R.id.button_lib);
        text = findViewById(R.id.text);
        linearLayout = findViewById(R.id.linear_layout);

        Coloring.get().setButtonRipple(button);
        Coloring.get().setViewRipple(text, "" + ContextCompat.getColor(this, R.color.primary));
        Coloring.get().setLayoutRipple(linearLayout);
    }

}
