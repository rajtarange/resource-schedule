package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;

import javax.swing.event.EventListenerList;
import java.util.Date;
import java.util.List;

/**
 * Abstract schedule model to take care of listeners and fire methods.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public abstract class AScheduleModel implements IScheduleModel
{
    protected EventListenerList _listenerList = new EventListenerList();


    protected void fireResourceAdded(@NotNull IResource resource, @NotNull Date dateTime)
    {

    }
}
