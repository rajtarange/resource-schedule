package com.thirdnf.ResourceScheduler.components;

import com.thirdnf.ResourceScheduler.IAppointment;
import org.jetbrains.annotations.NotNull;

import java.awt.*;


/**
 * Instances of this class will be the appointment components which are actually drawn on the panel.
 * The eventual goal is to have these drawn similar to the Google calendar appointments and allow them
 * to expand and move by drag and drop.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class BasicAppointmentComponent extends AbstractAppointmentComponent
{
    /**
     * Constructor given an appointment to wrap.
     *
     * @param appointment (not null) The appointment to wrap.
     */
    public BasicAppointmentComponent(@NotNull IAppointment appointment)
    {
        super(appointment);

        // The preferred size is pretty much just ignored for right now.
        setPreferredSize(new Dimension(100, 100));
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

        graphics.setColor(getBackground());
        graphics.fillRoundRect(insets.left, insets.top, insets.left+width-1, insets.top+height-1, arc, arc);

        graphics.setColor(Color.gray);
        graphics.drawRoundRect(insets.left, insets.top, insets.left+width-1, insets.top+height-1, arc, arc);

        FontMetrics fontMetrics = getFontMetrics(getFont());
        int fontHeight = fontMetrics.getHeight();

        graphics.setColor(Color.black);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.drawString(_appointment.getTitle(), insets.left + 10, insets.top + fontHeight + 2);

        graphics.setRenderingHints(renderHints);
        graphics.setColor(oldColor);
    }
}
