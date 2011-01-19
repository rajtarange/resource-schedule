package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;

import javax.swing.event.EventListenerList;


/**
 * Abstract schedule model to take care of listeners and fire methods.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public abstract class AScheduleModel implements IScheduleModel
{
    // We are going to use the EventListenerList which allows multiple events to share one list.
    protected final EventListenerList _listenerList = new EventListenerList();


    /**
     * Method to request that the given listener be notified when a resource is added to the model.
     *
     * @param listener (not null) Listener to notify when a resource is added to the model.
     */
    public void addResourceAddedListener(@NotNull IResourceAddedListener listener)
    {
        _listenerList.add(IResourceAddedListener.class, listener);
    }


    /**
     * Removes a listener from the list that's notified each time a
     * resource is added.
     *
     * @param listener (not null) The resource added listener
     */
    public void removeTableModelListener(IResourceAddedListener listener)
    {
        _listenerList.remove(IResourceAddedListener.class, listener);
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
            if (listeners[i]==IResourceAddedListener.class) {
                ((IResourceAddedListener)listeners[i+1]).resourceAdded(resource);
            }
        }
    }
}
