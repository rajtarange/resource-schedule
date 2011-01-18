package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;

import java.util.Date;

public interface IScheduleModel
{
    /**
     * Visit all appointments for the given date time.  The Resource Scheduler uses Joda DateTime
     * rather than Java's DateTime for the flexability.
     *
     * @param visitor (not null) The visitor to visit with every appointment for the given day
     * @param dateTime (not null) The day to visit the appointments for.
     */
    void visitAppointments(IAppointmentVisitor visitor, @NotNull Date dateTime);


    void visitResources(IResourceVisitor visitor, @NotNull Date dateTime);


    /**
     * Get the start time for a particular date.  Eventually I am thinking that this should really get
     * a date *and* a resource, since not all resources may be available for the same portions of the day.
     * Like, for example, a mechanic that works in the morning and another that works in the evening.
     *
     * @param dateTime (not null) The day to get the start time for.
     * @return (not null) The time the day starts for the given day.
     */
    Time getStartTime(@NotNull Date dateTime);


    Time getEndTime(@NotNull Date dateTime);
}
