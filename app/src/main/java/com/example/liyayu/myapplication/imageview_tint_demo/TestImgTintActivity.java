package com.example.liyayu.myapplication.imageview_tint_demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.liyayu.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liyayu on 2018/3/9.
 */

public class TestImgTintActivity extends Activity {
    @BindView(R.id.btn_change)
    Button btnChange;
    @BindView(R.id.btn_skip)
    Button btnSkip;
    @BindView(R.id.img_for_change)
    ImageView imgForChange;
    private ImageView iv_green;
    private ImageView iv_red;
    private ImageView iv_transparent;
    private Spinner spinner;
    //透明度滑动条
    private SeekBar sb_transparent;
    //红色滑动条
    private SeekBar sb_red;
    //绿色滑动条
    private SeekBar sb_green;
    //蓝色滑动条
    private SeekBar sb_blue;
    private static final PorterDuff.Mode[] MODES = new PorterDuff.Mode[]{
            PorterDuff.Mode.CLEAR,
            PorterDuff.Mode.SRC,
            PorterDuff.Mode.DST,
            PorterDuff.Mode.SRC_OVER,
            PorterDuff.Mode.DST_OVER,
            PorterDuff.Mode.SRC_IN,
            PorterDuff.Mode.DST_IN,
            PorterDuff.Mode.SRC_OUT,
            PorterDuff.Mode.DST_OUT,
            PorterDuff.Mode.SRC_ATOP,
            PorterDuff.Mode.DST_ATOP,
            PorterDuff.Mode.XOR,
            PorterDuff.Mode.DARKEN,
            PorterDuff.Mode.LIGHTEN,
            PorterDuff.Mode.MULTIPLY,
            PorterDuff.Mode.SCREEN,
            PorterDuff.Mode.ADD,
            PorterDuff.Mode.OVERLAY
    };
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test_tint);
        ButterKnife.bind(this);
        iv_green = (ImageView) findViewById(R.id.green);
        iv_transparent = (ImageView) findViewById(R.id.transparent);
        iv_red = (ImageView) findViewById(R.id.red);
        sb_transparent = (SeekBar) findViewById(R.id.alpha_seekBar);
        sb_red = (SeekBar) findViewById(R.id.red_seekBar);
        sb_green = (SeekBar) findViewById(R.id.green_seekBar);
        sb_blue = (SeekBar) findViewById(R.id.blue_seekBar);
        spinner = (Spinner) findViewById(R.id.spinner);
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.modes, android.R.layout.simple_list_item_1);
        spinner.setAdapter(spinnerAdapter);
        initEvent();
        updateImage(getRGBColor(), getMode());
    }

    private void initEvent() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateImage(getRGBColor(), getMode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateImage(getRGBColor(), getMode());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        sb_transparent.setOnSeekBarChangeListener(seekBarChangeListener);
        sb_red.setOnSeekBarChangeListener(seekBarChangeListener);
        sb_green.setOnSeekBarChangeListener(seekBarChangeListener);
        sb_blue.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    private PorterDuff.Mode getMode() {
        return MODES[spinner.getSelectedItemPosition()];
    }

    /**
     * 根据ARGB值计算颜色值
     *
     * @return 颜色值
     */
    private int getRGBColor() {
        int alpha = sb_transparent.getProgress();
        int red = sb_red.getProgress();
        int green = sb_green.getProgress();
        int blue = sb_blue.getProgress();
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * 更新颜色与模式
     *
     * @param color 颜色
     * @param mode  模式
     */
    private void updateImage(int color, PorterDuff.Mode mode) {
        iv_red.setColorFilter(color, mode);
        iv_green.setColorFilter(color, mode);
        iv_transparent.setColorFilter(color, mode);
    }
    boolean hasChange = false;
    @OnClick({R.id.btn_change, R.id.btn_skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                if (hasChange){
                    imgForChange.setColorFilter(ContextCompat.getColor(this,R.color.colorAccent));
                    hasChange = false;
                }else {
                    imgForChange.setColorFilter(ContextCompat.getColor(this,R.color.primary));
                    hasChange = true;
                }
                break;
            case R.id.btn_skip:
                intent = new Intent(this,NewActivityForImg.class);
                startActivity(intent);
                break;
        }
    }
}
