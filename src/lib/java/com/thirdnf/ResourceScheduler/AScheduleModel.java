package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;

import javax.swing.event.EventListenerList;
import java.util.Date;


/**
 * Abstract schedule model to take care of listeners and fire methods.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public abstract class AScheduleModel implements IScheduleModel
{
    protected EventListenerList _listenerList = new EventListenerList();


    public void addResourceAddedListener(IResourceAddedListener listener)
    {
        _listenerList.add(IResourceAddedListener.class, listener);
    }


    /**
     * Removes a listener from the list that's notified each time a
     * resource is added
     *
     * @param listener (not null) The resource added listener
     */
    public void removeTableModelListener(IResourceAddedListener listener)
    {
        _listenerList.remove(IResourceAddedListener.class, listener);
    }


    protected void fireResourceAdded(@NotNull IResource resource, @NotNull Date dateTime)
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
