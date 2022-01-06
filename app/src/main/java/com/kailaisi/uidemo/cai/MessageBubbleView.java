package com.kailaisi.uidemo.cai;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 描述：拖拽数字爆炸效果
 * 分析：1.点击之后，会有两个圆，一个位于原来的位置，一个位于手指位置。
 * 2. 原来位置的圆的大小随着两个圆的距离变化而变化
 * 3. 两个圆之间有个不规则的黏性曲线
 *
 * <p/>作者：wu
 * <br/>创建时间：2022-01-06:21:10
 */
class MessageBubbleView extends View {
    private PointF mFiixatonPoint;//点击位置
    private PointF mDragPoint;//拖拽点位置

    private int mDragRadius=10;

    private Paint mPaint;
    private final int mFiixatonMaxRadius=30;
    private int mFiixatonRadius;

    public MessageBubbleView(Context context) {
        this(context,null);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 要显示两个圆
                initPoint(event.getX(),event.getY());
            case MotionEvent.ACTION_MOVE:
                updateDragPoint(event.getX(),event.getY());
            case MotionEvent.ACTION_UP:
        }
        invalidate();
        return true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mDragPoint==null || mFiixatonPoint==null){
            return;
        }
        // 拖拽圆
        canvas.drawCircle(mDragPoint.x,mDragPoint.y,mDragRadius,mPaint);
        // 固定圆,有初始大小，而且随着距离的变大而变小
        double distance=Math.sqrt((mDragPoint.x-mFiixatonPoint.x)*(mDragPoint.x-mFiixatonPoint.x)+(mDragPoint.y-mFiixatonPoint.y)*(mDragPoint.y-mFiixatonPoint.y));
        mFiixatonRadius= (int) (mFiixatonMaxRadius-distance/4);
        canvas.drawCircle(mFiixatonPoint.x,mFiixatonPoint.y,mFiixatonRadius,mPaint);
    }

    private void updateDragPoint(float x, float y) {
        mDragPoint=new PointF(x,y);
    }


    private void initPoint(float x, float y){
        mFiixatonPoint=new  PointF(x,y);
        mDragPoint=new PointF(x,y);
    }
}
