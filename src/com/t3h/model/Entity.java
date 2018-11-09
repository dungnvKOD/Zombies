package com.t3h.model;

import java.awt.*;


/**
 * Created by Duy on 2/27/2017.
 */
public abstract class Entity {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    protected double x, y;
    protected Image img;
    protected double speed;
    protected int health;


    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    //    public void draw(Graphics2D g2d) {
//        int xRotate = x + img.getWidth(null) - 17;
//        int yRotate = y + img.getHeight(null) / 2;
//        AffineTransform reset = new AffineTransform();
//        reset.rotate(0, 0, 0);
//        g2d.rotate(2*3.14/3, xRotate, yRotate);
//        g2d.drawImage(img, x, y, null);
//        g2d.setTransform(reset);
//    }

//    public void changeOrient(int newOrient) {
//        orient = newOrient;
//    }
//
//    public void changeOrientGun(double mX, double mY) {
//        angle = Math.atan2(319 - mY, 439 - mX);
//    }
//
//
//
//    public void move() {
//        int speed = 1;
//        switch (orient) {
//            case LEFT:
//                x -= speed;
//                break;
//            case RIGHT:
//                x += speed;
//                break;
//            case UP:
//                y -= speed;
//                break;
//            case DOWN:
//                y += speed;
//                break;
//        }
//    }

    public abstract void draw(Graphics2D g2d);

}
