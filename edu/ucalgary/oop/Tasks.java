package edu.ucalgary.oop;

/**
 * The Tasks interface has basic getters that relate to the tasks.
 */
public interface Tasks {
    /**
     * Gets the unique ID of the task.
     *
     * @return the task ID
     */
    int getTaskID();

    /**
     * Gets the description of the task.
     *
     * @return the task description
     */
    String getDescription();

    /**
     * Gets the duration of the task in minutes.
     *
     * @return the task duration
     */
    int getDuration();

    /**
     * Gets the maximum time window in which the task can be scheduled.
     *
     * @return the maximum time window for the task
     */
    int getMaxWindow();
}
