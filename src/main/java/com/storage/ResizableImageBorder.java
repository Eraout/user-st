package com.storage;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ResizableImageBorder extends AbstractBorder {
    private BufferedImage image;
    private int width;
    private int height;

    public ResizableImageBorder(BufferedImage image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawImage(image, x, y, this.width, this.height, c);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = insets.top = insets.bottom = 0;
        return insets;
    }
}
