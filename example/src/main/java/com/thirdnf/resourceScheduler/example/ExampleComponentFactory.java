package com.thirdnf.resourceScheduler.example;

import com.thirdnf.resourceScheduler.Appointment;
import com.thirdnf.resourceScheduler.Resource;
import com.thirdnf.resourceScheduler.components.AbstractAppointmentComponent;
import com.thirdnf.resourceScheduler.components.AbstractResourceComponent;
import com.thirdnf.resourceScheduler.components.BasicComponentFactory;
import org.jetbrains.annotations.NotNull;


/**
 * This is the example component factory which is used to create custom resource and appointment components.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class ExampleComponentFactory extends BasicComponentFactory
{
    // The appointment listener to call for mouse clicks on the appointments.
    private AppointmentListener _appointmentListener;

    // The resource listener to call for mouse clicks on the resources.
    private ResourceListener _resourceListener;


    @Override
    @NotNull
    public AbstractResourceComponent makeResourceComponent(@NotNull Resource resource)
    {
        ExampleResourceComponent component = new ExampleResourceComponent(resource);
        component.setResourceListener(_resourceListener);

        return component;
    }


    @Override
    @NotNull
    public AbstractAppointmentComponent makeAppointmentComponent(@NotNull Appointment appointment)
    {
        ExampleAppointmentComponent component = new ExampleAppointmentComponent(appointment);
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
