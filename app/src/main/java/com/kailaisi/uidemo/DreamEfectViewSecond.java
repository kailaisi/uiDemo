package com.kailaisi.uidemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 通过使用Shade来将图片进行一个变换处理
 * 1。先通过ColorMitra来进行绿色处理
 * 2。通过shader将图片的四角变暗（通过使用RadialGradient+Matrix来进行混合处理）
 */
public class DreamEfectViewSecond extends View {
    private  Bitmap darkCornerBitmap;
    private Paint mPaint,mShaderPaint;
    private PorterDuffXfermode mode;

    private Bitmap mBitmmap;
    private int x, y;

    public DreamEfectViewSecond(Context context) {
        super(context);
    }

    public DreamEfectViewSecond(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DreamEfectViewSecond(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        //实例化对应的Shader图形的画笔
        mShaderPaint=new Paint();
        //根据原图大小生成一个暗角Bitmap
        darkCornerBitmap = Bitmap.createBitmap(mBitmmap.getWidth(), mBitmmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(darkCornerBitmap);
        float radiu=canvas.getHeight()*2/3F;
        //设置径向渐变，中心是图片的中心，
        //颜色中心为透明，两边为黑色
        RadialGradient shader = new RadialGradient(mBitmmap.getWidth() / 2, mBitmmap.getHeight() / 2, radiu,
                Color.TRANSPARENT, Color.BLACK, Shader.TileMode.CLAMP);
        //对原来的圆形的渐变进行一个变形处理：将高度变为2倍，宽度不变
        Matrix matrix = new Matrix();
        //原来的圆角半径是2/3h，现在将x轴进行缩小，变为椭圆，椭圆的半径为 w的2/3，y轴不变，为h的2/3。
        matrix.setScale((canvas.getWidth()/(2*radiu)),1F);
        // 设置矩阵的预平移  即先进行平移操作。然后scale再执行。这里y一直没有动，而中心点，从 w/2=>  w/2+radiu*2-w/2=radiu/2=> radiu/2*w/(2*radiu)=w/2。
        // 所以最终横轴中心点未变，但是对应的缩小了
        matrix.preTranslate(((radiu * 2F) - canvas.getWidth()) / 2F, 0);
        shader.setLocalMatrix(matrix);
        mShaderPaint.setShader(shader);
        //按照椭圆效果画矩形
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),mShaderPaint);
    }


    @Override
    protected void onDraw(Canvas canvas) {
       canvas.drawColor(Color.BLACK);
        int layer = canvas.saveLayer(x, y, x + mBitmmap.getWidth(), y + mBitmmap.getHeight(),null,Canvas.ALL_SAVE_FLAG);
        //绘制混合的底色
        canvas.drawColor(0xcc1c093e);
        mPaint.setXfermode(mode);
        //绘制位图
        canvas.drawBitmap(mBitmmap,x,y,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layer);
        //绘制一个矩形。中间透明，两边黑色，突出图片
        //canvas.drawRect(x,y,x+mBitmmap.getWidth(),mBitmmap.getHeight(),mShaderPaint);
        canvas.drawBitmap(darkCornerBitmap,x,y,null);
    }
}
