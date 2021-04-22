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

import androidx.annotation.Nullable;

/**
 * 一个倒影效果的美女
 */
public class ReflectView extends View {
    private Paint mPaint;
    private Bitmap mSrcBitmap, mRefBitmap;
    private PorterDuffXfermode mode;

    private int x, y;

    public ReflectView(Context context) {
        super(context);
    }

    public ReflectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReflectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //描边paint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(0xFF000000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        //生成Bitmap
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
        Matrix matrix = new Matrix();
        //设置倒影效果，y轴取反，x轴不变
        matrix.setScale(1F,-1F);
        //创建原图像的倒影图像
        mRefBitmap =Bitmap.createBitmap(mSrcBitmap,0,0,mSrcBitmap.getWidth(),mSrcBitmap.getHeight(),matrix,true);
        int[] screenSize = ScreenUtil.getScreenSize(context);
        //图像中心
        x=0;//screenSize[0]/2-mSrcBitmap.getWidth()/2;
        y=0;//screenSize[1]/2-mSrcBitmap.getHeight()/2;
        mPaint=new Paint();
        //设置从中间位置开始向下，进行颜色渐变，从灰到透明
        LinearGradient shader = new LinearGradient(x, y + mSrcBitmap.getHeight(), x, y + mSrcBitmap.getHeight() + mSrcBitmap.getHeight() / 4, 0xAA000000,Color.TRANSPARENT, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        //设置模式为源在内部。这时候是黑色透明，白色不透明
        mode=new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //黑色面板
   //     canvas.drawColor(Color.BLACK);
        //从中心位置开始画原图
       // canvas.drawBitmap(mSrcBitmap, x, y, null);
        //离层操作，这里会将后面的操作保存一个新的图层中，
        int sc = canvas.saveLayer(x, y + mSrcBitmap.getHeight(), x + mRefBitmap.getWidth(), x + mSrcBitmap.getHeight() * 2, null, Canvas.ALL_SAVE_FLAG);
        //画倒影，倒影的位置，倒影的起始位置，是从原图的底部开始的
        canvas.drawBitmap(mRefBitmap, x, y + mSrcBitmap.getHeight(), null);
        mPaint.setXfermode(mode);
        //画渐变的方形，位置和倒影是一样的。但是使用了mode。所以底部是白色的，然后sr效果就是没有了。。。
        canvas.drawRect(x, y + mSrcBitmap.getHeight(), x + mRefBitmap.getWidth(), y + mSrcBitmap.getHeight() * 2, mPaint);
        mPaint.setXfermode(null);
        //恢复
        canvas.restoreToCount(sc);
    }
}
