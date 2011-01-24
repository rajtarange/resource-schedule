package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;

import java.util.EventListener;


/**
 * Implementations of this can listen for resources being added
 */
public interface ResourceChangeListener extends EventListener
{
    /**
     * Called when a resource has been made available for a specific date.
     *
     * @param resource (not null) Resource being made available for the date.
     * @param date (not null) the date.
     * @param index Location to add, use -1 to indicate an add.
     */
    void resourceAdded(@NotNull Resource resource, @NotNull LocalDate date, int index);


    void resourceRemoved(@NotNull Resource resource, @NotNull LocalDate date);


    void resourceUpdated(@NotNull Resource resource);
}
