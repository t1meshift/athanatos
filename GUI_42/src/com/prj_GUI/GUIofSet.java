package com.prj_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIofSet extends JFrame {
    private JTextField textField1;
    private JButton cancelButton;
    private JButton applyButton;
    private JButton browseButton;
    private JPanel Main_panel;

    protected GUIofSet(Component relative){
        JFrame form2 = new JFrame("Settings");
        form2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        form2.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("icon2.png")));

        form2.getContentPane().add(Main_panel);
        form2.setLocationRelativeTo(relative);
        form2.pack();
        form2.setVisible(true);

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fopen = new JFileChooser();
                fopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fopen.setAcceptAllFileFilterUsed(false);

                int ret = fopen.showDialog(null, "Select directory");
                switch (ret){
                    case JFileChooser.APPROVE_OPTION:
                        String TempPath = textField1.getText();
                        textField1.setText(fopen.getSelectedFile().getAbsolutePath());
                        break;
                }
            }
        });


    }

}
