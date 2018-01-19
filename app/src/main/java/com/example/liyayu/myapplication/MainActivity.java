package com.example.liyayu.myapplication;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        setupWindowAnimations();
        getList();
    }

    private static ArrayList<String> list = new ArrayList<>();
    private void getList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i =0;i<10_0000;i++){
                    list.add(String.valueOf(i));
                }
            }
        }).start();
    }


    private void setupWindowAnimations() {
        Explode explode = null;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        explode = new Explode();
        explode.setDuration(2000);
        explode.setInterpolator(new FastOutSlowInInterpolator());
        getWindow().setExitTransition(explode);


        Explode explode2 = new Explode();
        explode2.setDuration(4000);
        explode2.setInterpolator(new FastOutSlowInInterpolator());
        getWindow().setReenterTransition(explode2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static class MyHandler extends Handler {

            private WeakReference<PlaceholderFragment> mWeakActivity;

            public MyHandler(PlaceholderFragment fragment) {
                mWeakActivity = new WeakReference<>(fragment);
            }

            @Override
            public void handleMessage(Message msg) {

                PlaceholderFragment activity = mWeakActivity.get();
                if (activity != null) {
                    // 更新进度条
                    activity.progressBar.setProgress(msg.arg1);
                    // 更新数值显示
                    activity.proText.setText(msg.arg1 + "%");

                    activity.mCircleSeekBar.setProgress(msg.arg1);
                }

            }
        }
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private TextView proText;
        private ProgressBar progressBar ,pro_bar_round;
        private MyHandler mHandler;
        private MCircleSeekBar mCircleSeekBar;
        private CircleProgressBar circleProgressbar;

        public PlaceholderFragment() {
        }

        @Override
        public void onStart() {
            super.onStart();

            new Thread(new Runnable() {
                @SuppressWarnings("InfiniteLoopStatement")
                @Override
                public void run() {
                    try {
                        while (true) {
                            for (int i = 0; i <= 100; i++) {
                                Thread.sleep(50);
                                Message message = mHandler.obtainMessage();
                                message.arg1 = i;
                                mHandler.sendMessage(message);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public void onPause() {
            super.onPause();

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            mHandler = new MyHandler(this);
            proText = (TextView) rootView.findViewById(R.id.pro_text);
            progressBar  = (ProgressBar) rootView.findViewById(R.id.prog_bar);
            pro_bar_round  = (ProgressBar) rootView.findViewById(R.id.pro_bar_round);
            circleProgressbar  = (CircleProgressBar) rootView.findViewById(R.id.circleProgressbar);
            mCircleSeekBar = rootView.findViewById(R.id.m_circleSeekBar_set_perencet);
        /*
         * 下面三行代码请大家自己尝试值不同时的效果，第一行是控制圆环效果和圆效果的切换
         */
            mCircleSeekBar.setRingMode(true);
            mCircleSeekBar.setCanMove(false);
            mCircleSeekBar.setShowProgressBar(true);
            mCircleSeekBar.setBarWidth(8);



            progressBar.setMax(100);
            progressBar.setProgress(20);
            progressBar.setIndeterminate(true);
            pro_bar_round.setIndeterminate(true);
            proText.setText("20%");
            ImageView img = (ImageView) rootView.findViewById(R.id.img);
            img.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
//                    TestOneActivity.lanch(getActivity(),list);
                    Intent intent =  new Intent(getActivity(),TestOneActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }
            });

            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }



        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
