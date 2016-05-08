package com.sample;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.chrischeng.bezierpageindicator.CirclePageIndicator;
import com.chrischeng.loopviewpager.LoopViewPager;
import com.chrischeng.loopviewpager.OnPageItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        OnPageItemClickListener {

    private List<Integer> mAds;

    private LoopViewPager mLoopViewPager;
    private CirclePageIndicator mIndicator;
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoopViewPager = (LoopViewPager) findViewById(R.id.lvp);
        mIndicator = (CirclePageIndicator) findViewById(R.id.pi);

        TextView notifyTextView = (TextView) findViewById(R.id.tv_notify);
        assert notifyTextView != null;
        notifyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAds.add(R.drawable.ad1);
                PagerAdapter adapter = mLoopViewPager.getAdapter();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                    mLoopViewPager.setAdapter(adapter);
                    mIndicator.setCount(adapter.getCount());
                }
            }
        });

        isFirst = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isFirst) {
            mAds = new ArrayList<>();
            mAds.add(R.drawable.ad1);
            mAds.add(R.drawable.ad2);
            mAds.add(R.drawable.ad3);

            isFirst = false;
            mLoopViewPager.setScrollDuration(350);
            PagerAdapter adapter = new AdPagerAdapter(this, mAds);
            mLoopViewPager.setAdapter(adapter);
            mLoopViewPager.addOnPageChangeListener(this);
            mLoopViewPager.setOnPageItemClickListener(this);
            mIndicator.setCount(adapter.getCount());
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
