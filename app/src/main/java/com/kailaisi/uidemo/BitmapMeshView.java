package com.kailaisi.uidemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 基于Mesh的图片斜体
 */
public class BitmapMeshView extends View {

    public static final int WIDTH = 19;
    public static final int HEIGHT = 19;
    public static final int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    //图片
    private Bitmap mBitmap;
    //原坐标数组
    private float[] matrixOrigin = new float[COUNT * 2];
    private float[] matrixMoved = new float[COUNT * 2];

    private Paint originPaint, movePaint, linePaint;
    //屏幕触摸点
    private float clickX, clickY;

    public BitmapMeshView(Context context) {
        super(context);
    }

    public BitmapMeshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public BitmapMeshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        setFocusable(true);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
        originPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        originPaint.setColor(0x660000FF);
        movePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        movePaint.setColor(0x99FF0000);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFFFFFB00);

        int index = 0;
        for (int y = 0; y <= HEIGHT; y++) {
            float fy = mBitmap.getHeight() * y / HEIGHT;
            for (int x = 0; x <= WIDTH; x++) {
                float fx = mBitmap.getWidth() * x / WIDTH;
                setXY(matrixMoved, index, fx, fy);
                setXY(matrixOrigin, index, fx, fy);
                index += 1;
            }
        }
    }

    private void setXY(float[] array, int index, float fx, float fy) {
        array[index * 2] = fx;
        array[index * 2 + 1] = fy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // canvas.drawBitmap(mBitmap,0,0,null);
        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, matrixMoved, 0, null, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        smudge();
        invalidate();
        return super.onTouchEvent(event);
    }

    /**
     * 计算变换后的数组坐标
     */
    private void smudge() {
        for (int i = 0; i < COUNT * 2; i += 2) {
            float xOriginal = matrixOrigin[i];
            float yOriginal = matrixOrigin[i + 1];

            float dist_click_to_origin_x = clickX - xOriginal;
            float dist_click_to_origin_y = clickY - yOriginal;

            float kv_kat = dist_click_to_origin_x * dist_click_to_origin_x + dist_click_to_origin_y * dist_click_to_origin_y;

            float pull = (float) (1000000 / kv_kat / Math.sqrt(kv_kat));

            if (pull >= 1) {
                matrixMoved[i] = clickX;
                matrixMoved[i + 1] = clickY;
            } else {
                matrixMoved[i] = xOriginal + dist_click_to_origin_x * pull;
                matrixMoved[i + 1] = yOriginal + dist_click_to_origin_y * pull;
            }
        }
    }
}
