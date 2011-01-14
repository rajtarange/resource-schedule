/*
 * Created by JFormDesigner on Thu Jan 13 18:25:17 PST 2011
 */

package com.thirdnf.ResourceScheduler;

import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Joshua Gerth
 */
public class Scheduler extends JPanel 
{
    public Scheduler() 
    {
        initComponents();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        daySchedule1 = new DaySchedule();

        //======== this ========
        setLayout(new FormLayout(
            "default:grow",
            "default:grow"));
        add(daySchedule1, CC.xy(1, 1, CC.FILL, CC.FILL));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private DaySchedule daySchedule1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
