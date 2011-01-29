package com.thirdnf.ResourceScheduler.demo;

import com.thirdnf.ResourceScheduler.Appointment;
import com.thirdnf.ResourceScheduler.Resource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;

/**
* Created by IntelliJ IDEA.
* User: jgerth
* Date: 1/28/11
* Time: 3:53 PM
* To change this template use File | Settings | File Templates.
*/
public class DemoAppointment implements Appointment
{
    private DemoCategory _category;
    private DemoResource _resource;
    private String _title;
    private DateTime _dateTime;
    private Duration _duration;


    public DemoAppointment(@NotNull String title, DemoCategory category, DemoResource resource)
    {
        _title = title;
        _category = category;
        _resource = resource;
    }


    public DemoCategory getCategory()
    {
        return _category;
    }


    public void setCategory(@NotNull DemoCategory category)
    {
        _category = category;
    }


    @NotNull
    @Override
    public DateTime getDateTime()
    {
        return _dateTime;
    }


    @Override
    public Resource getResource()
    {
        return _resource;
    }


    public void setResource(@NotNull DemoResource resource)
    {
        _resource = resource;
    }


    @NotNull
    @Override
    public Duration getDuration()
    {
        return _duration;
    }


    public void setDuration(@NotNull Duration duration)
    {
        _duration = duration;
    }


    @NotNull
    @Override
    public String getTitle()
    {
        return _title;
    }


    /**
     * Set the title of the appointment.
     *
     * @param title (not null) New title for the appointment
     */
    public void setTitle(@NotNull String title)
    {
        _title = title;
    }


    public void setDateTime(@NotNull DateTime time)
    {
        _dateTime = time;
    }


    public static DemoAppointment create(@NotNull String title, @NotNull DemoCategory category,
                                         @Nullable DemoResource resource,
                                         @NotNull LocalTime time, int minutes)
    {
        DemoAppointment appointment = new DemoAppointment(title, category, resource);
        DateTime date = new DateTime(ScheduleModelDemo.Today.getYear(), ScheduleModelDemo.Today.getMonthOfYear(), ScheduleModelDemo.Today.getDayOfMonth(),
                time.getHourOfDay(), time.getMinuteOfHour(), time.getSecondOfMinute(), 0);
        appointment.setDateTime(date);
        appointment.setDuration(Duration.standardMinutes(minutes));

        return appointment;
    }
}
