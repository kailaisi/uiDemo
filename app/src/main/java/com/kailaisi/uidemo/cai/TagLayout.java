package com.kailaisi.uidemo.cai;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.LinkAddress;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.kailaisi.uidemo.R;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {
    private static final String TAG = "TagLayout";
    private List<List<View>> list = new ArrayList<>();

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0, left, bottom = 0, right = 0;
        for (List<View> views : list) {
            left = getPaddingLeft();
            bottom += views.get(0).getMeasuredHeight();
            for (View view : views) {
                right += view.getMeasuredWidth();
                view.layout(left, top, right, bottom);
                left += view.getMeasuredWidth();
            }
            top += views.get(0).getMeasuredHeight();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //需要设置为宽高相同
        int count = getChildCount();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = getPaddingTop() + getPaddingBottom();
        int lineWidth = getPaddingLeft();
        list.clear();
        ArrayList<View> views = new ArrayList<>();
        int lineHeight = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //这个就可以获取子View的宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            if (lineWidth + child.getMeasuredWidth() > width) {
                //换行,高度累加，宽度归零
                height += lineHeight;
                list.add(views);
                //数据进行归零
                lineHeight = 0;
                lineWidth = 0;
                views = new ArrayList<>();
            }
            lineHeight = Math.max(lineHeight, child.getMeasuredHeight());
            views.add(child);
            lineWidth += child.getMeasuredWidth();
        }
        height += lineHeight;
        Log.e(TAG, "onMeasure: " + "height：" + height + "，width：" + width);
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}