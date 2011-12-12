/*
 * Created by JFormDesigner on Thu Jan 20 10:45:23 PST 2011
 */

package com.thirdnf.resourceScheduler.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.thirdnf.resourceScheduler.Resource;
import com.thirdnf.resourceScheduler.ResourceVisitor;
import org.jetbrains.annotations.NotNull;
import org.joda.time.*;


/**
 * Dialog to add an appointment.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
@SuppressWarnings({"FieldCanBeLocal"})
public class AppointmentDialog extends JDialog
{
    private static final LocalTime StartTime = new LocalTime(6, 0, 0);
    private static final LocalTime EndTime   = new LocalTime(18, 0, 0);
    private static final Duration  Increment = Duration.standardMinutes(15);
    private LocalDate _date;
    private ExampleAppointment _appointment;

    private IOkayListener _listener;


    public AppointmentDialog(@NotNull Frame owner, @NotNull LocalDate date,
                             @NotNull ExampleScheduleModel model)
    {
        super(owner);
        initComponents();
        _date = date;
        _appointment = null;

        initialize(model);

        setTitle("Add Appointment");
    }


    public AppointmentDialog(@NotNull Frame owner, @NotNull Resource resource, @NotNull DateTime dateTime,
                             @NotNull ExampleScheduleModel model)
    {
        super(owner);
        initComponents();
        _date = dateTime.toLocalDate();

        _appointment = null;

        initialize(model);
        LocalTime last = null;
        LocalTime localTime = dateTime.toLocalTime();
        for (int index=0; index<_startTimeCombo.getItemCount(); ++index) {
            LocalTime time = (LocalTime) _startTimeCombo.getItemAt(index);
            if (time.compareTo(localTime) > 0) {
                if (last != null) { _startTimeCombo.setSelectedItem(last); }
                break;
            }
            last = time;
        }
        _resourceCombo.setSelectedItem(resource);
    }

    public AppointmentDialog(@NotNull Frame owner, @NotNull ExampleAppointment appointment,
                             @NotNull ExampleScheduleModel model)
    {
        super(owner);
        initComponents();
        _date = appointment.getDateTime().toLocalDate();
        _appointment = appointment;

        initialize(model);

        _titleField.setText(_appointment.getTitle());
        _categoryCombo.setSelectedItem(_appointment.getCategory());
        _resourceCombo.setSelectedItem(_appointment.getResource());
        _startTimeCombo.setSelectedItem(_appointment.getDateTime().toLocalTime());
        _durationSpinner.setValue((int) (_appointment.getDuration().getStandardSeconds()/60));

        setTitle("Edit Appointment");
    }


    private void initialize(ExampleScheduleModel model)
    {
        // We are using a combo box simply because we are too lazy to try to parse a string and give helpful
        //  error messages.  This is a example after all.
        _startTimeCombo.setRenderer(new TimeCellRenderer());
        for (LocalTime time = StartTime; time.compareTo(EndTime) <= 0; time = time.plus(Increment.toPeriod())) {
            _startTimeCombo.addItem(time);
        }

        model.visitResources(new ResourceVisitor()
        {
            @Override
            public boolean visitResource(@NotNull Resource resource)
            {
                _resourceCombo.addItem(resource);
                return true;
            }
        }, _date);

        model.visitCategories(new CategoryVisitor()
        {
            @Override
            public boolean visitCategory(ExampleCategory category)
            {
                _categoryCombo.addItem(category);
                return true;
            }
        });
    }


    public void setOkayListener(@NotNull IOkayListener listener)
    {
        _listener = listener;
    }


    private void handleOkay()
    {
        if (_listener != null) {
            String title = _titleField.getText().trim();
            ExampleResource resource = (ExampleResource) _resourceCombo.getSelectedItem();
            ExampleCategory category = (ExampleCategory) _categoryCombo.getSelectedItem();
            LocalTime startTime = (LocalTime) _startTimeCombo.getSelectedItem();
            int duration = (Integer) _durationSpinner.getValue();

            if (_appointment == null) {
                _appointment = new ExampleAppointment(title, category, resource);
            }
            else {
                _appointment.setTitle(title);
                _appointment.setResource(resource);
                _appointment.setCategory(category);
            }
            _appointment.setDateTime(new LocalDateTime(_date.year().get(), _date.monthOfYear().get(),
                    _date.dayOfMonth().get(), startTime.getHourOfDay(),
                    startTime.getMinuteOfHour(), startTime.getSecondOfMinute(), 0));
            _appointment.setDuration(Duration.standardMinutes(duration));

            _listener.handleOkay(_appointment);
        }

        dispose();
    }


    /**
     * Handle the user clicking the cancel button.  This just closes the dialog.
     */
    private void handleCancelButton()
    {
        dispose();
    }



    public static interface IOkayListener
    {
        /**
         * Handle the okay button being clicked.  This should send back the appointment that was
         *  added or updated.
         *
         * @param appointment (not null) Appointment which was either created or updated.
         */
        void handleOkay(@NotNull ExampleAppointment appointment);
    }


    private static class TimeCellRenderer extends DefaultListCellRenderer
    {
        @Override
        public Component getListCellRendererComponent(@NotNull JList list, @NotNull Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus)
        {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setText(((LocalTime) value).toString("h:mm a"));

            return this;
        }
    }



    /** Init Components ... owned by JFormDesigner */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        _titleField = new JTextField();
        label2 = new JLabel();
        _startTimeCombo = new JComboBox();
        label3 = new JLabel();
        panel1 = new JPanel();
        _durationSpinner = new JSpinner();
        label5 = new JLabel();
        label4 = new JLabel();
        _resourceCombo = new JComboBox();
        label6 = new JLabel();
        _categoryCombo = new JComboBox();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();
        CellConstraints cc = new CellConstraints();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(Borders.DIALOG_BORDER);
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new FormLayout(
                    "default, $lcgap, [50dlu,default]:grow",
                    "4*(default, $lgap), default"));

                //---- label1 ----
                label1.setText("Title");
                contentPanel.add(label1, cc.xy(1, 1));
                contentPanel.add(_titleField, cc.xy(3, 1));

                //---- label2 ----
                label2.setText("Start Time");
                contentPanel.add(label2, cc.xy(1, 3));
                contentPanel.add(_startTimeCombo, cc.xy(3, 3));

                //---- label3 ----
                label3.setText("Duration");
                contentPanel.add(label3, cc.xy(1, 5));

                //======== panel1 ========
                {
                    panel1.setLayout(new FormLayout(
                        "[50dlu,default]:grow, $lcgap, default:grow",
                        "default"));
                    panel1.add(_durationSpinner, cc.xy(1, 1));

                    //---- label5 ----
                    label5.setText("minutes");
                    panel1.add(label5, cc.xy(3, 1));
                }
                contentPanel.add(panel1, cc.xy(3, 5));

                //---- label4 ----
                label4.setText("Resource");
                contentPanel.add(label4, cc.xy(1, 7));
                contentPanel.add(_resourceCombo, cc.xy(3, 7));

                //---- label6 ----
                label6.setText("Category");
                contentPanel.add(label6, cc.xy(1, 9));
                contentPanel.add(_categoryCombo, cc.xy(3, 9));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
                buttonBar.setLayout(new FormLayout(
                    "$glue, $button, $rgap, $button",
                    "pref"));

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleOkay();
                    }
                });
                buttonBar.add(okButton, cc.xy(2, 1));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleCancelButton();
                    }
                });
                buttonBar.add(cancelButton, cc.xy(4, 1));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JTextField _titleField;
    private JLabel label2;
    private JComboBox _startTimeCombo;
    private JLabel label3;
    private JPanel panel1;
    private JSpinner _durationSpinner;
    private JLabel label5;
    private JLabel label4;
    private JComboBox _resourceCombo;
    private JLabel label6;
    private JComboBox _categoryCombo;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
