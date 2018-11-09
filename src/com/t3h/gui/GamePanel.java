package com.t3h.gui;

import com.t3h.model.Entity;
import com.t3h.model.GameManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.BitSet;


/**
 * Created by Duy on 2/19/2017.
 */
public class GamePanel extends JPanel implements KeyListener, Runnable, MouseInputListener {
    private GameManager gameManager = new GameManager();
    private boolean isRunning = true;
    private BitSet bitSet = new BitSet(256);
    private double mouseX, mouseY;
    private boolean checkMouse = false;
    private boolean isPause = false;


    public GamePanel() {
        setLayout(null);
        gameManager.initGame();
        // listen key
        //setFocusable(true);
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
                requestFocusInWindow();
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        //requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);


        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        gameManager.draw(g2d);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon(getClass().getResource("/image/crosshair.png")).getImage(),
                new Point(0, 0), "custom cursor")
        );

        if (isPause == true) {
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g2d.setColor(Color.BLUE);
            g2d.drawString("Game Paused!", 370, 300);
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g2d.setColor(Color.GREEN);
            g2d.drawString("Press key P to continue...", 375, 360);
        }


        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2d.setColor(Color.MAGENTA);
        g2d.drawString("Press Space-Bar to change gun...", 400, 650);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            isPause = !isPause;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameManager.changePlayerGun();
        }
        bitSet.set(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        bitSet.clear(e.getKeyCode());
    }

    @Override
    public void run() {
        gameManager.playBackgroundSound();
        while (isRunning) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isPause == false) {
                if (gameManager.checkPlayerAlive() == false || gameManager.isWin() == true) {
                    isRunning = false;
                }
                if (bitSet.get(KeyEvent.VK_LEFT) || bitSet.get(KeyEvent.VK_A)) {
                    gameManager.playerMove(Entity.LEFT);
                }
                if (bitSet.get(KeyEvent.VK_RIGHT) || bitSet.get(KeyEvent.VK_D)) {
                    gameManager.playerMove(Entity.RIGHT);
                }
                if (bitSet.get(KeyEvent.VK_UP) || bitSet.get(KeyEvent.VK_W)) {
                    gameManager.playerMove(Entity.UP);
                }
                if (bitSet.get(KeyEvent.VK_DOWN) || bitSet.get(KeyEvent.VK_S)) {
                    gameManager.playerMove(Entity.DOWN);
                }


                gameManager.playerChangeGunOrient(mouseX, mouseY);
                if (checkMouse) gameManager.playerFire();
                gameManager.AI();
                //repaint();
                if (isRunning == false) {
                    if (gameManager.checkPlayerAlive() == false) {
                        int result = JOptionPane.showConfirmDialog(null, "Do you want replay?", "Game Over!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (result == JOptionPane.YES_OPTION) {
                            isRunning = true;
                            gameManager.initGame();
                            bitSet.clear();
                            checkMouse = false;
                        } else {
                            System.exit(0);
                        }
                    } else if (gameManager.isWin() == true){
                        int result = JOptionPane.showConfirmDialog(null, "Play again?", "You Win!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (result == JOptionPane.YES_OPTION) {
                            isRunning = true;
                            gameManager.initGame();
                            bitSet.clear();
                            checkMouse = false;
                        } else {
                            System.exit(0);
                        }
                    }
                }
            }
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            checkMouse = true;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        checkMouse = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            checkMouse = true;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
