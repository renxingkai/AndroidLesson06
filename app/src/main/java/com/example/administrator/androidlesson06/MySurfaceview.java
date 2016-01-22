package com.example.administrator.androidlesson06;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * MySurfaceview
 *
 * @author:${ Xingkai Ren}
 * @time:2016/1/22 10:18
 */
public class MySurfaceview extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public final static String TAG = "MySurfaceview";

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Paint paint;
    private Thread thread;
    private boolean aBoolean;
    private float x, y;
    private float speedX, speedY;

    private float radius;
    private int color;

    //向量位置，速度，加速度
    private Vector loca;
    private Vector speed;
    private Vector acc;

    //矩形参数
    private float rectx, recty;
    private float rectwidth, rectheight;
    //另一个矩形参数
    private float rectx1, recty1;
    private float rectwidth1, rectheight1;

    private boolean isColl;

    /**
     * 直接构造
     *
     * @param context
     */
    public MySurfaceview(Context context) {
        super(context);
        init();
    }

    /**
     * 重写run方法
     */
    @Override
    public void run() {
        while (aBoolean) {
            long start = System.currentTimeMillis();
            canvas = surfaceHolder.lockCanvas();//锁住
            if (canvas != null) {
                myDraw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);//解锁
            }
            logic();
            long end = System.currentTimeMillis();
            if ((end - start) < 50) {
                try {
                    Thread.sleep(50 - (end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 布局
     *
     * @param context
     * @param attrs
     */
    public MySurfaceview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(15);
        radius = 50;
        color = Color.RED;
        //第二个球
        loca = new Vector(100, 100);
        speed = new Vector(10, 20);
        acc = new Vector(0.5f, 0.5f);
        //矩形
        rectx = 300;
        recty = 300;
        rectheight = 200;
        rectwidth = 160;
//        rectx1 = 200;
//        recty1 = 200;
//        rectheight1 = getWidth() / 2 - 150;
//        rectwidth1 = getHeight() / 2 - 100;


    }

    /**
     * 初始化游戏
     */
    private void initGames() {
        x = 0;
        y = 0;
        speedX = 10;
        speedY = 20;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initGames();
        aBoolean = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "surfaceDestroyed");
        aBoolean = false;
    }

    /**
     * 画图
     *
     * @param canvas
     */
    private void myDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        paint.setColor(Color.RED);
        canvas.drawCircle(x, y, radius, paint);
        //画出矩形
        paint.setColor(Color.YELLOW);
        canvas.drawRect(rectx, recty, rectx + rectwidth, recty + rectheight, paint);
        //可移动矩形
//        paint.setColor(Color.GREEN);
//        canvas.drawRect(rectx1, recty1, rectx1 + rectwidth1, recty1 + rectheight1, paint);
        //画出另一个球
        paint.setColor(Color.BLUE);
        canvas.drawCircle(loca.x, loca.y, 35, paint);
    }

    /**
     * 逻辑函数
     */
    private void logic() {
        //检测移动
        x += speedX;
        y += speedY;
        if (x >= getWidth() || x < 0) {
            speedX = -speedX;
        }
        if (y >= getHeight() || y < 0) {
            speedY = -speedY;
        }
//        rectx1+=speedX;
//        recty1+=speedY;
//        if (rectx1>= getWidth() ||rectx1< 0) {
//            speedX = -speedX;
//        }
//        if (recty1>= getHeight() || recty1 < 0) {
//            speedY = -speedY;
//        }
        //检测第二个球的加速度，速度
        loca.add(speed);
        speed.add(acc);
        speed.limit(20);
        if (loca.x >= getWidth() || loca.x < 0) {
            speed.x = -speed.x;
            acc.x = -acc.x;
        }
        if (loca.y >= getHeight() || loca.y < 0) {
            speed.y = -speed.y;
            acc.y = -acc.y;
        }
        //检测矩形碰撞
//        isColl = rectAndRect(rectx, recty, rectwidth, rectheight, rectx1, recty1, rectwidth1, rectheight1);
//        if(isColl){
//            paint.setColor(Color.GREEN);
//            canvas.drawCircle(100,100,20,paint);
//        }
//        isColl = cicleAndCircle(x, y, radius, loca.x, loca.y, 25);
//        if (isColl) {
//            paint.setColor(Color.GREEN);
//            canvas.drawCircle(100, 100, 20, paint);
//        }
        isColl=circleAndRect(rectx, recty, rectwidth, rectheight,x, y, radius);
        if(isColl){
            paint.setColor(Color.GREEN);
            canvas.drawCircle(100, 100, 20, paint);
        }
    }

    private boolean circleAndRect(float rect1x, float rect1y, float rect1width, float rect1height,
                                  float c1x, float c1y, float c1r) {
        if (c1x + c1r < rect1x) {
            return false;
        } else if (c1x - c1r > rect1x + rect1width) {
            return false;
        } else if (c1y + c1r < rect1y) {
            return false;
        } else if (c1y - c1r > rect1y + rect1height) {
            return false;
        }
//        }else if(Math.pow(rect1x-c1x,2)+Math.pow(rect1y-c1y,2)>c1r){
//            return false;
//        }else if(Math.pow(rect1x+rect1width-c1x,2)+Math.pow(rect1y-c1y,2)>c1r){
//            return false;
//        }else if(Math.pow(rect1x-c1x,2)+Math.pow(rect1y+rect1height-c1y,2)>c1r){
//            return false;
//        }else if(Math.pow(rect1x+rect1height-c1x,2)+Math.pow(rect1y+rect1width-c1y,2)>c1r){
//            return false;
//        }
        return true;
    }


    private boolean cicleAndCircle(float c1x, float c1y, float c1r, float c2x, float c2y, float c2r) {
        float dis = (float) (Math.pow(c2x - c1x, 2) + Math.pow(c2y - c1y, 2));
        if (dis > Math.pow(c1r + c2r, 2)) {
            return false;
        }
        return true;
    }

    /**
     * 检测矩形
     *
     * @param rect1x
     * @param rect1y
     * @return
     */
    private boolean rectAndRect(float rect1x, float rect1y, float rect1width, float rect1height,
                                float rect2x, float rect2y, float rect2width, float rect2height) {
        if (rect1x + rect1width < rect2x) {
            return false;
        } else if (rect1x > rect2x + rect2width) {
            return false;
        } else if (rect1y + rect1height < rect2y) {
            return false;
        } else if (rect1y > rect2y + rect2height) {
            return false;
        }
        return false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int[] a = new int[]{Color.GREEN, Color.YELLOW, Color.RED};
        color = a[new Random().nextInt(a.length)];
        color = Color.argb(0, x % 255, y % 255, 100);
        radius = new Random().nextInt(50) + 50;

        //加上力
        Vector v = new Vector(x, y);
        acc = Vector.sub(v, loca);
        acc.nomarlline();
        acc.mult(15.0f);

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }


}
