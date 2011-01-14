package com.thirdnf.ResourceScheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;


/**
 * Panel to show a given day.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class DaySchedule extends JPanel implements ComponentListener
{
    private Map<AppointmentComponent, Time> _appointmentMap = new HashMap<AppointmentComponent, Time>();

    private Map<Time, Integer> _timeMap = new HashMap<Time, Integer>();

    private int _columns = 3;
    private double _columnWidth = 0;
    private int _columnHeader = 100;

    private int _minuteIncrements = 15;
    private int _hours = 10;
    private int _rows = _hours * 60 / _minuteIncrements;
    private double _rowHeight = 0;
    private int _rowHeader = 100;

    private boolean _recompute = false;

    private AppointmentComponent _appointmentComponent = new AppointmentComponent("Bob");


    public DaySchedule()
    {
        setLayout(null);
        setBackground(Color.white);

        addComponentListener(this);

//        setOpaque(true);

        JButton b1 = new JButton("one");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleButtonPressed(e);
            }
        });

        JButton b2 = new JButton("two");

        set(_appointmentComponent, new Time(10, 0, 0));

        add(b1);
        add(b2);
        add(_appointmentComponent);

        Insets insets = getInsets();

        Dimension size = b1.getPreferredSize();
        b1.setBounds(25 + insets.left, 5 + insets.top,
                size.width, size.height);
        size = b2.getPreferredSize();
        b2.setBounds(55 + insets.left, 40 + insets.top,
                size.width, size.height);
    }


    private void set(AppointmentComponent appointment, Time time)
    {
        _appointmentMap.put(appointment, time);


//        Insets insets = getInsets();
//        Dimension size = appointment.getPreferredSize();
//
//
//
//        appointment.setBounds(150 + insets.left, 15 + insets.top,
//                size.width + 50, size.height + 20);
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

        if (_recompute) { recompute(); }

        if (_columnWidth < 1.0 || _rowHeight < 1.0 || _timeMap.isEmpty()) { return; }

        Graphics2D graphics = (Graphics2D)g;

        Insets insets = getInsets();

        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;

        FontMetrics fontMetrics = getFontMetrics(getFont());
        int fontHeight = fontMetrics.getHeight();

        Color oldColor = graphics.getColor();

        graphics.setColor(Color.lightGray);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i=0; i<_columns; ++i) {
            int x = _columnHeader + (int)(i*_columnWidth);
            graphics.drawLine(x, insets.top, x, insets.top+height);
        }


        Time time = new Time(8, 0, 0);
        for (int i=0; i< _rows; ++i) {
            Integer y = _timeMap.get(time);
            if (y != null) {
                boolean onTheHour = time.isOnTheHour();

                if (onTheHour) {
                    graphics.setColor(Color.black);
                }

                graphics.drawLine(insets.left, y, insets.left + width, y);

                if (onTheHour) {
                    graphics.drawString(time.toString(), 10, y + fontHeight);
                    graphics.setColor(Color.lightGray);
                }
            }
            time = time.addMinutes(_minuteIncrements);
        }

        graphics.setColor(oldColor);
    }


    public void componentResized(ComponentEvent e)
    {
        _recompute = true;
        repaint();

        //        Insets insets = getInsets();
//        Dimension size = appointment.getPreferredSize();
//
//
//
//        appointment.setBounds(150 + insets.left, 15 + insets.top,
//                size.width + 50, size.height + 20);
    }


    private void recompute()
    {
        System.out.println("Recomputing");

        Insets insets = getInsets();

        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;


        // Draw rows with hour markers
        _rowHeight = (double)(height - _rowHeader) / (double)_rows;
        _columnWidth = (double)(width - _columnHeader) / (double) _columns;


        Time time = new Time(8, 0, 0);
        _timeMap.clear();
        for (int i=0; i< _rows; ++i) {
            int y = _rowHeader + (int) (i* _rowHeight);

            _timeMap.put(time, y);

            time = time.addMinutes(_minuteIncrements);
        }

        _recompute = false;
    }


    public void componentMoved(ComponentEvent e) { }


    public void componentShown(ComponentEvent e) { }


    public void componentHidden(ComponentEvent e) { }
}
