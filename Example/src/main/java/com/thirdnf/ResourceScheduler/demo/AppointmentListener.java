package com.thirdnf.ResourceScheduler.demo;

import com.thirdnf.ResourceScheduler.Appointment;
import org.jetbrains.annotations.NotNull;


public interface AppointmentListener
{
    /**
     * Method to call when a mouse click has happened on an appointment.
     *
     * @param appointment (not null) The appointment which was clicked on.
     */
    void handleClick(@NotNull Appointment appointment);

    void handleDelete(@NotNull Appointment appointment);

    void handleEdit(@NotNull Appointment appointment);
}
