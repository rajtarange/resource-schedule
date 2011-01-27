package com.thirdnf.ResourceScheduler.demo;

import com.thirdnf.ResourceScheduler.Appointment;
import com.thirdnf.ResourceScheduler.Resource;
import com.thirdnf.ResourceScheduler.components.AbstractAppointmentComponent;
import com.thirdnf.ResourceScheduler.components.AbstractResourceComponent;
import com.thirdnf.ResourceScheduler.components.ComponentFactory;
import org.jetbrains.annotations.NotNull;


/**
 * This is the demo component factory which is used to create custom resource and appointment components.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class DemoComponentFactory extends ComponentFactory
{
    // The appointment listener to call for mouse clicks on the appointments.
    private AppointmentListener _appointmentListener;

    // The resource listener to call for mouse clicks on the resources.
    private ResourceListener _resourceListener;


    @Override
    @NotNull
    public AbstractResourceComponent makeResourceComponent(@NotNull Resource resource)
    {
        DemoResourceComponent component = new DemoResourceComponent(resource);
        component.setResourceListener(_resourceListener);

        return component;
    }


    @Override
    @NotNull
    public AbstractAppointmentComponent makeAppointmentComponent(@NotNull Appointment appointment)
    {
        DemoAppointmentComponent component = new DemoAppointmentComponent(appointment);
        component.setAppointmentListener(_appointmentListener);

        return component;
    }


    /**
     * Set the appointment listener.  This will be passed into every appointment component which
     * is created.
     *
     * @param appointmentListener (not null) The listener to call for mouse clicks on the appointments.
     */
    public void setAppointmentListener(@NotNull AppointmentListener appointmentListener)
    {
        _appointmentListener = appointmentListener;
    }


    public void setResourceListener(@NotNull ResourceListener resourceListener)
    {
        _resourceListener = resourceListener;
    }
}
