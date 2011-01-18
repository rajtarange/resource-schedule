package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: jgerth
 * Date: 1/13/11
 * Time: 9:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISchedule
{
    int getY(@NotNull Time time);
}
