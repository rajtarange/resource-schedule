package com.thirdnf.ResourceScheduler.demo;

import com.thirdnf.ResourceScheduler.IResource;
import com.thirdnf.ResourceScheduler.components.BasicResourceComponent;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class DemoResourceComponent extends BasicResourceComponent implements MouseListener
{
    public DemoResourceComponent(@NotNull IResource resource)
    {
        super(resource);

        addMouseListener(this);
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
