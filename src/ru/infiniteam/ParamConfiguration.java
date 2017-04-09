package ru.infiniteam;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class ParamConfiguration {
    public static int posXMainWindow; //Upper left X
    public static int posYMainWindow; //Upper left Y
    public static int widthMainWindow;//width of frame
    public static int heightMainWindow;//height of frame
    public static String serverIP;
    public static String pathToVault;

    void set(){}
    public  void load() {
        Preferences prefs = Preferences.userRoot();
        posXMainWindow = prefs.getInt("posXMainWindow", 0);
        posYMainWindow = prefs.getInt("posYMainWindow", 0);
        widthMainWindow = prefs.getInt("widthMainWindow", 240);
        heightMainWindow = prefs.getInt("heightMainWindow", 400);
        serverIP = prefs.get("serverIP", "10.193.57.253");
        pathToVault = prefs.get("pathToVault", "");

        set();
    }
    public static void saveBounds(JFrame frame) {
        Rectangle frameBounds = frame.getBounds();
        Preferences prefs = Preferences.userRoot();
        prefs.putInt("posXMainWindow", frameBounds.x);
        prefs.putInt("posYMainWindow", frameBounds.y);
        prefs.putInt("widthMainWindow", frameBounds.width);
        prefs.putInt("heightMainWindow", frameBounds.height);
    }
    public static void saveServerIP(String newIP) {
        Preferences prefs = Preferences.userRoot();
        prefs.put("serverIP", newIP);
    }
    public static void savePath(String newPath) {
        Preferences prefs = Preferences.userRoot();
        prefs.put("pathToVault", newPath);
    }
}
