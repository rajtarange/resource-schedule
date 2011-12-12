package com.thirdnf.ResourceScheduler.example;

import com.thirdnf.ResourceScheduler.Appointment;
import org.jetbrains.annotations.NotNull;


public interface AppointmentListener
{
    /**
     * Method to call when a mouse click has happened on an appointment.
     *
     * @param appointment (not null) The appointment which was clicked on.
     * @param clickCount Click count
     */
    void handleClick(@NotNull Appointment appointment, int clickCount);

    void handleDelete(@NotNull Appointment appointment);

    void handleEdit(@NotNull Appointment appointment);
}
