package com.sample;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class AdPagerAdapter extends PagerAdapter {

    private List<Integer> mAds;
    private LayoutInflater mInflater;

    public AdPagerAdapter(Context context, List<Integer> ads) {
        mAds = ads;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mAds.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = mInflater.inflate(R.layout.item_ad, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.iv);
        imageView.setImageResource(mAds.get(position));
        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
