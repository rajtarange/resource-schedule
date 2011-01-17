/*
 * Created by JFormDesigner on Thu Jan 13 18:25:41 PST 2011
 */

package com.thirdnf.ResourceScheduler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.*;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import org.jetbrains.annotations.NotNull;

/**
 * Just a demo application to show how to use the ResourceScheduler
 *
 * @author Joshua Gerth
 */
public class SchedulerDemo extends JFrame
{

    public static void main(@NotNull String[] args)
    {
        SchedulerDemo mw = new SchedulerDemo();
        mw.pack();
        mw.setVisible(true);
    }

    public SchedulerDemo()
    {
        initComponents();

        _scheduler.setModel(new ScheduleModelDemo());
        _scheduler.showDate(new Date());
        _scheduler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                IAppointment appointment = (IAppointment)e.getSource();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Info About: ").append(appointment.getTitle()).append('\n')
                        .append("Start time: ").append(appointment.getTime()).append('\n')
                        .append("For Resource: ").append(appointment.getResource());


                _detailsPane.setText(stringBuilder.toString());
            }
        });
    }


    private void handlePrint()
    {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(_scheduler);
        if (printJob.printDialog()) {
            try {
                System.out.println("Printing from main");
                printJob.print();
            }
            catch(PrinterException pe) {
                System.out.println("Error printing: " + pe);
            }
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        _scheduler = new Scheduler();
        scrollPane1 = new JScrollPane();
        _detailsPane = new JTextPane();
        button2 = new JButton();
        button3 = new JButton();
        button1 = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 300));
        setTitle("Resource Scheduler Demo");
        Container contentPane = getContentPane();
        contentPane.setLayout(new FormLayout(
                "default:grow",
                "default:grow"));

        //======== panel1 ========
        {
            panel1.setLayout(new FormLayout(
                    "default, $lcgap, [40dlu,default], $lcgap, default:grow",
                    "default, $lgap, default:grow, 2*($lgap, default)"));

            //---- label1 ----
            label1.setText("Date:");
            panel1.add(label1, CC.xy(1, 1));
            panel1.add(textField1, CC.xy(3, 1));
            panel1.add(_scheduler, CC.xywh(5, 1, 1, 7, CC.DEFAULT, CC.FILL));

            //======== scrollPane1 ========
            {

                //---- _detailsPane ----
                _detailsPane.setBorder(new TitledBorder("Appointment Details"));
                scrollPane1.setViewportView(_detailsPane);
            }
            panel1.add(scrollPane1, CC.xywh(1, 3, 3, 1, CC.DEFAULT, CC.FILL));

            //---- button2 ----
            button2.setText("Add Resource");
            panel1.add(button2, CC.xy(1, 5));

            //---- button3 ----
            button3.setText("Add Appointment");
            panel1.add(button3, CC.xy(3, 5));

            //---- button1 ----
            button1.setText("Print");
            button1.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    handlePrint();
                }
            });
            panel1.add(button1, CC.xywh(1, 7, 3, 1));
        }
        contentPane.add(panel1, new CellConstraints(1, 1, 1, 1, CC.DEFAULT, CC.FILL, new Insets(5, 5, 5, 5)));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label1;
    private JTextField textField1;
    private Scheduler _scheduler;
    private JScrollPane scrollPane1;
    private JTextPane _detailsPane;
    private JButton button2;
    private JButton button3;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
