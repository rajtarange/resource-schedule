package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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


    @Nullable
    ICategory getCategory();


    /**
     * Get the time of the appointment
     * @return
     */
    @NotNull
    Time getTime();


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
