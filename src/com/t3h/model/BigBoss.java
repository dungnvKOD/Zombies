package com.t3h.model;

import javax.swing.*;
import java.awt.*;


/**
 * Created by Duy on 3/1/2017.
 */
public class BigBoss extends Boss {

    private Image image = new ImageIcon(getClass().getResource("/image/boss/SLOW_ZOMBIE.gif")).getImage();


    public BigBoss(int x, int y) {
        super(x, y);
        img = image;
        speed = 0.3;
        point = 200;
        health = 500;
    }

}
