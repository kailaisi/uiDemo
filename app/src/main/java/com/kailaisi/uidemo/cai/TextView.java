package com.kailaisi.uidemo.cai;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 描述：自定义实现TextView
 * <p/>作者：kailaisi
 * <br/>创建时间：2021-07-25:17:42
 */
class TextView extends View {
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
    }
}
