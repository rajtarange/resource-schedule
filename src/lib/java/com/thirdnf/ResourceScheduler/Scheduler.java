/*
 * Created by JFormDesigner on Thu Jan 13 18:25:17 PST 2011
 */

package com.thirdnf.ResourceScheduler;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the main entry point for the Scheduler.  This will have methods on it to determine which view is
 * visible (day, week, month, year) and which date to view.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class Scheduler extends JPanel 
{
    private static final String DayView = "DayView";

    private final Set<ActionListener> _actionListeners = new HashSet<ActionListener>();
    private final DaySchedule _daySchedule;

    public Scheduler() 
    {
        CardLayout cardLayout = new CardLayout();

        setLayout(cardLayout);
        _daySchedule = new DaySchedule();
        add(_daySchedule, DayView);

        _daySchedule.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                for (ActionListener listener : _actionListeners) {
                    listener.actionPerformed(e);
                }
            }
        });
    }


    public void setModel(@NotNull IScheduleModel model)
    {
        _daySchedule.setModel(model);
    }


    public void showDate(@NotNull Date date)
    {
        // TODO - Make sure the day view is loaded

        _daySchedule.showDate(date);
    }


    public void addActionListener(@NotNull ActionListener actionListener)
    {
        _actionListeners.add(actionListener);
    }
}
