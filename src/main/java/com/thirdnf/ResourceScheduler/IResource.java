package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Implementations of this will define a resource that is needing to be scheduled.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public interface IResource
{
    /**
     * Get the title of the resource.  This is what is shown on the panel.
     *
     * @return (not null) The title of the resource.
     */
    @NotNull
    String getTitle();


    /**
     * Get the resource color.  I'm not entirely convinced that this should be in the
     * resource interface.  This is really more related to presentation so perhaps it should
     * only exist in the concrete class.
     *
     * @return (not null) The color for the resource.
     */
    @NotNull
    Color getColor();
}
