package com.example.surfaceviewdemo.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Bird extends DrawablePart {

    private int x;
    private int y;

    private static final float  RADIO_Y_POS=1/2F; //设置为屏幕的1/2高度

    private static  final int WIDTH_BIRD=30;//设置鸟的宽度

    private int mWidth;
    private int mHeight;

    private RectF rectF=new RectF();

    public Bird(Context context, int gameW, int gameH, Bitmap bitmap) {
        super(context, gameW, gameH, bitmap);
        y= (int) (gameH*RADIO_Y_POS);
        mWidth=Utils.db2px(context,WIDTH_BIRD);
        mHeight= (int) (mWidth*1.0f/bitmap.getWidth()*bitmap.getHeight());

        x=gameW/2-mWidth/2;
    }

    @Override
    public void draw(Canvas canvas) {
            rectF.set(x,y,x+mWidth,y+mHeight);
            canvas.drawBitmap(bitmap,null,rectF,null);
    }

    public int getY()
    {
        return  y;
    }

    public void setY(int y)
    {
        this.y=y;
    }

    public void reset()
    {
        y= (int) (gameH*RADIO_Y_POS);
    }
}
