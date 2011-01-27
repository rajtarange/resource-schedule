package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;
import org.joda.time.Duration;
import org.joda.time.LocalTime;


/**
 * An availability is a simple class that holds a single instance when a resource is available.  Availability is
 * from given time for a given duration.  A resource may have multiple availabilities in a given day (like before
 * lunch and after lunch).
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class Availability
{
    // Start of the availability
    private final LocalTime _time;

    // Length of the availability
    private final Duration _duration;


    /**
     * Construct an availability from a starting time and a duration.
     *
     * @param time (not null) Starting time of the availability.
     * @param duration (not null) Length of the availability
     */
    public Availability(@NotNull LocalTime time, @NotNull Duration duration)
    {
        _time = time;
        _duration = duration;
    }


    /**
     * Get the starting time of the availability.
     *
     * @return (not null) The availability start time.
     */
    @NotNull
    public LocalTime getTime()
    {
        return _time;
    }


    /**
     * Get the duration of the availability.
     *
     * @return (not null) Availability duration.
     */
    public Duration getDuration()
    {
        return _duration;
    }
}
