package com.example.surfaceviewdemo.game;

import android.content.Context;
import android.util.TypedValue;

public class Utils {
        public static int db2px(Context context,int dpVal)
        {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal,context.getResources().getDisplayMetrics());
        }
}
