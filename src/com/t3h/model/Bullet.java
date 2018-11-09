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
import java.util.Random;

/**
 * Created by Duy on 3/2/2017.
 */
public class Bullet extends Entity{

    private static double DEFAULT_SPEED = 20;

    private double angle;
    private Image image = new ImageIcon(getClass().getResource("/image/bullet/bullet.png")).getImage();
    private Random random = new Random();
    private int damge;
    private boolean checkExists;

    public Bullet(double x, double y, double angle) {
        super(x,y);
        this.angle = angle + (random.nextInt(4) - 2) * Math.PI/360;
        speed = DEFAULT_SPEED;
        img = image;
        damge = 25;
        checkExists = true;
    }


    boolean isCheckExists() {
        return checkExists;
    }


    public void draw(Graphics2D g2d) {
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        g2d.rotate(angle, x + img.getWidth(null)/2, y + img.getHeight(null)/2);
        g2d.drawImage(image, (int)x,(int) y, null);
        g2d.setTransform(reset);
    }

    public void move(){
        //double a = angle + (random.nextInt(2) - 1) * 3.14 / 360;
        x = (int) (x - speed * Math.cos(angle));
        y = (int) (y - speed * Math.sin(angle));
    }

    public boolean checkMap(){
        if (x >= 0 && x <= MyFrame.W_FRAME && y >= 0 && y <= MyFrame.H_FRAME){
            return true;
        }
        return false;
    }

    public Rectangle getHitBox(){
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        Rectangle rect = new Rectangle((int) x,(int) y, w, h);
        return rect;
    }

    //va chạm với Boss
    public void explosion(ArrayList<Boss> arrBoss,Player player,ArrayList<Items> arrItems){
        for (int i = arrBoss.size() - 1;i >= 0; i--){
            Rectangle rect = arrBoss.get(i).getHitBox();
            boolean check = checkBullet(rect);
            if (check){
                arrBoss.get(i).health -= damge;
                if (arrBoss.get(i).health <= 0) {
                    player.setScore(player.getScore() + arrBoss.get(i).point);
                    double xItem = arrBoss.get(i).getX();
                    double yItem = arrBoss.get(i).getY();
                    Items items = new Items(xItem,yItem);
                    arrItems.add(items);
                    arrBoss.get(i).playDeathSound();
                    arrBoss.remove(i);
                }
                checkExists = false;
            }
        }

    }

    private boolean checkBullet(Rectangle rect) {
        Rectangle r = rect.intersection(getHitBox());
        if (!r.isEmpty()){
            return true;
        }
        return false;
    }

    public void playSound(){
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("src\\image\\bullet\\bullet.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
