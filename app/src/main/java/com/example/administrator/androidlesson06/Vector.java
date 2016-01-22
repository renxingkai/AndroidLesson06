package com.example.administrator.androidlesson06;

/**
 * Vector
 *
 * @author:${ Xingkai Ren}
 * @time:2016/1/22 14:17
 */
public class Vector {

    public float x, y;//坐标

    public Vector() {
    }

    public Vector(float y, float x) {
        this.y = y;
        this.x = x;
    }


    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * 加法
     *
     * @param v
     * @return
     */
    public Vector add(Vector v) {
        x += v.x;
        y += v.y;
        return this;
    }

    /**
     * 两个向量加法
     *
     * @param v1
     * @param v2
     * @return
     */

    public static Vector add(Vector v1, Vector v2) {
        return new Vector(v1.x + v2.x, v1.y + v2.y);
    }

    /**
     * 减法
     *
     * @param v
     * @return
     */
    public Vector sub(Vector v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    /**
     * 两个向量减法
     *
     * @param v1
     * @param v2
     * @return
     */
    public static Vector sub(Vector v1, Vector v2) {
        return new Vector(v1.x - v2.x, v1.y - v2.y);
    }

    /**
     * 乘法
     *
     * @param n
     * @return
     */
    public Vector mult(float n) {
        x *= n;
        y *= n;
        return this;
    }

    /**
     * 除法
     *
     * @param n
     * @return
     */
    public Vector div(float n) {
        if (n != 0) {
            x /= n;
            y /= n;
        }
        return this;
    }

    /**
     * 向量的模
     *
     * @return
     */
    public float mag() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 对模的限制
     *
     * @param max
     */
    public void limit(float max) {
        if (max * max < mag() * mag()) {
            nomarlline();
            mult(max);
        }
    }

    /**
     * 单位化
     *
     * @return
     */
    public void nomarlline() {
        div(mag());
    }


}
