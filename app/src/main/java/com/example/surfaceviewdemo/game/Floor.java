package com.example.surfaceviewdemo.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

public class Floor extends Bird{

    private final BitmapShader mBitmapShader; //片断着色器
    private int x;
    private int y;

    private static final float RADIO_Y_POS=4/5f;
    private Paint mPaint;

    public Floor(Context context, int gameW, int gameH, Bitmap bitmap) {
        super(context, gameW, gameH, bitmap);

        //地板的纵坐标
        y= (int) (gameH*RADIO_Y_POS);
        //初始化画笔
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mBitmapShader=new BitmapShader(bitmap, Shader.TileMode.REPEAT,Shader.TileMode.CLAMP);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(x,y);
        mPaint.setShader(mBitmapShader);
        canvas.drawRect(x,0,-x+gameW,gameH-y,mPaint);


        canvas.restore();
        mPaint.setShader(null);

    }
    public int getX()
    {
        return  x;
    }
    public void setX(int x)
    {
        this.x=x;
        if (-x>gameW)
        {
            this.x=x%gameW;
        }
    }
}
