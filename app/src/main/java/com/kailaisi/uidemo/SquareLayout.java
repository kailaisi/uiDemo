package com.kailaisi.uidemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * 自定义布局类，将控件按照正方形来进行布局。
 */
public class SquareLayout extends ViewGroup {
    public static final int Ori_Hori = 0, Ori_Veri = 1;
    public static final int DefaultMaxRow = Integer.MAX_VALUE;
    public static final int DefaultMaxColumn = Integer.MAX_VALUE;

    private int mMaxRow = DefaultMaxRow;//最大行数
    private int mMaxColumn = DefaultMaxColumn;//最大列数

    private int mOrientation = Ori_Veri;//排列方向

    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMaxColumn = mMaxRow = 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = 0;
        int parentHeight = 0;

        int childMeasureState = 0;
        if (getChildCount() > 0) {
            // 声明两个一维数组存储子元素宽高数据
            int[] childWidths = new int[getChildCount()];
            int[] childHeights = new int[getChildCount()];

            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility() != View.GONE) {
                    //测量子元素并考虑其外边距
                    measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                    int childMeasureSize = Math.max(child.getMeasuredWidth(), child.getMeasuredHeight());
                    //重新设置其测量方案
                    int childMeasure = MeasureSpec.makeMeasureSpec(childMeasureSize, MeasureSpec.EXACTLY);
                    child.measure(childMeasure, childMeasure);

                    MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
                    childWidths[i] = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                    childHeights[i] = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                    childMeasureState = combineMeasuredStates(childMeasureState, child.getMeasuredState());
                }
            }
            int indexMultiWidth = 0, indexMultiHeight = 0;
            if (mOrientation == Ori_Hori) {
                //横向排列
                if (getChildCount() > mMaxColumn) {
                    //行数
                    int row = getChildCount() / mMaxColumn;
                    //余数
                    int remain = getChildCount() % mMaxColumn;
                    //下标
                    int index = 0;
                    for (int x = 0; x < row; x++) {
                        for (int i = 0; i < mMaxColumn; i++) {
                            indexMultiWidth += childWidths[index];
                            indexMultiHeight = Math.max(indexMultiHeight, childHeights[i]);
                        }
                        parentWidth = Math.max(parentWidth, indexMultiWidth);
                        parentHeight += indexMultiHeight;
                        //重制归零
                        indexMultiWidth = indexMultiHeight = 0;
                    }
                    if (remain > 0) {
                        for (int i = getChildCount() - remain; i < getChildCount(); i++) {
                            indexMultiWidth += childWidths[i];
                            indexMultiHeight = Math.max(indexMultiHeight, childHeights[i]);
                        }
                        parentWidth = Math.max(parentWidth, indexMultiWidth);
                        parentHeight += indexMultiHeight;
                        //重制归零
                        indexMultiWidth = indexMultiHeight = 0;
                    }
                } else {
                    for (int i = 0; i < getChildCount(); i++) {
                        parentHeight += childHeights[i];
                        parentWidth = Math.max(parentWidth, childWidths[i]);
                    }
                }
            }


            /*
             * 如果为竖向排列
             */
            else if (mOrientation == Ori_Veri) {
                if (getChildCount() > mMaxRow) {
                    int column = getChildCount() / mMaxRow;
                    int remainder = getChildCount() % mMaxRow;
                    int index = 0;

                    for (int x = 0; x < column; x++) {
                        for (int y = 0; y < mMaxRow; y++) {
                            indexMultiHeight += childHeights[index];
                            indexMultiWidth = Math.max(indexMultiWidth, childWidths[index++]);
                        }
                        parentHeight = Math.max(parentHeight, indexMultiHeight);
                        parentWidth += indexMultiWidth;
                        indexMultiWidth = indexMultiHeight = 0;
                    }

                    if (remainder != 0) {
                        for (int i = getChildCount() - remainder; i < getChildCount(); i++) {
                            indexMultiHeight += childHeights[i];
                            indexMultiWidth = Math.max(indexMultiHeight, childWidths[i]);
                        }
                        parentHeight = Math.max(parentHeight, indexMultiHeight);
                        parentWidth += indexMultiWidth;
                        indexMultiWidth = indexMultiHeight = 0;
                    }
                } else {
                    for (int i = 0; i < getChildCount(); i++) {
                        // 累加子元素的实际宽度
                        parentWidth += childWidths[i];

                        // 获取子元素中高度最大值
                        parentHeight = Math.max(parentHeight, childHeights[i]);
                    }
                }
            }

            /*
             * 考量父容器内边距将其累加到期望值
             */
            parentWidth += getPaddingLeft() + getPaddingRight();
            parentHeight += getPaddingTop() + getPaddingBottom();

            /*
             * 尝试比较父容器期望值与Android建议的最小值大小并取较大值
             */
            parentWidth = Math.max(parentWidth, getSuggestedMinimumWidth());
            parentHeight = Math.max(parentHeight, getSuggestedMinimumHeight());
        }

        // 确定父容器的测量宽高
        setMeasuredDimension(resolveSizeAndState(parentWidth, widthMeasureSpec, childMeasureState),
                resolveSizeAndState(parentHeight, heightMeasureSpec, childMeasureState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        /*
         * 如果父容器下有子元素
         */
        if (getChildCount() > 0) {
            // 声明临时变量存储宽高倍增值
            int multi = 0;

            // 指数倍增值
            int indexMulti = 1;

            // 声明临时变量存储行/列宽高
            int indexMultiWidth = 0, indexMultiHeight = 0;

            // 声明临时变量存储行/列临时宽高
            int tempHeight = 0, tempWidth = 0;

            /*
             * 遍历子元素
             */
            for (int i = 0; i < getChildCount(); i++) {
                // 获取对应遍历下标的子元素
                View child = getChildAt(i);

                /*
                 * 如果该子元素没有以“不占用空间”的方式隐藏则表示其需要被测量计算
                 */
                if (child.getVisibility() != View.GONE) {
                    // 获取子元素布局参数
                    MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();

                    // 获取控件尺寸
                    int childActualSize = child.getMeasuredWidth();// child.getMeasuredHeight()

                    /*
                     * 如果为横向排列
                     */
                    if (mOrientation == Ori_Hori) {
                        /*
                         * 如果子元素数量比限定值大
                         */
                        if (getChildCount() > mMaxColumn) {
                            /*
                             * 根据当前子元素进行布局
                             */
                            if (i < mMaxColumn * indexMulti) {
                                child.layout(getPaddingLeft() + mlp.leftMargin + indexMultiWidth, getPaddingTop() + mlp.topMargin + indexMultiHeight,
                                        childActualSize + getPaddingLeft() + mlp.leftMargin + indexMultiWidth, childActualSize + getPaddingTop()
                                                + mlp.topMargin + indexMultiHeight);
                                indexMultiWidth += childActualSize + mlp.leftMargin + mlp.rightMargin;
                                tempHeight = Math.max(tempHeight, childActualSize) + mlp.topMargin + mlp.bottomMargin;

                                /*
                                 * 如果下一次遍历到的子元素下标值大于限定值
                                 */
                                if (i + 1 >= mMaxColumn * indexMulti) {
                                    // 那么累加高度到高度倍增值
                                    indexMultiHeight += tempHeight;

                                    // 重置宽度倍增值
                                    indexMultiWidth = 0;

                                    // 增加指数倍增值
                                    indexMulti++;
                                }
                            }
                        } else {
                            // 确定子元素左上、右下坐标
                            child.layout(getPaddingLeft() + mlp.leftMargin + multi, getPaddingTop() + mlp.topMargin, childActualSize
                                    + getPaddingLeft() + mlp.leftMargin + multi, childActualSize + getPaddingTop() + mlp.topMargin);

                            // 累加倍增值
                            multi += childActualSize + mlp.leftMargin + mlp.rightMargin;
                        }
                    }

                    /*
                     * 如果为竖向排列
                     */
                    else if (mOrientation == Ori_Veri) {
                        if (getChildCount() > mMaxRow) {
                            if (i < mMaxRow * indexMulti) {
                                child.layout(getPaddingLeft() + mlp.leftMargin + indexMultiWidth, getPaddingTop() + mlp.topMargin + indexMultiHeight,
                                        childActualSize + getPaddingLeft() + mlp.leftMargin + indexMultiWidth, childActualSize + getPaddingTop()
                                                + mlp.topMargin + indexMultiHeight);
                                indexMultiHeight += childActualSize + mlp.topMargin + mlp.bottomMargin;
                                tempWidth = Math.max(tempWidth, childActualSize) + mlp.leftMargin + mlp.rightMargin;
                                if (i + 1 >= mMaxRow * indexMulti) {
                                    indexMultiWidth += tempWidth;
                                    indexMultiHeight = 0;
                                    indexMulti++;
                                }
                            }
                        } else {
                            // 确定子元素左上、右下坐标
                            child.layout(getPaddingLeft() + mlp.leftMargin, getPaddingTop() + mlp.topMargin + multi, childActualSize
                                    + getPaddingLeft() + mlp.leftMargin, childActualSize + getPaddingTop() + mlp.topMargin + multi);

                            // 累加倍增值
                            multi += childActualSize + mlp.topMargin + mlp.bottomMargin;
                        }
                    }
                }
            }
        }
    }


    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }


}
