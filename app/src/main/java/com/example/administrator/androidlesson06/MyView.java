package com.example.administrator.androidlesson06;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * MyView
 *
 * @author:Administrator
 * @time:2016/1/228:21
 */
public class MyView extends View {

    private Paint paint;
    private int x, y;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        init();
        drawtest(canvas);
        //drawPath(canvas);
        //drawBitmap(canvas);


    }

    /*
    初始化
     */
    public void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        x = 100;
        y = 100;
    }

    public void drawtest(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE); //设置画笔的Style:STROKE只画不填充

        paint.setStrokeWidth(15);           //设置画笔宽度
        canvas.drawLine(0, 0, x, y, paint);//画出直线

//        canvas.drawCircle(200, 200, 100, paint);//画出圆
        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.GREEN);
//        canvas.drawCircle(200, 200, 100, paint);

        //画矩形
//        paint.setColor(Color.YELLOW);
//        canvas.drawRect(100, 500, 200, 600, paint);

        //画三角形
        // canvas.drawRoundRect(100,700,200,800,10,10,paint);


    }

    /*

    路径绘制
     */
    private void drawPath(Canvas canvas) {
        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(100, 300);
        path.lineTo(200, 250);
        paint.setColor(Color.YELLOW);
        canvas.drawPath(path, paint);
        Path path1 = new Path();
        path1.addCircle(500, 500, 200, Path.Direction.CW);
        path1.addCircle(500, 500, 180, Path.Direction.CCW);
        path1.moveTo(500, 300);
        path1.lineTo(500, 700);
        path1.moveTo(300, 500);
        path1.lineTo(700, 500);
        paint.setColor(Color.GREEN);
        //paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path1, paint);
    }

    /**
     * 画出图片
     *
     * @param canvas
     */
    private void drawBitmap(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        canvas.drawBitmap(bitmap, 200, 200, paint);

        canvas.save();
        canvas.rotate(90);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(15);

        canvas.drawLine(getWidth() / 2, 0, -getHeight() / 2, 0, paint);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();

        canvas.drawBitmap(bitmap, 0, 0, paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = (int) event.getX();
        y = (int) event.getY();
        invalidate();
        return super.onTouchEvent(event);
    }
}
