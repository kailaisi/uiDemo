package com.kailaisi.uidemo.cai;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kailaisi.uidemo.R;

/**
 * 描述：
 * <p/>作者：wu
 * <br/>创建时间：2021-07-29:13:43
 */
@SuppressLint("AppCompatCustomView")
public class ColorTrackTextView extends TextView {
    //两个画笔
    private Paint mOriginPaint;
    private Paint mChangePaint;

    //进度值
    private float mProcess = 0.5f;

    private boolean revert=false;

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }


    public void setProcess(float mProcess) {
        this.mProcess = mProcess;
        postInvalidate();
    }

    public void setRevert(boolean revert) {
        this.revert = revert;
        postInvalidate();
    }
    public void setChangeColor(int changeColor){
        mChangePaint=getPaintColor(changeColor);
    }
    public void setOriginColor(int changeColor){
        mOriginPaint=getPaintColor(changeColor);
    }
    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        mOriginPaint = getPaintColor(array.getColor(R.styleable.ColorTrackTextView_originColor, getTextColors().getDefaultColor()));
        mChangePaint = getPaintColor(array.getColor(R.styleable.ColorTrackTextView_changeColor, getTextColors().getDefaultColor()));
    }


    private Paint getPaintColor(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(getTextSize());
        return paint;
    }



    //一个文字两个颜色
    //利用clipRect，可以裁剪，左边一个颜色，右边一个颜色
    @Override
    protected void onDraw(Canvas canvas) {
        String text = getText().toString();
        int middle = (int) (mProcess * getWidth());
        if (revert){
            drawText(canvas, mChangePaint, text, 0, middle);
            drawText(canvas, mOriginPaint, text, middle, getWidth());
        }else {
            drawText(canvas, mOriginPaint, text, 0, middle);
            drawText(canvas, mChangePaint, text, middle, getWidth());
        }
    }

    private void drawText(Canvas canvas, Paint paint,String text,int start,int end){
        canvas.save();
        //获取字体的宽度
        //左侧位置，不变色
        Rect bounds = new Rect(start, 0, end, getHeight());
        canvas.clipRect(bounds);
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (int) (getWidth() / 2 - bounds.width() / 2);
        //字体的宽度/2
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, paint);
        canvas.restore();
    }


}
