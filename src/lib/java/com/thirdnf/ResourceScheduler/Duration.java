package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;

public class Duration extends ATime implements Comparable<Duration>
{
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


    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(_hours).append(" hours, ").append(_minutes).
                append(" minutes,").append(_seconds).append(" seconds");

        return stringBuilder.toString();
    }
}
