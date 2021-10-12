package com.kailaisi.uidemo.cai;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * 描述：侧滑菜单
 * <p/>作者：wu
 * <br/>创建时间：2021-10-12:21:41
 */
public class KGSlidingMenu extends HorizontalScrollView {
    public KGSlidingMenu(Context context) {
        this(context,null);
    }

    public KGSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public KGSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
