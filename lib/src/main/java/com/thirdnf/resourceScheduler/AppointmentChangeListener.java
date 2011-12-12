package com.thirdnf.resourceScheduler;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;


/**
 * Implementations will respond to appointment changes.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public interface AppointmentChangeListener extends EventListener
{
    /**
     * Called when an appointment has been added.
     *
     * @param appointment (not null) Appointment being added.
     */
    void appointmentAdded(@NotNull Appointment appointment);


    /**
     * Called when an appointment has been removed.
     *
     * @param appointment (not null) Appointment being removed.
     */
    void appointmentRemoved(@NotNull Appointment appointment);


    /**
     * Called when an appointment has been updated (date/time, resource) or just needs to be
     * redrawn.
     *
     * @param appointment (not null) Appointment which has been updated.
     */
    void appointmentUpdated(@NotNull Appointment appointment);
}
