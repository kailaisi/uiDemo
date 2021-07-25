package com.kailaisi.uidemo.aige;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.kailaisi.uidemo.R;
import com.kailaisi.uidemo.ScreenUtil;

/**
 * 通过使用Shade来将图片进行一个变换处理
 * 1。先通过ColorMitra来进行绿色处理
 * 2。
 */
public class DreamEfectView extends View {
    private Paint mPaint,mShaderPaint;
    private PorterDuffXfermode mode;

    private Bitmap mBitmmap;
    private int x, y;

    public DreamEfectView(Context context) {
        super(context);
    }

    public DreamEfectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DreamEfectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //生成Bitmap
        mBitmmap = BitmapFactory.decodeResource(getResources(), R.mipmap.f);
        int[] screenSize = ScreenUtil.getScreenSize(context);
        //设置模式为源在内部
        mode=new PorterDuffXfermode(PorterDuff.Mode.SCREEN);
        //图像中心
        x=0;//screenSize[0]/2-mSrcBitmap.getWidth()/2;
        y=0;//screenSize[1]/2-mSrcBitmap.getHeight()/2;
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        // 去饱和、提亮、色相矫正
        mPaint.setColorFilter(new ColorMatrixColorFilter(new float[] { 0.8587F, 0.2940F, -0.0927F, 0, 6.79F, 0.0821F, 0.9145F, 0.0634F, 0, 6.79F, 0.2019F, 0.1097F, 0.7483F, 0, 6.79F, 0, 0, 0, 1, 0 }));

        mShaderPaint=new Paint();
        //设置径向渐变，中心是图片的中心，
        //颜色中心为透明，两边为黑色
        mShaderPaint.setShader(new RadialGradient(mBitmmap.getWidth()/2,mBitmmap.getHeight()/2,mBitmmap.getHeight(),
                Color.TRANSPARENT,Color.BLACK, Shader.TileMode.CLAMP));
    }


    @Override
    protected void onDraw(Canvas canvas) {
       canvas.drawColor(Color.BLACK);
        int layer = canvas.saveLayer(x, y, x + mBitmmap.getWidth(), y + mBitmmap.getHeight(),null,Canvas.ALL_SAVE_FLAG);
        canvas.drawColor(0xcc1c093e);
        mPaint.setXfermode(mode);
        canvas.drawBitmap(mBitmmap,x,y,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layer);
        //绘制一个矩形。中间透明，两边黑色，突出图片
        canvas.drawRect(x,y,x+mBitmmap.getWidth(),mBitmmap.getHeight(),mShaderPaint);
    }
}
