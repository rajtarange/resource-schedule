package com.thirdnf.ResourceScheduler;

import com.thirdnf.ResourceScheduler.components.AppointmentComponent;
import com.thirdnf.ResourceScheduler.components.ResourceComponent;
import org.jetbrains.annotations.NotNull;
import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * SchedulerLayout is responsible for laying out the components on a time grid.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
@SuppressWarnings({"UnusedDeclaration"})
public class SchedulerLayout implements LayoutManager2
{
    // Location map telling us which column each appointment belongs to.
    private Map<Component, Integer> _columnMap = new HashMap<Component, Integer>();

    // Space to give to the top header.
    private int _topHeader = 25;
    private int _leftHeader = 100;

    private final LocalTime _startTime;

    private final long _totalSeconds;

    // X offset location, equal to the inset.left
    private int  _x;

    // Y offset location, equal to the inset.top
    private int  _y;

    // Total number of columns, equal to number of resources + unassigned optional
    private int _columns;

    // Our scaling factor assuming 1:1 means one pixel == one second
    private float _scale;

    private float _columnWidth = 0;


    /**
     * Constructor.  So far the only thing that must be provided is the increments to use for the layout.
     * I think changing this would require a nearly full re-layout.
     *
     * @param startTime (not null) Time of the day to start.
     * @param endTime (not null) Time of the day to end.
     */
    public SchedulerLayout(@NotNull LocalTime startTime, @NotNull LocalTime endTime)
    {
        _startTime = startTime;

        // Total number of seconds we are representing
        _totalSeconds = Period.fieldDifference(startTime, endTime).toStandardDuration().getStandardSeconds();

        // Start with a 1:1 scale
        _scale = 1.0f;
    }


    /**
     * Set the top header size to the given number of pixels.
     * @param pixels Number of pixels for the top header
     */
    public void setTopHeader(int pixels)
    {
        _topHeader = pixels;

        // TODO - force a recalculate or re-layout
    }


    /**
     * Set the left header size to the given number of pixels.
     *
     * @param pixels Number of pixels for the left header
     */
    public void setLeftHeader(int pixels)
    {
        _leftHeader = pixels;

        // TODO - force a recalculate or re-layout
    }


    @Override
    public void addLayoutComponent(String name, Component comp)
    {
        // Not used by this class
    }


    @Override
    public void removeLayoutComponent(Component comp)
    {
        // Not used by this class
    }


    @Override
    public Dimension preferredLayoutSize(Container parent)
    {
        // TODO - Calculate this for real
        return new Dimension(400, 300);
    }


    @Override
    public Dimension minimumLayoutSize(Container parent)
    {
        // TODO - Calculate this off the minimum height for the fonts and some minimum width
        return new Dimension(300, 300);
    }


    @Override
    public void layoutContainer(Container target)
    {
        synchronized (target.getTreeLock()) {
            recomputeLayout(target);

            int componentCount = target.getComponentCount();

            for (int i = 0 ; i < componentCount ; ++i) {
                Component component = target.getComponent(i);
                if (! component.isVisible()) { continue; }

                if (component instanceof ResourceComponent) {
                    // These are placed at the top of their row

                    int column = _columnMap.get(component);

                    int x = _x + _leftHeader + (int)(_columnWidth * column);

                    int width = (int) _columnWidth;

                    // One rowHeight is one increment
                    int height = _topHeader;

                    component.setBounds(x, _y, width, height);
                }
                else if (component instanceof AppointmentComponent) {
                    AppointmentComponent appointmentComponent = (AppointmentComponent)component;

                    IAppointment appointment = appointmentComponent.getAppointment();
                    LocalTime time = appointment.getTime();
                    Duration duration = appointment.getDuration();

                    int column = _columnMap.get(component);

                    int y = getY(time);
                    int x = getX(column);

                    int width = (int) _columnWidth - 15;

                    // One rowHeight is one increment
                    int height = (int) Math.ceil(_scale * (float)duration.getStandardSeconds());

                    component.setBounds(x, y, width, height);
                }
                else {
                    System.out.println("Don't know how to layout component: " + component);
                }
            }
        }
    }


    /**
     * Internal method to recompute how wide the columns and rows should be.  This is usually only called after
     * a panel resize or on startup.
     *
     * @param target (not null) the containing panel.
     */
    private void recomputeLayout(@NotNull Container target)
    {
        // Dynamically determine how many columns we are going to need.
        _columns = 0;
        Collection<Integer> values =  _columnMap.values();
        for (Integer column : values) {
            _columns = Math.max(_columns, column);
        }
        // The number of columns is one greater than the last column index.
        _columns +=1;

        Insets insets = target.getInsets();
        _x = insets.left;
        _y = insets.top;

        int width = target.getWidth() - insets.left - insets.right;
        int height = target.getHeight() - insets.top - insets.bottom;

        _scale       = (float)(height - _topHeader) / (float)_totalSeconds;
        _columnWidth = (float)(width - _leftHeader) / (float) _columns;
    }


    /**
     * Public method to ask the layout where a given time location should be placed.  I'm not 100% sure if this
     * breaks the abstraction of the layout by exposing this.  To access this the caller needs to get the
     * layout and then cast it to a ScheduleLayout.  Doesn't seem ideal.
     *
     * @param time (not null) Time in question to ask for the y location of.
     * @return The y location on the current panel for the given time.
     */
    public int getY(@NotNull LocalTime time)
    {
        // Get the seconds which have passed from the start time to the time they are asking about.
        long seconds = Period.fieldDifference(_startTime, time).toStandardDuration().getStandardSeconds();

        return _y + _topHeader + (int)(_scale * (float)seconds);
    }


    /**
     * Get the x location in the current panel for the given column id.  This gives the x location of the
     * columns left hand side.  Use (column + 1) to find the right hand location.
     *
     * @param column The column to get the x value for.
     * @return The x location of the column start position.
     */
    public int getX(int column)
    {
        return _x + _leftHeader + (int)(column*_columnWidth);
    }


    /**
     * Get the number of columns that the layout has determined it needs to draw.  This will be equal to the
     * number of resources available on a given day plus a possible extra column for unassigned appointments,
     * or appointments which are assigned to resources which are not available on that day.
     *
     * @return Number of columns in the layout.
     */
    public int getColumnCount()
    {
        return _columns;
    }


    @Override
    public void addLayoutComponent(Component comp, Object constraints)
    {
        if (! (constraints instanceof Integer)) {
            throw new IllegalArgumentException("Constraint must be a Location");
        }

        _columnMap.put(comp, (Integer)constraints);
    }


    @Override
    public Dimension maximumLayoutSize(Container target)
    {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }


    @Override
    public float getLayoutAlignmentX(Container target)
    {
        return 0.5f;
    }


    @Override
    public float getLayoutAlignmentY(Container target)
    {
        return 0.5f;
    }


    @Override
    public void invalidateLayout(Container target)
    {
    }
}

