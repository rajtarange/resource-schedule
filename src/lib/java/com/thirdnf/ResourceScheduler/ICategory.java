package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public interface ICategory
{
    @NotNull
    String getTitle();


    @NotNull
    Color getColor();
}
