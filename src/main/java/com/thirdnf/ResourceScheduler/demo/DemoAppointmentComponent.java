package com.thirdnf.ResourceScheduler.demo;

import com.thirdnf.ResourceScheduler.IAppointment;
import com.thirdnf.ResourceScheduler.components.BasicAppointmentComponent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



public class DemoAppointmentComponent extends BasicAppointmentComponent implements MouseListener
{
    // The single action listener which will get click events.  This is a single entity for now, but we
    //  could make this a list if there was a need for it.
    private ActionListener _actionListener = null;

    private final JPopupMenu _popupMenu;

    /**
     * Constructor given an appointment to wrap.
     *
     * @param appointment (not null) The appointment to wrap.
     */
    public DemoAppointmentComponent(@NotNull IAppointment appointment)
    {
        super(appointment);

        // Is this the right place for this?
        if (appointment instanceof ScheduleModelDemo.DemoAppointment) {
            Color c = ((ScheduleModelDemo.DemoAppointment)appointment).getCategory().getColor();
            setBackground(c);
        }

        // Allow this instance to respond to mouse clicks.  I'm a bit uncomfortable with accessing 'this' at
        //  this point, but I think that is just an old C++ fear.
        addMouseListener(this);

        _popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleEdit();
            }
        });
        _popupMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleDelete();
            }
        });
        _popupMenu.add(deleteItem);
    }


    private void handleEdit()
    {
        System.out.println("Need to edit");
    }


    private void handleDelete()
    {
        System.out.println("Need to delete");
    }


    /**
     * Set the one and only action listener which will get called on mouse clicks.
     *
     * @param actionListener (not null) The action listener who cares about mouse clicks.
     */
    public void setActionListener(@NotNull ActionListener actionListener)
    {
        _actionListener = actionListener;
    }


    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (_actionListener != null) {
            ActionEvent actionEvent = new ActionEvent(_appointment, ActionEvent.ACTION_PERFORMED, _appointment.getTitle());
            _actionListener.actionPerformed(actionEvent);
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
