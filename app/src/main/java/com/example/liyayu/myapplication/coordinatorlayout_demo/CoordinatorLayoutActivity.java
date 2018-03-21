package com.example.liyayu.myapplication.coordinatorlayout_demo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liyayu.myapplication.R;
import com.example.liyayu.myapplication.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liyayu on 2018/3/13.
 */
public class CoordinatorLayoutActivity extends AppCompatActivity {
    List<String> mData ;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_top)
    ImageView imageTop;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecyclerView.Adapter madapter;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);
        initData(1);
        initView();
    }

    private void initView() {
        madapter = new RecyclerView.Adapter<MyRecycleHolder>() {
            @Override
            public MyRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.item_rec_layout, parent, false);
                return new MyRecycleHolder(view);
            }

            @Override
            public void onBindViewHolder(MyRecycleHolder holder, final int position) {
                holder.tvText.setText(mData.get(position));
                if (position == 0){
                    holder.tvText.setText("点击进入ViewPager和Fragment的+TabLayout示例");
                }
                holder.tvText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 0){
                            intent = new Intent(CoordinatorLayoutActivity.this,TabViewPageActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        };
        toolbar.setTitleTextColor(getResources().getColor(R.color.light_icon));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.light_icon));
        toolbar.setTitle("大愣子头部");
        toolbar.setSubtitle("2愣子头部");
        setSupportActionBar(toolbar);
        //tablayout监听应该放在addtab之前否则不会默认显示第一页 ，
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                initData(tab.getPosition() + 1);
                madapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //第一页要默认选中部分
        tabLayout.addTab(tabLayout.newTab().setText("TAB 1"), true);
        for (int i = 1; i < 20; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("TAB" + (i + 1)));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(madapter);
    }

    private void initData(int i) {
        mData = new ArrayList<>();
        for (int j = 0; j < 50; j++) {
            mData.add("page" + i + "   item" + j);
        }
    }

    @OnClick({R.id.toolbar, R.id.image_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                ToastUtil.showToast(this,"1111111111toolbar");
                break;
            case R.id.image_top:
                ToastUtil.showToast(getApplicationContext(),"1111111111image_top");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageTop.setImageDrawable(getDrawable(R.drawable.mvc));
                }else {
                    imageTop.setImageDrawable(getResources().getDrawable(R.drawable.mvc));
                }
                break;
        }
    }

    class MyRecycleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_text)
        TextView tvText;

        private MyRecycleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
