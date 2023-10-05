package edu.ucalgary.oop;

/**
 * The BasicTasks class represents a basic task with a task ID, description,
 * duration,
 * maximum window, and preparation time.
 * 
 * Ethan Bensler, Liam Brennan, Andrew Duong, Joseph Duong
 * 
 */
public class BasicTasks implements Tasks {
    private int taskID;
    private String description;
    private int duration;
    private int maxWindow;
    private int prepTime;
    private String species;
    private int count;

    /**
     * Constructs a new BasicTasks object with the specified task ID, description,
     * duration, maximum window, and preparation time.
     *
     * @param taskID      the ID of the task
     * @param description the description of the task
     * @param duration    the duration of the task in minutes
     * @param maxWindow   the maximum time window in which the task must be
     *                    completed
     * @param prepTime    the preparation time required before starting the task
     */
    public BasicTasks(int taskID, String description, int duration, int maxWindow, int prepTime, String species,
            int count) {
        this.taskID = taskID;
        this.description = description;
        this.duration = duration;
        this.maxWindow = maxWindow;
        this.prepTime = prepTime;
        this.species = species;
        this.count = count;
    }

    /**
     * Returns the preparation time required before starting the task.
     *
     * @return the preparation time required before starting the task
     */
    public int getPrepTime() {
        return this.prepTime;
    }

    /**
     * Returns the ID of the task.
     *
     * @return the ID of the task
     */
    public int getTaskID() {
        return this.taskID;
    }

    /**
     * Returns the description of the task.
     *
     * @return the description of the task
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the duration of the task in minutes.
     *
     * @return the duration of the task in minutes
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Returns the maximum time window in which the task must be completed.
     *
     * @return the maximum time window in which the task must be completed
     */
    public int getMaxWindow() {
        return this.maxWindow;
    }

    /**
     * 
     * Returns the species of the animals the task is performed on.
     * 
     * @return the species of the animals
     */
    public String getSpecies() {
        return this.species;
    }

    /**
     * 
     * Returns the count of animals the task is performed on.
     * 
     * @return the count of animals
     */
    public int getCount() {
        return this.count;
    }
}
