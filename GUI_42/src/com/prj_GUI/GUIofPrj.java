package com.prj_GUI;

import sun.applet.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIofPrj extends JFrame{
    private JButton Upload;
    private JButton Download;
    private JButton Settings;
    private JPanel Main_Panel;
    private JPanel Buttons;

    private GUIofPrj(){
        JFrame jfrm = new JFrame("Athanatos");
        jfrm.setIconImage(new ImageIcon("src/icon2.ico").getImage());
        jfrm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jfrm.getContentPane().add(Main_Panel);
        jfrm.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("icon1.png")));

        BtnInitialize(Upload);
        BtnInitialize(Download);
        BtnInitialize(Settings);

        Buttons.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
        Main_Panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
        jfrm.pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth()/2 - jfrm.getWidth()));
        if (x < 0) {
            x = 0;
        }
        int y = (int) ((screenSize.getHeight()/2 - jfrm.getHeight()));
        if (y < 0) {
            y = 0;
        }
        System.out.println(x);
        System.out.print(y);
        jfrm.setBounds(x, y, jfrm.getWidth(), jfrm.getHeight());
        jfrm.setVisible(true);

        Upload.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fopen = new JFileChooser();
                fopen.setFileSelectionMode(JFileChooser.FILES_ONLY);
            }

        });

        Download.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //обработчик кнопки загрузки файлов
            }
        });

        Settings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIofSet(Settings);
            }
        });

    }
    private static void BtnInitialize(JButton btn){
        btn.setBorder(new EmptyBorder(0, 0, 0, 0));
        btn.addMouseListener(new MouseEvents());
    }
    public static void main(String args[]){
        new GUIofPrj();
    }
}
