package com.thirdnf.ResourceScheduler.components;

import com.thirdnf.ResourceScheduler.Resource;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;


/**
 * This is a very basic implementation of the AbstractResourceComponent.  This component does not
 * draw any color or have any mouse notifications.  If you want to change the rendering or
 * add mouse notification you need to create your own ComponentFactory and extend AbstractResourceComponent.
 * You can also just extend this one if you only want to modify it slightly.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class BasicResourceComponent extends AbstractResourceComponent
{
    public BasicResourceComponent(@NotNull Resource resource)
    {
        super(resource);

        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createEtchedBorder());
        setBackground(Color.lightGray);
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
