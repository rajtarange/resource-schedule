package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


/**
 * Panel to show a given day.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class DaySchedule extends JPanel
{
    private Map<AppointmentComponent, Time> _appointmentMap = new HashMap<AppointmentComponent, Time>();

    private Set<ActionListener> _actionListeners = new HashSet<ActionListener>();

    private IScheduleModel _model = null;


    public DaySchedule()
    {
        TimeLayout layout = new TimeLayout();
        setLayout(layout);
        setBackground(Color.white);
        setOpaque(true);

        AppointmentComponent appointment1 = new AppointmentComponent("Fred");
        add(appointment1, new Location(new Time(10, 0, 0), 1));

        AppointmentComponent appointment2 = new AppointmentComponent("George");
        add(appointment2, new Location(new Time(13, 0, 0), 0));
    }


    public void setModel(@NotNull IScheduleModel model)
    {
        _model = model;

        _model.visitAppointments(new IAppointmentVisitor()
        {
            @Override
            public boolean visitAppointment(@NotNull IAppointment appointment)
            {
                System.out.println("Visting appt " + appointment);

                return true;
            }
        }, new Date());
    }



    public void addActionListener(@NotNull ActionListener actionListener)
    {
        _actionListeners.add(actionListener);
    }


    private void handleButtonPressed(ActionEvent e)
    {
        JButton button = (JButton)e.getSource();

        Rectangle r = button.getBounds();
        Point p = r.getLocation();
        p.x += 5;

        button.setLocation(p);
    }



    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        TimeLayout layout = (TimeLayout)getLayout();

        Graphics2D graphics = (Graphics2D)g;

        Insets insets = getInsets();

        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;

        FontMetrics fontMetrics = getFontMetrics(getFont());
        int fontHeight = fontMetrics.getHeight();

        Color oldColor = graphics.getColor();

        graphics.setColor(Color.lightGray);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int columns = layout.getColumns();
        int rows = layout.getRows();
        int leftHeader = layout.getLeftHeader();
        float columnWidth = layout.getColumnWidth();

        for (int i=0; i<columns; ++i) {
            int x = leftHeader + (int)(i*columnWidth);
            graphics.drawLine(x, insets.top, x, insets.top+height);
        }


        Time time = new Time(8, 0, 0);
        for (int i=0; i< rows; ++i) {
            Integer y = layout.getY(time);
            if (y != null) {
                boolean onTheHour = time.isOnTheHour();

                int x = insets.left;
                if (onTheHour) {
                    graphics.setColor(Color.black);
                }
                else {
                    x = leftHeader;
                }

                graphics.drawLine(x, y, insets.left + width, y);

                if (onTheHour) {
                    graphics.drawString(time.toString(), 10, y + fontHeight);
                    graphics.setColor(Color.lightGray);
                }
            }
            time = time.addMinutes(15);
        }

        graphics.setColor(oldColor);
    }
}
