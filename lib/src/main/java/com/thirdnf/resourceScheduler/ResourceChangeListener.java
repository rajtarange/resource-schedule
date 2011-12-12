package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;

import java.util.EventListener;


/**
 * Implementations of this can listen for resources being added, removed or updated.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public interface ResourceChangeListener extends EventListener
{
    /**
     * Called when a resource has been made available for a specific date.  The Java Swing API says
     * that it is okay to add a resource more than once as it will first be removed so it is okay
     * to move a resource by adding it to a new location.
     *
     * @param resource (not null) Resource being made available for the date.
     * @param date (not null) the date.
     * @param index Location to add, use -1 to indicate an add, any other illegal value
     *   will also result in an add.
     */
    void resourceAdded(@NotNull Resource resource, @NotNull LocalDate date, int index);


    /**
     * Called when a resource has been removed from the model and any listeners should follow suit.
     *
     * @param resource (not null) Resource which was removed
     * @param date (not null) Date the resource was removed
     */
    void resourceRemoved(@NotNull Resource resource, @NotNull LocalDate date);


    /**
     * Called if a resource has been updated and the panel should redraw.
     *
     * @param resource (not null) Resource which was updated
     */
    void resourceUpdated(@NotNull Resource resource);
}
