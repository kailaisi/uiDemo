package com.kailaisi.uidemo;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtil {
    public static int[] getScreenSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm= context.getResources().getDisplayMetrics();
        return new int[]{dm.widthPixels,dm.heightPixels};
    }
}
