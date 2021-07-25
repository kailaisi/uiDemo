package com.kailaisi.uidemo.aige;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.kailaisi.uidemo.ScreenUtil;

/**
 * 自定义Layout，来进行布局的测量和布局。极简单版本，主要用来联系onLayout和onMeasure的相关定义
 */
public class CustomLayout extends ViewGroup {

    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int[] size = ScreenUtil.getScreenSize(context);
        int centerX = size[0] / 2;
        int centerY = size[1] / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = 0;
        int parentHeight = 0;
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                CustomLayoutParams params = (CustomLayoutParams) child.getLayoutParams();
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                parentWidth += child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                parentHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            }
            parentHeight = Math.max(parentHeight, getSuggestedMinimumHeight());
            parentWidth = Math.max(parentWidth, getSuggestedMinimumWidth());
        }
        setMeasuredDimension(resolveSize(parentWidth, widthMeasureSpec), resolveSize(parentHeight, widthMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        if (getChildCount() > 0) {
            int height = 0;
            height += top;
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                CustomLayoutParams layoutParams = (CustomLayoutParams) view.getLayoutParams();
                //要设置对应的margin距离
                view.layout(left + layoutParams.leftMargin, height + layoutParams.topMargin, view.getMeasuredWidth() + left + layoutParams.leftMargin, layoutParams.topMargin + view.getMeasuredHeight() + height);
                //要计算子控件对应的margin数据
                height += view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            }
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CustomLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new CustomLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CustomLayoutParams;
    }

    public static class CustomLayoutParams extends MarginLayoutParams {

        public CustomLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public CustomLayoutParams(int width, int height) {
            super(width, height);
        }

        public CustomLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public CustomLayoutParams(LayoutParams source) {
            super(source);
        }
    }
}
