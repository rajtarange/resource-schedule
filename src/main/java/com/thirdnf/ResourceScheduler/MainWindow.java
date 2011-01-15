/*
 * Created by JFormDesigner on Thu Jan 13 18:25:41 PST 2011
 */

package com.thirdnf.ResourceScheduler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * Just a demo application to show how to use the ResourceScheduler
 *
 * @author Joshua Gerth
 */
public class MainWindow extends JFrame 
{
    public MainWindow() 
    {
        initComponents();

        _scheduler.setModel(new ScheduleModelDemo());
        _scheduler.showDate(new Date());
        _scheduler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IAppointment appointment = (IAppointment)e.getSource();
                _detailsPane.setText("Info about: " + appointment.getTitle());
            }
        });
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane1 = new JScrollPane();
        _detailsPane = new JTextPane();
        _scheduler = new Scheduler();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 300));
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
            "[50dlu,default], $lcgap, default:grow, $lcgap, default",
            "default:grow"));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(_detailsPane);
        }
        contentPane.add(scrollPane1, CC.xy(1, 1));
        contentPane.add(_scheduler, CC.xy(3, 1, CC.DEFAULT, CC.FILL));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane1;
    private JTextPane _detailsPane;
    private Scheduler _scheduler;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
