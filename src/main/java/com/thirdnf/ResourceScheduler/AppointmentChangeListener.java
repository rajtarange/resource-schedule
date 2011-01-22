package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;


public interface AppointmentChangeListener extends EventListener
{
    /**
     * Called when an appointment has been added for a specific date.
     *
     * @param appointment (not null) Appointment being added for the date.
     */
    void appointmentAdded(@NotNull Appointment appointment);


    void appointmentRemoved(@NotNull Appointment appointment);


    void appointmentUpdated(@NotNull Appointment appointment);
}
