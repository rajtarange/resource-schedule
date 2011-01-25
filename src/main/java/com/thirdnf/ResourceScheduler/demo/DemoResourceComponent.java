package com.thirdnf.ResourceScheduler.demo;

import com.thirdnf.ResourceScheduler.Resource;
import com.thirdnf.ResourceScheduler.components.BasicResourceComponent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Instances of this will be the resources displayed along the top.  This extends the
 * {@link BasicResourceComponent} and adds a mouse listener with some context menus.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class DemoResourceComponent extends BasicResourceComponent implements MouseListener
{

    private ResourceListener _resourceListener;

    private final JPopupMenu _popupMenu;

    /**
     * Constructor given a resource to wrap.
     *
     * @param resource (not null)
     */
    public DemoResourceComponent(@NotNull Resource resource)
    {
        super(resource);

        // If our resource is a Demo Resource (and it really should be) then get its color.
        if (resource instanceof ScheduleModelDemo.DemoResource) {
            Color c = ((ScheduleModelDemo.DemoResource)resource).getColor();
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
                if (_resourceListener != null) { _resourceListener.handleEdit(_resource); }
            }
        });
        _popupMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (_resourceListener != null) { _resourceListener.handleDelete(_resource); }
            }
        });
        _popupMenu.add(deleteItem);
    }


    /**
     * Set the one and only resource listener which will get called on mouse clicks.
     *
     * @param resourceListener (not null) The resource listener who cares about mouse clicks.
     */
    public void setResourceListener(@NotNull ResourceListener resourceListener)
    {
        _resourceListener = resourceListener;
    }


    @Override
    public void mouseClicked(MouseEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
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
