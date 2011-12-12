package com.thirdnf.resourceScheduler.example;

import com.thirdnf.resourceScheduler.Resource;
import com.thirdnf.resourceScheduler.components.BasicResourceComponent;
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
public class ExampleResourceComponent extends BasicResourceComponent implements MouseListener
{
    private ResourceListener _resourceListener;

    private final JPopupMenu _popupMenu;

    /**
     * Constructor given a resource to wrap.
     *
     * @param resource (not null)
     */
    public ExampleResourceComponent(@NotNull Resource resource)
    {
        super(resource);

        // Listen for mouse stuff
        addMouseListener(this);

        // Create our context menu
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


    @Override
    public void paintComponent(Graphics g)
    {
        // If our resource is a Demo Resource (and it really should be) then get its color.
        // We do this here so that if the resource is updated we will pick up its new color
        if (_resource instanceof ExampleResource) {
            Color c = ((ExampleResource)_resource).getColor();
            setBackground(c);
        }

        super.paintComponent(g);
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
    public void mouseClicked(MouseEvent e) { }


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
    public void mouseEntered(MouseEvent e) { }


    @Override
    public void mouseExited(MouseEvent e) { }


    /**
     * I'm not entirely sure of this pattern, but it was copied directly from the Javadocs.
     *
     * @param e (not null) The mouse event
     */
    private void maybeShowPopup(@NotNull MouseEvent e)
    {
        if (e.isPopupTrigger()) {
            _popupMenu.show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }
}
