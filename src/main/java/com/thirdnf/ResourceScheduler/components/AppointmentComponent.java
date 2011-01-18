package com.thirdnf.ResourceScheduler.components;

import com.thirdnf.ResourceScheduler.IAppointment;
import com.thirdnf.ResourceScheduler.ICategory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Instances of this class will be the appointment components which are actually drawn on the panel.
 * The eventual goal is to have these drawn similar to the Google calendar appointments and allow them
 * to expand and move by drag and drop.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class AppointmentComponent extends JComponent implements MouseListener
{
    // Background to draw the component if we don't have a color.  I haven't yet decided if all appointments
    //  must have a group and therefore must have a color.
    private static final Color Background = new Color(0x11b9f8);

    // The appointment this component is wrapping.
    private final IAppointment _appointment;

    // The single action listener which will get click events.  This is a single entity for now, but we
    //  could make this a list if there was a need for it.
    private ActionListener _actionListener = null;


    /**
     * Constructor given an appointment to wrap.
     *
     * @param appointment (not null) The appointment to wrap.
     */
    public AppointmentComponent(@NotNull IAppointment appointment)
    {
        _appointment = appointment;

        // The preferred size is pretty much just ignored for right now.
        setPreferredSize(new Dimension(100, 100));

        // Allow this instance to respond to mouse clicks.  I'm a bit uncomfortable with accessing 'this' at
        //  this point, but I think that is just an old C++ fear.
        addMouseListener(this);
    }


    /**
     * Get the appointment from the component.
     *
     * @return (not null) The appointment this component was wrapping.
     */
    @NotNull
    public IAppointment getAppointment()
    {
        return _appointment;
    }


    /**
     * Set the one and only action listener which will get called on mouse clicks.
     *
     * @param actionListener (not null) The action listener who cares about mouse clicks.
     */
    public void setActionListener(@NotNull ActionListener actionListener)
    {
        _actionListener = actionListener;
    }


    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (_actionListener != null) {
            ActionEvent actionEvent = new ActionEvent(_appointment, ActionEvent.ACTION_PERFORMED, _appointment.getTitle());
            _actionListener.actionPerformed(actionEvent);
        }
    }


    @Override
    public void mousePressed(MouseEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public void mouseReleased(MouseEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public void mouseEntered(MouseEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public void mouseExited(MouseEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
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

        ICategory category = _appointment.getCategory();
        Color color = Background;
        if (category != null) {
            color = category.getColor();
        }
        graphics.setColor(color);
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
