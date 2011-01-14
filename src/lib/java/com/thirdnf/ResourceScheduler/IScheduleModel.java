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
}
