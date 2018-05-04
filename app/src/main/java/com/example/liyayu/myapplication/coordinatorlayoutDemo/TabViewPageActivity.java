package com.example.liyayu.myapplication.coordinatorlayoutDemo;

import android.app.SearchManager;
import android.app.Service;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.liyayu.myapplication.BaseKotlinActivity;
import com.example.liyayu.myapplication.R;
import com.example.liyayu.myapplication.fragment.ContextFragment;
import com.example.liyayu.myapplication.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liyayu on 2018/3/16.
 */

public class TabViewPageActivity extends BaseKotlinActivity {
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;

    private List<String> tabIndicators;
    private List<Fragment> fragmentList;
    private ContextPagerAdapter mAdapter;

    public TabViewPageActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_viewpage);
        ButterKnife.bind(this);
        if (getToolbar()!=null){
            getToolbar().setTitle("test kt in java");
        }
        initViewPager();
        initTav();
    }

    private void initTav() {
        tlTab.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tlTab.setTabTextColors();
        ViewCompat.setElevation(tlTab, 10);
        tlTab.setupWithViewPager(vpContent);
    }

    private void initViewPager() {
        tabIndicators = new ArrayList<>();
        fragmentList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            tabIndicators.add("tab" + i);
        }
        for (String s : tabIndicators) {
            fragmentList.add(new ContextFragment(s));
        }
        mAdapter = new ContextPagerAdapter(getSupportFragmentManager());
        vpContent.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab_tool, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Service.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchResultActivity.class)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                tabIndicators.add("tab" + tabIndicators.size());
                fragmentList.add(new ContextFragment(tabIndicators.get(tabIndicators.size() - 1)));
                mAdapter.notifyDataSetChanged();
                tlTab.setupWithViewPager(vpContent);
                return true;
            case R.id.menu_mode_scr:
                tlTab.setTabMode(TabLayout.MODE_SCROLLABLE);
                return true;
            case R.id.menu_fix:
                tlTab.setTabMode(TabLayout.MODE_FIXED);
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.tb_toolbar, R.id.tl_tab, R.id.vp_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tb_toolbar:
                ToastUtil.showToast(this, "tb_toolbar");
                pushFragment(R.id.frame_layout, new ContextFragment("toolbar_test"));
                break;
            case R.id.tl_tab:
                ToastUtil.showToast(this, "tl_tab");
                pushFragment(R.id.vp_content, new ContextFragment("tl_tab_test"));
                break;
            case R.id.vp_content:
                ToastUtil.showToast(this, "vp_content");
                pushFragment(R.id.vp_content, new ContextFragment("vp_content_test"));
                break;
        }
    }

    @OnClick(R.id.frame_layout)
    public void onViewClicked() {
        ToastUtil.showToast(this, "frame_layout");
        pushFragment(R.id.frame_layout, new ContextFragment("frame_layout"));
    }

    class ContextPagerAdapter extends FragmentPagerAdapter {

        public ContextPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }

}
