/*
 * Created by JFormDesigner on Thu Jan 13 18:25:17 PST 2011
 */

package com.thirdnf.ResourceScheduler;

import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import org.jetbrains.annotations.NotNull;

/**
 * @author Joshua Gerth
 */
public class Scheduler extends JPanel 
{
    public Scheduler() 
    {
        initComponents();
    }


    public void setModel(@NotNull IScheduleModel model)
    {
        _daySchedule.setModel(model);
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
