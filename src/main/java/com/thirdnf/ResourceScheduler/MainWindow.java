/*
 * Created by JFormDesigner on Thu Jan 13 18:25:41 PST 2011
 */

package com.thirdnf.ResourceScheduler;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Joshua Gerth
 */
public class MainWindow extends JFrame 
{
    public MainWindow() 
    {
        initComponents();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scheduler1 = new Scheduler();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 300));
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
            "default:grow",
            "default:grow"));
        contentPane.add(scheduler1, CC.xy(1, 1, CC.DEFAULT, CC.FILL));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private Scheduler scheduler1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
