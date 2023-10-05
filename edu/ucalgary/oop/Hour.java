package edu.ucalgary.oop;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 
 * The Hour class represents a specific hour of the day and its associated tasks
 * and treatments.
 * 
 * Ethan Bensler, Liam Brennan, Andrew Duong, Joseph Duong
 * 
 */
public class Hour {
    private boolean backup = false; // indicates whether there is a backup volunteer for this hour
    private int hour; // the hour of the day represented by this Hour object
    private int timeAvailable = 60; // the amount of time available for tasks and treatments, in minutes
    private ArrayList<BasicTasks> hourlyTasks = new ArrayList<BasicTasks>(); // the tasks assigned for this hour
    private ArrayList<Treatment> hourlyTreatments = new ArrayList<Treatment>(); // the treatments assigned for this hour

    /**
     * 
     * Constructs an Hour object with the specified hour, tasks, and treatments.
     * 
     * @param hour         the hour of the day represented by this Hour object
     * @param newTask      the tasks assigned for this hour
     * @param newTreatment the treatments assigned for this hour
     */
    public Hour(int hour, ArrayList<BasicTasks> newTask, ArrayList<Treatment> newTreatment) {
        this.hour = hour;
        this.hourlyTasks = newTask;
        this.hourlyTreatments = newTreatment;
    }

    /**
     * 
     * Constructs an Hour object with the specified hour and no tasks or treatments.
     * 
     * @param hour the hour of the day represented by this Hour object
     */
    public Hour(int hour) {
        this.hour = hour;
    }

    /**
     * 
     * Sets whether there is a backup volunteer for this hour, and updates the
     * available time accordingly.
     * 
     * @param backup true if there is a backup volunteer for this hour, false
     *               otherwise
     */
    public void setBackup(boolean backup) {
        this.backup = backup;
        if (backup == true) {
            this.timeAvailable += 60;
        }
    }

    /**
     * 
     * Adds a task to the list of hourly tasks, if there is enough time available.
     * 
     * @param newTask the task to be added
     * @throws NotEnoughTimeException if there is not enough time available for the
     *                                task
     */
    public void setTask(BasicTasks newTask) throws NotEnoughTimeException {
        // check if time is available for the task
        if ((newTask.getDuration() * newTask.getCount() + newTask.getPrepTime()) <= timeAvailable) {
            // add task to hourlyTasks
            hourlyTasks.add(newTask);
        } else {
            throw new NotEnoughTimeException();
        }
    }

    /**
     * 
     * Adds a treatment to the list of hourly treatments, if there is enough time
     * available.
     * 
     * @param newTreatment the treatment to be added
     * @return true if the treatment was added successfully, false otherwise
     */
    public boolean addTreatment(Treatment newTreatment) {
        // check if time is available for the treatment
        if (newTreatment.getDuration() <= timeAvailable) {
            // add treatment to hourlyTreatments
            hourlyTreatments.add(newTreatment);
            // update timeAvailable
            timeAvailable -= newTreatment.getDuration();
            return true;
        }
        return false;
    }

    /**
     * 
     * Sets the amount of time available for tasks and treatments, in minutes.
     * 
     * @param timeAvailable the amount of time available
     */
    public void setTimeAvailable(int timeAvailable) {
        this.timeAvailable = timeAvailable;
    }

    /**
     * 
     * Returns the amount of time available for tasks and treatments, in minutes.
     * 
     * @return the amount of time available
     */
    public int getTimeAvailable() {
        return timeAvailable;
    }

    /**
     * 
     * Returns the list of basic tasks scheduled for this hour.
     * 
     * @return the list of basic tasks.
     */
    public ArrayList<BasicTasks> getHourlyTasks() {
        return hourlyTasks;
    }

    /**
     * 
     * Returns the list of treatments scheduled for this hour.
     * 
     * @return the list of treatments
     */
    public ArrayList<Treatment> getHourlyTreatments() {
        return this.hourlyTreatments;
    }

    /**
     * Returns whether this Hour has a backup volunteer assigned to it.
     * 
     * @return true if a backup volunteer is assigned, false otherwise
     */
    public boolean getBackup() {
        return this.backup;
    }

    /**
     * Returns a formatted string representing the hour of this Hour object.
     * 
     * @return the hour formatted as a string in "hh:00" format
     */
    public String getHour() {
        String formatted = String.format("%02d",this.hour) + ":00";
        return formatted;
    }

}
