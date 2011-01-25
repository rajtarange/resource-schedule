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
import java.util.Iterator;
import java.util.List;

/**
 * A demo model, has its database hard coded and it is only capable of showing two days, Today and
 * Tomorrow.  Everything else is blank and will ignore updates.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public class ScheduleModelDemo extends AbstractScheduleModel
{
    private static final LocalDate Today    = new LocalDate();
    private static final LocalDate Tomorrow = Today.plusDays(1);

    private static final List<Resource> TodayResources    = new ArrayList<Resource>();
    private static final List<Resource> TomorrowResources = new ArrayList<Resource>();

    private static final List<Appointment> TodayAppointments = new ArrayList<Appointment>();
    private static final List<Appointment> TomorrowAppointments = new ArrayList<Appointment>();

    // Resources
    private static final DemoResource Bobby = new DemoResource("Bobby", new Color(251, 198, 12, 200));
    private static final DemoResource Johnny = new DemoResource("Johnny", new Color(12, 251, 160, 200));
    private static final DemoResource Sally = new DemoResource("Sally", new Color(166, 251, 12, 200));
    private static final DemoResource Freddy = new DemoResource("Freddy", new Color(66, 151, 12, 200));

    // Our categories for our appointments
    private static final DemoCategory Green = new DemoCategory("Green", new Color(9, 246, 76, 200));
    private static final DemoCategory Blue  = new DemoCategory("Blue", new Color(9, 171, 246, 200));

    // This initializes the defaults.
    static {
        TodayResources.add(Bobby);
        TodayResources.add(Johnny);
        TodayResources.add(Sally);

        // Freddy is not going to be listed for today, but an appointment today is going to be assigned
        //  to Freddy.  The appointment should show up as unassigned for the day.

        // Populate some default appointments
        TodayAppointments.add(DemoAppointment.create("Appointment1", Green, Bobby, new LocalTime(10, 5, 0),  45));
        TodayAppointments.add(DemoAppointment.create("Appointment2", Blue, Johnny, new LocalTime(13, 0, 0), 75));
        TodayAppointments.add(DemoAppointment.create("Appointment3", Blue, Sally, new LocalTime(8, 0, 0), 60));
        TodayAppointments.add(DemoAppointment.create("Appointment4", Green, Sally, new LocalTime(8, 45, 0), 120));
        TodayAppointments.add(DemoAppointment.create("Appointment5", Blue, Sally, new LocalTime(10, 45, 0), 30));
        TodayAppointments.add(DemoAppointment.create("Appointment7", Green, Sally, new LocalTime(12, 30, 0), 40));
        TodayAppointments.add(DemoAppointment.create("Appointment8", Blue, Freddy, new LocalTime(13, 0, 0), 50));
    }


    /**
     * Boring empty constructor for our model.
     */
    public ScheduleModelDemo()
    {
    }


    @Override
    public void visitAppointments(AppointmentVisitor visitor, @NotNull LocalDate dateTime)
    {
        List<Appointment> appointments;
        if (dateTime.equals(Today)) {
            appointments = TodayAppointments;
        }
        else if (dateTime.equals(Tomorrow)) {
            appointments = TomorrowAppointments;
        }
        else {
            return;
        }

        for (Appointment appointment : appointments) {
            visitor.visitAppointment(appointment);
        }
    }


    @Override
    public void visitResources(ResourceVisitor visitor, @NotNull LocalDate date)
    {
        List<Resource> resources;
        if (date.equals(Today)) {
            resources = TodayResources;
        }
        else if (date.equals(Tomorrow)) {
            resources = TomorrowResources;
        }
        else {
            return;
        }

        for (Resource resource : resources) {
            visitor.visitResource(resource);
        }
    }


    /**
     * Our model has been told to add a resource to its database.  This method will add the
     *  resource to the underlying database and then trigger a redraw to any components using
     *  this model.
     *
     * @param date (not null) The date to add the resource to.
     * @param title (not null) Title for the resource.
     * @param color (not null) Color for the resource
     * @param index Position to add the resource, -1 indicates that it should be added a the end.
     */
    public void addResource(@NotNull LocalDate date, @NotNull String title, @NotNull Color color, int index)
    {
        List<Resource> resources;
        if (date.equals(Today)) {
            resources = TodayResources;
        }
        else if (date.equals(Tomorrow)) {
            resources = TomorrowResources;
        }
        else {
            return;
        }

        Resource resource = new DemoResource(title, color);
        resources.add(resource);

        fireResourceAdded(resource, date, index);
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
        @Override
        public Iterator<Availability> getAvailability(@NotNull LocalDate date)
        {
            List<Availability> list = new ArrayList<Availability>();

            // Today the availability is from 8 - 3, tomorrow it is from 10 - 5
            if (date.equals(Today)) {
                list.add(new Availability(new LocalTime(8, 0, 0), Duration.standardHours(6)));
            }
            else if (date.equals(Tomorrow)) {
                list.add(new Availability(new LocalTime(10, 0, 0), Duration.standardHours(6)));
            }

            return list.iterator();
        }


        /**
         * Get the color for our resource.  This is used by the demo resource component to paint
         *  the component.
         *
         * @return (not null) The color to paint the component.
         */
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


        /**
         * This model uses categories to color the appointments so this category color determines the
         * appointment color.
         * @return (not null) Color for the category.
         */
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
