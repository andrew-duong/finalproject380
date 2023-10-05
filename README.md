# Schedule Building Program

**Developers:** Ethan Bensler, Liam Brennan, Andrew Duong, Joseph Duong

The Schedule Building Program is an application designed to create a schedule of tasks for Wildlife Rescue centers using data from an SQL database. 

## How to Use

The program is executed by running the "Gui.java" file. Upon starting, the user is prompted to login to the SQL database.

Once logged in, the program generates a schedule based on the data available in the database. The schedule is presented as a table with three buttons along the top of the application: "Regenerate," "Export as .txt," and "Reset To Initial."

### Schedule Table

The schedule table contains the following columns:

- **Hour**: A drop-down menu that allows modification of the starting hour of a specific task. Note: tasks with description "Feed [animal]" or "Clean [animal] cages" will can not have a changed start time.
- **Task Description**: A description of the task and the animals/number of animals involved.
- **Backup**: A column that indicates whether a backup volunteer is required to complete the tasks for that hour.

### Regenerate Button

The "Regenerate" button generates a new schedule based on any changes made using the "Hour" drop-down menu. For example, if a task was originally scheduled for 01:00 but was changed to start at 02:00, using the regenerate button will attempt to generate a schedule with that task starting at 02:00. If such a schedule is not possible, the program will notify the user via a popup window and allow the user to try again.

### Export as .txt Button

The "Export as .txt" button creates a .txt file with the current schedule. Upon clicking this button, the program asks for confirmation from the user to export and ensures that the backup volunteers for the affected hours have been notified. After confirmation, the program creates a .txt file with a formatted schedule.

### Reset To Initial Button

The "Reset To Initial" button resets the schedule to a new initial instance of the schedule based on the information provided in the database. This option exists to aid users if they cannot seem to make any possible schedules due to too many conflicts.

## Conclusion

The program terminates when the user decides to exit. We hope this program helps Wildlife Rescue centers efficiently manage their tasks and schedules. Thank you for using our program!
