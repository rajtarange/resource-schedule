package com.thirdnf.ResourceScheduler.demo;

import com.thirdnf.ResourceScheduler.IAppointment;
import org.jetbrains.annotations.NotNull;


public interface AppointmentListener
{
    void handleClick(@NotNull IAppointment appointment);
}
