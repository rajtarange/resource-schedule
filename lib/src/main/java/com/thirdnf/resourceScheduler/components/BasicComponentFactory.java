package com.thirdnf.resourceScheduler.components;


import com.thirdnf.resourceScheduler.Appointment;
import com.thirdnf.resourceScheduler.Resource;
import org.jetbrains.annotations.NotNull;


/**
 * This is the Base ComponentFactory which is responsible for creating the components which
 * wrap a resource and appointment.  If you want to use your own custom components then
 * you need to extend this factory and call the
 * {@link com.thirdnf.resourceScheduler.Scheduler#setComponentFactory(BasicComponentFactory) setComponentFactory}
 * method.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class BasicComponentFactory
{
    /**
     * Factory method to create a resource component for a given resource.
     *
     * @param resource (not null) The resource for this resource component.
     *
     * @return (not null) A resource component which derives from the AbstractResourceComponent.
     */
    @NotNull
    public AbstractResourceComponent makeResourceComponent(@NotNull Resource resource)
    {
        return new BasicResourceComponent(resource);
    }


    /**
     * Factory method to create an appointment component for the given appointment.
     *
     * @param appointment (not null) The appointment for this appointment component.
     *
     * @return (not null) An appointment component which derives from AbstractAppointmentComponent
     */
    @NotNull
    public AbstractAppointmentComponent makeAppointmentComponent(@NotNull Appointment appointment)
    {
        return new BasicAppointmentComponent(appointment);
    }
}
