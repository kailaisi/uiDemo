package com.kailaisi.uidemo.cai;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.kailaisi.uidemo.R;

/**
 * 描述：
 * <p/>作者：wu
 * <br/>创建时间：2021-09-29:21:50
 */
public class ProgressBar extends View {

    Paint mTextPaint;
    Paint innerPaint;
    Paint outerPaint;
    private int roundWidth;

    private int progress = 50;
    private int max = 100;

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar);
        innerPaint = new Paint();
        innerPaint.setAntiAlias(false);
        int color = array.getColor(R.styleable.ProgressBar_innerBackground, Color.WHITE);
        roundWidth = array.getDimensionPixelSize(R.styleable.ProgressBar_roundWidth, 3);
        innerPaint.setColor(color);
        innerPaint.setStrokeWidth(roundWidth);
        innerPaint.setStyle(Paint.Style.STROKE);

        color = array.getColor(R.styleable.ProgressBar_outerBackground, Color.BLACK);
        outerPaint = new Paint();
        outerPaint.setAntiAlias(true);
        outerPaint.setColor(color);
        outerPaint.setStrokeWidth(roundWidth);
        outerPaint.setStyle(Paint.Style.STROKE);


        color = array.getColor(R.styleable.ProgressBar_progressTextColor, Color.BLACK);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(color);
        int size = array.getDimensionPixelSize(R.styleable.ProgressBar_progressTextSize, 3);
        mTextPaint.setTextSize(size);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //内圆
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, getWidth() / 2f - roundWidth / 2, outerPaint);

        //外圆
        RectF rect = new RectF(roundWidth / 2, roundWidth / 2, getWidth() - roundWidth / 2, getHeight() - roundWidth / 2);
        canvas.drawArc(rect, 0, progress * 360 / 100, false, innerPaint);

        //文字
        String text = progress + "/100";
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
        int dx = getWidth() / 2 - bounds.width() / 2;
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, dx, baseLine, mTextPaint);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int min = Math.min(width, height);
        setMeasuredDimension(min, min);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }
}
