/*
 * Created by JFormDesigner on Thu Jan 20 08:32:49 PST 2011
 */

package com.thirdnf.resourceScheduler.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import org.jetbrains.annotations.NotNull;
import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.joda.time.Period;

/**
 * @author Joshua Gerth
 */
public class ResourceDialog extends JDialog
{
    private static final Color[] Colors = new Color[] {
            new Color(51, 204, 255),
            new Color(55, 102, 205),
            new Color(204, 255, 51),
            new Color(251, 198, 12, 200),
            new Color(12, 251, 160, 200),
            new Color(166, 251, 12, 200),
            new Color(66, 151, 12, 200),
    };


    private static final LocalTime[] StartTimes = new LocalTime[] {
            new LocalTime(8, 0, 0),
            new LocalTime(8, 30, 0),
            new LocalTime(9, 0, 0),
            new LocalTime(9, 30, 0),
            new LocalTime(10, 0, 0)
    };


    private static final LocalTime[] EndTimes = new LocalTime[] {
            new LocalTime(15, 0, 0),
            new LocalTime(15, 30, 0),
            new LocalTime(16, 0, 0),
            new LocalTime(16, 30, 0),
            new LocalTime(17, 0, 0)
    };

    private IOkayListener _listener;
    private ExampleResource _resource;

    /**
     * Resource dialog constructor for a new resource.
     *
     * @param owner (not null) Owning frame for the dialog.
     */
    public ResourceDialog(@NotNull Frame owner)
    {
        super(owner);
        initComponents();

        initialize();

        setTitle("Add Resource");
    }


    /**
     * Resource dialog constructor for editing a resource.
     *
     * @param owner (not null) Owning frame for the dialog.
     * @param resource (not null) Resource to edit.
     */
    public ResourceDialog(@NotNull Frame owner, @NotNull ExampleResource resource)
    {
        super(owner);
        initComponents();

        initialize();

        _resource = resource;

        setTitle("Edit Resource");

        _titleField.setText(resource.getTitle());

        _colorSelect.setSelectedItem(resource.getColor());

        // For edits don't let them change the column.  Changing the column means it is basically just
        //  an add and we want to test/example update
        _columnLabel.setVisible(false);
        _columnPanel.setVisible(false);

        _lunchCheckbox.setSelected(resource.getTakeLunch());
        LocalTime startTime = resource.getStartTime();
        _startTimeSelect.setSelectedItem(startTime);
        Duration duration = resource.getDuration();
        LocalTime endTime = startTime.plus(duration.toPeriod());
        _endTimeSelect.setSelectedItem(endTime);
    }


    /**
     * Private initialize method which just sets up a default regardless if we are editing a resource or
     *  adding new one.
     */
    private void initialize()
    {
        _colorSelect.setRenderer(new ColoredCellRenderer());
        for (Color c : Colors) {
            _colorSelect.addItem(c);
        }

        _startTimeSelect.setRenderer(new TimeCellRenderer());
        for (LocalTime time : StartTimes) {
            _startTimeSelect.addItem(time);
        }

        _endTimeSelect.setRenderer(new TimeCellRenderer());
        for (LocalTime time : EndTimes) {
            _endTimeSelect.addItem(time);
        }
    }


    /**
     * Handle when the user has clicked on the okay button.  This builds or updates the resource and
     * notifies the okay listener if one was configured.
     */
    private void handleOkay()
    {
        int column = -1;

        // If they have decided to go with a specified column then try to parse that, if it fails we default to -1
        //  which is an add
        if (_columnRadio.isSelected()) {
            String columnString = _columnField.getText().trim();
            if (! columnString.isEmpty()) {
                try {
                    column = Integer.parseInt(columnString);
                }
                catch (NumberFormatException ignored) {
                    column = -1;
                }
            }
        }

        if (_listener != null) {
            Color color = (Color)_colorSelect.getSelectedItem();
            if (_resource == null) {
                _resource = new ExampleResource(_titleField.getText(), color);
            }
            else {
                _resource.setTitle(_titleField.getText());
                _resource.setColor(color);
            }
            LocalTime startTime = (LocalTime)(_startTimeSelect.getSelectedItem());
            LocalTime endTime   = (LocalTime)(_endTimeSelect.getSelectedItem());
            Duration duration = Period.fieldDifference(startTime, endTime).toStandardDuration();
            _resource.setStartTime(startTime);
            _resource.setDuration(duration);
            _resource.setTakeLunch(_lunchCheckbox.isSelected());

            _listener.handleOkay(_resource, column - 1);
        }
        dispose();
    }


    /**
     * Handle when the user clicks on the cancel ... which is to just close the dialog.
     */
    private void handleCancel()
    {
        dispose();
    }

    private void handleAddRadioClicked()
    {
        _columnField.setText("");
        _columnField.setEnabled(false);
    }

