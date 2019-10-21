package com.example.surfaceviewdemo.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class DrawablePart {

    protected Context context;
    protected int gameW;
    protected int gameH;
    protected Bitmap bitmap;

    public DrawablePart(Context context, int gameW, int gameH, Bitmap bitmap)
    {

        this.context = context;
        this.gameW = gameW;
        this.gameH = gameH;
        this.bitmap = bitmap;
    }
    public abstract  void draw(Canvas canvas);
}
