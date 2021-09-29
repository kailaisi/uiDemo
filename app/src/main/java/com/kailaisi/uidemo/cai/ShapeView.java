package com.kailaisi.uidemo.cai;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.kailaisi.uidemo.R;

public class ShapeView extends View {

    private Shape shape = Shape.Triple;

    private Paint mPaint = new Paint();

    private Path mPath = new Path();

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setAntiAlias(true);
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
        switch (shape) {
            case Circle:
                mPaint.setColor(Color.RED);
                canvas.drawCircle(center, center, center, mPaint);
                break;
            case Square:
                //正方形
                mPaint.setColor(Color.BLUE);
                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
                break;
            case Triple:
                //三角形
                mPaint.setColor(Color.GREEN);
                mPath.moveTo(getWidth() / 2, 0);
                double height = Math.sqrt(3) / 2;
                mPath.lineTo(0, (float) (getHeight() * height));
                mPath.lineTo(getWidth(), (float) (getHeight() * height));
                mPath.close();
                canvas.drawPath(mPath, mPaint);
                break;
        }
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void exchange() {
        switch (shape) {
            case Circle:
                shape = Shape.Square;
                break;
            case Square:
                shape = Shape.Triple;
                break;
            case Triple:
                shape = Shape.Circle;
                break;
        }
        invalidate();
    }

    enum Shape {
        Circle, Square, Triple
    }
}