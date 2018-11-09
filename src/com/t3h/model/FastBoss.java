package com.t3h.model;

import javax.swing.*;
import java.awt.*;


/**
 * Created by Duy on 3/1/2017.
 */
public class FastBoss extends Boss {
    private Image image = new ImageIcon(getClass().getResource("/image/boss/FAST_ZOMBIE.gif")).getImage();


    public FastBoss(double x, double y) {
        super(x, y);
        img = image;
        speed = 0.75;
        point = 100;
        health = 150;
    }

}
