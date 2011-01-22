package com.thirdnf.ResourceScheduler.demo;

import com.thirdnf.ResourceScheduler.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A demo model, has its database hard coded.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class ScheduleModelDemo extends AbstractScheduleModel
{
    // Our categories for our appointments
    private static final DemoCategory Green = new DemoCategory("Green", new Color(9, 246, 76, 200));
    private static final DemoCategory Blue  = new DemoCategory("Blue", new Color(9, 171, 246, 200));

    // Resources
    private List<Resource> _resources = new ArrayList<Resource>();

    // The appointments
    private List<Appointment> _appointments = new ArrayList<Appointment>();


    public ScheduleModelDemo()
    {
        // Populate some default resources
        _resources.add(new DemoResource("Bobby", new Color(251, 198, 12, 200)));
        _resources.add(new DemoResource("Johnny", new Color(12, 251, 160, 200)));
        _resources.add(new DemoResource("Sally", new Color(166, 251, 12, 200)));

        // Populate some default appointments
        _appointments.add(DemoAppointment.create("Appointment1", Green, _resources.get(0), new LocalTime(10, 5, 0),  45));
        _appointments.add(DemoAppointment.create("Appointment2", Blue, _resources.get(1), new LocalTime(13, 0, 0),  75));
        _appointments.add(DemoAppointment.create("Appointment3", Blue, _resources.get(2),  new LocalTime(8, 0, 0),   60));
        _appointments.add(DemoAppointment.create("Appointment4", Green, _resources.get(2), new LocalTime(8, 45, 0),  120));
        _appointments.add(DemoAppointment.create("Appointment5", Blue, _resources.get(2), new LocalTime(10, 45, 0),  30));
        _appointments.add(DemoAppointment.create("Appointment7", Green, _resources.get(2), new LocalTime(12, 30, 0), 40));
    }


    @Override
    public void visitAppointments(AppointmentVisitor visitor, @NotNull LocalDate dateTime)
    {
        // TODO - Actually break the appointments down by days.

        for (Appointment appointment : _appointments) {
            visitor.visitAppointment(appointment);
        }
    }


    @Override
    public void visitResources(ResourceVisitor visitor, @NotNull LocalDate dateTime)
    {
        for (Resource resource : _resources) {
            visitor.visitResource(resource);
        }
    }


    public void addResource(@NotNull String title, @NotNull Color color)
    {
        Resource resource = new DemoResource(title, color);
        _resources.add(resource);

        fireResourceAdded(resource, new LocalDate());
    }


    @Override
    public LocalTime getEndTime(@NotNull LocalDate dateTime)
    {
        return new LocalTime(18, 0, 0); // 6 pm
    }


    @Override
    public LocalTime getStartTime(@NotNull LocalDate dateTime)
    {
        return new LocalTime(8, 0, 0); // 8 am
    }



    public static class DemoResource implements Resource
    {
        private final String _title;
        private final Color _color;


        /**
         * Create the demo resource.
         * @param title Title for the resource.
         * @param color Color to assign the resource.
         */
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


    public static class DemoCategory
    {
        private final Color _color;
        private final String _title;

        /**
         * Create the category.
         * @param title (not null) Title of the category
         * @param color (not null) Color for the category
         */
        public DemoCategory(@NotNull String title, @NotNull Color color)
        {
            _title = title;
            _color = color;
        }


        @NotNull
        public Color getColor()
        {
            return _color;
        }


        @NotNull
        public String getTitle()
        {
            return _title;
        }
    }


    public static class DemoAppointment implements Appointment
    {
        private final DemoCategory _category;
        private final Resource _resource;
        private final String _title;
        private DateTime _time;
        private Duration _length;


        public DemoAppointment(@NotNull String title, DemoCategory category, Resource resource)
        {
            _title = title;
            _category = category;
            _resource = resource;
        }


        public DemoCategory getCategory()
        {
            return _category;
        }

        @NotNull
        @Override
        public DateTime getDateTime()
        {
            return _time;
        }


        @Override
        public Resource getResource()
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


        public void setTime(@NotNull DateTime time)
        {
            _time = time;
        }


        public void setLength(@NotNull Duration length)
        {
            _length = length;
        }


        public static DemoAppointment create(@NotNull String title, @NotNull DemoCategory category,
                                             @Nullable Resource resource,
                                             @NotNull LocalTime time, int minutes)
        {
            DemoAppointment appointment = new DemoAppointment(title, category, resource);

            DateTime date = new DateTime(2011, 1, 20, time.getHourOfDay(), time.getMinuteOfHour(), time.getSecondOfMinute(), 0);
            appointment.setTime(date);
            appointment.setLength(Duration.standardMinutes(minutes));

            return appointment;
        }
    }
}
