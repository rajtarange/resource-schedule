package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;


public interface IResourceAddedListener extends EventListener
{
    void resourceAdded(@NotNull IResource resource);
}
