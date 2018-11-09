package com.t3h.model;

import com.t3h.gui.MyFrame;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Duy on 2/28/2017.
 */
public class GameManager {
    private Image background = new ImageIcon(getClass().getResource("/image/background.png")).getImage();

    private Player player;

    private ArrayList<Boss> arrBoss;
    private ArrayList<Bullet> arrBullet;
    private ArrayList<Items> arrItems;

    private Random random = new Random();
    private int spawn = 0;
    private int xB,yB;
    private int bulletTimer = 0;
    private int highScore;

    public void initGame() {
        //tạo người chơi
        player = new Player(MyFrame.W_FRAME/2, MyFrame.H_FRAME/2);
        //mảng boss
        arrBoss = new ArrayList<Boss>();
        //mảng chứa đạn
        arrBullet = new ArrayList<Bullet>();
        //mảng chứa Items
        arrItems = new ArrayList<Items>();


        //tạo ra các đợt tấn công
        spawn = 1;
        createSpawn(1000);


    }

    synchronized public void draw(Graphics2D g2d) {
        //vẽ background
        g2d.drawImage(background, 0, 0, MyFrame.W_FRAME, MyFrame.H_FRAME, null);
        //vẽ Items
        for (Items items : arrItems) {
            items.draw(g2d);
        }
        //vẽ người chơi
        player.draw(g2d);
        //vẽ mảng boss
        for (Boss boss : arrBoss) {
            boss.draw(g2d);
        }
        //vẽ mảng đạn
        for (Bullet bullet : arrBullet) {
            bullet.draw(g2d);
        }


        //vẽ điểm số
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g2d.setColor(Color.red);
        g2d.drawString("Score:", 50, 50);
        g2d.drawString(player.getScore() + "",200,50);
        //vẽ thanh máu

        //vẽ high score
        setHighScore(player.getScore());
        getHighScore();
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g2d.setColor(Color.WHITE);
        g2d.drawString("High Score:", 50, 90);
        g2d.drawString(highScore + "",200,90);

    }

    public void playerMove(int newOrient) {
        player.changeOrient(newOrient);
        player.move();
    }

    public void playerChangeGunOrient(double mX, double mY) {
        player.changeOrientGun(mX, mY);
    }

    public void playerFire() {
        if (player.getGun() == 1) {
            if (bulletTimer > 7) {
                bulletTimer = 0;
                Bullet bullet = player.fireNormalGun();
                arrBullet.add(bullet);
            } else bulletTimer++;
        } else {
            if (bulletTimer > 25) {
                bulletTimer = 0;
                Bullet[] arr = new Bullet[5];
                arr = player.fireShotGun();
                for (int i = 0; i < arr.length; i++) {
                    arrBullet.add(arr[i]);
                }
            } else bulletTimer++;
        }
    }

    synchronized public void AI() {

        //boss di chuyển
        for (int i = 0; i < arrBoss.size(); i++) {
            arrBoss.get(i).move(player);
        }
        //đạn bay
        for (int i = 0; i < arrBullet.size(); i++) {
            if (arrBullet.get(i).checkMap())
                arrBullet.get(i).move();
            else arrBullet.remove(i);
        }
        // xóa đạn khi trúng boss
        for (int i = arrBullet.size() - 1; i >= 0 ; i--) {
            arrBullet.get(i).explosion(arrBoss,player,arrItems);
            if (!arrBullet.get(i).isCheckExists()) arrBullet.remove(i);
        }


        //tạo ra đợt boss thứ 2
        if (spawn == 2 && arrBoss.size() == 0){
                createSpawn(4000);
        }
        //tạo ra đợt boss thứ 3
        if (spawn == 3 && arrBoss.size() == 0){
            createSpawn(8000);
        }

        //ăn Items
        player.checkItems(arrItems);


    }

    public void playBackgroundSound(){
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("src\\image\\Spaced_Moosader.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            if (player.isAlive() == false || isWin() == true){
                clip.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void randomPositionBoss() {
        int sideOrTop = random.nextInt(2);
        if(sideOrTop == 1){
            int leftOrRight = random.nextInt(2);
            if(leftOrRight == 1){
                xB = -50;
            }
            else{
                xB = MyFrame.W_FRAME + 50;
            }
            yB = random.nextInt(MyFrame.H_FRAME);
        } else{
            int leftOrRight = random.nextInt(2);
            if(leftOrRight == 1){
                yB = -50;
            }
            else{
                yB = MyFrame.H_FRAME + 50;
            }
            xB = random.nextInt(MyFrame.W_FRAME);
        }
    }

    public void createSpawn(int a){
        while (a >= 0) {
            if (a % 1000 == 0) {
                randomPositionBoss();
                BigBoss bigBoss = new BigBoss(xB, yB);
                arrBoss.add(bigBoss);
            }
            if (a % 500 == 0) {
                randomPositionBoss();
                FastBoss fastBoss = new FastBoss(xB, yB);
                arrBoss.add(fastBoss);
            }
            if (a % 200 == 0) {
                randomPositionBoss();
                BasicBoss basicBoss = new BasicBoss(xB, yB);
                arrBoss.add(basicBoss);
            }
            a -= 10;
        }
        spawn++;
    }

    public void changePlayerGun(){
        player.changeGun();
    }

    public boolean checkPlayerAlive(){
        player.checkAlive(arrBoss);
        if (!player.isAlive()){
            return false;
        }
        return true;
    }

    public boolean isWin(){
        if (spawn == 4 && arrBoss.size() == 0){
            return true;
        }
            return false;
    }

    public void getHighScore(){
        File file = new File("src\\image\\highscore.txt");
        if (file.exists() == false){
            return;
        }
        try {
            FileInputStream inputStream = new FileInputStream(file);
            int b = inputStream.read();
            StringBuilder builder = new StringBuilder();
            while (b != -1){
                builder.append((char)b);
                b = inputStream.read();
            }
            inputStream.close();
            //System.out.println(builder);
            highScore = Integer.parseInt(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHighScore(int score){
        if (score > highScore){
            try {
                File file = new File("src\\image\\highscore.txt");
                file.delete();
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file,true);
                String s = score + "";
                byte[] b = s.getBytes();
                outputStream.write(b);
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
