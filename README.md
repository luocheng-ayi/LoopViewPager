# LoopViewPager
An autoscroll viewpager on android.
## Usage
**Add the dependencies to your gradle file:**
```javascript
dependencies {
    compile 'com.chrischeng:loopviewpager:1.0.1'
}
```
**XML:**
```xml
<com.chrischeng.loopviewpager.LoopViewPager
        android:id="@+id/lvp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```
**Java:**
```java
@Override
protected void onResume() {
    super.onResume();
    mLoopViewPager.startLoopScroll();
    ...
}

@Override
protected void onPause() {
    super.onPause();
    mLoopViewPager.pauseLoopScroll();
    ...
}

mLoopViewPager.setOnPageItemClickListener(new OnPageItemClickListener() {
    @Override
    public void onPageItemClick(int pos) {
              ...     
    }
});

mLoopViewPager.setScrollDuration(350); //default 300
mLoopViewPager.setScrollInterval(3_000); //default 3_000
```
**Notice:**
To refresh data of adapter:
```java
PagerAdapter adapter = mLoopViewPager.getAdapter();
    if (adapter != null) {
        adapter.notifyDataSetChanged();
        mLoopViewPager.setAdapter(adapter);
    }
```
