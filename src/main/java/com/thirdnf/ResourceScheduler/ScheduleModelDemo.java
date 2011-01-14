package com.thirdnf.ResourceScheduler;


import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Date;

/**
 * A demo model, has its database hard coded.
 */
public class ScheduleModelDemo implements IScheduleModel
{
    private static final ICategory Green = new DemoCategory("Green", Color.green);
    private static final ICategory Blue  = new DemoCategory("Blue", Color.blue);

    private static final IAppointment[] Appointments = new IAppointment[] {
            DemoAppointment.create("Appointment1", Green, new Time(10, 0, 0), 45),
            DemoAppointment.create("Appointment2", Blue, new Time(13, 0, 0), 90)
    };


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
        private final String _title;
        private Time _time;
        private Time _length;

        public DemoAppointment(@NotNull String title, ICategory category)
        {
            _title = title;
            _catgegory = category;
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

        @NotNull
        @Override
        public Time getLength()
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


        public void setLength(@NotNull Time length)
        {
            _length = length;
        }


        public static DemoAppointment create(@NotNull String title, @NotNull ICategory category, @NotNull Time time, int length)
        {
            DemoAppointment appointment = new DemoAppointment(title, category);
            appointment.setTime(time);
            appointment.setLength(new Time(0, length, 0));

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
}
