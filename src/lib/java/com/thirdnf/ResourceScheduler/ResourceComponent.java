package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;


public class ResourceComponent extends JComponent
{
    private final IResource _resource;


    public ResourceComponent(@NotNull IResource resource)
    {
        _resource = resource;


        setOpaque(true);
        setPreferredSize(new Dimension(100, 100));
//        addMouseListener(this);

    }


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D)g;

        Color oldColor = graphics.getColor();

        RenderingHints renderHints = graphics.getRenderingHints();

        Insets insets = getInsets();

        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;

        // Draw our border
        int arc = 10;

        graphics.setColor(Color.lightGray);
        graphics.fillRect(insets.left, insets.top, insets.left+width-1, insets.top+height-1);

        graphics.setColor(Color.black);
        graphics.drawRect(insets.left, insets.top, insets.left+width-1, insets.top+height-1);

        FontMetrics fontMetrics = getFontMetrics(getFont());
        int fontHeight = fontMetrics.getHeight();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.drawString(_resource.getTitle(), insets.left + 10, insets.top + fontHeight + 2);

        graphics.setRenderingHints(renderHints);
        graphics.setColor(oldColor);
    }
}
