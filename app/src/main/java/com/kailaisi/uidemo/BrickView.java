package com.kailaisi.uidemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 狙击枪，指哪儿打哪儿
 */
public class BrickView extends View {
    private Paint mStrokePaint;
    private int left, right, top, bottom;
    private Paint mFillPaint;
    public static final int RECT_SIZE = 400;
    private float centerX;
    private float centerY;

    public BrickView(Context context) {
        super(context);
    }

    public BrickView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BrickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //描边paint
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mStrokePaint.setColor(0xFF000000);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(5);
        //生成Bitmap
        mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mFillPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        canvas.drawCircle(centerX,centerY,100,mStrokePaint);
        canvas.drawCircle(centerX,centerY,100,mFillPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            centerX = event.getX();
            centerY = event.getY();
            postInvalidate();
        }
        return true;

    }
}
