package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;


public interface AppointmentVisitor
{
    boolean visitAppointment(@NotNull Appointment appointment);
}
