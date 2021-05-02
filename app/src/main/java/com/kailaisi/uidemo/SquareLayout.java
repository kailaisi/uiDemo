package com.kailaisi.uidemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * 自定义布局类，将控件按照正方形来进行布局。
 */
public class SquareLayout extends ViewGroup {
    public static final int Ori_Hori = 0, Ori_Veri = 1;
    public static final int DefaultMaxRow = Integer.MAX_VALUE;
    public static final int DefaultMaxColumn = Integer.MAX_VALUE;

    private int mMaxRow=DefaultMaxRow;//最大行数
    private int mMaxColumn=DefaultMaxColumn;//最大列数

    private int mOrientation=Ori_Hori;//排列方向

    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth=0;
        int parentHeight=0;
        if (getChildCount()>0) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility()!=View.GONE){
                    //测量子元素并考虑其外边距
                    measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,0);
                    int childMeasureSize=Math.max(child.getMeasuredWidth(),child.getMeasuredHeight());
                    //重新设置其测量方案
                    int childMeasure=MeasureSpec.makeMeasureSpec(childMeasureSize,MeasureSpec.EXACTLY);
                    child.measure(childMeasure,childMeasure);

                    MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
                    int width = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                    int height = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                    //横向排列
                    if (mOrientation==Ori_Hori){
                        parentWidth+=width;
                        parentHeight=Math.max(parentHeight,height);
                    }else{
                        parentHeight+=height;
                        parentWidth=Math.max(parentWidth,width);
                    }
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }


}
