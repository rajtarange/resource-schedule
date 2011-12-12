package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

import java.util.EventListener;

public interface ScheduleListener extends EventListener
{
    /**
     * Deal with an action performed (likely a user click) on the given resource at the given time.  Implementations
     *  would likely prompt to add an appointment.
     *
     * @param resource (not null) Resource clicked in.
     * @param time (not null) Time selected.
     */
    void actionPerformed(@NotNull Resource resource, @NotNull DateTime time);
}
