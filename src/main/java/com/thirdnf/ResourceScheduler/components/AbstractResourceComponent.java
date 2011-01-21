package com.thirdnf.ResourceScheduler.components;

import com.thirdnf.ResourceScheduler.IResource;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


public class AbstractResourceComponent extends JComponent
{
    protected final IResource _resource;

    protected AbstractResourceComponent(@NotNull IResource resource)
    {
        _resource = resource;
    }


    public IResource getResource()
    {
        return _resource;
    }
}
