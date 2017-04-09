package ru.infiniteam;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIofPrj {
    private JButton Upload;
    private JButton Download;
    private JButton Settings;
    private JPanel Main_Panel;
    private JPanel Buttons;

    public GUIofPrj() {
        JFrame jfrm = new JFrame("Athanatos");
        jfrm.setIconImage(new ImageIcon("src/icon2.ico").getImage());
        jfrm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jfrm.getContentPane().add(Main_Panel);
        jfrm.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("icon1.png")));

        BtnInitialize(Upload);
        BtnInitialize(Download);
        BtnInitialize(Settings);

       // Buttons.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
        Main_Panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));

        new ParamConfiguration().load();
        jfrm.setBounds(ParamConfiguration.posXMainWindow,
                ParamConfiguration.posYMainWindow,
                ParamConfiguration.widthMainWindow,
                ParamConfiguration.heightMainWindow);

        JMenuBar menubar = new JMenuBar();
        JMenu help = new JMenu("Help");
        menubar.add(help);
        JMenuItem about = new JMenuItem("About");
        help.add(about);
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jfrm, "Thank you for chosen our soft to use. \n" +
                        "We are so sorry, that you have to see it. \n" +
                        "Our team consist of 5 people: \n" +
                        "Yury Kurlykov, \n" +
                        "Boris Timofeenko, \n" +
                        "Diana Rusak, \n" +
                        "Bogdan Kalnysh, \n" +
                        "Glushkov Vladimir.");
            }
        });


        jfrm.setJMenuBar(menubar);
        jfrm.setVisible(true);

        Upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fopen = new JFileChooser();
                fopen.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fopen.setMultiSelectionEnabled(false);
                int ret = fopen.showDialog(null, "File Selection");
                if(ret == JFileChooser.APPROVE_OPTION){
                        BlockchainAPI.uploadFile(fopen.getSelectedFile().getAbsolutePath());
                }
            }
        });

        Download.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fopen = new JFileChooser();
                fopen.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fopen.setMultiSelectionEnabled(false);
                int ret = fopen.showDialog(null, "File Selection");
                if(ret == JFileChooser.APPROVE_OPTION){
                    BlockchainAPI.downloadFile(new KeyFile(fopen.getSelectedFile().getAbsolutePath()));
                }

            }
        });

        Settings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIofSet(Settings);
            }
        });

        jfrm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ParamConfiguration.saveBounds(jfrm);
                System.exit(0);
            }
        });

    }
    private static void BtnInitialize(JButton btn){
        btn.setBorder(new EmptyBorder(0, 0, 0, 0));
        btn.addMouseListener(new MouseEvents());
    }
}
