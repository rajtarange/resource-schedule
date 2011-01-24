package com.thirdnf.ResourceScheduler;

import com.thirdnf.ResourceScheduler.components.*;
import org.jetbrains.annotations.NotNull;
import org.joda.time.*;

import javax.swing.*;
import java.awt.*;
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
public class DaySchedule extends JPanel implements Printable, ResourceChangeListener, AppointmentChangeListener
{
    private ActionListener _actionListener = null;

    private ScheduleModel _model = null;

    // The inner panel holds the real days.
    private JPanel _innerPanel;

    private Map<Resource, Integer> _columnMap = new HashMap<Resource, Integer>();

    // The date which is currently being shown
    private LocalDate _currentDate;

    // The Label which we can update
    private final JLabel _currentDateLabel;

    private int _nextResource = 0;

    private ComponentFactory _componentFactory;


    public DaySchedule()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        _currentDateLabel = new JLabel("Today's Date");
        add(_currentDateLabel);
        setBackground(Color.white);
        setOpaque(true);

        _componentFactory = new ComponentFactory();
    }


    /**
     * Set the model that this panel should use.
     *
     * @param model (not null) The model that should be used.
     */
    public void setModel(@NotNull ScheduleModel model)
    {
        _model = model;

        // Tie into the model's notification about resource and appointment changes
        _model.addResourceChangeListener(this);
        _model.addAppointmentChangeListener(this);
    }


    /**
     * Set the component factory for drawing the components.  By default it draws basic
     * components.
     *
     * @param componentFactory (not null) New component factory to use when drawing appointments
     * and resources.
     */
    public void setComponentFactory(@NotNull ComponentFactory componentFactory)
    {
        _componentFactory = componentFactory;
    }


    /**
     * Set the date which is shown.  This will trigger a complete redraw of the panel.
     *
     * @param date (not null) Date to show.
     */
    public void setDate(@NotNull LocalDate date)
    {
        // Check if we area already showing a date.  If so, remove it
        if (_innerPanel != null) {
            remove(_innerPanel);
        }
        _columnMap.clear();
        _nextResource = 0;

        _currentDate = date;
        _currentDateLabel.setText(date.toString("EEEE - MMMM d, yyyy"));

        // The model knows the begin and end times of the day for this date
        LocalTime startTime = _model.getStartTime(date);
        LocalTime endTime = _model.getEndTime(date);

        _innerPanel = new InnerPanel(startTime, endTime);
        add(_innerPanel);

        _model.visitResources(new ResourceVisitor()
        {
            @Override
            public boolean visitResource(@NotNull Resource resource)
            {
                addResource(resource);
                return true;
            }
        }, date);

        _model.visitAppointments(new AppointmentVisitor()
        {
            @Override
            public boolean visitAppointment(@NotNull Appointment appointment)
            {
                addAppointment(appointment);
                return true;
            }
        }, date);


        // Trigger a repaint to show the new information.
        revalidate();
        repaint();
    }


    /**
     * Add a resource to the panel by wrapping it in a component and then adding it to the
     * layout.
     * @param resource (not null) The resource to add.
     */
    private void addResource(@NotNull Resource resource)
    {
        // Get the next available column.
        int column = _nextResource ++;

        // Wrap the resource in a component
        AbstractResourceComponent resourceComponent = _componentFactory.makeResourceComponent(resource);

        _columnMap.put(resource, column);

        _innerPanel.add(resourceComponent, new Integer(column));
    }


    private void removeResource(@NotNull Resource resource)
    {
        // We have to find the one to remove ... we could use a map but I don't think there are going to be a
        //  lot so this is more straight forward
        int count = _innerPanel.getComponentCount();

        for (int index=0; index<count; ++index) {
            Component component = _innerPanel.getComponent(count);
            if (component instanceof AbstractResourceComponent) {
                AbstractResourceComponent resourceComponent = (AbstractResourceComponent) component;
                if (resourceComponent.getResource().equals(resource)) {
                    // This is the guy to remove
                    _innerPanel.remove(resourceComponent);
                    return;
                }
            }
        }
    }


    /**
     * Private method to add an appointment to the panel.  This takes the appointment given and wraps it
     * in a component and adds the component to the inner panel.
     *
     * @param appointment (not null) Appointment to add.
     */
    private void addAppointment(@NotNull Appointment appointment)
    {
        AbstractAppointmentComponent appointmentComponent = _componentFactory.makeAppointmentComponent(appointment);

        Resource resource = appointment.getResource();
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
     * Add an action listener to be notified when a user clicks anywhere in the panel which is not
     * on an appointment or resource.  The values sent are the "time" location of the event and
     * the resource column.  From this the listener could pull up a dialog box and ask to add
     * an appointment if they wanted to.
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


    @Override
    public void resourceAdded(@NotNull Resource resource, @NotNull LocalDate date)
    {
        // This is a day view so we only care if the day matches
        if (! date.equals(_currentDate)) { return; }

        // Add it to the panel
        addResource(resource);

        // Force the layout to redraw
        revalidate();

        // We have added a column so we need to repaint our background as well
        repaint();
    }


    @Override
    public void resourceRemoved(@NotNull Resource resource, @NotNull LocalDate date)
    {
        // This is a day view so we only care if the day matches
        if (! date.equals(_currentDate)) { return; }

        // First remove the component
        removeResource(resource);

        // Force the layout to redraw
        revalidate();

        // We have removed a column so we need to repaint our background as well
        repaint();
    }


    @Override
    public void resourceUpdated(@NotNull Resource resource)
    {
        // I think this is just a repaint
        repaint();
    }


    @Override
    public void appointmentAdded(@NotNull Appointment appointment)
    {
        LocalDate date = appointment.getDateTime().toLocalDate();

        // This is a day view so we only care if the day matches
        if (! date.equals(_currentDate)) { return; }

        // TODO - Handle this in the existing frame without forcing a redraw of everything

        // For now we are going to cheat and just reload the date
        setDate(_currentDate);
    }


    @Override
    public void appointmentRemoved(@NotNull Appointment appointment)
    {
        LocalDate date = appointment.getDateTime().toLocalDate();

        // This is a day view so we only care if the day matches
        if (! date.equals(_currentDate)) { return; }

        // TODO - Handle this in the existing frame without forcing a redraw of everything

        // For now we are going to cheat and just reload the date
        setDate(_currentDate);
    }


    @Override
    public void appointmentUpdated(@NotNull Appointment appointment)
    {
        // The appointment may have moved so re-layout
        revalidate();

        // I don't think we need a repaint.
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
