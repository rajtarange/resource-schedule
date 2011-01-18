package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;


public interface IAppointmentVisitor
{
    boolean visitAppointment(@NotNull IAppointment appointment);
}
