package com.t3h.model;

import com.t3h.gui.MyFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.Random;

/**
 * Created by Duy on 3/1/2017.
 */
public abstract class Boss extends Entity {

    int point;
    private double angle;
    private Random random;

    Boss(double x, double y) {
        super(x, y);

    }

    public void draw(Graphics2D g2d) {
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        g2d.rotate(angle, x + img.getWidth(null) / 2, y + img.getHeight(null) / 2);
        g2d.drawImage(img, (int)x,(int) y, null);
        g2d.setTransform(reset);
    }




    void move(Player player){
        angle = (Math.atan2(y - player.getyRotate(), x - player.getxRotate()));
        x = x - speed * Math.cos(angle);
        y = y - speed * Math.sin(angle);
    }



    Rectangle getHitBox(){
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        Rectangle rect = new Rectangle((int) x,(int) y, w, h);
        return rect;
    }

    public void playDeathSound(){
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("src\\image\\boss\\zombieDeath.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
