package com.thirdnf.ResourceScheduler.components;

import com.thirdnf.ResourceScheduler.Resource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings({"UnusedDeclaration"})
public class AbstractResourceComponent extends JComponent
{
    // The resource this component is wrapping.
    protected final Resource _resource;

    // This is the color which the DaySchedule and other views should use to identify this
    //  resource.  By default this is null which means they don't identify it.  This is not
    //  the same as the background or foreground colors which are used to paint this actual
    //  component.
    protected Color _color;


    /**
     * Constructor given a resource to wrap
     * @param resource (not null) Resource to wrap.
     */
    protected AbstractResourceComponent(@NotNull Resource resource)
    {
        _resource = resource;
    }


    /**
     * Simple getter to get the wrapped resource.
     *
     * @return (not null) The wrapped resource
     */
    @NotNull
    public Resource getResource()
    {
        return _resource;
    }


    /**
     * Simple getter to get the color identifier for this resource.
     * @return (nullable) Color for the resource or null if it has not been set.
     */
    @Nullable
    public Color getColor()
    {
        return _color;
    }


    /**
     * Simple setter for this resource.
     *
     * @param color (nullable) Color to set for this resource.
     */
    public void setColor(@Nullable Color color)
    {
        _color = color;
    }
}
