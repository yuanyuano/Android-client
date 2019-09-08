package com.example.news.NineGrid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class UnScrollListView  extends ListView {
    public UnScrollListView(Context context) {
        super(context);
    }
    public UnScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public UnScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);//这里返回的是刚写好的expandSpec
    }
}