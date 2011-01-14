package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;

public class Location
{
    private Time _time;

    private int  _column;


    public Location(@NotNull Time time, int column)
    {
        _time = time;
        _column = column;
    }


    public Time getTime()
    {
        return _time;
    }


    public int getColumn()
    {
        return _column;
    }
}
