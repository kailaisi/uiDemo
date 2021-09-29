package com.kailaisi.uidemo.cai;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.kailaisi.uidemo.R;

/**
 * 描述：五星打分
 * 作者：wujinxiang
 * 创建时间：2021/7/30 1:21 PM
 */
public class RatingBar extends View {
    private int mGradeNumber = 5;
    private Bitmap mFocusBitmap;
    private Bitmap mNormalBitmap;
    private int ratingPadding = 10;

    private int focusNum = 0;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        int normal = array.getResourceId(R.styleable.RatingBar_starNormal, 0);
        int select = array.getResourceId(R.styleable.RatingBar_starSelect, 0);
        ratingPadding = array.getDimensionPixelOffset(R.styleable.RatingBar_rating_padding, ratingPadding);
        if (normal == 0 || select == 0) {
            throw new IllegalArgumentException("请设置图片");
        }
        mNormalBitmap = BitmapFactory.decodeResource(getResources(), normal);
        mFocusBitmap = BitmapFactory.decodeResource(getResources(), select);
        mGradeNumber = array.getInt(R.styleable.RatingBar_gradeNumber, mGradeNumber);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //高度是一张图片的高度
        int height = mNormalBitmap.getHeight();
        int width = mGradeNumber * mNormalBitmap.getWidth();
        width += (mGradeNumber - 1) * ratingPadding;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < focusNum; i++) {
            int left = i * mNormalBitmap.getWidth() + i * ratingPadding;
            canvas.drawBitmap(mFocusBitmap, left, 0, null);
        }
        for (int i = focusNum; i < mGradeNumber; i++) {
            int left = i * mNormalBitmap.getWidth() + i * ratingPadding;
            canvas.drawBitmap(mNormalBitmap, left, 0, null);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //判断手指的位置

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                int percent = (int) (mGradeNumber * x / getWidth());
                if (percent!=focusNum){
                    System.out.println("move x:" + x);
                    focusNum=percent;
                    invalidate();
                }
        }

        return true;
    }
}