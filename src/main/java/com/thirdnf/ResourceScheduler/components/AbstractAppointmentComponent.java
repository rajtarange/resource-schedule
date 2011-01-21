package com.thirdnf.ResourceScheduler.components;

import com.thirdnf.ResourceScheduler.IAppointment;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


/**
 * This is the base class for an appointment component which are actually drawn on the panel.
 * At minimum it needs to be a JComponent but the drawing of the component is entirely up to
 * the implementation.
 *
 * @see BasicAppointmentComponent
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class AbstractAppointmentComponent extends JComponent
{
    // The appointment this component is wrapping.
    protected final IAppointment _appointment;


    /**
     * Constructor given an appointment to wrap.
     *
     * @param appointment (not null) The appointment to wrap
     */
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
