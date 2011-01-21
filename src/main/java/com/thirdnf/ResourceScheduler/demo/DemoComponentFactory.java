package com.thirdnf.ResourceScheduler.demo;

import com.thirdnf.ResourceScheduler.IAppointment;
import com.thirdnf.ResourceScheduler.IResource;
import com.thirdnf.ResourceScheduler.components.AbstractAppointmentComponent;
import com.thirdnf.ResourceScheduler.components.AbstractResourceComponent;
import com.thirdnf.ResourceScheduler.components.ComponentFactory;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DemoComponentFactory extends ComponentFactory
{
    private AppointmentListener _appointmentListener;


    @Override
    public AbstractResourceComponent makeResourceComponent(@NotNull IResource resource)
    {
        return new DemoResourceComponent(resource);
    }


    @Override
    public AbstractAppointmentComponent makeAppointmentComponent(@NotNull final IAppointment appointment)
    {
        DemoAppointmentComponent component = new DemoAppointmentComponent(appointment);
        component.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (_appointmentListener != null) { _appointmentListener.handleClick(appointment); }
            }
        });

        return component;
    }


    public void setAppointmentListener(@NotNull AppointmentListener appointmentListener)
    {
        _appointmentListener = appointmentListener;
    }
}
