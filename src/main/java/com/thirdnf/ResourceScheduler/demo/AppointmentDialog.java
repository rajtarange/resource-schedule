/*
 * Created by JFormDesigner on Thu Jan 20 10:45:23 PST 2011
 */

package com.thirdnf.ResourceScheduler.demo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/**
 * @author Joshua Gerth
 */
public class AppointmentDialog extends JDialog
{
    public AppointmentDialog(Frame owner)
    {
        super(owner);
        initComponents();
    }

    private void handleCancel()
    {
        dispose();
    }

    private void handleOkay()
    {
        dispose();
    }

    private void handleCancelButton() {
        // TODO add your code here
    }



    /** Init Components ... owned by JFormDesigner */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        label3 = new JLabel();
        textField3 = new JTextField();
        label4 = new JLabel();
        comboBox1 = new JComboBox();
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
                    "3*(default, $lgap), default"));

                //---- label1 ----
                label1.setText("Title");
                contentPanel.add(label1, CC.xy(1, 1));
                contentPanel.add(textField1, CC.xy(3, 1));

                //---- label2 ----
                label2.setText("Start Time");
                contentPanel.add(label2, CC.xy(1, 3));
                contentPanel.add(textField2, CC.xy(3, 3));

                //---- label3 ----
                label3.setText("End Time");
                contentPanel.add(label3, CC.xy(1, 5));
                contentPanel.add(textField3, CC.xy(3, 5));

                //---- label4 ----
                label4.setText("Resource");
                contentPanel.add(label4, CC.xy(1, 7));
                contentPanel.add(comboBox1, CC.xy(3, 7));
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
    private JTextField textField2;
    private JLabel label3;
    private JTextField textField3;
    private JLabel label4;
    private JComboBox comboBox1;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
