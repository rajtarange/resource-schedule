package com.thirdnf.resourceScheduler.example;

import com.thirdnf.resourceScheduler.Appointment;
import com.thirdnf.resourceScheduler.components.BasicAppointmentComponent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



public class ExampleAppointmentComponent extends BasicAppointmentComponent implements MouseListener
{
    // The single action listener which will get click events.  This is a single entity for now, but we
    //  could make this a list if there was a need for it.
    private AppointmentListener _appointmentListener = null;

    private final JPopupMenu _popupMenu;

    /**
     * Constructor given an appointment to wrap.
     *
     * @param appointment (not null) The appointment to wrap.
     */
    public ExampleAppointmentComponent(@NotNull Appointment appointment)
    {
        super(appointment);

        // Allow this instance to respond to mouse clicks.  I'm a bit uncomfortable with accessing 'this' at
        //  this point, but I think that is just an old C++ fear.
        addMouseListener(this);

        _popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (_appointmentListener != null) { _appointmentListener.handleEdit(_appointment); }
            }
        });
        _popupMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (_appointmentListener != null) { _appointmentListener.handleDelete(_appointment); }
            }
        });
        _popupMenu.add(deleteItem);
    }


    @Override
    public void paintComponent(Graphics g)
    {
        // If our resource is a Demo Resource (and it really should be) then get its color.
        // We do this here so that if the resource is updated we will pick up its new color
        if (_appointment instanceof ExampleAppointment) {
            Color c = ((ExampleAppointment)_appointment).getCategory().getColor();
            setBackground(c);
        }

        super.paintComponent(g);
    }


    /**
     * Set the one and only appointment listener which will get called on mouse clicks.
     *
     * @param appointmentListener (not null) The appointment listener who cares about mouse clicks.
     */
    public void setAppointmentListener(@NotNull AppointmentListener appointmentListener)
    {
        _appointmentListener = appointmentListener;
    }


    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (_appointmentListener != null) {
            _appointmentListener.handleClick(_appointment, e.getClickCount());
        }
    }


    @Override
    public void mousePressed(MouseEvent e)
    {
        maybeShowPopup(e);
    }


    @Override
    public void mouseReleased(MouseEvent e)
    {
        maybeShowPopup(e);
    }


    @Override
    public void mouseEntered(MouseEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public void mouseExited(MouseEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    private void maybeShowPopup(MouseEvent e)
    {
        if (e.isPopupTrigger()) {
            _popupMenu.show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }
}
