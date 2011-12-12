package com.thirdnf.resourceScheduler.example;

import com.thirdnf.resourceScheduler.Availability;
import com.thirdnf.resourceScheduler.Resource;
import org.jetbrains.annotations.NotNull;
import org.joda.time.*;

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
public class ExampleResource implements Resource
{
    private String _title;
    private Color _color;
    private LocalTime _startTime = new LocalTime(9, 0, 0);
    private Duration _duration = Duration.standardHours(6);
    private boolean _takeLunch = true;

    /**
     * Create the example resource.
     * @param title Title for the resource.
     * @param color Color to assign the resource.
     */
    public ExampleResource(@NotNull String title, @NotNull Color color)
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


    public void setStartTime(@NotNull LocalTime time)
    {
        _startTime = time;
    }

    public LocalTime getStartTime()
    {
        return _startTime;
    }

    public void setDuration(@NotNull Duration duration)
    {
        _duration = duration;
    }

    public Duration getDuration()
    {
        return _duration;
    }

    public void setTakeLunch(boolean truth)
    {
        _takeLunch = truth;
    }

    public boolean getTakeLunch()
    {
        return _takeLunch;
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

        if (_takeLunch) {
            // Split it into two chunks and give them an hour lunch
            Seconds seconds = _duration.toStandardSeconds().minus(3600).dividedBy(2);
            Duration halfDay = seconds.toStandardDuration();
            list.add(new Availability(_startTime, halfDay));
            list.add(new Availability(_startTime.plus(seconds).plus(Period.hours(1)), halfDay));
        }
        else {
            list.add(new Availability(_startTime, _duration));
        }

        return list.iterator();
    }


    /**
     * Get the color for our resource.  This is used by the example resource component to paint
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
