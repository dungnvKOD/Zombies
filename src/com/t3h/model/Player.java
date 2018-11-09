package com.t3h.model;

import com.t3h.gui.MyFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Duy on 2/28/2017.
 */
public class Player extends Entity {

    private Image image = new ImageIcon(getClass().getResource("/image/player/player.PNG")).getImage();
    private Image imgSheild = new ImageIcon(getClass().getResource("/image/player/sh.PNG")).getImage();
    private int shield;
    private int orient = 0;
    private double angle = 0;
    private double xRotate, yRotate;
    private int score;
    private boolean alive;
    private int gun;
    private int timeCheckAlive;


    public Player(double x, double y) {
        super(x, y);
        orient = UP;
        img = image;
        health = 500;
        score = 0;
        speed = 1;
        xRotate = x + img.getWidth(null) - 17;
        yRotate = y + img.getHeight(null) / 2;
        alive = true;
        gun = 1;
        shield = 0;
        timeCheckAlive = 25;
    }

    public double getxRotate() {
        return xRotate;
    }

    public double getyRotate() {
        return yRotate;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGun() {
        return gun;
    }

    public void draw(Graphics2D g2d) {
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        g2d.rotate(angle, xRotate, yRotate);
        g2d.drawImage(image, (int)x,(int) y, null);
        g2d.setTransform(reset);

        if (shield == 1){
            g2d.drawImage(imgSheild, (int) xRotate - 35 ,(int) yRotate - 35, null);
        }

    }

    public void changeGun(){
        if (gun == 1){
            gun = 2;
        } else {
            gun = 1;
        }
    }

    public void changeOrient(int newOrient) {
        orient = newOrient;
    }

    public void changeOrientGun(double mX, double mY) {
        angle = Math.atan2(yRotate - mY, xRotate - mX);

    }


    public void move() {
        switch (orient) {
            case LEFT:
                if (x > -25)
                x -= speed;
                break;
            case RIGHT:
                if (x < MyFrame.W_FRAME - img.getWidth(null))
                x += speed;
                break;
            case UP:
                if (y > 0)
                y -= speed;
                break;
            case DOWN:
                if (y < MyFrame.H_FRAME - img.getHeight(null))
                y += speed;
                break;
        }
        xRotate = x + img.getWidth(null) - 17;
        yRotate = y + img.getHeight(null) / 2;
    }

    public Bullet fireNormalGun(){
        int xB = (int) (xRotate - 30 * Math.cos(angle)) - 8;
        int yB = (int) (yRotate - 30 * Math.sin(angle));
        Bullet bullet = new Bullet(xB,yB,angle);
        bullet.playSound();
        return bullet;
    }

    public Bullet[] fireShotGun(){
        int xB = (int) (xRotate - 30 * Math.cos(angle)) - 8;
        int yB = (int) (yRotate - 30 * Math.sin(angle));
        Bullet[] arr = new Bullet[5];
        arr[0] = new Bullet(xB,yB,angle);
        arr[1] = new Bullet(xB,yB,angle - Math.PI/24);
        arr[2] = new Bullet(xB,yB,angle + Math.PI/24);
        arr[3] = new Bullet(xB,yB,angle + Math.PI/12);
        arr[4] = new Bullet(xB,yB,angle - Math.PI/12);
        arr[0].playSound();
        return arr;
    }

    public Rectangle getHitBox(){
        int w = img.getWidth(null) - 15;
        int h = img.getHeight(null) - 30;
        Rectangle rect = new Rectangle((int) x + 27,(int) y + 20, w, h);
        return rect;
    }

    public void checkAlive(ArrayList<Boss> arrBoss){
        for (Boss boss : arrBoss) {
            Rectangle rect = getHitBox().intersection(boss.getHitBox());
            while (timeCheckAlive == 0) {
                if (!rect.isEmpty()) {
                    if (shield != 1) {
                        alive = false;
                        playDeathSound();
                        return;
                    } else shield = 0;
                }
                timeCheckAlive = 25;
            }
            timeCheckAlive--;
        }
    }

    public void playDeathSound(){
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("src\\image\\player\\playerDeath.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkItems(ArrayList<Items> arrItems){
        for (int i = arrItems.size() - 1; i >= 0 ; i--) {
            Rectangle rect = getHitBox().intersection(arrItems.get(i).getHitBox());
            if (!rect.isEmpty()){
                if (arrItems.get(i).getItem() == 1){
                     shield = 1;
                } else if (arrItems.get(i).getItem() == 2){
                    if (speed <= 2)
                        speed += 0.5;
                } else if (arrItems.get(i).getItem() == 3){
                    score += 50;
                } else if (arrItems.get(i).getItem() == 4){
                    score += 200;
                }
                arrItems.remove(i);
            }
        }
    }


}
