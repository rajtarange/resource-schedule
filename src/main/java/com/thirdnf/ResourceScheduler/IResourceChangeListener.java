package com.thirdnf.ResourceScheduler;

import com.thirdnf.ResourceScheduler.IResource;
import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;

import java.util.EventListener;
import java.util.Locale;


/**
 * Implementations of this can listen for resources being added
 */
public interface IResourceChangeListener extends EventListener
{
    /**
     * Called when a resource has been made available for a specific date.
     *
     * @param resource (not null) Resource being made available for the date.
     * @param date (not null) the date.
     */
    void resourceAdded(@NotNull IResource resource, @NotNull LocalDate date);


    void resourceRemoved(@NotNull IResource resource, @NotNull LocalDate date);


    void resourceUpdated(@NotNull IResource resource);
}
