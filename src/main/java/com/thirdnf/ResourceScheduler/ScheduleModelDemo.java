package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Date;

/**
 * A demo model, has its database hard coded.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class ScheduleModelDemo implements IScheduleModel
{
    // Our categories for our appointments
    private static final ICategory Green = new DemoCategory("Green", new Color(9, 246, 76, 200));
    private static final ICategory Blue  = new DemoCategory("Blue", new Color(9, 171, 246, 200));

    // The resources we can assign
    private static final IResource Bobby = new DemoResource("Bobby", new Color(251, 198, 12, 200));
    private static final IResource Johnny = new DemoResource("Johnny", new Color(12, 251, 160, 200));
    private static final IResource Sally = new DemoResource("Sally", new Color(166, 251, 12, 200));

    // The appointments
    private static final IAppointment[] Appointments = new IAppointment[] {
            DemoAppointment.create("Appointment1", Green, Bobby, new Time(10, 0, 0), new Duration(0, 45, 0)),
            DemoAppointment.create("Appointment2", Blue, Johnny, new Time(13, 0, 0),  new Duration(1, 15, 0)),
            DemoAppointment.create("Appointment3", Blue, Sally, new Time(8, 0, 0),  new Duration(1, 0, 0)),
            DemoAppointment.create("Appointment4", Green, Sally, new Time(8, 45, 0),  new Duration(2, 0, 0))
    };


    private static class DemoResource implements IResource
    {
        private final String _title;
        private final Color _color;

        public DemoResource(@NotNull String title, @NotNull Color color)
        {
            _title = title;
            _color = color;
        }


        @Override
        @NotNull
        public String getTitle()
        {
            return _title;
        }


        @Override
        @NotNull
        public Color getColor()
        {
            return _color;
        }


        @Override
        @NotNull
        public String toString()
        {
            return _title;
        }
    }


    private static class DemoCategory implements ICategory
    {
        private final Color _color;
        private final String _title;

        public DemoCategory(@NotNull String title, @NotNull Color color)
        {
            _title = title;
            _color = color;
        }


        @Override
        @NotNull
        public Color getColor()
        {
            return _color;
        }


        @NotNull
        @Override
        public String getTitle()
        {
            return _title;
        }
    }


    private static class DemoAppointment implements IAppointment
    {
        private final ICategory _catgegory;
        private final IResource _resource;
        private final String _title;
        private Time _time;
        private Duration _length;

        public DemoAppointment(@NotNull String title, ICategory category, IResource resource)
        {
            _title = title;
            _catgegory = category;
            _resource = resource;
        }


        @Override
        public ICategory getCategory()
        {
            return _catgegory;
        }

        @NotNull
        @Override
        public Time getTime()
        {
            return _time;
        }


        @Override
        public IResource getResource()
        {
            return _resource;
        }

        @NotNull
        @Override
        public Duration getDuration()
        {
            return _length;
        }

        @NotNull
        @Override
        public String getTitle()
        {
            return _title;
        }


        public void setTime(@NotNull Time time)
        {
            _time = time;
        }


        public void setLength(@NotNull Duration length)
        {
            _length = length;
        }


        public static DemoAppointment create(@NotNull String title, @NotNull ICategory category,
                                             @Nullable IResource resource,
                                             @NotNull Time time, @NotNull Duration duration)
        {
            DemoAppointment appointment = new DemoAppointment(title, category, resource);
            appointment.setTime(time);
            appointment.setLength(duration);

            return appointment;
        }
    }


    @Override
    public void visitAppointments(IAppointmentVisitor visitor, @NotNull Date dateTime)
    {
        // TODO - Actually break the appointments down by days.

        for (IAppointment appointment : Appointments) {
            visitor.visitAppointment(appointment);
        }
    }


    @Override
    public void visitResources(IResourceVisitor visitor, @NotNull Date dateTime)
    {
        visitor.visitResource(Bobby);
        visitor.visitResource(Johnny);
        visitor.visitResource(Sally);
    }


    @Override
    public Time getEndTime(@NotNull Date dateTime)
    {
        return new Time(18, 0, 0); // 6 pm
    }

    @Override
    public Time getStartTime(@NotNull Date dateTime)
    {
        return new Time(8, 0, 0); // 8 am
    }
}
