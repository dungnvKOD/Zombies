package com.t3h.model;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Duy on 3/10/2017.
 */
public class Items extends Entity{

    private Random random = new Random();
    private int item;
    private Image[] arrImg = new Image[]{
            new ImageIcon(getClass().getResource("/image/items/shield.png")).getImage(),
            new ImageIcon(getClass().getResource("/image/items/speed.png")).getImage(),
            new ImageIcon(getClass().getResource("/image/items/coin-01.png")).getImage(),
            new ImageIcon(getClass().getResource("/image/items/coin-02.png")).getImage()
    };


    public Items(double x, double y) {
        super(x,y);
        int a = random.nextInt(101);
        if (a > 90 && a <= 95){
            item = 1;
        } else if (a > 95){
            item = 2;
        }else if (a > 70 && a <= 80){
            item = 3;
        }else if (a > 80 && a <= 90){
            item = 4;
        } else item = 0;
        img = getImages();

    }

    public int getItem() {
        return item;
    }

    public Image getImages(){
        Image images = null;

        if (item == 1){
            images = arrImg[0];
        } else if (item == 2){
            images = arrImg[1];
        } else if (item == 3){
            images = arrImg[2];
        } else if (item == 4){
            images = arrImg[3];
        }
        return images;
    }


    @Override
    public void draw(Graphics2D g2d) {
            g2d.drawImage(img, (int)x,(int) y, null);
    }

    public Rectangle getHitBox(){
        if (item != 0) {
            int w = img.getWidth(null);
            int h = img.getHeight(null);
            Rectangle rect = new Rectangle((int) x, (int) y, w, h);
            return rect;
        }
        return new Rectangle();
    }

}
