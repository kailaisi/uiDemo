package com.kailaisi.uidemo.cai;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * 描述：侧滑菜单
 * 实现思路：
 * 1. 系统DrawerLayout  一般做侧滑效果
 * 2. 自定义ViewGroup+手势处理类（处理麻烦，代码多）
 * 3. 自定义ScrollerView+（默认自己可以滚动）
 *   1.1
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
