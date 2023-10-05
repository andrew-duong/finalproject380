package edu.ucalgary.oop;

/**
 * The Treatment class represents a medical treatment for an animal.
 * 
 * It implements the Tasks interface, which defines common methods for tasks.
 * 
 * The class includes instance variables for the treatment ID, animal ID, task
 * ID,
 * 
 * start hour, maximum window, description, and duration. The constructor
 * includes
 * 
 * input validation to ensure that these variables are valid. The class also
 * includes getter
 * 
 * and setter methods for the treatment ID, animal ID, start hour, and a few
 * other methods
 * 
 * that are required by the Tasks interface.
 * 
 * Ethan Bensler, Liam Brennan, Andrew Duong, Joseph Duong
 * 
 */

public class Treatment implements Tasks {
    private int treatmentID;
    private int animalID;
    private int taskID;
    private int startHour;
    private int maxWindow;
    private String description;
    private int duration;
    private String name;

    /**
     * Constructs a Treatment object with the given parameters. Input validation is
     * included
     * 
     * to ensure that the treatment ID, animal ID, task ID, start hour, maximum
     * window, description,
     * 
     * and duration are all valid.
     * 
     * @param treatmentID the unique identifier for the treatment
     * 
     * @param animalID    the unique identifier for the animal receiving the
     *                    treatment
     * 
     * @param taskID      the unique identifier for the task associated with the
     *                    treatment
     * 
     * @param startHour   the hour at which the treatment should start (0-23)
     * 
     * @param maxWindow   the maximum number of hours after the start time that the
     *                    treatment can be administered
     * 
     * @param description a brief description of the treatment
     * 
     * @param duration    the duration of the treatment in hours
     * 
     * @throws IllegalArgumentException if any of the input parameters are invalid
     */

    public Treatment(int treatmentID, int animalID, int taskID, int startHour, int maxWindow, int duration, String description, String name) {
        this.treatmentID = treatmentID;
        this.animalID = animalID;
        this.taskID = taskID;
        this.maxWindow = maxWindow;
        this.description = description;
        this.duration = duration;
        this.name = name;

        // Check that the start hour is between 0 and 23
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23");
        }
        this.startHour = startHour;
    }

    /**
     * 
     * Returns the treatment ID of the treatment.
     * 
     * @return the treatment ID of the treatment
     */
    public int getTreatmentID() {
        return treatmentID;
    }

    /**
     * 
     * Returns the animal name of the treatment.
     * 
     * @return the animal name of the treatment
     */
    public String getAnimalName() {
        return name;
    }

    /**
     * 
     * Returns the animal ID of the treatment.
     * 
     * @return the animal ID of the treatment
     */
    public int getAnimalID() {
        return animalID;
    }

    /**
     * 
     * Returns the start hour of the treatment.
     * 
     * @return the start hour of the treatment
     */
    public int getStartHour() {
        return startHour;
    }

    /**
     * 
     * Sets the start hour of the treatment.
     * 
     * @param startHour the start hour of the treatment to set
     */
    public void setStartHour(int startHour) throws IllegalArgumentException{
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23");
        }
        this.startHour = startHour;
    }
    public void setMaxWindow(int maxWindow){
        this.maxWindow = maxWindow;
    }

    /**
     * 
     * Returns the maximum window of the treatment.
     * 
     * @return the maximum window of the treatment
     */
    @Override
    public int getMaxWindow() {
        return this.maxWindow;
    }

    /**
     * 
     * Returns the task ID of the treatment.
     * 
     * @return the task ID of the treatment
     */
    @Override
    public int getTaskID() {
        return this.taskID;
    }

    /**
     * 
     * Returns the duration of the treatment.
     * 
     * @return the duration of the treatment
     */
    @Override
    public int getDuration() {
        return this.duration;
    }

    /**
     * 
     * Returns the description of the treatment.
     * 
     * @return the description of the treatment
     */
    @Override
    public String getDescription() {
        return this.description;
    }


}
