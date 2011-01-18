package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;

import java.awt.*;

public interface IResource
{
    @NotNull
    String getTitle();

    @NotNull
    Color getColor();
}
