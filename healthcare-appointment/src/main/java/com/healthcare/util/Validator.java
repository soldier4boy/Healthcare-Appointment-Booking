package com.healthcare.util;

import javax.swing.*;
import java.awt.*;

public class Validator {
    public static boolean isEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static void highlightIfEmpty(JTextField field) {
        if (isEmpty(field)) {
            field.setBackground(new Color(0xFFE6E6));
        } else {
            field.setBackground(Color.WHITE);
        }
    }
}
