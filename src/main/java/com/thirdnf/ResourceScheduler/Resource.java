package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;


/**
 * Implementations of this will define a resource that is needing to be scheduled.
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
}
