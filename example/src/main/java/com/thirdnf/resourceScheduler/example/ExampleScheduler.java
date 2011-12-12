/*
 * Created by JFormDesigner on Thu Jan 13 18:25:41 PST 2011
 */

package com.thirdnf.resourceScheduler.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;

import com.jgoodies.forms.layout.*;
import com.thirdnf.resourceScheduler.Appointment;
import com.thirdnf.resourceScheduler.Resource;
import com.thirdnf.resourceScheduler.ScheduleListener;
import com.thirdnf.resourceScheduler.Scheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;


/**
 * Just a example application to show how to use the ResourceScheduler.  The coding in here is very
 * rough and not polished.  I would not necessarily suggest copying this code exactly into your
 * production application.  You can if you choose, but some of this was hacked to make it
 * work rather than make it production worth.
 *
 * @author Joshua Gerth
 */
@SuppressWarnings({"FieldCanBeLocal"})
public class ExampleScheduler extends JFrame
{
    private final ExampleScheduleModel _model;

    private static final LocalDate Today = new LocalDate();
    private static final LocalDate Tomorrow = Today.plusDays(1);

    /**
     * Main entry point.  This method is responsible for creating the main window and showing it.
     * @param args (not null) Any args which are passed in.  This is currently ignored.
     */
    public static void main(@NotNull String[] args)
    {
        ExampleScheduler mw = new ExampleScheduler();
        mw.pack();
        mw.setVisible(true);
    }


    /**
     * Main scheduler example.  This window just has some controls to show off some of the features of the
     * Resource Scheduler and an instance of the Resource Scheduler itself.
     */
    public ExampleScheduler()
    {
        initComponents();
        _scheduler.addScheduleListener(new ScheduleListener()
        {
            @Override
            public void actionPerformed(@NotNull Resource resource, @NotNull DateTime time)
            {
                handleAddAppointment(resource, time);
            }
        });

        _model = new ExampleScheduleModel();

        ExampleComponentFactory componentFactory = new ExampleComponentFactory();
        componentFactory.setAppointmentListener(new AppointmentListener() {
            @Override
            public void handleClick(@NotNull Appointment appointment, int clickCount)
            {
                if (clickCount == 1) {
                    handleAppointmentClick(appointment);
                }
                else {
                    handleAppointmentEdit(appointment);
                }
            }

            @Override
            public void handleDelete(@NotNull Appointment appointment)
            {
                _model.deleteAppointment(appointment);
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
            public void handleDelete(@NotNull Resource resource)
            {
                // Pass this directly down to the model
                LocalDate date = _todayRadio.isSelected() ? Today : Tomorrow;
                _model.deleteResource(resource, date);
            }

            @Override
            public void handleEdit(@NotNull Resource resource)
            {
                handleResourceEdit(resource);
            }
        });

        _scheduler.setComponentFactory(componentFactory);

        _scheduler.setModel(_model);
        _scheduler.showDate(new LocalDate());
    }


    /**
     * Handle when the user clicks on an appointment.  This just displays appointment information in the
     * appointment text area.
     *
     * @param appointment (not null) The appointment clicked on
     */
    private void handleAppointmentClick(@NotNull Appointment appointment)
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


    public void handleAppointmentEdit(@NotNull Appointment appointment)
    {
        if (! (appointment instanceof ExampleAppointment)) {
            System.err.println("Can't edit non-example appointment.");
            return;
        }
        ExampleAppointment exampleAppointment = (ExampleAppointment)appointment;

        AppointmentDialog dialog = new AppointmentDialog(this, exampleAppointment, _model);
        dialog.setOkayListener(new AppointmentDialog.IOkayListener() {
            @Override
            public void handleOkay(@NotNull ExampleAppointment appointment)
            {
                _model.updateAppointment(appointment);
            }
        });
        dialog.pack();
        dialog.setVisible(true);
    }


    public void handleResourceEdit(@NotNull Resource resource)
    {
        if (! (resource instanceof ExampleResource)) {
            System.err.println("Can't edit a non-example resource");
            return;
        }

        ExampleResource exampleResource = (ExampleResource) resource;

        ResourceDialog dialog = new ResourceDialog(this, exampleResource);
        dialog.setOkayListener(new ResourceDialog.IOkayListener() {
            @Override
            public void handleOkay(@NotNull ExampleResource resource, int column)
            {
                _model.updateResource(resource);
            }
        });
        dialog.pack();
        dialog.setVisible(true);
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
            public void handleOkay(@NotNull ExampleResource resource, int column)
            {
                LocalDate date = _todayRadio.isSelected() ? Today : Tomorrow;
                _model.addResource(resource, date, column);
            }
        });
        dialog.pack();
        dialog.setVisible(true);
    }


    /**
     * Handle the user clicking on the Today radio button by telling the scheduler the date has changed.
     */
    private void handleSelectToday()
    {
        _scheduler.showDate(Today);
    }


    /**
     * Handle the user clicking on the Tomorrow radio button by telling the scheduler the date has changed.
     */
    private void handleSelectTomorrow()
    {
        _scheduler.showDate(Tomorrow);
    }


    /**
     * Handle an add appointment.  This is usually from when the client has clicked
     * the 'add appointment' button, as opposed to responding to a click on the
     * schedule itself.
     */
    private void handleAddAppointment()
    {
        LocalDate date = _todayRadio.isSelected() ? Today : Tomorrow;
        AppointmentDialog dialog = new AppointmentDialog(this, date, _model);
        dialog.setOkayListener(new AppointmentDialog.IOkayListener() {
            @Override
            public void handleOkay(@NotNull ExampleAppointment appointment)
            {
                _model.addAppointment(appointment);
            }
        });
        dialog.pack();
        dialog.setVisible(true);
    }


    private void handleAddAppointment(@Nullable Resource resource, @NotNull DateTime dateTime)
    {
        AppointmentDialog dialog = new AppointmentDialog(this, resource, dateTime, _model);
        dialog.setOkayListener(new AppointmentDialog.IOkayListener() {
            @Override
            public void handleOkay(@NotNull ExampleAppointment appointment)
            {
                _model.addAppointment(appointment);
            }
        });
        dialog.pack();
        dialog.setVisible(true);
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
                _tomorrowRadio.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleSelectTomorrow();
                    }
                });
                panel2.add(_tomorrowRadio);
            }
            panel1.add(panel2, CC.xywh(1, 1, 3, 1, CC.FILL, CC.FILL));

            //======== scrollPane1 ========
            {
                scrollPane1.setMinimumSize(new Dimension(15, 23));

                //---- _detailsPane ----
                _detailsPane.setBorder(new TitledBorder("Appointment Details"));
                scrollPane1.setViewportView(_detailsPane);
            }
            panel1.add(scrollPane1, CC.xywh(1, 3, 3, 1, CC.DEFAULT, CC.FILL));
            panel1.add(_scheduler, CC.xywh(5, 1, 1, 7));

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
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleAddAppointment();
                }
            });
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
    private JScrollPane scrollPane1;
    private JTextPane _detailsPane;
    private Scheduler _scheduler;
    private JButton button2;
    private JButton button3;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
