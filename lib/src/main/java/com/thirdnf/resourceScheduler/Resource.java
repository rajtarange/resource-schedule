package com.thirdnf.resourceScheduler;


import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;

import java.util.Iterator;


/**
 * Implementations of this will define a resource that is available to be scheduled.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public interface Resource
{
    /**
     * Get the title of the resource.  This is what is shown on the panel.
     *
     * @return (not null) The title of the resource.
     */
    @NotNull
    String getTitle();


    /**
     * Get the availability of this resource for a given date.
     *
     * @param date (not null) Date in question
     * @return (not null) Iterator of availabilities.
     */
    @NotNull
    Iterator<Availability> getAvailability(@NotNull LocalDate date);
}
