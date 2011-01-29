package com.thirdnf.ResourceScheduler.example;

import com.thirdnf.ResourceScheduler.Resource;
import org.jetbrains.annotations.NotNull;


public interface ResourceListener
{
    void handleDelete(@NotNull Resource resource);


    void handleEdit(@NotNull Resource resource);
}
