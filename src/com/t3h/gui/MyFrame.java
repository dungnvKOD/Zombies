package com.t3h.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Duy on 2/14/2017.
 */
public class MyFrame extends JFrame implements WindowListener,ActionListener{
    public static final int W_FRAME = 1000;
    public static final int H_FRAME = 700;


    JButton play = new JButton(new ImageIcon("src\\image\\menu\\button_play.png"));
    JButton exit = new JButton(new ImageIcon("src\\image\\menu\\button_exit.png"));
    JButton help = new JButton(new ImageIcon("src\\image\\menu\\button_help.png"));
    JButton mainmenu = new JButton(new ImageIcon("src\\image\\menu\\button-back.png"));

    CardLayout layout = new CardLayout();

    JPanel panel = new JPanel();
    GamePanel game;
    JPanel menu = new JPanel();
    JPanel helppanel = new JPanel();

    JLabel label = new JLabel(new ImageIcon("src\\image\\menu\\zombie.png"));
    JLabel label1 = new JLabel(new ImageIcon("src\\image\\menu\\zombie1.png"));
    JLabel label2 = new JLabel(new ImageIcon("src\\image\\menu\\zombie2.png"));
    JLabel label3 = new JLabel("* Use A/S/D/W or left/right/up/down arrow to move");
    JLabel label4 = new JLabel("* Use left mouse to fire");
    JLabel label5 = new JLabel("* Use space-bar to change gun");
    JLabel label6 = new JLabel(new ImageIcon("src\\image\\menu\\Bungee_zombie.png"));
    JLabel label7 = new JLabel(new ImageIcon("src\\image\\menu\\Explorer_Zombie.png"));

    public MyFrame(){
        setTitle("!");
        setSize(W_FRAME,H_FRAME);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        panel.setLayout(layout);
        addPanel();
        addWindowListener(this);
        setVisible(true);
    }

    private void addPanel() {

        play.addActionListener(this);
        exit.addActionListener(this);
        help.addActionListener(this);
        mainmenu.addActionListener(this);

        //menu buttons
        menu.setLayout(null);
        menu.add(label);
        label.setBounds(70,50,869,239);
        menu.add(label1);
        label1.setBounds(50,300,220,220);
        menu.add(label2);
        label2.setBounds(650,180,294,488);
        menu.add(play);
        play.setBounds(380,300,240,100);
        play.setBackground(null);
        play.setBorderPainted(false);
        play.setFocusPainted(false);
        menu.add(exit);
        exit.setBounds(404,450,192,100);
        exit.setBackground(null);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        menu.add(help);
        help.setBounds(850,15,100,84);
        help.setBackground(null);
        help.setBorderPainted(false);
        help.setFocusPainted(false);


        //background colors
        menu.setBackground(Color.gray);

        //help panel
        helppanel.setLayout(null);
        label6.setBounds(100,0,360,197);
        helppanel.add(label6);
        label7.setBounds(650,250,336,435);
        helppanel.add(label7);
        label3.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        helppanel.add(label3);
        label3.setBounds(150,200,700,50);
        label4.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        helppanel.add(label4);
        label4.setBounds(150,250,500,50);
        label5.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        helppanel.add(label5);
        label5.setBounds(150,300,500,50);
        helppanel.add(mainmenu);
        mainmenu.setBounds(850,20,100,56);
        mainmenu.setBackground(null);
        mainmenu.setBorderPainted(false);
        mainmenu.setFocusPainted(false);
        helppanel.setBackground(Color.gray);

        //adding children to parent Panel
        panel.add(menu,"Menu");
        //panel.add(game,"Game");
        //game.setPause(true);
        panel.add(helppanel,"Help");


        add(panel);
        layout.show(panel,"Menu");

    }



    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(null,"Do you want close?","Message!",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == exit) {
            System.exit(0);
        } else if (source == play) {
            game = new GamePanel();
            panel.add(game,"Game");
            layout.show(panel, "Game");
            //game.setPause(false);
        } else if (source == help) {
            layout.show(panel, "Help");
        } else if (source == mainmenu) {
            layout.show(panel, "Menu");
        }

    }
}
