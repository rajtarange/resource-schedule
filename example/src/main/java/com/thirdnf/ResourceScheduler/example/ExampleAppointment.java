package com.thirdnf.ResourceScheduler.example;

import com.thirdnf.ResourceScheduler.Appointment;
import com.thirdnf.ResourceScheduler.Resource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

/**
 * Example Appointment
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class ExampleAppointment implements Appointment
{
    private ExampleCategory _category;
    private ExampleResource _resource;
    private String _title;
    private LocalDateTime _dateTime;
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
    public LocalDateTime getDateTime()
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


    public void setDateTime(@NotNull LocalDateTime time)
    {
        _dateTime = time;
    }


    public static ExampleAppointment create(@NotNull String title, @NotNull ExampleCategory category,
                                            @Nullable ExampleResource resource,
                                            @NotNull LocalTime time, int minutes)
    {
        ExampleAppointment appointment = new ExampleAppointment(title, category, resource);
        LocalDateTime date = new LocalDateTime(ExampleScheduleModel.Today.getYear(), ExampleScheduleModel.Today.getMonthOfYear(), ExampleScheduleModel.Today.getDayOfMonth(),
                time.getHourOfDay(), time.getMinuteOfHour(), time.getSecondOfMinute(), 0);
        appointment.setDateTime(date);
        appointment.setDuration(Duration.standardMinutes(minutes));

        return appointment;
    }
}
