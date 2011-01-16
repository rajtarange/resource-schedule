/*
 * Created by JFormDesigner on Thu Jan 13 18:25:41 PST 2011
 */

package com.thirdnf.ResourceScheduler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.*;
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
        label1 = new JLabel();
        textField1 = new JTextField();
        scrollPane1 = new JScrollPane();
        _detailsPane = new JTextPane();
        _scheduler = new Scheduler();
        button2 = new JButton();
        button3 = new JButton();
        button1 = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 300));
        setTitle("Resource Scheduler Demo");
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
            "2*([50dlu,default], $lcgap), default:grow",
            "default, $lgap, default:grow, 2*($lgap, default)"));

        //---- label1 ----
        label1.setText("Date:");
        contentPane.add(label1, CC.xy(1, 1));
        contentPane.add(textField1, CC.xy(3, 1));

        //======== scrollPane1 ========
        {

            //---- _detailsPane ----
            _detailsPane.setBorder(new TitledBorder("Appointment Details"));
            scrollPane1.setViewportView(_detailsPane);
        }
        contentPane.add(scrollPane1, CC.xywh(1, 3, 3, 1, CC.DEFAULT, CC.FILL));
        contentPane.add(_scheduler, CC.xywh(5, 1, 1, 7, CC.DEFAULT, CC.FILL));

        //---- button2 ----
        button2.setText("Add Resource");
        contentPane.add(button2, CC.xy(1, 5));

        //---- button3 ----
        button3.setText("Add Appointment");
        contentPane.add(button3, CC.xy(3, 5));

        //---- button1 ----
        button1.setText("Print");
        contentPane.add(button1, CC.xywh(1, 7, 3, 1));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JTextField textField1;
    private JScrollPane scrollPane1;
    private JTextPane _detailsPane;
    private Scheduler _scheduler;
    private JButton button2;
    private JButton button3;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
