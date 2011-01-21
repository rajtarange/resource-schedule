package com.thirdnf.ResourceScheduler.components;

import com.thirdnf.ResourceScheduler.IResource;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.geom.Rectangle2D;


public class BasicResourceComponent extends AbstractResourceComponent
{
    public BasicResourceComponent(@NotNull IResource resource)
    {
        super(resource);

        setBackground(_resource.getColor());
        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Color oldColor = g.getColor();

        Graphics2D graphics = (Graphics2D)g;

        RenderingHints renderHints = graphics.getRenderingHints();

        Insets insets = getInsets();

        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;

        g.setColor(getBackground());
        graphics.fillRect(insets.left, insets.top, insets.left + width - 1, insets.top + height - 1);

        graphics.setColor(Color.black);

        // We want to locate the text in the center/center

        FontMetrics fontMetrics = getFontMetrics(getFont());

        Rectangle2D rect = fontMetrics.getStringBounds(_resource.getTitle(), graphics);
        double stringWidth  = rect.getWidth();
        double stringHeight = rect.getHeight();
        double stringX      = ((double)width - stringWidth)/2.0;
        double stringY      = ((double)height - stringHeight)/2.0 + stringHeight;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.drawString(_resource.getTitle(), insets.left + (int)stringX, insets.top + (int)stringY);

        graphics.setRenderingHints(renderHints);
        graphics.setColor(oldColor);
    }
}
