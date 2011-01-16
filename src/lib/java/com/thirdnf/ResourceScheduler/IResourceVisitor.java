package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;

public interface IResourceVisitor
{
    boolean visitResource(@NotNull IResource resource);
}
