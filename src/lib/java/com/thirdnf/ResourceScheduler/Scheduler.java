/*
 * Created by JFormDesigner on Thu Jan 13 18:25:17 PST 2011
 */

package com.thirdnf.ResourceScheduler;

import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import org.jetbrains.annotations.NotNull;

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
    private final Set<ActionListener> _actionListeners = new HashSet<ActionListener>();

    public Scheduler() 
    {
        initComponents();
    }


    public void setModel(@NotNull IScheduleModel model)
    {
        _daySchedule.setModel(model);

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


    public void showDate(@NotNull Date date)
    {
        // TODO - Make sure the day view is loaded

        _daySchedule.showDate(date);
    }


    public void addActionListener(@NotNull ActionListener actionListener)
    {
        _actionListeners.add(actionListener);
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        _daySchedule = new DaySchedule();

        //======== this ========
        setLayout(new FormLayout(
            "default:grow",
            "default:grow"));
        add(_daySchedule, CC.xy(1, 1, CC.FILL, CC.FILL));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private DaySchedule _daySchedule;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
