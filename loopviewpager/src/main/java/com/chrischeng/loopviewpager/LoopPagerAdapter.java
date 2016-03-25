package com.chrischeng.loopviewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class LoopPagerAdapter extends PagerAdapter {

    private PagerAdapter mAdapter;

    public LoopPagerAdapter(PagerAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getCount() {
        if (mAdapter == null)
            return 0;
        else if (mAdapter.getCount() > 1)
            return mAdapter.getCount() + 2;
        else
            return mAdapter.getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return mAdapter.isViewFromObject(view, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (position == 0)
            return mAdapter.instantiateItem(container, mAdapter.getCount() - 1);
        else if (position == mAdapter.getCount() + 1)
            return mAdapter.instantiateItem(container, 0);
        else
            return mAdapter.instantiateItem(container, position - 1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mAdapter.destroyItem(container, position, object);
    }
}
