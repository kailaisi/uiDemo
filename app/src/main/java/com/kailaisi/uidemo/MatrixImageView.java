package com.kailaisi.uidemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * 能够根据手指的缩放来进行图片的旋转和缩放
 */
public class MatrixImageView extends AppCompatImageView {
    private static final String TAG = "MatrixImageView";

    public static final int MODE_NONE = 1;
    public static final int MODE_DRAG = 2;//拖拽
    public static final int MODE_ZOOM = 3;//缩放或者旋转

    private int mode;//当前模式
    private float preMove = 1;//上次移动距离

    private float saveRotate = 0;
    private float[] preEventCoor;//上次个触摸点的合集

    private PointF start, mid;//起点，中点对象

    private Matrix currentMatrix, savedMatrix;//当前和保存的matrix对象

    private Context mContext;

    public MatrixImageView(Context context) {
        super(context);
    }

    public MatrixImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        currentMatrix = new Matrix();
        savedMatrix = new Matrix();
        start = new PointF();
        mid = new PointF();
        mode = MODE_NONE;

        //图片资源

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
        int[] screenSize = ScreenUtil.getScreenSize(mContext);
        //创建一个可以旋转的图片
        bitmap = Bitmap.createScaledBitmap(bitmap, screenSize[0], screenSize[1], true);
        //显示出来
        setImageBitmap(bitmap);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN://单点触摸
            case MotionEvent.ACTION_POINTER_UP://第二个点离开屏幕
                savedMatrix.set(currentMatrix);
                start.set(event.getX(), event.getY());
                mode = MODE_DRAG;
                preEventCoor = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN://双指触摸
                preMove = calSpacing(event);
                if (preMove > 10F) {
                    savedMatrix.set(currentMatrix);
                    calMidPoint(mid, event);
                    mode = MODE_ZOOM;
                }
                //记录两个手指的位置消息
                preEventCoor = new float[4];
                preEventCoor[0] = event.getX(0);
                preEventCoor[1] = event.getX(1);
                preEventCoor[2] = event.getY(0);
                preEventCoor[3] = event.getY(1);
                saveRotate = calRotation(event);//计算两个手指大的角度
                break;
            case MotionEvent.ACTION_UP://单点离开屏幕
                break;
            case MotionEvent.ACTION_MOVE://触摸点移动
                //单点触控，执行拖拽评议
                if (mode == MODE_DRAG) {
                    //先加载以前的变换处理
                    currentMatrix.set(savedMatrix);
                    //执行移动操作
                    currentMatrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                } else if (mode == MODE_ZOOM && event.getPointerCount() == 2) {//两点触控，执行缩放或者旋转
                    //计算当前两指之间大的距离
                    float currentMove = calSpacing(event);
                    currentMatrix.set(savedMatrix);
                    Log.d(TAG, "currentMove: " + currentMove + ",preMove:" + preMove);
                    if (Math.abs(currentMove - preMove) > 10) {
                        //指尖移动距离大于10F,执行缩放
                        float scale = currentMove / preMove;
                        currentMatrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    //执行旋转操作
                    if (preEventCoor != null) {
                        //旋转角度
                        float rotate = calRotation(event);
                        float r = rotate - saveRotate;
                        currentMatrix.postRotate(r, getMeasuredWidth() >> 1, getMinimumHeight() >> 1);
                    }
                }
                break;
        }
        //执行图片的旋转和缩放操作
        setImageMatrix(currentMatrix);
        return true;
    }

    /**
     * 计算两个手指之间的角度
     */
    private float calRotation(MotionEvent event) {
        float deltaX = event.getX(0) - event.getX(1);
        float deltaY = event.getY(0) - event.getY(1);
        //计算tan的值
        double radius = Math.atan2(deltaY, deltaX);
        //计算角度
        return (float) Math.toDegrees(radius);
    }

    /**
     * 计算触摸中点
     */
    private void calMidPoint(PointF mid, MotionEvent event) {
        float x = event.getX() - start.x;
        float y = event.getY() - start.y;
        mid.set(x / 2, y / 2);
    }

    /**
     * 计算两个触摸点大的距离
     */
    private float calSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

}
