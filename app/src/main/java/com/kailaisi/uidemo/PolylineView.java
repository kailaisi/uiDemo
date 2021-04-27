package com.kailaisi.uidemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 基于Mesh的图片斜体
 */
public class PolylineView extends View {
    private static final float LEFT = 1 / 16F, TOP = 1 / 16F, RIGHT = 15 / 16F, BOTTOM = 7 / 8F;// 网格区域相对位置
    private static final float TIME_X = 3 / 32F, TIME_Y = 1 / 16F, MONEY_X = 31 / 32F, MONEY_Y = 15 / 16F;// 文字坐标相对位置
    private static final float TEXT_SIGN = 1 / 32F;// 文字相对大小
    private static final float THICK_LINE_WIDTH = 1 / 128F, THIN_LINE_WIDTH = 1 / 512F;// 粗线和细线相对大小

    private TextPaint mTextPaint;// 文字画笔
    private Paint linePaint, pointPaint;// 线条画笔和点画笔
    private Path mPath;// 路径对象
    private Bitmap mBitmap;// 绘制曲线的Btimap对象
    private Canvas mCanvas;// 装载mBitmap的Canvas对象

    private List<PointF> pointFs;// 数据列表
    private float[] rulerX, rulerY;// xy轴向刻度

    private String signX, signY;// 设置X和Y坐标分别表示什么的文字
    private float textY_X, textY_Y, textX_X, textX_Y;// 文字坐标
    private float textSignSzie;// xy坐标标识文本字体大小
    private float thickLineWidth, thinLineWidth;// 粗线和细线宽度
    private float left, top, right, bottom;// 网格区域左上右下两点坐标
    private int viewSize;// 控件尺寸
    private float maxX, maxY;// 横纵轴向最大刻度
    private float spaceX, spaceY;// 刻度间隔

    public PolylineView(Context context) {
        super(context);
    }

    public PolylineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public PolylineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setColor(Color.WHITE);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.WHITE);

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pointPaint.setColor(Color.WHITE);

        mPath = new Path();
        mCanvas = new Canvas();

        initData();
    }

    private void initData() {
        Random random = new Random();
        pointFs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            PointF pointF = new PointF();
            pointF.x = random.nextInt(100) * i;
            pointF.y = random.nextInt(100) * i;
            pointFs.add(pointF);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFF9596c4);
        //标识元素
        drawSign(canvas);
        //绘制网格
        drawGrid(canvas);
        //绘制曲线
        drawPolyline(canvas);
    }

    private void drawPolyline(Canvas canvas) {

    }

    private void drawGrid(Canvas canvas) {

    }

    private void drawSign(Canvas canvas) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        // 获取控件尺寸
        viewSize = w;

        // 计算纵轴标识文本坐标
        textY_X = viewSize * TIME_X;
        textY_Y = viewSize * TIME_Y;

        // 计算横轴标识文本坐标
        textX_X = viewSize * MONEY_X;
        textX_Y = viewSize * MONEY_Y;

        // 计算xy轴标识文本大小
        textSignSzie = viewSize * TEXT_SIGN;

        // 计算网格左上右下两点坐标
        left = viewSize * LEFT;
        top = viewSize * TOP;
        right = viewSize * RIGHT;
        bottom = viewSize * BOTTOM;

        // 计算粗线宽度
        thickLineWidth = viewSize * THICK_LINE_WIDTH;

        // 计算细线宽度
        thinLineWidth = viewSize * THIN_LINE_WIDTH;
    }
}