    private void handleColumnRadioClicked()
    {
        _columnField.setEnabled(true);
    }


    public void setOkayListener(@NotNull IOkayListener listener)
    {
        _listener = listener;
    }


    public static interface IOkayListener
    {
        void handleOkay(@NotNull ExampleResource resource, int column);
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


    private static class ColoredCellRenderer extends DefaultListCellRenderer
    {
        private Border _unselectedBorder = null;
        private Border _selectedBorder = null;
        private boolean _isBordered = true;

        @Override
        public void setBackground(Color col)
        {
            // Ignore setting the background, we will do that
        }


        @Override
        public Component getListCellRendererComponent(@NotNull JList list, @NotNull Object color,
                                                      int index, boolean isSelected, boolean cellHasFocus)
        {
            setText(" ");
            super.setBackground((Color) color);
            if (_isBordered) {
                if (isSelected) {
                    if (_selectedBorder == null) {
                        _selectedBorder = BorderFactory.createMatteBorder(2,5,2,5, list.getSelectionBackground());
                    }
                    setBorder(_selectedBorder);
                }
                else {
                    if (_unselectedBorder == null) {
                        _unselectedBorder = BorderFactory.createMatteBorder(2,5,2,5, list.getBackground());
                    }
                    setBorder(_unselectedBorder);
                }
            }
            return this;
        }
    }


    /** JFormDesigner init ... don't mess */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        _titleField = new JTextField();
        label2 = new JLabel();
        _colorSelect = new JComboBox();
        label3 = new JLabel();
        panel1 = new JPanel();
        label4 = new JLabel();
        _startTimeSelect = new JComboBox();
        label5 = new JLabel();
        _endTimeSelect = new JComboBox();
        _lunchCheckbox = new JCheckBox();
        _columnLabel = new JLabel();
        _columnPanel = new JPanel();
        _addRadio = new JRadioButton();
        _columnRadio = new JRadioButton();
        _columnField = new JTextField();
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
                    "default, $lcgap, [100dlu,default]:grow",
                    "3*(default, $lgap), default"));

                //---- label1 ----
                label1.setText("Enter Resource Name:");
                contentPanel.add(label1, cc.xy(1, 1));
                contentPanel.add(_titleField, cc.xy(3, 1));

                //---- label2 ----
                label2.setText("Color:");
                contentPanel.add(label2, cc.xy(1, 3));
                contentPanel.add(_colorSelect, cc.xy(3, 3));

                //---- label3 ----
                label3.setText("Availability:");
                contentPanel.add(label3, cc.xy(1, 5));

                //======== panel1 ========
                {
                    panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

                    //---- label4 ----
                    label4.setText("From");
                    panel1.add(label4);
                    panel1.add(_startTimeSelect);

                    //---- label5 ----
                    label5.setText("To");
                    panel1.add(label5);
                    panel1.add(_endTimeSelect);

                    //---- _lunchCheckbox ----
                    _lunchCheckbox.setText("Lunch Break");
                    panel1.add(_lunchCheckbox);
                }
                contentPanel.add(panel1, cc.xy(3, 5, CellConstraints.DEFAULT, CellConstraints.FILL));

                //---- _columnLabel ----
                _columnLabel.setText("Column");
                contentPanel.add(_columnLabel, cc.xy(1, 7));

                //======== _columnPanel ========
                {
                    _columnPanel.setLayout(new BoxLayout(_columnPanel, BoxLayout.X_AXIS));

                    //---- _addRadio ----
                    _addRadio.setText("Add");
                    _addRadio.setSelected(true);
                    _addRadio.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            handleAddRadioClicked();
                        }
                    });
                    _columnPanel.add(_addRadio);

                    //---- _columnRadio ----
                    _columnRadio.setText("Specify Column");
                    _columnRadio.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            handleColumnRadioClicked();
                        }
                    });
                    _columnPanel.add(_columnRadio);

                    //---- _columnField ----
                    _columnField.setEnabled(false);
                    _columnPanel.add(_columnField);
                }
                contentPanel.add(_columnPanel, cc.xy(3, 7, CellConstraints.DEFAULT, CellConstraints.FILL));
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
                        handleCancel();
                    }
                });
                buttonBar.add(cancelButton, cc.xy(4, 1));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(_addRadio);
        buttonGroup1.add(_columnRadio);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JTextField _titleField;
    private JLabel label2;
    private JComboBox _colorSelect;
    private JLabel label3;
    private JPanel panel1;
    private JLabel label4;
    private JComboBox _startTimeSelect;
    private JLabel label5;
    private JComboBox _endTimeSelect;
    private JCheckBox _lunchCheckbox;
    private JLabel _columnLabel;
    private JPanel _columnPanel;
    private JRadioButton _addRadio;
    private JRadioButton _columnRadio;
    private JTextField _columnField;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
