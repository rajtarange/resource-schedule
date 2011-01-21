package com.thirdnf.ResourceScheduler.components;


import com.thirdnf.ResourceScheduler.IAppointment;
import com.thirdnf.ResourceScheduler.IResource;
import org.jetbrains.annotations.NotNull;

public class ComponentFactory
{
    public AbstractResourceComponent makeResourceComponent(@NotNull IResource resource)
    {
        return new BasicResourceComponent(resource);
    }


    public AbstractAppointmentComponent makeAppointmentComponent(@NotNull IAppointment appointment)
    {
        return new BasicAppointmentComponent(appointment);
    }
}
