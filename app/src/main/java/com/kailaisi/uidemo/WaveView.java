package com.kailaisi.uidemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

/**
 * 基于Mesh的图片斜体
 */
public class WaveView extends View {
    private Paint mPaint;
    private Path mPath;
    private int vWidth,vHeight;
    private float ctrX,ctrY;
    private float waveY;//整个Wavee顶部两端点的Y坐标，该坐标和控制点的Y坐标增减幅度一致
    private boolean isInc;
    private Random random;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(0xFFA2D6AE);
        mPath = new Path();
        random=new Random(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //从左边瓶子外面开始
        mPath.moveTo(-vWidth/4F,waveY);
        //画到右边瓶子外，只需要ctrX，xtrY变化，就能出现浪的效果
        mPath.quadTo(ctrX,ctrY,vWidth+vWidth/4F,waveY);
        //封闭path
        mPath.lineTo(vWidth+vWidth/4F,vHeight);

        mPath.lineTo(-vWidth/4F,vHeight);
        mPath.close();
        /**
         * 画路径
         */
        canvas.drawPath(mPath,mPaint);

        //变化控制点数据，
        if (ctrX>vWidth+vWidth/4F){
            isInc=false;
        }else if(ctrX<-vWidth/4F){
            isInc=true;
        }

        ctrX=isInc?ctrX+=random.nextInt(20):ctrX-random.nextInt(20);
        if (ctrY<=vHeight){
            ctrY+=2;
            waveY+=2;
        }
        mPath.reset();
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        vWidth = w;
        vHeight = h;
        waveY = vHeight / 8F;
        ctrY = -vHeight / 16F;
    }
}
