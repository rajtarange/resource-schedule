package com.thirdnf.ResourceScheduler;

import com.thirdnf.ResourceScheduler.IAppointment;
import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;

import java.util.EventListener;


public interface IAppointmentChangeListener extends EventListener
{
    /**
     * Called when an appointment has been added for a specific date.
     *
     * @param appointment (not null) Appointment being added for the date.
     */
    void appointmentAdded(@NotNull IAppointment appointment);


    void appointmentRemoved(@NotNull IAppointment appointment);


    void appointmentUpdated(@NotNull IAppointment appointment);
}
