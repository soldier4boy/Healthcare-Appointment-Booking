package com.healthcare.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIEffects {
    public static void applyHoverEffect(JButton button) {
        Color original = button.getBackground();
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(original.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(original);
            }
        });
    }
}
