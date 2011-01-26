package com.thirdnf.ResourceScheduler.demo;

import com.thirdnf.ResourceScheduler.Availability;
import com.thirdnf.ResourceScheduler.Resource;
import org.jetbrains.annotations.NotNull;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
* Created by IntelliJ IDEA.
* User: jgerth
* Date: 1/25/11
* Time: 7:35 AM
* To change this template use File | Settings | File Templates.
*/
public class DemoResource implements Resource
{
    private String _title;
    private Color _color;

    /**
     * Create the demo resource.
     * @param title Title for the resource.
     * @param color Color to assign the resource.
     */
    public DemoResource(@NotNull String title, @NotNull Color color)
    {
        _title = title;
        _color = color;
    }


    @Override
    @NotNull
    public String getTitle()
    {
        return _title;
    }


    public void setTitle(@NotNull String title)
    {
        _title = title;
    }


    public void setColor(@NotNull Color color)
    {
        _color = color;
    }


    @NotNull
    @Override
    public Iterator<Availability> getAvailability(@NotNull LocalDate date)
    {
        List<Availability> list = new ArrayList<Availability>();

        // Today the availability is from 8 - 3, tomorrow it is from 10 - 5
        if (date.equals(ScheduleModelDemo.Today)) {
            list.add(new Availability(new LocalTime(8, 0, 0), Duration.standardHours(7)));
        }
        else if (date.equals(ScheduleModelDemo.Tomorrow)) {
            list.add(new Availability(new LocalTime(10, 0, 0), Duration.standardHours(7)));
        }

        return list.iterator();
    }


    /**
     * Get the color for our resource.  This is used by the demo resource component to paint
     *  the component.
     *
     * @return (not null) The color to paint the component.
     */
    @NotNull
    public Color getColor()
    {
        return _color;
    }


    @Override
    @NotNull
    public String toString()
    {
        return _title;
    }
}
