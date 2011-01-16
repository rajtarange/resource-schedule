package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.*;


/**
 * Panel to show a given day.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class DaySchedule extends JPanel
{
    private Map<AppointmentComponent, Time> _appointmentMap = new HashMap<AppointmentComponent, Time>();

    private ActionListener _actionListener = null;

    private IScheduleModel _model = null;

    // The inner panel holds the real days.
    private JPanel _innerPanel;

    private final JLabel _currentDateLabel;

    private int _nextResource = 0;


    public DaySchedule()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        _currentDateLabel = new JLabel("Today's Date");
        add(_currentDateLabel);
        setBackground(Color.white);
        setOpaque(true);
    }


    public void setModel(@NotNull IScheduleModel model)
    {
        _model = model;

        // TODO - Tie into the models notifications about changes to appointments.
    }


    public void showDate(@NotNull Date date)
    {
        // Check if we area already showing a date.  If so, remove it
        if (_innerPanel != null) {
            remove(_innerPanel);
        }

        _currentDateLabel.setText(date.toString());

        // The model knows the begin and end times of the day for this date
        Time startTime = _model.getStartTime(date);
        Time endTime = _model.getEndTime(date);

        _innerPanel = new InnerPanel(startTime, endTime);
        add(_innerPanel);

        _model.visitAppointments(new IAppointmentVisitor()
        {
            @Override
            public boolean visitAppointment(@NotNull IAppointment appointment)
            {
                addAppointment(appointment);
                return true;
            }
        }, date);


        _model.visitResources(new IResourceVisitor()
        {
            @Override
            public boolean visitResource(@NotNull IResource resource)
            {
                addResource(resource);
                return true;
            }
        }, date);
    }



    private void addResource(@NotNull IResource resource)
    {
        ResourceComponent resourceComponent = new ResourceComponent(resource);

        _innerPanel.add(resourceComponent, new Integer(_nextResource ++));
    }


    private void addAppointment(@NotNull IAppointment appointment)
    {
        AppointmentComponent appointmentComponent = new AppointmentComponent(appointment);
        appointmentComponent.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (_actionListener != null) { _actionListener.actionPerformed(e); }
            }
        });

        IResource resource = appointment.getResource();

        // TODO - determine which column corresponds to which resource.

        _innerPanel.add(appointmentComponent, new Integer(1));
    }


    /**
     * Add an action listener to be notified when a user clicks on a an appointment.
     * The source of the actionListener will be the appointment which was clicked on and not
     * this panel.
     *
     * @param actionListener (not null) the action listener to be notified on an appointment click.
     */
    public void setActionListener(@NotNull ActionListener actionListener)
    {
        _actionListener = actionListener;
    }


    private static class InnerPanel extends JPanel
    {
        private final Time _startTime;
        private final Time _endTime;


        InnerPanel(@NotNull Time startTime, @NotNull Time endTime)
        {
            _startTime = startTime;
            _endTime   = endTime;

            setLayout(new TimeLayout(100, 25, startTime, endTime, new Duration(0, 15, 0)));
            setBackground(Color.white);
            setOpaque(true);
            setBorder(BorderFactory.createEtchedBorder());
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
            Duration increments = layout.getIncrements();

            for (int i=0; i<columns; ++i) {
                int x = leftHeader + (int)(i*columnWidth);
                graphics.drawLine(x, insets.top, x, insets.top+height);
            }


            Time time = _startTime;
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
                        // We want to draw hour markers and right justify them.
                        String timeString = time.toString();

                        Rectangle2D rect = fontMetrics.getStringBounds(timeString, graphics);
                        int stringX = (int)(leftHeader - rect.getWidth() - 10);

                        graphics.drawString(time.toString(), stringX, y + fontHeight);
                        graphics.setColor(Color.lightGray);
                    }
                }
                time = time.add(increments);
            }

            graphics.setColor(oldColor);
        }
    }
}