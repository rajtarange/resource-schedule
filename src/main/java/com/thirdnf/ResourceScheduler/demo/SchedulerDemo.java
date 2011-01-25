/*
 * Created by JFormDesigner on Thu Jan 13 18:25:41 PST 2011
 */

package com.thirdnf.ResourceScheduler.demo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.*;
import javax.swing.border.*;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.thirdnf.ResourceScheduler.Appointment;
import com.thirdnf.ResourceScheduler.Resource;
import com.thirdnf.ResourceScheduler.Scheduler;
import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;


/**
 * Just a demo application to show how to use the ResourceScheduler.  The coding in here is very
 * rough and not polished.  I would not necessarily suggest copying this code exactly into your
 * production application.  You can if you choose, but some of this was hacked to make it
 * work rather than make it production worth.
 *
 * @author Joshua Gerth
 */
@SuppressWarnings({"FieldCanBeLocal"})
public class SchedulerDemo extends JFrame
{
    private final ScheduleModelDemo _model;

    private static final LocalDate Today = new LocalDate();
    private static final LocalDate Tomorrow = Today.plusDays(1);

    /**
     * Main entry point.  This method is responsible for creating the main window and showing it.
     * @param args (not null) Any args which are passed in.  This is currently ignored.
     */
    public static void main(@NotNull String[] args)
    {
        SchedulerDemo mw = new SchedulerDemo();
        mw.pack();
        mw.setVisible(true);
    }


    /**
     * Main scheduler demo.  This window just has some controls to show off some of the features of the
     * Resource Scheduler and an instance of the Resource Scheduler itself.
     */
    public SchedulerDemo()
    {
        initComponents();

        DemoComponentFactory componentFactory = new DemoComponentFactory();
        componentFactory.setAppointmentListener(new AppointmentListener() {
            @Override
            public void handleClick(@NotNull Appointment appointment)
            {
                handleAppointmentClick(appointment);
            }

            @Override
            public void handleDelete(@NotNull Appointment appointment)
            {
                handleAppointmentDelete(appointment);
            }

            @Override
            public void handleEdit(@NotNull Appointment appointment)
            {
                handleAppointmentEdit(appointment);
            }
        });

        componentFactory.setResourceListener(new ResourceListener()
        {
            @Override
            public void handleClick(@NotNull Resource resource)
            {
                handleResourceClick(resource);
            }

            @Override
            public void handleDelete(@NotNull Resource resource)
            {
                handleResourceDelete(resource);
            }

            @Override
            public void handleEdit(@NotNull Resource resource)
            {
                handleResourceEdit(resource);
            }
        });

        _scheduler.setComponentFactory(componentFactory);

        _model = new ScheduleModelDemo();
        _scheduler.setModel(_model);
        _scheduler.showDate(new LocalDate());
    }


    private void handleAppointmentClick(Appointment appointment)
    {
        StringBuilder stringBuilder = new StringBuilder();
        LocalTime time = appointment.getDateTime().toLocalTime();
        Period period = appointment.getDuration().toPeriod();

        stringBuilder.append("Info About: ").append(appointment.getTitle()).append('\n')
                .append("Start time: ").append(time.toString("h:mm a")).append('\n')
                .append("Duration: ").append(period.toString(PeriodFormat.getDefault())).append('\n');
        stringBuilder.append("For Resource: ").append(appointment.getResource());


        _detailsPane.setText(stringBuilder.toString());
    }


    public void handleAppointmentDelete(@NotNull Appointment appointment)
    {
        System.out.println("Delete request for appointment");
    }


    public void handleAppointmentEdit(@NotNull Appointment appointment)
    {
        System.out.println("Edit request for appointment");
    }


    public void handleResourceClick(@NotNull Resource resource)
    {
        System.out.println("Click request for resource");
    }


    public void handleResourceDelete(@NotNull Resource resource)
    {
        System.out.println("Delete request for resource");
    }


    public void handleResourceEdit(@NotNull Resource resource)
    {
        System.out.println("Edit request for resource");
    }


    /**
     * Handle when the user clicks on the print button.
     */
    private void handlePrint()
    {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(_scheduler);
        if (printJob.printDialog()) {
            try {
                System.out.println("Printing from main");
                printJob.print();
            }
            catch (PrinterException pe) {
                System.out.println("Error printing: " + pe);
            }
        }
    }


    /**
     * Method called when the user has clicked the add resource button and wants to add a resource on the
     *   given day.
     */
    private void handleAddResource()
    {
        ResourceDialog dialog = new ResourceDialog(this);
        dialog.setOkayListener(new ResourceDialog.IOkayListener() {
            @Override
            public void handleOkay(@NotNull String title, @NotNull Color color, int column)
            {
                LocalDate date = _todayRadio.isSelected() ? Today : Tomorrow;
                _model.addResource(date, title, color, column);
            }
        });
        dialog.pack();
        dialog.setVisible(true);
    }


    private void handleSelectToday()
    {
        _scheduler.showDate(Today);
    }


    private void handleSelectTomorrow()
    {
        _scheduler.showDate(Tomorrow);
    }


    /**
     * Initialize the components.
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        panel2 = new JPanel();
        label1 = new JLabel();
        _todayRadio = new JRadioButton();
        _tomorrowRadio = new JRadioButton();
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
                "2*(default, $lcgap), default:grow",
                "default, $lgap, default:grow, 2*($lgap, default)"));

            //======== panel2 ========
            {
                panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

                //---- label1 ----
                label1.setText("Date:");
                panel2.add(label1);

                //---- _todayRadio ----
                _todayRadio.setText("Today");
                _todayRadio.setSelected(true);
                _todayRadio.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleSelectToday();
                    }
                });
                panel2.add(_todayRadio);

                //---- _tomorrowRadio ----
                _tomorrowRadio.setText("Tomorrow");
                _tomorrowRadio.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        handleSelectTomorrow();
                    }
                });
                panel2.add(_tomorrowRadio);
            }
            panel1.add(panel2, CC.xywh(1, 1, 3, 1, CC.FILL, CC.FILL));
            panel1.add(_scheduler, CC.xywh(5, 1, 1, 7, CC.DEFAULT, CC.FILL));

            //======== scrollPane1 ========
            {
                scrollPane1.setMinimumSize(new Dimension(15, 23));

                //---- _detailsPane ----
                _detailsPane.setBorder(new TitledBorder("Appointment Details"));
                scrollPane1.setViewportView(_detailsPane);
            }
            panel1.add(scrollPane1, CC.xywh(1, 3, 3, 1, CC.DEFAULT, CC.FILL));

            //---- button2 ----
            button2.setText("Add Resource");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleAddResource();
                }
            });
            panel1.add(button2, CC.xy(1, 5));

            //---- button3 ----
            button3.setText("Add Appointment");
            panel1.add(button3, CC.xy(3, 5));

            //---- button1 ----
            button1.setText("Print");
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handlePrint();
                }
            });
            panel1.add(button1, CC.xywh(1, 7, 3, 1));
        }
        contentPane.add(panel1, new CellConstraints(1, 1, 1, 1, CC.DEFAULT, CC.FILL, new Insets(5, 5, 5, 5)));
        pack();
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(_todayRadio);
        buttonGroup1.add(_tomorrowRadio);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JPanel panel2;
    private JLabel label1;
    private JRadioButton _todayRadio;
    private JRadioButton _tomorrowRadio;
    private Scheduler _scheduler;
    private JScrollPane scrollPane1;
    private JTextPane _detailsPane;
    private JButton button2;
    private JButton button3;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
