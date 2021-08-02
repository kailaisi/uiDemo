package com.kailaisi.uidemo.cai;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.kailaisi.uidemo.R;

/**
 * 描述：自定义字母列表
 * <p/>作者：kailaisi
 * <br/>创建时间：2021-07-25:17:42
 */
public class LetterSideView extends View {

    private Paint mPaint;
    private int mTextSize = 15;
    private int mTextColor = Color.BLACK;
    private TouchLetterListener listener;
    private static String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"};
    private int itemHeight;
    private int curPosition;

    /**
     * 手动使用构造方法创建View
     */
    public LetterSideView(Context context) {
        this(context, null);
    }

    /**
     * 该方法是xml中使用的布局的时候，调用的构造方法
     */
    public LetterSideView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 如果xml布局中，有 style 设置样式的时候，使用的构造方法
     */
    public LetterSideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LetterSideView);
        mTextSize = array.getDimensionPixelSize(R.styleable.LetterSideView_sideTextSize, mTextSize);
        mTextColor = array.getColor(R.styleable.LetterSideView_sideTextColor, mTextColor);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //宽度，是padding+字体的宽度
        float text = mPaint.measureText("W");
        int width = (int) (getPaddingLeft() + getPaddingEnd() + text);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //设置控制的宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
        for (int i = 0; i < letters.length; i++) {
            //
            int center = itemHeight * i + getPaddingTop() + itemHeight / 2;
            //要在Text的baseLine位置画text
            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
            int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            //宽度，是padding+字体的宽度
            float text = mPaint.measureText(letters[i]);
            int x = (int) (getWidth() / 2 - text / 2);
            int baseLine = center + dy;
            //计算基线
            if (i == curPosition) {
                mPaint.setColor(Color.BLUE);
            } else {
                mPaint.setColor(mTextColor);
            }
            canvas.drawText(letters[i], x, baseLine, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int position = (int) ((y - getPaddingTop()) / itemHeight);
                if (position < 0) {
                    position = 0;
                }
                if (position > letters.length - 1) {
                    position = letters.length - 1;
                }
                if (position != curPosition) {
                    if (listener != null) {
                        listener.onTouch(letters[position]);
                    }
                    curPosition = position;
                    System.out.println(curPosition);
                    invalidate();
                }
        }
        return true;
    }

    public void setListener(TouchLetterListener listener) {
        this.listener = listener;
    }

    public interface TouchLetterListener {
        void onTouch(String text);
    }
}
