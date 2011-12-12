package com.thirdnf.resourceScheduler.example;

import com.thirdnf.resourceScheduler.Resource;
import org.jetbrains.annotations.NotNull;


public interface ResourceListener
{
    void handleDelete(@NotNull Resource resource);


    void handleEdit(@NotNull Resource resource);
}
