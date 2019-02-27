package com.example.liyayu.myapplication.demoViews.bigImgDemo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.liyayu.myapplication.R;
import com.example.liyayu.myapplication.demoViews.bigImgDemo.glide.OkHttpProgressGlideModule;
import com.example.liyayu.myapplication.demoViews.bigImgDemo.glide.ProgressTarget;
import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.FileBitmapDecoderFactory;

import java.io.File;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;


public class NetworkDemoActivity extends FragmentActivity {
    private LargeImageView largeImageView;
    private RingProgressBar ringProgressBar;
    private View clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networkdemo);
        largeImageView = (LargeImageView) findViewById(R.id.networkDemo_photoView);
        ringProgressBar = (RingProgressBar) findViewById(R.id.networkDemo_ringProgressBar);
        clearButton = findViewById(R.id.networkDemo_button);

        final Glide glide = Glide.get(this);
        OkHttpProgressGlideModule a = new OkHttpProgressGlideModule();
        a.registerComponents(this, glide,glide.getRegistry());

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "开始清除缓存", Toast.LENGTH_SHORT).show();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Glide.get(getApplicationContext()).clearDiskCache();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "清除缓存成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }.start();
            }
        });

        String url = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%B1%82%E8%B6%85%E5%A4%A7%E5%9B%BE%E9%93%BE%E6%8E%A5%E5%9C%B0%E5%9D%80&step_word=&hs=2&pn=8&spn=0&di=83600&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=2652075135%2C3260844768&os=3487815213%2C1203230303&simid=4162120737%2C558051114&adpicid=0&lpn=0&ln=1128&fr=&fmq=1551250591387_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fb.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F314e251f95cad1c8be2255217b3e6709c93d511e.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bp7xt_z%26e3Bv54_z%26e3BvgAzdH3Fetjok-8l9an-8l9ann9da_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0&islist=&querylist=&force=undefined";
        Glide.with(this).load(url).downloadOnly(new ProgressTarget<String, File>(url, null) {
            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                ringProgressBar.setVisibility(View.VISIBLE);
                ringProgressBar.setProgress(0);
            }

            @Override
            public void onProgress(long bytesRead, long expectedLength) {
                int p = 0;
                if (expectedLength >= 0) {
                    p = (int) (100 * bytesRead / expectedLength);
                }
                ringProgressBar.setProgress(p);
            }

            @Override
            public void onResourceReady(File resource, Transition<? super File> animation) {
                super.onResourceReady(resource, animation);
                ringProgressBar.setVisibility(View.GONE);
                largeImageView.setImage(new FileBitmapDecoderFactory(resource));
            }

            @Override
            public void getSize(SizeReadyCallback cb) {
                cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            }
        });
    }
}
