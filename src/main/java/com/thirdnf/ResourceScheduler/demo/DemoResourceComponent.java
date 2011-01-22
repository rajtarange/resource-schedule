package com.thirdnf.ResourceScheduler.demo;

import com.thirdnf.ResourceScheduler.Resource;
import com.thirdnf.ResourceScheduler.components.BasicResourceComponent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class DemoResourceComponent extends BasicResourceComponent implements MouseListener
{
    private ResourceListener _resourceListener;


    public DemoResourceComponent(@NotNull Resource resource)
    {
        super(resource);

        // Is this the right place for this?
        if (resource instanceof ScheduleModelDemo.DemoResource) {
            Color c = ((ScheduleModelDemo.DemoResource)resource).getColor();
            setBackground(c);
        }

        addMouseListener(this);
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
            System.out.println("Show popup");
//            popup.show(e.getComponent(),
//                       e.getX(), e.getY());
        }
    }
}
