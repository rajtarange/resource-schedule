package com.thirdnf.ResourceScheduler;

import com.thirdnf.ResourceScheduler.components.AppointmentComponent;
import com.thirdnf.ResourceScheduler.components.ResourceComponent;
import org.jetbrains.annotations.NotNull;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.*;


/**
 * Panel to show a given day.
 *
 * I'm still working on exactly where the api line here should be drawn between the panel and the layout, but
 * my thinking is that the panel knows of the model but the layout does not.  The layout only knows of its
 * size and the components it has been asked to draw.  I realize that seems sort of obvious, but hindsight is
 * 20/20.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class DaySchedule extends JPanel implements Printable
{
    private Map<AppointmentComponent, LocalTime> _appointmentMap = new HashMap<AppointmentComponent, LocalTime>();

    private ActionListener _actionListener = null;

    private IScheduleModel _model = null;

    // The inner panel holds the real days.
    private JPanel _innerPanel;

    private Map<IResource, Integer> _columnMap = new HashMap<IResource, Integer>();

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


    public void showDate(@NotNull LocalDate date)
    {
        // Check if we area already showing a date.  If so, remove it
        if (_innerPanel != null) {
            remove(_innerPanel);
        }

        _currentDateLabel.setText(date.toString("EEEE - MMMM d, yyyy"));

        // The model knows the begin and end times of the day for this date
        LocalTime startTime = _model.getStartTime(date);
        LocalTime endTime = _model.getEndTime(date);

        _innerPanel = new InnerPanel(startTime, endTime);
        add(_innerPanel);

        _model.visitResources(new IResourceVisitor()
        {
            @Override
            public boolean visitResource(@NotNull IResource resource)
            {
                addResource(resource);
                return true;
            }
        }, date);

        _model.visitAppointments(new IAppointmentVisitor()
        {
            @Override
            public boolean visitAppointment(@NotNull IAppointment appointment)
            {
                addAppointment(appointment);
                return true;
            }
        }, date);
    }



    private void addResource(@NotNull IResource resource)
    {
        // Get the next available column.
        int column = _nextResource ++;

        // Wrap the resource in a component
        ResourceComponent resourceComponent = new ResourceComponent(resource);

        _columnMap.put(resource, column);

        _innerPanel.add(resourceComponent, new Integer(column));
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
        Integer column = _columnMap.get(resource);
        if (column == null) {
            // TODO - deal with unassigned resource.
            // This is an unassigned resource for this day
            System.out.println("Deal with unassigned resource.");

            column = 1;
        }


        _innerPanel.add(appointmentComponent, column);
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


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException
    {
        // TODO - Implement print

        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        // User (0,0) is typically outside the imageable area, so we must
        // translate by the X and Y values in the PageFormat to avoid clipping
        //
        Graphics2D g2d = (Graphics2D)graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        _innerPanel.paintComponents(g2d);

        // tell the caller that this page is part of the printed document
        return PAGE_EXISTS;
    }


    /**
     * This is the inner panel that actually holds the components.  This panel can be removed and replaced
     * as the date changes or as otherwise needed.
     */
    private static class InnerPanel extends JPanel
    {
        private final LocalTime _startTime;
        private final LocalTime _endTime;
        private final Duration  _increments;


        /**
         * Create the inner panel with a start and end times as given.  These start and end times are the
         * total span that should be shown for the day.  There is no way to specify a day which spans midnight,
         * this library is simply not being designed to handle that.
         *
         * @param startTime (not null) Time of the day to start.
         * @param endTime (not null) Time of the day to end.
         */
        InnerPanel(@NotNull LocalTime startTime, @NotNull LocalTime endTime)
        {
            _startTime  = startTime;
            _endTime    = endTime;
            _increments = Duration.standardMinutes(15);

            setLayout(new SchedulerLayout(startTime, endTime));
            setBackground(Color.white);
            setOpaque(true);
            setBorder(BorderFactory.createEtchedBorder());
        }


        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            SchedulerLayout layout = (SchedulerLayout)getLayout();

            Graphics2D graphics = (Graphics2D)g;

            Insets insets = getInsets();

            int width = getWidth() - insets.left - insets.right;
            int height = getHeight() - insets.top - insets.bottom;

            FontMetrics fontMetrics = getFontMetrics(getFont());
            int fontHeight = fontMetrics.getHeight();

            Color oldColor = graphics.getColor();

            graphics.setColor(Color.lightGray);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int columns    = layout.getColumnCount();
            int leftHeader = layout.getX(0);

            for (int i=0; i<columns; ++i) {
                int x = layout.getX(i);
                graphics.drawLine(x, insets.top, x, insets.top+height);
            }

            Period period = _increments.toPeriod();


            for (LocalTime time = _startTime; time.compareTo(_endTime) < 0; time = time.plus(period)) {
                Integer y = layout.getY(time);
                if (y != null) {
                    boolean onTheHour = time.getMinuteOfHour() == 0;

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
                        String timeString = time.toString("h:mm a");

                        Rectangle2D rect = fontMetrics.getStringBounds(timeString, graphics);
                        int stringX = (int)(leftHeader - rect.getWidth() - 10);

                        graphics.drawString(timeString, stringX, y + fontHeight);
                        graphics.setColor(Color.lightGray);
                    }
                }
            }

            graphics.setColor(oldColor);
        }
    }
}