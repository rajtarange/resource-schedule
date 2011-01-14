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
    private Map<Component, Location> _locationMap = new HashMap<Component, Location>();

    private Map<Time, Integer> _timeMap = new HashMap<Time, Integer>();

    private int _columns = 3;
    private float _columnWidth = 0;
    private int _topHeader = 50;

    private int _minuteIncrements = 15;
    private int _hours = 10;
    private int _rows = _hours * 60 / _minuteIncrements;
    private float _rowHeight = 0;
    private int _leftHeader = 100;

    /**
     * Boring empty constructor.
     */
    public TimeLayout()
    {
        System.out.println("Making layout");
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
        System.out.println("layoutContainer");

        synchronized (target.getTreeLock()) {
            recomputeLayout(target);

            int componentCount = target.getComponentCount();


            for (int i = 0 ; i < componentCount ; ++i) {
                Component component = target.getComponent(i);
                if (! component.isVisible()) { continue; }

                Location location = _locationMap.get(component);
                if (location == null) {
                    continue;
                }

                Time time = location.getTime();

                int y = _timeMap.get(time);
                int x = _leftHeader + (int)(_columnWidth * location.getColumn());

                component.setBounds(x, y, (int) _columnWidth - 15, (int) (_rowHeight * 4));
            }
        }
    }


    private void recomputeLayout(Container target)
    {
        Insets insets = target.getInsets();

        int width = target.getWidth() - insets.left - insets.right;
        int height = target.getHeight() - insets.top - insets.bottom;


        // Draw rows with hour markers
        _rowHeight = (float)(height - _topHeader) / (float)_rows;
        _columnWidth = (float)(width - _leftHeader) / (float) _columns;


        Time time = new Time(8, 0, 0);
        _timeMap.clear();
        for (int i=0; i< _rows; ++i) {
            int y = _topHeader + (int) (i* _rowHeight);

            _timeMap.put(time, y);

            time = time.addMinutes(_minuteIncrements);
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


    @Override
    public void addLayoutComponent(Component comp, Object constraints)
    {
        if (! (constraints instanceof Location)) {
            throw new IllegalArgumentException("Constraint must be a Location");
        }

        _locationMap.put(comp, (Location)constraints);
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

