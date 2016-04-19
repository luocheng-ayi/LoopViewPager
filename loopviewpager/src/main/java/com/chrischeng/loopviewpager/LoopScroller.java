package com.chrischeng.loopviewpager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class LoopScroller extends Scroller {

    private Context mContext;
    private int mDuration;

    public LoopScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
        mContext = context;
        init();
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    private void init() {
        mDuration = mContext.getResources().getInteger(R.integer.default_duration);
    }
}
