package com.kailaisi.uidemo.aige;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ECGView extends View {
    private Paint mPaint;
    private Path mPath;



    private int screenW, screenH;// 屏幕宽高
    private float x, y;// 路径初始坐标
    private float initScreenW;// 屏幕初始宽度
    private float initX;// 初始X轴坐标
    private float transX, moveX;// 画布移动的距离

    private boolean isCanvasMove;// 画布是否需要平移
    public ECGView(Context context) {
        super(context);
    }

    public ECGView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    public ECGView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        mPath.lineTo(x,y);
        canvas.translate(-transX,0);

        calCoors();
        canvas.drawPath(mPath,mPaint);
        invalidate();
    }

    /**
     * 计算坐标
     */
    private void calCoors() {
        if (isCanvasMove == true) {
            transX += 4;
        }

        if (x < initX) {
            x += 8;
        } else {
            if (x < initX + moveX) {
                x += 2;
                y -= 8;
            } else {
                if (x < initX + (moveX * 2)) {
                    x += 2;
                    y += 14;
                } else {
                    if (x < initX + (moveX * 3)) {
                        x += 2;
                        y -= 12;
                    } else {
                        if (x < initX + (moveX * 4)) {
                            x += 2;
                            y += 6;
                        } else {
                            if (x < initScreenW) {
                                x += 8;
                            } else {
                                isCanvasMove = true;
                                initX = initX + initScreenW;
                            }
                        }
                    }
                }
            }

        }
    }
}
