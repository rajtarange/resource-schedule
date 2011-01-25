/*
 * Created by JFormDesigner on Thu Jan 13 18:25:17 PST 2011
 */

package com.thirdnf.ResourceScheduler;

import javax.swing.*;
import javax.swing.event.EventListenerList;

import com.thirdnf.ResourceScheduler.components.ComponentFactory;
import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;


/**
 * This is the main entry point for the Scheduler.  This will have methods on it to determine which view is
 * visible (day, week, month, year) and which date to view.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class Scheduler extends JPanel implements Printable
{
    private static final String DayView = "DayView";

    // We are going to use the EventListenerList which allows multiple events to share one list.
    protected final EventListenerList _listenerList = new EventListenerList();

    private final DaySchedule _daySchedule;

    /**
     * Constructor for the main scheduler panel.  This is responsible for deciding which view to show
     *  (day, week, month) and handing requests to switch between the views.
     */
    public Scheduler()
    {
        CardLayout cardLayout = new CardLayout();

        setLayout(cardLayout);
        _daySchedule = new DaySchedule();
        add(_daySchedule, DayView);

        _daySchedule.setScheduleListener(new ScheduleListener()
        {
            @Override
            public void actionPerformed(@NotNull Resource resource, @NotNull LocalTime time)
            {
                // Guaranteed to return a non-null array
                Object[] listeners = _listenerList.getListenerList();
                // Process the listeners last to first, notifying
                // those that are interested in this event
                for (int i = listeners.length - 2; i >= 0; i -= 2) {
                    if (listeners[i] == ActionListener.class) {
                        ((ScheduleListener) listeners[i + 1]).actionPerformed(resource, time);
                    }
                }
            }
        });

        setPreferredSize(new Dimension(500, 400));
    }


    /**
     * Set the model for the scheduler to use.  This will eventually proxy this request down to all panels, but for
     * now we only have the one.
     *
     * @param model (not null) The model to use for the component.
     */
    public void setModel(@NotNull ScheduleModel model)
    {
        _daySchedule.setModel(model);
    }


    public void setComponentFactory(@NotNull ComponentFactory componentFactory)
    {
        _daySchedule.setComponentFactory(componentFactory);
    }


    public void showDate(@NotNull LocalDate date)
    {
        // TODO - Make sure the day view is loaded

        _daySchedule.setDate(date);
    }


    public void addScheduleListener(@NotNull ScheduleListener scheduleListener)
    {
        _listenerList.add(ScheduleListener.class, scheduleListener);
    }


    public void removeScheduleListener(@NotNull ScheduleListener scheduleListener)
    {
        _listenerList.remove(ScheduleListener.class, scheduleListener);
    }


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException
    {
        // Just pass it down to the current view.
        System.out.println("Passing it down");
        return _daySchedule.print(graphics, pageFormat, pageIndex);
    }
}
