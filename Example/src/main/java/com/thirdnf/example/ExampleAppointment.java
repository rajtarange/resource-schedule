package com.thirdnf.ResourceScheduler.example;

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
public class ExampleAppointment implements Appointment
{
    private ExampleCategory _category;
    private ExampleResource _resource;
    private String _title;
    private DateTime _dateTime;
    private Duration _duration;


    public ExampleAppointment(@NotNull String title, ExampleCategory category, ExampleResource resource)
    {
        _title = title;
        _category = category;
        _resource = resource;
    }


    public ExampleCategory getCategory()
    {
        return _category;
    }


    public void setCategory(@NotNull ExampleCategory category)
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


    public void setResource(@NotNull ExampleResource resource)
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


    public static ExampleAppointment create(@NotNull String title, @NotNull ExampleCategory category,
                                         @Nullable ExampleResource resource,
                                         @NotNull LocalTime time, int minutes)
    {
        ExampleAppointment appointment = new ExampleAppointment(title, category, resource);
        DateTime date = new DateTime(ExampleScheduleModel.Today.getYear(), ExampleScheduleModel.Today.getMonthOfYear(), ExampleScheduleModel.Today.getDayOfMonth(),
                time.getHourOfDay(), time.getMinuteOfHour(), time.getSecondOfMinute(), 0);
        appointment.setDateTime(date);
        appointment.setDuration(Duration.standardMinutes(minutes));

        return appointment;
    }
}
