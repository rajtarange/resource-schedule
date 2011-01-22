package com.thirdnf.ResourceScheduler.demo;

import com.thirdnf.ResourceScheduler.Resource;
import org.jetbrains.annotations.NotNull;


public interface ResourceListener
{
    void handleClick(@NotNull Resource resource);


    void handleDelete(@NotNull Resource resource);


    void handleEdit(@NotNull Resource resource);
}
