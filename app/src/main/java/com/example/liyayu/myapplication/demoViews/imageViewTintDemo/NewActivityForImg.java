package com.example.liyayu.myapplication.demoViews.imageViewTintDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.liyayu.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liyayu on 2018/3/13.
 */

public class NewActivityForImg extends AppCompatActivity {
    @BindView(R.id.img_new)
    ImageView imgNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_img_page);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_new)
    public void onViewClicked() {
        imgNew.setColorFilter(ContextCompat.getColor(this,R.color.primary));
    }
}
