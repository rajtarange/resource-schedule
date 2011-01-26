package com.thirdnf.ResourceScheduler;

import org.jetbrains.annotations.NotNull;


/**
 * Instances of these will expect to be visited with all appointments which matched a given search
 * criteria.
 *
 * @author Joshua Gerth - jgerth@thirdnf.com
 */
public interface AppointmentVisitor
{
    /**
     * Visit appointment which matched the search criteria.
     *
     * @param appointment (not null) An appointment which matched the search.
     * @return {@code true} If the caller should keep looping, {@code false} if it should stop.
     */
    boolean visitAppointment(@NotNull Appointment appointment);
}
