/*
 * Created by JFormDesigner on Thu Jan 20 10:45:23 PST 2011
 */

package com.thirdnf.ResourceScheduler.demo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.thirdnf.ResourceScheduler.Resource;
import com.thirdnf.ResourceScheduler.ResourceVisitor;
import com.thirdnf.ResourceScheduler.ScheduleModel;
import org.jetbrains.annotations.NotNull;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * Dialog to add an appointment.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class AppointmentDialog extends JDialog
{
    private static final LocalTime StartTime = new LocalTime(6, 0, 0);
    private static final LocalTime EndTime   = new LocalTime(18, 0, 0);
    private static final Duration  Increment = Duration.standardMinutes(15);
    private final LocalDate _date;

    public AppointmentDialog(@NotNull Frame owner, @NotNull LocalDate date, @NotNull ScheduleModelDemo model)
    {
        super(owner);
        initComponents();
        _date = date;

        initialize(model);

        setTitle("Add Appointment");
    }


    private void initialize(ScheduleModelDemo model)
    {
        // We are using a combo box simply because we are too lazy to try to parse a string and give helpful
        //  error messages.  This is a demo after all.
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
            public boolean visitCategory(DemoCategory category)
            {
                _categoryCombo.addItem(category);
                return true;
            }
        });
    }


    private void handleOkay()
    {
        dispose();
    }


    private void handleCancelButton()
    {
        dispose();
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
        textField1 = new JTextField();
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
                contentPanel.add(label1, CC.xy(1, 1));
                contentPanel.add(textField1, CC.xy(3, 1));

                //---- label2 ----
                label2.setText("Start Time");
                contentPanel.add(label2, CC.xy(1, 3));
                contentPanel.add(_startTimeCombo, CC.xy(3, 3));

                //---- label3 ----
                label3.setText("Duration");
                contentPanel.add(label3, CC.xy(1, 5));

                //======== panel1 ========
                {
                    panel1.setLayout(new FormLayout(
                        "[50dlu,default]:grow, $lcgap, default:grow",
                        "default"));
                    panel1.add(_durationSpinner, CC.xy(1, 1));

                    //---- label5 ----
                    label5.setText("minutes");
                    panel1.add(label5, CC.xy(3, 1));
                }
                contentPanel.add(panel1, CC.xy(3, 5));

                //---- label4 ----
                label4.setText("Resource");
                contentPanel.add(label4, CC.xy(1, 7));
                contentPanel.add(_resourceCombo, CC.xy(3, 7));

                //---- label6 ----
                label6.setText("Category");
                contentPanel.add(label6, CC.xy(1, 9));
                contentPanel.add(_categoryCombo, CC.xy(3, 9));
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
                buttonBar.add(okButton, CC.xy(2, 1));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleCancelButton();
                    }
                });
                buttonBar.add(cancelButton, CC.xy(4, 1));
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
    private JTextField textField1;
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
