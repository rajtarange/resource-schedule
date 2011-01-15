package com.thirdnf.ResourceScheduler;


/**
 * Abstract Time representation.  This can either represent a point in the day or a duration of time.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class ATime
{
    protected final int _hours;
    protected final int _minutes;
    protected final int _seconds;


    /**
     * Constructor.  Will always normalize the minutes and seconds to being less than 60.
     *
     * @param hours Hours
     * @param minutes Minutes
     * @param seconds Seconds
     */
    protected ATime(int hours, int minutes, int seconds)
    {
        // Normalize
        if (seconds >= 60) {
            minutes += seconds / 60;
            seconds %= 60;
        }

        if (minutes >= 60) {
            hours += minutes / 60;
            minutes %= 60;
        }

        _hours = hours;
        _minutes = minutes;
        _seconds = seconds;
    }


    /**
     * Get the hour values.
     *
     * @return Hours values
     */
    public int getHours()
    {
        return _hours;
    }


    /**
     * Get the minutes value.
     *
     * @return Minutes value
     */
    public int getMinutes()
    {
        return _minutes;
    }


    /**
     * Get the seconds value.
     *
     * @return Seconds value.
     */
    public int getSeconds()
    {
        return _seconds;
    }


    /**
     * Get the total time as a number of seconds.
     *
     * @return Total time as seconds.
     */
    public int toSeconds()
    {
        return (_hours*60 + _minutes)*60 + _seconds;
    }


    public int compareTo(ATime o)
    {
        if (o._hours > _hours) { return 1; }
        if (o._hours < _hours) { return -1; }

        if (o._minutes > _minutes) { return 1; }
        if (o._minutes < _minutes) { return -1; }

        if (o._seconds > _seconds) { return 1; }
        if (o._seconds < _seconds) { return -1; }

        return 0;
    }


    public int hashCode()
    {
        return toSeconds();
    }


    public boolean equals(Object o)
    {
        return o instanceof ATime && compareTo((ATime) o) == 0;
    }
}
