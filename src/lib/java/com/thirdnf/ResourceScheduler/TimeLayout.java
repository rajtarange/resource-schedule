package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * TimeLayout is responsible for laying out the components on a time grid.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class TimeLayout implements LayoutManager2
{
    // Location map telling us where everything is on the time grid
    private Map<Component, Integer> _columnMap = new HashMap<Component, Integer>();

    private Map<Time, Integer> _timeMap = new HashMap<Time, Integer>();

    private final int _topHeader;
    private final int _leftHeader;

    private final Duration _increments;
    private final Time _startTime;
    private final Time _endTime;

    private int _columns;
    private float _columnWidth = 0;
    private int _rows;
    private float _rowHeight = 0;


    /**
     * Constructor.  So far the only thing that must be provided is the increments to use for the layout.
     * I think changing this would require a nearly full re-layout.
     *
     * @param leftHeader Size to give the left hand header.
     * @param topHeader Size to give the top header
     * @param increments (not null) Increments for the layout
     */
    public TimeLayout(int leftHeader, int topHeader, @NotNull Time startTime, @NotNull Time endTime,
                      @NotNull Duration increments)
    {
        _startTime = startTime;
        _endTime = endTime;

        _leftHeader = leftHeader;
        _topHeader = topHeader;
        _increments = increments;
        _rows = (endTime.toSeconds() - startTime.toSeconds()) / _increments.toSeconds();
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
        return new Dimension(300, 300);
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
                    ResourceComponent resourceComponent = (ResourceComponent)component;

                    int column = _columnMap.get(component);

                    int y = 0;
                    int x = _leftHeader + (int)(_columnWidth * column);

                    int width = (int) _columnWidth;

                    // One rowHeight is one increment
                    int height = _topHeader;

                    component.setBounds(x, y, width, height);
                }
                else if (component instanceof AppointmentComponent) {
                    AppointmentComponent appointmentComponent = (AppointmentComponent)component;

                    IAppointment appointment = appointmentComponent.getAppointment();
                    Time time = appointment.getTime();
                    Duration duration = appointment.getDuration();

                    int column = _columnMap.get(component);

                    int y = _timeMap.get(time);
                    int x = _leftHeader + (int)(_columnWidth * column);

                    int width = (int) _columnWidth - 15;

                    // One rowHeight is one increment
                    int height = (int) Math.ceil(_rowHeight * duration.divide(_increments));

                    component.setBounds(x, y, width, height);
                }
                else {
                    System.out.println("Don't know how to layout component: " + component);
                }
            }
        }
    }


    private void recomputeLayout(Container target)
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

        int width = target.getWidth() - insets.left - insets.right;
        int height = target.getHeight() - insets.top - insets.bottom;

        _rowHeight = (float)(height - _topHeader) / (float)_rows;
        _columnWidth = (float)(width - _leftHeader) / (float) _columns;

        Time time = new Time(8, 0, 0);
        _timeMap.clear();
        for (int i=0; i< _rows; ++i) {
            int y = _topHeader + (int) (i* _rowHeight);

            _timeMap.put(time, y);

            time = time.add(_increments);
        }
    }


    public int getY(@NotNull Time time)
    {
        return _timeMap.get(time);
    }


    public int getColumns()
    {
        return _columns;
    }


    public int getRows()
    {
        return _rows;
    }

    public int getTopHeader()
    {
        return _topHeader;
    }

    public float getColumnWidth()
    {
        return _columnWidth;
    }

    public int getLeftHeader()
    {
        return _leftHeader;
    }


    @NotNull
    public Duration getIncrements()
    {
        return _increments;
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

