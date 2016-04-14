package com.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chrischeng.loopviewpager.LoopViewPager;

public class MainActivity extends AppCompatActivity {

    private int[] ads = new int[]{R.drawable.ad1, R.drawable.ad2, R.drawable.ad3};

    private LoopViewPager mLoopViewPager;
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoopViewPager = (LoopViewPager) findViewById(R.id.lvp);

        isFirst = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isFirst) {
            isFirst = false;
            mLoopViewPager.setAdapter(new AdPagerAdapter(this, ads));
        }

        mLoopViewPager.startLoopScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mLoopViewPager.pauseLoopScroll();
    }
}
