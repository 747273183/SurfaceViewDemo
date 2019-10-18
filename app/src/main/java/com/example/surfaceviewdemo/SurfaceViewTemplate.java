package com.example.surfaceviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewTemplate extends SurfaceView implements  Runnable{
    private Thread thread;
    //volatile 线程同步,线程间的可见性
    private volatile boolean isRunning=false;

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        //1.获得SurfaceHolder对象
        SurfaceHolder handler = (SurfaceHolder) getHandler();
        //2.监听Surface的创建
        handler.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
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
}
