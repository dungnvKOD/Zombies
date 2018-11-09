package com.t3h.model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Duy on 3/1/2017.
 */
public class BasicBoss extends Boss {

    private Image image = new ImageIcon(getClass().getResource("/image/boss/zombie.gif")).getImage();


    public BasicBoss(double x, double y) {
        super(x, y);
        img = image;
        speed = 0.5;
        point = 50;
        health = 50;
    }



}
