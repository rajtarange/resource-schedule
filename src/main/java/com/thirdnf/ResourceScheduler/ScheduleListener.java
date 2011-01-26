package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

import java.util.EventListener;

public interface ScheduleListener extends EventListener
{
    void actionPerformed(@NotNull Resource resource, @NotNull DateTime time);
}
