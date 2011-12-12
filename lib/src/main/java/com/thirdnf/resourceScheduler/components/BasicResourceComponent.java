package com.thirdnf.resourceScheduler.components;

import com.thirdnf.resourceScheduler.Resource;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;


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
    /**
     * Constructor given a resource to wrap.  This just saves off the resource and sets
     *  a default background color of light gray.  To change the background color extend the
     *  ComponentFactory and make your own components.
     *
     * @param resource (not null) Resource to wrap.
     */
    public BasicResourceComponent(@NotNull Resource resource)
    {
        super(resource);

        // This is mostly ignored.
        setPreferredSize(new Dimension(100, 100));

        // The beveled one doesn't look as good.
        setBorder(BorderFactory.createEtchedBorder());

        // Default gray background
        setBackground(Color.lightGray);
    }


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Save off the old color so we can restore it at the end.
        Color oldColor = g.getColor();

        Graphics2D graphics = (Graphics2D)g;
        RenderingHints renderHints = graphics.getRenderingHints();
        Insets insets = getInsets();

        int width  = getWidth()  - insets.left - insets.right;
        int height = getHeight() - insets.top  - insets.bottom;

        g.setColor(getBackground());
        graphics.fillRect(insets.left, insets.top, insets.left + width - 1, insets.top + height - 1);

        graphics.setColor(Color.black);

        // We want to locate the text in the center/center

        FontMetrics fontMetrics = getFontMetrics(getFont());

        double stringWidth  = fontMetrics.stringWidth(_resource.getTitle());

        // For the height we need to subtract the descent to get to the baseline and not the bottom of the font
        //  as the drawing occurs at the baseline.
        double stringHeight = fontMetrics.getHeight() - fontMetrics.getDescent();
        double stringX      = ((double)width - stringWidth)/2.0;
        double stringY      = ((double)height - stringHeight)/2.0 + stringHeight;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.drawString(_resource.getTitle(), insets.left + (int)stringX, insets.top + (int)stringY);

        graphics.setRenderingHints(renderHints);
        graphics.setColor(oldColor);
    }


    @Override
    public void print(@NotNull Graphics2D graphics, Rectangle area)
    {
        Color oldColor = graphics.getColor();

        graphics.setColor(getBackground());
        graphics.fillRect(area.x, area.y, area.width, area.height);

        graphics.setColor(oldColor);
    }
}
