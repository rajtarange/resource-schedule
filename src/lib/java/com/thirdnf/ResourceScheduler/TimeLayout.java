package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
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

    private int _columns = 3;
    private float _columnWidth = 0;
    private int _topHeader = 50;

    private final Duration _increments;
    private int _hours = 10;
    private int _rows;
    private float _rowHeight = 0;
    private int _leftHeader = 100;

    /**
     * Constructor.  So far the only thing that must be provided is the increments to use for the layout.
     * I think changing this would require a nearly full re-layout.
     *
     * @param increments (not null) Increments for the layout
     */
    public TimeLayout(@NotNull Duration increments)
    {
        _increments = increments;
        _rows = _hours * 60 / _increments.getMinutes();
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

                if (! (component instanceof AppointmentComponent)) { continue; }
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
        }
    }


    private void recomputeLayout(Container target)
    {
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

