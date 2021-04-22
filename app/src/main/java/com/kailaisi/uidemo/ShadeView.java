package com.kailaisi.uidemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * shadeView
 */
public class ShadeView extends View {
    private  int left,right,top,bottom;
    private Paint mPaint;
    public static final int RECT_SIZE=400;

    public ShadeView(Context context) {
        super(context);
    }

    public ShadeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int[] size = ScreenUtil.getScreenSize(context);
        int centerX=size[0]/2;
        int centerY=size[1]/2;
        left=centerX-RECT_SIZE;
        right=centerX+RECT_SIZE;
        top=centerY-RECT_SIZE;
        bottom=centerY+RECT_SIZE;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
        mPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(left,top,right,bottom,mPaint);
    }
}
