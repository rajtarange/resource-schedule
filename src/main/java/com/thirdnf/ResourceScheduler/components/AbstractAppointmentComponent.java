package com.thirdnf.ResourceScheduler.components;

import com.thirdnf.ResourceScheduler.IAppointment;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


public class AbstractAppointmentComponent extends JComponent
{
    // The appointment this component is wrapping.
    protected final IAppointment _appointment;


    protected AbstractAppointmentComponent(@NotNull IAppointment appointment)
    {
        _appointment = appointment;
    }


    /**
     * Get the appointment from the component.
     *
     * @return (not null) The appointment this component was wrapping.
     */
    @NotNull
    public IAppointment getAppointment()
    {
        return _appointment;
    }

}
