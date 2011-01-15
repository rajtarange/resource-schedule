package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class AppointmentComponent extends JComponent implements MouseListener
{
    private static final Color Background = new Color(0x11b9f8);


    private final IAppointment _appointment;
    private ActionListener _actionListener = null;


    public AppointmentComponent(@NotNull IAppointment appointment)
    {
        _appointment = appointment;

        setOpaque(true);
        setPreferredSize(new Dimension(100, 100));
        addMouseListener(this);
    }


    @NotNull
    public IAppointment getAppointment()
    {
        return _appointment;
    }


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

        graphics.setColor(Color.black);
        graphics.drawRoundRect(insets.left, insets.top, insets.left+width-1, insets.top+height-1, arc, arc);

        FontMetrics fontMetrics = getFontMetrics(getFont());
        int fontHeight = fontMetrics.getHeight();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.drawString(_appointment.getTitle(), insets.left + 10, insets.top + fontHeight + 2);

        graphics.setRenderingHints(renderHints);
        graphics.setColor(oldColor);
    }
}
