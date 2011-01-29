package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public interface ScheduleModel
{
    /**
     * Visit all appointments for the given date time.  The Resource Scheduler uses Joda DateTime
     * rather than Java's DateTime for the flexibility.
     *
     * @param visitor (not null) The visitor to visit with every appointment for the given day
     * @param dateTime (not null) The day to visit the appointments for.
     */
    void visitAppointments(AppointmentVisitor visitor, @NotNull LocalDate dateTime);


     /**
     * Visit all appointments for the given date time.  The Resource Scheduler uses Joda DateTime
     * rather than Java's DateTime for the flexibility.
     *
     * @param visitor (not null) The visitor to visit with every appointment for the given day
     * @param dateTime (not null) The day to visit the appointments for.
     */
    void visitResources(ResourceVisitor visitor, @NotNull LocalDate dateTime);


    /**
     * Get the start time for a particular date.  Currently there is a not-null requirement
     * on this, but eventually this should allow nulls and just determine the start time
     * automatically from the start times of all the available resources.
     *
     * @param dateTime (not null) The day to get the start time for.
     * @return (not null) The time the day starts for the given day.
     */
    @NotNull
    LocalTime getStartTime(@NotNull LocalDate dateTime);


    /**
     * Get the end time for the day ... I'm thinking that this should really be a dynamic value and
     *  not something set through the model.
     *
     * @param dateTime (not null) Day to get the end date for.
     * @return (not null) The time
     */
    LocalTime getEndTime(@NotNull LocalDate dateTime);


    /**
     * Method to request that the given listener be notified when a resource has been either added or
     * removed from the model.
     *
     * @param listener (not null) Listener to notify when a resource has been added or removed.
     */
    void addResourceChangeListener(ResourceChangeListener listener);


    /**
     * Method to request that the given listener be removed from notification when a resource has
     * been either added or removed from the model.
     *
     * @param listener (not null) Listener to notify when a resource has been added or removed.
     */
    void removeResourceChangeListener(ResourceChangeListener listener);


    void addAppointmentChangeListener(@NotNull AppointmentChangeListener listener);


    void removeAppointmentChangeListener(@NotNull AppointmentChangeListener listener);
}
