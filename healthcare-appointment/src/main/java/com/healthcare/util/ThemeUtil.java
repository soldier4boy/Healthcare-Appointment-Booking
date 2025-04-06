package com.healthcare.util;

import javax.swing.*;
import java.awt.*;

public class ThemeUtil {
    public static void applyTheme() {
        UIManager.put("Button.background", new Color(0x1976D2));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("TextField.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("TextArea.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("Table.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("TableHeader.font", new Font("SansSerif", Font.BOLD, 14));
        UIManager.put("OptionPane.messageForeground", Color.BLACK);
    }
}
