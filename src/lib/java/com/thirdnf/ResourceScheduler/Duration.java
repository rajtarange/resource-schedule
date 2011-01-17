package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;


/**
 * Instances of this class will represent a duration of time as hours, minutes and seconds.  All times are
 *  normalized on construction so that the seconds and minutes will always be between 0 and 59.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class Duration extends ATime implements Comparable<Duration>
{
    /**
     * Constructor given hours, minutes and seconds to represent as a duration.
     *
     * @param hours The hours
     * @param minutes The minutes
     * @param seconds The seconds
     */
    public Duration(int hours, int minutes, int seconds)
    {
        super(hours, minutes, seconds);
    }


    /**
     * Divide one duration by another.  So if your total span is 45 minutes and you want to know how many
     * chunks of 15 mins go into it, you would use this.
     *
     * @param duration (not null) The duration to divide into this object.
     * @return The floating value of the division.
     */
    public float divide(@NotNull Duration duration)
    {
        return (float)toSeconds() / (float)(duration.toSeconds());
    }


    @Override
    public int compareTo(Duration o)
    {
        return super.compareTo(o);
    }


    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(_hours).append(" hours, ").append(_minutes).
                append(" minutes,").append(_seconds).append(" seconds");

        return stringBuilder.toString();
    }
}
