package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;

/**
 * A simple simple class to hold hours, minutes and seconds.  This can either represent a point in time, or a
 * duration.
 */
public class Time extends ATime implements Comparable<Time>
{
    public Time(int hours, int minutes, int seconds)
    {
        super(hours, minutes, seconds);
    }


    public boolean isOnTheHour()
    {
        return _minutes == 0 && _seconds == 0;
    }

    public boolean isAM()
    {
        return _hours < 13;
    }


    public Time add(Duration duration)
    {
        return new Time(_hours + duration._hours, _minutes + duration._minutes, _seconds + duration._seconds);
    }


    @Override
    public int compareTo(Time o)
    {
        return super.compareTo(o);
    }


    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        boolean pm = false;
        if (_hours > 12) {
            stringBuilder.append(_hours - 12);
            pm = true;
        }
        else {
            stringBuilder.append(_hours);
        }
        stringBuilder.append(':');

        stringBuilder.append(_minutes);
        if (_minutes < 10) { stringBuilder.append('0'); }

        if (pm) {
            stringBuilder.append(" PM");
        }
        else {
            stringBuilder.append(" AM");
        }

        return stringBuilder.toString();
    }
}
