package com.kailaisi.uidemo.cai;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.kailaisi.uidemo.R;

public class ProgressBar extends View {


    private Paint mTextPaint;
    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    private int mTextColor = Color.BLACK;
    private int borderWidth = 20;
    private int mTextSize = 20;
    private Paint mOuterPaint;
    private Paint mInnerPaint;

    private int mProgress = 50;
    private int MAX = 100;

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar);
        mOuterColor = array.getColor(R.styleable.ProgressBar_outBackground, mOuterColor);
        mInnerColor = array.getColor(R.styleable.ProgressBar_innerBackground, mInnerColor);
        mTextColor = array.getColor(R.styleable.ProgressBar_progressTextColor, mTextColor);
        borderWidth = array.getDimensionPixelSize(R.styleable.ProgressBar_progressBorderWidth, borderWidth);
        mTextSize = array.getDimensionPixelSize(R.styleable.ProgressBar_progressTextSize, mTextSize);
        array.recycle();
        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterColor);
        mOuterPaint.setStrokeWidth(borderWidth);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);
        mOuterPaint.setStyle(Paint.Style.STROKE);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeWidth(borderWidth);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);


        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeCap(Paint.Cap.SQUARE);
    }


    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            return;
        }
        this.mProgress = progress;
        invalidate();
    }

    public void setMax(int MAX) {
        this.MAX = MAX;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //需要设置为宽高相同
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(height, width);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2;
        int halfBorder = borderWidth / 2;
        canvas.drawCircle(center, getHeight() / 2, center - halfBorder, mInnerPaint);
        if (mProgress == 0) {
            return;
        }
        int progress=(100*mProgress/MAX);
        String text = progress + "%";
        canvas.drawArc(halfBorder, halfBorder, getWidth() - halfBorder, getHeight() - halfBorder,
                0, 45, false, mOuterPaint);
        Rect bound = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), bound);
        int left = getWidth() / 2 - bound.width() / 2;
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        int baseLine = getHeight() / 2 + dy;

        canvas.drawText(text, left, baseLine, mTextPaint);
    }
}