package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;


/**
 * Instances of this class will be appointments to add to the schedule.
 */
public interface IAppointment
{
    /**
     * Get the appointment title/name.  This is what the gui will display.
     *
     * @return (not null) Title for the appointment.
     */
    @NotNull
    String getTitle();


    /**
     * Get the date/time of the appointment
     * @return The date/time for the appointment.
     */
    @NotNull
    DateTime getDateTime();


    /**
     * Get the resource this appointment was assigned to.  It may return null if the appointment has not
     * yet been assigned to a resource.
     *
     * @return (nullable) The resource this appointment has been assigned to.
     */
    @Nullable
    IResource getResource();


    /**
     * Get the length of the appointment.
     * @return
     */
    @NotNull
    Duration getDuration();
}
