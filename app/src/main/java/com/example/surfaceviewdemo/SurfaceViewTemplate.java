package com.example.surfaceviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewTemplate extends SurfaceView implements  Runnable{

    private Thread thread;
    //volatile 线程同步,线程间的可见性
    private volatile boolean isRunning=false;

    private Paint mPaint;//画笔
    private int mMaxRadius;
    private int mMinRadius;
    private int mRadius;
    private int mFlag;

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        //1.获得SurfaceHolder对象
        SurfaceHolder holder = (SurfaceHolder) getHolder();
        //2.监听Surface的创建
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                //设置线程的标志位为true
                isRunning=true;
                    //3.开启子线程,在线程中使用循环处理绘制
                    thread=new Thread(SurfaceViewTemplate.this);
                    thread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }
            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                //在surface销毁时,不再绘制
                isRunning=false;
            }
        });

        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        //初始化画笔
        initPaint();

    }

    private void initPaint() {
        mPaint=new Paint();
        mPaint.setDither(true);//抖动
        mPaint.setAntiAlias(true);//平滑
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMaxRadius=Math.min(w,h)-20;
        mRadius=mMinRadius=30;
        //20 30 都是自定义的,随便写的值

    }

    @Override
    public void run() {
            while (isRunning)
            {
                drawSelf();
            }
    }

    private void drawSelf() {
            //4.获取Canvas进行绘制
        Canvas canvas=null;

        try {

            canvas = getHolder().lockCanvas();
            if (canvas!=null)
            {
//                canvas.drawLine();
                drawCircle(canvas);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas!=null)
            {
                //释放canvas
                getHolder().unlockCanvasAndPost(canvas);
            }
        }


    }

    //画圆的方法
    private void drawCircle(Canvas canvas) {
        canvas.drawColor(Color.WHITE);//画一个背景
        canvas.drawCircle(getWidth()/2,getHeight()/2,mRadius,mPaint);

        //动态改变半径
        //到达最大值每次递增两个像素,到达最小值每次递减两个像素
        if (mRadius>=mMaxRadius)
        {
            mFlag=-1;
        }
        else if (mRadius<=mMinRadius)
        {
                mFlag=1;
        }
        mRadius+=mFlag*2;

    }
}
