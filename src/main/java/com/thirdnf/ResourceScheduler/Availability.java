package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;
import org.joda.time.Duration;
import org.joda.time.LocalTime;



public class Availability
{
    private LocalTime _time;
    private Duration _duration;

    public Availability(@NotNull LocalTime time, @NotNull Duration duration)
    {
        _time = time;
        _duration = duration;
    }


    public LocalTime getTime()
    {
        return _time;
    }


    public Duration getDuration()
    {
        return _duration;
    }
}
