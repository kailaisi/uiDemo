package com.kailaisi.uidemo.cai;

import android.annotation.SuppressLint;
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
 * 步数器
 */
public class QQStepView extends View {

    private Paint mTextPaint;
    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    private int mTextColor = Color.BLACK;
    private int borderWidth = 20;
    private int mText;
    private int mTextSize = 20;
    private Paint mPaint;
    private Paint mInnerPaint;

    //步数
    private int mCurrentStep = 1000;
    private int MaxStep = 5000;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mTextColor);
        borderWidth = array.getDimensionPixelSize(R.styleable.QQStepView_borderWidth, borderWidth);
        mTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mTextSize);
        array.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mOuterColor);
        mPaint.setStrokeWidth(borderWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //布局文件中可能是各种，宽高不一致，wrap
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //宽高不一致
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int dimension = Math.min(width, height);
        setMeasuredDimension(dimension, dimension);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆弧
        mPaint.setColor(mTextColor);
        RectF rect = new RectF(borderWidth / 2, borderWidth / 2, getWidth() - borderWidth / 2, getHeight() - borderWidth / 2);
        canvas.drawArc(rect, 135, 270, false, mPaint);
        //画内圆弧,是需要计算的对应弧度
        if (MaxStep == 0) {
            return;
        }
        int angle = (int) ((float) mCurrentStep / MaxStep * 270);
        canvas.drawArc(rect, 135, angle, false, mInnerPaint);
        //画文字

        String text = mCurrentStep + "";
        Rect rect1 = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect1);
        int left = getWidth() / 2 - rect1.width() / 2;
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, left, baseLine, mTextPaint);
        canvas.save();
    }


    public void setCurrentStep(int mCurrentStep) {
        this.mCurrentStep = mCurrentStep;
        invalidate();
    }

    public void setMaxStep(int maxStep) {
        MaxStep = maxStep;
    }
}
