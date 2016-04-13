package com.sample;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AdPagerAdapter extends PagerAdapter {

    private int[] ads;
    private LayoutInflater inflater;

    public AdPagerAdapter(Context context, int[] ads) {
        this.ads = ads;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ads.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = inflater.inflate(R.layout.item_ad, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.iv);
        imageView.setImageResource(ads[position]);
        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
