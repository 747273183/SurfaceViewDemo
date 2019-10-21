package com.example.surfaceviewdemo.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.surfaceviewdemo.R;

public class FlappyBird extends SurfaceView implements  Runnable{

    private Thread thread;
    //volatile 线程同步,线程间的可见性
    private volatile boolean isRunning=false;
    private int mFlag;

    //res
    private Bitmap mBg;
    private Bitmap mBirdBm;
    private Bitmap mFloorBm;

    private RectF dst;

    private Floor floor;
    private Bird bird;
    private int mSpend; //地板移动的速度

    private static  final int TOUCH_UP_SIZE=-16;
    private int mBirdUpDis;
    private static final int SIZE_AUTO_DOWN=2;
    private int mAutoDownDis;

    private  int mTempBirdDis;



    public FlappyBird(Context context, AttributeSet attrs) {
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
                    thread=new Thread(FlappyBird.this);
                    thread.start();
                    bird.reset();
                    mTempBirdDis=0;
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

        mSpend=Utils.db2px(getContext(),2);//初始化速度
        mBirdUpDis=Utils.db2px(getContext(),TOUCH_UP_SIZE);
        mAutoDownDis=Utils.db2px(getContext(),SIZE_AUTO_DOWN);

        //初始化资源
        initRes();


    }

    private void initRes() {
        mBg= BitmapFactory.decodeResource(getResources(), R.drawable.bg1);
        mBirdBm=BitmapFactory.decodeResource(getResources(),R.drawable.b1);
        mFloorBm=BitmapFactory.decodeResource(getResources(),R.drawable.floor_bg);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        dst=new RectF(0,0,w,h);
        floor=new Floor(getContext(),w,h,mFloorBm);
        bird=new Bird(getContext(),w,h,mBirdBm);


    }

    @Override
    public void run() {
        while (isRunning)
        {
            long start = System.currentTimeMillis();
            drawSelf();
            long end = System.currentTimeMillis();
            //为了保证每次的绘制过程至少是50毫秒
            if (end-start<50)
            {
                try {
                    Thread.sleep(50-(end-start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void drawSelf() {
        Canvas canvas=null;
        try {
            canvas=getHolder().lockCanvas();
            if (canvas!=null)
            {
                drawBg(canvas);

                logic();//用于执行一定的一些逻辑

                drawBird(canvas);
                drawFloor(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas!=null)
            {
                getHolder().unlockCanvasAndPost(canvas);
            }
        }

    }

    private void logic() {
            floor.setX(floor.getX()-mSpend);//地板坐标横向更新
        mTempBirdDis+=mAutoDownDis;
        bird.setY(bird.getY()+mTempBirdDis);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action=event.getAction();
        if (action==MotionEvent.ACTION_DOWN)
        {
            mTempBirdDis=mBirdUpDis;
        }
        return  true;
    }

    private void drawFloor(Canvas canvas) {
        floor.draw(canvas);
    }

    private void drawBird(Canvas canvas) {
        bird.draw(canvas);
    }

    private void drawBg(Canvas canvas) {

        canvas.drawBitmap(mBg,null,dst,null);
    }


}
