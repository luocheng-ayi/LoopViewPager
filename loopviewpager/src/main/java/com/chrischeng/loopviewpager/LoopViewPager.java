package com.chrischeng.loopviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;

import java.lang.reflect.Field;

public class LoopViewPager extends ViewPager {

    private static final int MSG_WHAT = 0;

    private PagerAdapter mSourceAdapter;
    private PagerAdapter mLoopAdapter;
    private LoopPageChangeListener mChangeListener;
    private OnPageClickListener mClickListener;

    private LoopScroller mScroller;
    private int mInterval;
    private int mTouchSlop;
    private boolean mIsLoopScroll;
    private Handler mHandler;
    private float mStartMotionX;
    private float mStartMotionY;
    private boolean mHasMoved;

    public LoopViewPager(Context context) {
        this(context, null);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mSourceAdapter = adapter;
        mLoopAdapter = (mSourceAdapter == null) ? null : new LoopPagerAdapter(mSourceAdapter);
        super.setAdapter(mLoopAdapter);

        if (mSourceAdapter != null && mSourceAdapter.getCount() > 0)
            setCurrentItem(0);
    }

    @Override
    public PagerAdapter getAdapter() {
        return mSourceAdapter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_DOWN:
                onActionDown(ev.getX(), ev.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                onActionMove(ev.getX(), ev.getY());
                break;
            case MotionEvent.ACTION_UP:
                onActionUp();
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        mChangeListener.setOnPageChangeListener(listener);
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        addOnPageChangeListener(listener);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item + 1);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item + 1, smoothScroll);
    }

    @Override
    public int getCurrentItem() {
        int loopCurrentItem = super.getCurrentItem();

        if (mSourceAdapter != null && mSourceAdapter.getCount() > 1) {
            if (loopCurrentItem == 0)
                loopCurrentItem = mSourceAdapter.getCount() - 1;
            else if (loopCurrentItem == mLoopAdapter.getCount() - 1)
                loopCurrentItem = 0;
            else
                loopCurrentItem -= 1;
        }

        return loopCurrentItem;
    }

    public void setOnPageClickListener(OnPageClickListener listener) {
        mClickListener = listener;
    }

    public void startLoopScroll() {
        if (getSourceCount() < 1 || mHandler.hasMessages(MSG_WHAT))
            return;

        mIsLoopScroll = true;
        mHandler.removeMessages(MSG_WHAT);
        mHandler.sendEmptyMessageDelayed(MSG_WHAT, mInterval);
    }

    public void pauseLoopScroll() {
        mIsLoopScroll = false;
        mHandler.removeMessages(MSG_WHAT);
    }

    public int getLoopCurrentItem() {
        return super.getCurrentItem();
    }

    public void setScrollDuration(int duration) {
        mScroller.setDuration(duration);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void init(AttributeSet attrs) {
        initAttrs(attrs);
        initListener();
    }

    private void initScroller(int duration) {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);
            mScroller = new LoopScroller(getContext(), new DecelerateInterpolator());
            mScroller.setDuration(duration);
            scrollerField.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LoopViewPager);
        int duration = a.getInteger(R.styleable.LoopViewPager_duration, getResources().getInteger(R.integer.default_duration));
        mInterval = a.getInteger(R.styleable.LoopViewPager_interval, getResources().getInteger(R.integer.default_interval));
        a.recycle();

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        initScroller(duration);
    }

    private void initListener() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                setCurrentItem(getCurrentItem() + 1);
                sendEmptyMessageDelayed(MSG_WHAT, mInterval);
            }
        };

        mChangeListener = new LoopPageChangeListener();
        super.addOnPageChangeListener(mChangeListener);
    }

    private void onActionDown(float x, float y) {
        mHandler.removeMessages(MSG_WHAT);

        mHasMoved = false;
        mStartMotionX = x;
        mStartMotionY = y;
    }

    private void onActionMove(float x, float y) {
        if (Math.abs(x - mStartMotionX) > mTouchSlop || Math.abs(y - mStartMotionY) > mTouchSlop)
            mHasMoved = true;
    }

    private void onActionUp() {
        if (!mHasMoved && mClickListener != null)
            mClickListener.onPageClick(getCurrentItem());

        if (mIsLoopScroll)
            startLoopScroll();
    }

    private int getSourceCount() {
        return mSourceAdapter != null ? mSourceAdapter.getCount() : 0;
    }

    private int getLoopCount() {
        return mLoopAdapter != null ? mLoopAdapter.getCount() : 0;
    }

    private class LoopPageChangeListener implements OnPageChangeListener {

        private OnPageChangeListener mChangeListener;

        public void setOnPageChangeListener(OnPageChangeListener listener) {
            mChangeListener = listener;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mChangeListener != null && position > 0 && position < getSourceCount())
                mChangeListener.onPageScrolled(position - 1, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            if (mChangeListener != null) {
                if (position == 0)
                    position = getSourceCount() - 1;
                else if (position == getLoopCount() - 1)
                    position = 0;
                else
                    position -= 1;

                mChangeListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == SCROLL_STATE_IDLE && getSourceCount() > 1) {
                if (getLoopCurrentItem() == 0)
                    setCurrentItem(getSourceCount() - 1, false);
                else if (getLoopCurrentItem() == getLoopCount() - 1)
                    setCurrentItem(0, false);
            }

            if (mChangeListener != null)
                mChangeListener.onPageScrollStateChanged(state);
        }
    }

}
