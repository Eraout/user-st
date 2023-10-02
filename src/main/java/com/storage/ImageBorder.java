package com.storage;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.border.AbstractBorder;

public class ImageBorder extends AbstractBorder {
    private BufferedImage image;

    public ImageBorder(BufferedImage image) {
        this.image = image;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(image.getHeight() / 2, image.getWidth() / 2, image.getHeight() / 2, image.getWidth() / 2);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = image.getHeight() / 2;
        return insets;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawImage(image, x, y, null);
    }
}
