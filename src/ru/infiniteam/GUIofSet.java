package ru.infiniteam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIofSet {
    private JTextField textField1;
    private JButton Cancel;
    private JButton Browse;
    private JPanel Main_panel;
    private JButton Apply;
    private JTextField textField2;
    private JPanel Path_Panel;
    private JPanel IP_Panel;

    private String tempPath;
    private String tempIP;

    protected GUIofSet(Component relative){
        JFrame form2 = new JFrame("Settings");
        form2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        form2.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("icon2.png")));
        Path_Panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        new ParamConfiguration().load();
        textField1.setText(ParamConfiguration.pathToVault);
        textField2.setText(ParamConfiguration.serverIP);
        tempPath = textField1.getText();
        tempIP = textField2.getText();

        form2.getContentPane().add(Main_panel);
        form2.setLocationRelativeTo(relative);
        form2.pack();
        form2.setVisible(true);



        Browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fopen = new JFileChooser();
                fopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fopen.setAcceptAllFileFilterUsed(false);
                fopen.setMultiSelectionEnabled(false);
                int ret = fopen.showDialog(null, "Directory selection");
                if(ret == JFileChooser.APPROVE_OPTION)
                        textField1.setText(fopen.getSelectedFile().getAbsolutePath());
            }
        });

        Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText(tempPath);
                textField2.setText(tempIP);
                form2.dispose();
            }
        });

        Apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ParamConfiguration.savePath(textField1.getText());
                ParamConfiguration.saveServerIP(textField2.getText());
                form2.dispose();
            }
        });
    }
}
