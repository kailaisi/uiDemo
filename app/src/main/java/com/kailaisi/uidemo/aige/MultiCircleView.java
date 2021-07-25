package com.kailaisi.uidemo.aige;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * 多个圆结构
 */
public class MultiCircleView extends AppCompatImageView {
    private static final float STROKE_WIDTH = 1F / 256F, // 描边宽度占比
            SPACE = 1F / 64F,// 大圆小圆线段两端间隔占比
            LINE_LENGTH = 3F / 32F, // 线段长度占比
            CIRCLE_LARGE_RADIUS = 3F / 32F,// 大圆半径
            CIRCLE_SMALL_RADIUS = 5F / 64F,// 小圆半径
            ARC_RADIU = 1F / 8F,// 弧半径
            ARC_TEXT_RADIU = 5F / 32F;// 弧围绕文字半径

    private Context mContext;
    private float size;//控件的大小
    private float strokeWidth;//描边的实际宽度
    private float largeCircleRadius;//大圆的半径
    private float smallCircleRadius;//大圆的半径
    private float ccX, ccY;//中心圆的中心点
    private Paint strokePaint;//画笔
    private float lineLength;
    private float space;

    public MultiCircleView(Context context) {
        super(context);
    }

    public MultiCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.WHITE);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFFF29B76);
        canvas.drawCircle(ccX, ccY, largeCircleRadius, strokePaint);
        drawTopLeft(canvas);


        // 绘制右上方图形
        drawTopRight(canvas);

        // 绘制左下方图形
        drawBottomLeft(canvas);

        // 绘制下方图形
        drawBottom(canvas);

        // 绘制右下方图形
        drawBottomRight(canvas);
    }

    /**
     * 绘制左上方图形
     *
     * @param canvas
     */
    private void drawTopLeft(Canvas canvas) {
        // 锁定画布
        canvas.save();

        // 平移和旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(-30);

        // 依次画：线-圈-线-圈
        canvas.drawLine(0, -largeCircleRadius, 0, -lineLength * 2, strokePaint);
        canvas.drawCircle(0, -lineLength * 3, largeCircleRadius, strokePaint);
        canvas.drawLine(0, -largeCircleRadius * 4, 0, -lineLength * 5, strokePaint);
        canvas.drawCircle(0, -lineLength * 6, largeCircleRadius, strokePaint);

        // 释放画布
        canvas.restore();
    }

    /**
     * 绘制右上方图形
     *
     * @param canvas
     */
    private void drawTopRight(Canvas canvas) {
        // 锁定画布
        canvas.save();

        // 平移和旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(30);

        // 依次画：线-圈
        canvas.drawLine(0, -largeCircleRadius, 0, -lineLength * 2, strokePaint);
        canvas.drawCircle(0, -lineLength * 3, largeCircleRadius, strokePaint);

        // 释放画布
        canvas.restore();
    }

    private void drawBottomLeft(Canvas canvas) {
        // 锁定画布
        canvas.save();

        // 平移和旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(-100);

        // 依次画：(间隔)线(间隔)-圈
        canvas.drawLine(0, -largeCircleRadius - space, 0, -lineLength * 2 - space, strokePaint);
        canvas.drawCircle(0, -lineLength * 2 - smallCircleRadius - space * 2, smallCircleRadius, strokePaint);

        // 释放画布
        canvas.restore();
    }

    private void drawBottom(Canvas canvas) {
        // 锁定画布
        canvas.save();

        // 平移和旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(180);

        // 依次画：(间隔)线(间隔)-圈
        canvas.drawLine(0, -largeCircleRadius - space, 0, -lineLength * 2 - space, strokePaint);
        canvas.drawCircle(0, -lineLength * 2 - smallCircleRadius - space * 2, smallCircleRadius, strokePaint);

        // 释放画布
        canvas.restore();
    }

    private void drawBottomRight(Canvas canvas) {
        // 锁定画布
        canvas.save();

        // 平移和旋转画布
        canvas.translate(ccX, ccY);
        canvas.rotate(100);

        // 依次画：(间隔)线(间隔)-圈
        canvas.drawLine(0, -largeCircleRadius - space, 0, -lineLength * 2 - space, strokePaint);
        canvas.drawCircle(0, -lineLength * 2 - smallCircleRadius - space * 2, smallCircleRadius, strokePaint);

        // 释放画布
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        size = w;
        //进行一些计算
        calculation();
    }

    private void calculation() {
        //描边宽度
        strokeWidth = STROKE_WIDTH * size;
        largeCircleRadius = size * CIRCLE_LARGE_RADIUS;
        lineLength = size * LINE_LENGTH;
        smallCircleRadius = CIRCLE_SMALL_RADIUS * size;


        // 计算大圆小圆线段两端间隔
        space = size * SPACE;

        ccX = size / 2;
        ccY = size / 2 + size * CIRCLE_LARGE_RADIUS;
        strokePaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //强制长宽一致
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
