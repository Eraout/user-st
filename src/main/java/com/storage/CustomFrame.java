package com.storage;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CustomFrame {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");  // Metal L&F
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");  // Nimbus L&F
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

