/*
 * Created by JFormDesigner on Thu Jan 20 08:32:49 PST 2011
 */

package com.thirdnf.ResourceScheduler.demo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.thirdnf.ResourceScheduler.Resource;
import org.jetbrains.annotations.NotNull;

/**
 * @author Joshua Gerth
 */
public class ResourceDialog extends JDialog
{
    private IOkayListener _listener;
    private Color _color = Color.gray;

    /**
     * Resource dialog constructor for a new resource.
     *
     * @param owner (not null) Owning frame for the dialog.
     */
    public ResourceDialog(@NotNull Frame owner)
    {
        super(owner);
        initComponents();

        setTitle("Add Resource");
    }


    /**
     * Resource dialog constructor for editing a resource.
     *
     * @param owner (not null) Owning frame for the dialog.
     * @param resource (not null) Resource to edit.
     */
    public ResourceDialog(@NotNull Frame owner, @NotNull Resource resource)
    {
        super(owner);
        initComponents();

        setTitle("Edit Resource");

        _titleField.setText(resource.getTitle());
//        _color = resource.getColor();
        _selectButton.setBackground(_color);
    }


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

        System.out.println("Using column: " + column);
        if (_listener != null) {
            _listener.handleOkay(_titleField.getText(), _color, column-1);
        }
        dispose();
    }

    private void handleCancel()
    {
        dispose();
    }

    private void handleSelectColor()
    {
        _color = JColorChooser.showDialog(this, "Choose a color", _color );
        _selectButton.setBackground(_color);
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
        void handleOkay(@NotNull String title, @NotNull Color color, int column);
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        _titleField = new JTextField();
        label2 = new JLabel();
        _selectButton = new JButton();
        label3 = new JLabel();
        panel1 = new JPanel();
        _addRadio = new JRadioButton();
        _columnRadio = new JRadioButton();
        _columnField = new JTextField();
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
                    "default, $lcgap, [100dlu,default]:grow",
                    "2*(default, $lgap), default"));

                //---- label1 ----
                label1.setText("Enter Resource Name:");
                contentPanel.add(label1, CC.xy(1, 1));
                contentPanel.add(_titleField, CC.xy(3, 1));

                //---- label2 ----
                label2.setText("Color:");
                contentPanel.add(label2, CC.xy(1, 3));

                //---- _selectButton ----
                _selectButton.setText("Select");
                _selectButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleSelectColor();
                    }
                });
                contentPanel.add(_selectButton, CC.xy(3, 3));

                //---- label3 ----
                label3.setText("Column");
                contentPanel.add(label3, CC.xy(1, 5));

                //======== panel1 ========
                {
                    panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

                    //---- _addRadio ----
                    _addRadio.setText("Add");
                    _addRadio.setSelected(true);
                    _addRadio.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            handleAddRadioClicked();
                        }
                    });
                    panel1.add(_addRadio);

                    //---- _columnRadio ----
                    _columnRadio.setText("Specify Column");
                    _columnRadio.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            handleColumnRadioClicked();
                        }
                    });
                    panel1.add(_columnRadio);

                    //---- _columnField ----
                    _columnField.setEnabled(false);
                    panel1.add(_columnField);
                }
                contentPanel.add(panel1, CC.xy(3, 5, CC.DEFAULT, CC.FILL));
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
                        handleCancel();
                    }
                });
                buttonBar.add(cancelButton, CC.xy(4, 1));
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
    private JButton _selectButton;
    private JLabel label3;
    private JPanel panel1;
    private JRadioButton _addRadio;
    private JRadioButton _columnRadio;
    private JTextField _columnField;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}