package com.kailaisi.uidemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class J extends View {
    private Bitmap mSrcBitmap, mRefBitmap;// 位图
    private Paint mPaint;// 画笔
    private PorterDuffXfermode mXfermode;// 混合模式

    private int x, y;// 位图起点坐标

    public J(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化资源
        initRes(context);
    }

    /*
     * 初始化资源
     */
    private void initRes(Context context) {
        // 获取源图
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);

        // 实例化一个矩阵对象
        Matrix matrix = new Matrix();
        matrix.setScale(1F, -1F);

        // 生成倒影图
        mRefBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight(), matrix, true);

        int screenH = 0;

        int screenW=0;
        x = screenW / 2 - mSrcBitmap.getWidth() / 2;
        y = screenH / 2 - mSrcBitmap.getHeight() / 2;

        // ………………………………
        mPaint = new Paint();
        mPaint.setShader(new LinearGradient(x, y + mSrcBitmap.getHeight(), x, y + mSrcBitmap.getHeight() + mSrcBitmap.getHeight() / 4, 0xAA000000, Color.TRANSPARENT, Shader.TileMode.CLAMP));

        // ………………………………
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //黑色面板
        canvas.drawColor(Color.BLACK);
        //从中心位置开始画原图
        canvas.drawBitmap(mSrcBitmap, x, y, null);
        //离层操作，这里会将后面的操作保存一个新的图层中，
        int sc = canvas.saveLayer(x, y + mSrcBitmap.getHeight(), x + mRefBitmap.getWidth(), x + mSrcBitmap.getHeight() * 2, null, Canvas.ALL_SAVE_FLAG);
        //画倒影，倒影的位置，倒影的起始位置，是从原图的底部开始的
        canvas.drawBitmap(mRefBitmap, x, y + mSrcBitmap.getHeight(), null);
        mPaint.setXfermode(mXfermode);
        //画渐变的方形，位置和倒影是一样的。但是使用了mode。所以底部是白色的，然后sr效果就是没有了。。。
        canvas.drawRect(x, y + mSrcBitmap.getHeight(), x + mRefBitmap.getWidth(), y + mSrcBitmap.getHeight() * 2, mPaint);
        mPaint.setXfermode(null);
        //恢复
        canvas.restoreToCount(sc);
    }
}