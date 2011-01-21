package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;

import javax.swing.event.EventListenerList;


/**
 * Abstract schedule model to take care of listeners and fire methods.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
@SuppressWarnings({"UnusedDeclaration"})
public abstract class AScheduleModel implements IScheduleModel
{
    // We are going to use the EventListenerList which allows multiple events to share one list.
    protected final EventListenerList _listenerList = new EventListenerList();


    @Override
    public void addResourceChangeListener(@NotNull IResourceChangeListener listener)
    {
        _listenerList.add(IResourceChangeListener.class, listener);
    }


    @Override
    public void removeResourceChangeListener(@NotNull IResourceChangeListener listener)
    {
        _listenerList.remove(IResourceChangeListener.class, listener);
    }


    @Override
    public void addAppointmentChangeListener(@NotNull IAppointmentChangeListener listener)
    {
        _listenerList.add(IAppointmentChangeListener.class, listener);
    }


    @Override
    public void removeAppointmentChangeListener(@NotNull IAppointmentChangeListener listener)
    {
        _listenerList.remove(IAppointmentChangeListener.class, listener);
    }


    /**
     * Protected method which an implementation of the Schedule Model would call every time a new
     * resource was added to the model.  This method takes care of calling all the subscribed listeners.
     *
     * @param resource (not null) Resource which has been added to the model.
     * @param dateTime (not null) Date the resource was added.
     */
    protected void fireResourceAdded(@NotNull IResource resource, @NotNull LocalDate dateTime)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = _listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==IResourceChangeListener.class) {
                ((IResourceChangeListener)listeners[i+1]).resourceAdded(resource, dateTime);
            }
        }
    }


    /**
     * Protected method which an implementation of the Schedule Model should call every time a
     * resource should be removed from the model.
     *
     * @param resource (not null) Resource being removed
     * @param dateTime (not null) Date the resource is being removed
     */
    protected void fireResourceRemoved(@NotNull IResource resource, @NotNull LocalDate dateTime)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = _listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==IResourceChangeListener.class) {
                ((IResourceChangeListener)listeners[i+1]).resourceRemoved(resource, dateTime);
            }
        }
    }


    /**
     * Protected method which an implementation of the Schedule Model should call every time a
     * resource should be updated.  Like the title or color has changed.
     *
     * @param resource (not null) Resource being updated
     */
    protected void fireResourceUpdated(@NotNull IResource resource)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = _listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==IResourceChangeListener.class) {
                ((IResourceChangeListener)listeners[i+1]).resourceUpdated(resource);
            }
        }
    }


    /**
     * Protected method called whenever the model has added an appointment.
     *
     * @param appointment (not null) The appointment being added.
     */
    protected void fireAppointmentAdded(@NotNull IAppointment appointment)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = _listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==IAppointmentChangeListener.class) {
                ((IAppointmentChangeListener)listeners[i+1]).appointmentAdded(appointment);
            }
        }
    }


    /**
     * Protected method called whenever the model has removed an appointment from a day.
     *
     * @param appointment (not null) The appointment being removed.
     */
    protected void fireAppointmentRemoved(@NotNull IAppointment appointment)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = _listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==IAppointmentChangeListener.class) {
                ((IAppointmentChangeListener)listeners[i+1]).appointmentRemoved(appointment);
            }
        }
    }


    /**
     * Protected method called whenever the model has updated some details about an appointment, like the
     *  title, time or resource it is assigned to.
     *
     * @param appointment (not null) The appointment.
     */
    protected void fireAppointmentUpdated(@NotNull IAppointment appointment)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = _listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==IAppointmentChangeListener.class) {
                ((IAppointmentChangeListener)listeners[i+1]).appointmentUpdated(appointment);
            }
        }
    }
}
