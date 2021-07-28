package com.kailaisi.uidemo.cai;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.kailaisi.uidemo.R;

/**
 * 描述：自定义实现TextView
 * <p/>作者：kailaisi
 * <br/>创建时间：2021-07-25:17:42
 */
public class TextView extends View {

    private int mTextColor = 0xffffff;
    private Paint mPaint;
    private String mText;
    private int mTextSize = 15;

    /**
     * 手动使用构造方法创建View
     */
    public TextView(Context context) {
        this(context, null);
    }

    /**
     * 该方法是xml中使用的布局的时候，调用的构造方法
     */
    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 如果xml布局中，有 style 设置样式的时候，使用的构造方法
     */
    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextView);
        mText = array.getString(R.styleable.TextView_drawText);
        mTextColor = array.getColor(R.styleable.TextView_drawTextColor, mTextColor);
        mTextSize = array.getDimensionPixelSize(R.styleable.TextView_drawTextSize, sp2px(mTextSize));
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setAntiAlias(true);
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //布局的宽高都是通过这方法确认，
        //获取宽高的模式
        int wideMode = MeasureSpec.getMode(widthMeasureSpec);

        //获取对应的宽高数据
        //1。确定的模式，则直接给多少算多少
        int width = MeasureSpec.getSize(widthMeasureSpec);

        //2。使用的是wrap，需要根据字符计算
        if (wideMode == MeasureSpec.AT_MOST) {
            //计算的宽度
            Rect rect = new Rect();
            mPaint.getTextBounds(mText, 0, mText.length(), rect);
            width = rect.width() + getPaddingLeft() + getPaddingRight();
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            //计算的宽度
            Rect rect = new Rect();
            mPaint.getTextBounds(mText, 0, mText.length(), rect);
            height = rect.height() + getPaddingTop() + getPaddingBottom();
        }
        //设置控制的宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //要在Text的baseLine位置画text
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(mText, 0, baseLine, mPaint);

    }
}
