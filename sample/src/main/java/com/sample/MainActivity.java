package com.sample;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chrischeng.bezierpageindicator.CirclePageIndicator;
import com.chrischeng.loopviewpager.LoopViewPager;
import com.chrischeng.loopviewpager.OnPageItemClickListener;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        OnPageItemClickListener {

    private int[] ads = new int[]{R.drawable.ad1, R.drawable.ad2, R.drawable.ad3};

    private LoopViewPager mLoopViewPager;
    private CirclePageIndicator mIndicator;
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoopViewPager = (LoopViewPager) findViewById(R.id.lvp);
        mIndicator = (CirclePageIndicator) findViewById(R.id.pi);

        isFirst = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isFirst) {
            isFirst = false;
            mLoopViewPager.setScrollDuration(350);
            AdPagerAdapter adPagerAdapter = new AdPagerAdapter(this, ads);
            mLoopViewPager.setAdapter(adPagerAdapter);
            mLoopViewPager.addOnPageChangeListener(this);
            mLoopViewPager.setOnPageItemClickListener(this);
            mIndicator.setCount(adPagerAdapter.getCount());
            mIndicator.setSlideable(false);
        }

        mLoopViewPager.startLoopScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mLoopViewPager.pauseLoopScroll();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        mIndicator.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mIndicator.onPageScrollStateChanged(state);
    }

    @Override
    public void onPageItemClick(int pos) {
        ToastUtil.showToast(String.valueOf(pos));
    }
}
