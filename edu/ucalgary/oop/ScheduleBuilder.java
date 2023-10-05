package edu.ucalgary.oop;

import java.util.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;

/**
 * 
 * ScheduleBuilder class is responsible for building the schedule based on
 * provided treatments, animal data,
 * 
 * basic tasks, and creating default hours.
 * 
 * Ethan Bensler, Liam Brennan, Andrew Duong, Joseph Duong
 * 
 */
public class ScheduleBuilder {

    /**
     * 
     * An ArrayList of Hour objects representing the hours in a day.
     */
    private ArrayList<Hour> hours = new ArrayList<Hour>();
    /**
     * 
     * An ArrayList of Treatment objects representing all treatments.
     */
    private ArrayList<Treatment> treatments = new ArrayList<Treatment>();
    /**
     * 
     * An ArrayList of BasicTasks objects representing all basic tasks.
     */
    private ArrayList<BasicTasks> basicTasks = new ArrayList<BasicTasks>();
    /**
     * 
     * A HashMap that maps each animal species to a list of Animal objects of that
     * species.
     */
    private HashMap<String, ArrayList<Animal>> animal = new HashMap<>();
    /**
     * 
     * The database connection used to retrieve data.
     */
    private Connection dbConnect;

    /**
     * 
     * Constructs a ScheduleBuilder object with the provided username and password
     * and initializes the treatments, animal,
     * 
     * basic tasks, and default hours.
     * 
     * @param username a String representing the username for the database
     *                 connection
     * 
     * @param password a String representing the password for the database
     *                 connection
     */
    public ScheduleBuilder(String username, String password) {
        createConnection(username, password);
        insertTreatments();
        insertAnimal();
        insertBasicTasks();
        initializeDefaultHours();

    }

    /**
     * 
     * Initializes the default hours for the object by creating a new Hour object
     * for each hour of the day.
     * This method is used by the constructor and for setting the default hours.
     */
    private void initializeDefaultHours() {
        // for loop to create default hours
        for (int i = 0; i < 24; i++) {
            hours.add(new Hour(i));
        }
    }

    /**
     * 
     * Updates the StartHour value for a treatment in the SQL database with the
     * given TreatmentID.
     * 
     * @param id    the ID of the treatment to be updated
     * @param start the new StartHour value to be set
     */
    public void updateSQL(int id, int start) {
        try {
            Statement treatmentStmt = dbConnect.createStatement();
            int query = treatmentStmt
                    .executeUpdate("UPDATE TREATMENTS SET StartHour = " + start + " WHERE TreatmentID = " + id);
            treatmentStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 
     * Inserts treatments into the program by querying the database and creating
     * Treatment objects.
     * 
     * Each Treatment object is added to the treatments ArrayList.
     * 
     * @return void
     */

    public void insertTreatments() {
        ResultSet treatmentResults;
        try {
            Statement treatmentStmt = dbConnect.createStatement();
            treatmentResults = treatmentStmt.executeQuery(
                    "select TreatmentID, Treatments.AnimalId, TREATMENTS.TaskID, StartHour, MaxWindow, tasks.Description, Duration, AnimalNickname from TREATMENTS, TASKS, ANIMALS where TREATMENTS.TaskID = TASKS.TaskID and Animals.AnimalID = Treatments.AnimalID;");

            while (treatmentResults.next()) {

                // int treatmentID, int animalID, int taskID, int startHour, int maxWindow,
                // String description, int duration
                Treatment newTreatment = new Treatment(
                        treatmentResults.getInt("TreatmentID"),
                        treatmentResults.getInt("AnimalID"),
                        treatmentResults.getInt("TaskID"),
                        treatmentResults.getInt("StartHour"),
                        treatmentResults.getInt("maxWindow"),
                        treatmentResults.getInt("Duration"),
                        treatmentResults.getString("description"),
                        treatmentResults.getString("AnimalNickname"));

                this.treatments.add(newTreatment);

            }

            treatmentStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 
     * This method inserts all basic tasks (feeding and cleaning) for each species
     * with the count into the ArrayList basicTasks.
     * It loops through the animal HashMap and creates a BasicTasks object for each
     * animal species with appropriate properties
     * such as task duration, maximum window, and preparation time. If there are
     * more than one animals of the same species,
     * the task is named accordingly with a plural form, otherwise a singular form.
     * For feeding tasks, it checks if there are any orphan animals to exclude them
     * from the count.
     * For cleaning tasks, it gets the cleaning time from the AnimalTimes enum
     * class.
     * 
     * @return void
     */
    public void insertBasicTasks() {
        // for loop to iterate through each species in the animal HashMap
        for (String key : animal.keySet()) {
            String feed = "";
            String clean = "";
            ArrayList<Animal> animalList = animal.get(key); // get the ArrayList of the animals of that species in key.
            int countFeed = animalList.size();
            int countClean = animalList.size();
            for (Animal a : animalList) { // since orphan feeding is a treatment, we do not add it to the basic task.
                if (a.getOrphan() == true) {
                    countFeed--;
                }
            }
            if (countFeed > 1) { // if statement for plural
                if (key == "fox") {
                    feed = "Feed " + key + "es";
                }
                feed = "Feed " + key + "s";
            } else {
                feed = "Feed " + key;
            }
            if (countClean > 1) {
                clean = "Clean " + key + " cages";
            } else {
                clean = "Clean " + key + " cage";
            }
            int duration = AnimalTimes.valueOf(key.toUpperCase()).duration();
            int window = AnimalTimes.valueOf(key.toUpperCase()).maxWindow();
            int prep = AnimalTimes.valueOf(key.toUpperCase()).prepTime();
            int cleanTime = AnimalTimes.valueOf(key.toUpperCase()).cleaning();
            // create new instance of BasicTask to add to the arrayList basicTasks
            BasicTasks newClean = new BasicTasks(101, clean, cleanTime, 24, 0, key, countClean);
            // create new instance of BasicTask to add to the arrayList basicTasks
            BasicTasks newFeed = new BasicTasks(100, feed.toString(), duration, window, prep, key, countFeed);

            basicTasks.add(newFeed);
            basicTasks.add(newClean);
        }
    }

    /**
     * 
     * Inserts animal objects into the animal HashMap by querying data from the
     * ANIMALS table in the database.
     * 
     * If an ArrayList for a species already exists, the new animal object is added
     * to it. Otherwise, a new ArrayList is created.
     * 
     * @return void
     */

    public void insertAnimal() {
        ResultSet animalResults;
        try {
            Statement animalStmt = dbConnect.createStatement();
            animalResults = animalStmt.executeQuery("SELECT AnimalSpecies, AnimalNickname, AnimalID FROM ANIMALS;");

            while (animalResults.next()) {
                Animal newAnimal = new Animal(
                        animalResults.getString("AnimalSpecies"),
                        animalResults.getString("AnimalNickname"),
                        animalResults.getInt("AnimalID"));
                // Get the existing list of animals for this species or create a new one if it
                // doesn't exist
                ArrayList<Animal> animalList = animal.getOrDefault(newAnimal.getSpecies(), new ArrayList<>());
                animalList.add(newAnimal);
                animal.put(newAnimal.getSpecies(), animalList);
            }

            animalStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 
     * Modifies the start hour of one or more treatments.
     * 
     * @param treatmentID An array of treatment IDs to be modified.
     * @param startHour   An array of start hours corresponding to the treatment IDs
     *                    to be modified.
     *                    The start hour at the i-th index in this array corresponds
     *                    to the treatment ID at the i-th index in the treatmentID
     *                    array.
     * @return void
     */
    public void modifyTreatment(int[] treatmentID, int[] startHour) {
        for (int i = 0; i < treatmentID.length; i++) {
            for (Treatment treatment : treatments) {
                if (treatment.getTreatmentID() == treatmentID[i]) {
                    treatment.setStartHour(startHour[i]);
                }
            }
        }
    }

    /**
     * 
     * This method generates a schedule for all the treatments and basic tasks.
     * 
     * @throws NotEnoughTimeException if there is not enough time to schedule all
     *                                treatments and tasks
     * @return void
     */
    public void generateSchedule() throws NotEnoughTimeException {
        int startHour;
        int duration;
        int maxWindow;
        int timeAvailable;
        ArrayList<BasicTasks> newBasic = new ArrayList<>();

        // insert all treatments into the hour
        for (Treatment treatment : treatments) {
            // get start hour for task
            startHour = treatment.getStartHour();

            duration = treatment.getDuration();
            maxWindow = treatment.getMaxWindow();

            // iterate through the max window and try each hour to see if the task can fit.
            for (int i = startHour; (i <= startHour + maxWindow - 1); i++) {
                // allow for overflow from end of the day to start of day for treatments
                if (i >= 24) {
                    startHour -= 24;
                    i -= 24;
                }
                timeAvailable = hours.get(i).getTimeAvailable();
                // check to see if treatment can be added

                if (hours.get(i).addTreatment(treatment)) {
                    break;
                } else if (i == startHour + maxWindow - 1) {
                    if (hours.get(i).getBackup()) {
                        // not enough time to do task, even with backup volunteer
                        throw new NotEnoughTimeException();
                    } else {
                        // set the backup volunteer to true, we don't need to check return value as no
                        // task is greater than 1 hour
                        hours.get(i).setBackup(true);
                        timeAvailable = hours.get(i).getTimeAvailable();
                        hours.get(i).addTreatment(treatment);
                        hours.get(i).setTimeAvailable(timeAvailable - duration);
                    }
                }
            }
        }
        // For loop for inserting all the feeding tasks into hour
        for (BasicTasks task : basicTasks) {
            HashMap<Integer, Integer> timeList = new HashMap<>();
            if (task.getTaskID() == 100) { // if taskID is a feeding task.
                startHour = AnimalTimes.valueOf(task.getSpecies().toUpperCase()).startHour();
                duration = task.getDuration();
                maxWindow = task.getMaxWindow();
                int prepTime = task.getPrepTime();
                int count = task.getCount();
                for (int i = startHour; (i <= startHour + maxWindow - 1) && (i < 24); i++) {
                    timeAvailable = hours.get(i).getTimeAvailable();
                    if (timeAvailable >= (duration + prepTime)) {
                        timeList.put(i, timeAvailable);
                    }
                }
                // While loop for when the HashMap contains available time
                while (!timeList.isEmpty()) {
                    int highestValue = Integer.MIN_VALUE;
                    int highestKey = -1;
                    // For loop to grab the highest time available and the coresponding hour.
                    for (Map.Entry<Integer, Integer> entry : timeList.entrySet()) {
                        if (entry.getValue() > highestValue) {
                            highestValue = entry.getValue();
                            highestKey = entry.getKey();
                        }
                    }
                    // If the highestKey has been changed, remove it along with the time from the
                    // Hashmap
                    if (highestKey != -1) {
                        timeList.remove(highestKey);
                    }
                    // to calculate the time remaining after the prep time has been subtracted.
                    int timeRemain = highestValue - prepTime;
                    int countAvail = timeRemain / duration; // to calculate how many counts of feeding can fit into the
                                                            // available time slot.
                    // if statement to see countAvail is larger than the required count.
                    if (countAvail >= count) {
                        BasicTasks feed = new BasicTasks(task.getTaskID(), task.getDescription(), duration,
                                maxWindow, prepTime, task.getSpecies(), count);
                        newBasic.add(feed);
                        hours.get(highestKey).setTask(feed);
                        hours.get(highestKey).setTimeAvailable(timeRemain - (duration * count));
                        count = 0;
                        break;
                    }
                    // else statement to change the count after countAvail has been subtracted.
                    else {
                        count -= countAvail;
                        BasicTasks feed = new BasicTasks(task.getTaskID(), task.getDescription(), duration,
                                maxWindow, prepTime, task.getSpecies(), countAvail);
                        hours.get(highestKey).setTask(feed);
                        newBasic.add(feed);
                        hours.get(highestKey).setTimeAvailable(timeRemain - (duration * countAvail));
                    }
                }
                // if timeList is empty then call for a backup so that there is an extra 60
                // minutes where needed.
                if (timeList.isEmpty()) {
                    for (int i = startHour; (i <= startHour + maxWindow - 1) && (i < 24); i++) {
                        if (count == 0) {
                            break;
                        }
                        if (hours.get(i).getBackup() == false) {
                            hours.get(i).setBackup(true);
                            timeAvailable = hours.get(i).getTimeAvailable();
                            int timeRemain = timeAvailable - prepTime;
                            int countAvail = timeRemain / duration;
                            if (countAvail >= count) {
                                BasicTasks feed = new BasicTasks(task.getTaskID(), task.getDescription(), duration,
                                        maxWindow, prepTime, task.getSpecies(), count);
                                newBasic.add(feed);
                                hours.get(i).setTask(feed);
                                hours.get(i).setTimeAvailable(timeRemain - (duration * count));
                                count = 0;
                                break;
                            } else {
                                count -= countAvail;
                                BasicTasks feed = new BasicTasks(task.getTaskID(), task.getDescription(), duration,
                                        maxWindow, prepTime, task.getSpecies(), countAvail);
                                newBasic.add(feed);
                                hours.get(i).setTask(feed);
                                hours.get(i).setTimeAvailable(timeRemain - (duration * countAvail));
                            }
                        } else {
                            continue;
                        }
                    }

                }
                if (count != 0) {
                    throw new NotEnoughTimeException();
                }
            }
        }
        // For loop for inserting all the cleaning tasks into hour
        for (BasicTasks task : basicTasks) {
            HashMap<Integer, Integer> timeList = new HashMap<>();
            if (task.getTaskID() == 101) {
                duration = task.getDuration();
                int count = task.getCount();
                // For loop to put all available hours and the time available into the HashMap
                // timeList.
                for (int i = 0; i <= 23; i++) {
                    timeAvailable = hours.get(i).getTimeAvailable();
                    if (timeAvailable >= (duration)) {
                        timeList.put(i, timeAvailable);
                    }
                }
                // While loop for when the HashMap contains available time
                while (!timeList.isEmpty()) {
                    int highestValue = Integer.MIN_VALUE;
                    int highestKey = -1;
                    // For loop to grab the highest time available and the coresponding hour.
                    for (Map.Entry<Integer, Integer> entry : timeList.entrySet()) {
                        if (entry.getValue() > highestValue) {
                            highestValue = entry.getValue();
                            highestKey = entry.getKey();
                        }
                    }
                    // If the highestKey has been changed, remove it along with the time from the
                    // Hashmap
                    if (highestKey != -1) {
                        timeList.remove(highestKey);
                    }
                    // to calculate how many counts of cleaning can fit into the available time
                    // slot.
                    int countAvail = highestValue / duration;
                    // if statement to see countAvail is larger than the required count.
                    if (countAvail >= count) {
                        BasicTasks clean = new BasicTasks(task.getTaskID(), task.getDescription(), duration,
                                24, 0, task.getSpecies(), count);
                        newBasic.add(clean);
                        hours.get(highestKey).setTask(clean);
                        hours.get(highestKey).setTimeAvailable(highestValue - (duration * count));
                        count = 0;
                        break;
                    }
                    // else statement to change the count after countAvail has been subtracted.
                    else {
                        count -= countAvail;
                        BasicTasks clean = new BasicTasks(task.getTaskID(), task.getDescription(), duration,
                                24, 0, task.getSpecies(), countAvail);
                        newBasic.add(clean);
                        hours.get(highestKey).setTask(clean);
                        hours.get(highestKey).setTimeAvailable(highestValue - (duration * countAvail));
                    }
                }
                // if timeList is empty then call for a backup so that there is an extra 60
                // minutes where needed.
                if (timeList.isEmpty()) {
                    for (int i = 0; i < 23; i++) {
                        if (count == 0) {
                            break;
                        }
                        if (hours.get(i).getBackup() == false) {
                            hours.get(i).setBackup(true);
                            timeAvailable = hours.get(i).getTimeAvailable();
                            int countAvail = timeAvailable / duration;
                            if (countAvail >= count) {
                                BasicTasks clean = new BasicTasks(task.getTaskID(), task.getDescription(), duration,
                                        24, 0, task.getSpecies(), count);
                                newBasic.add(clean);
                                hours.get(i).setTask(clean);
                                hours.get(i).setTimeAvailable(timeAvailable - (duration * count));
                                count = 0;
                                break;
                            } else {
                                count -= countAvail;
                                BasicTasks clean = new BasicTasks(task.getTaskID(), task.getDescription(), duration,
                                        24, 0, task.getSpecies(), countAvail);
                                newBasic.add(clean);
                                hours.get(i).setTask(clean);
                                hours.get(i).setTimeAvailable(timeAvailable - (duration * countAvail));
                            }
                        } else {
                            continue;
                        }
                    }

                }
                if (count != 0) {
                    throw new NotEnoughTimeException();
                }
            }
        }
        this.basicTasks = newBasic;
    }

    /**
     * 
     * Checks if any animal of the given species is an orphan.
     * An orphan is defined as an animal with only one name (no siblings).
     * 
     * @param species the species to check for orphans
     * @return true if any animal of the given species is an orphan, false otherwise
     */

    public boolean isOrphan(String species) {
        for (ArrayList<Animal> animals : animal.values()) {
            for (Animal innerAnimal : animals) {
                if (innerAnimal.getSpecies() == species) { // checks if the animal corresponding to species
                                                           // is an orphan or not.
                    String[] nameList = innerAnimal.getName().split("\\s*(?:,|\\band\\b)\\s*");
                    if (nameList.length > 1) { // if the regex finds that there are multiple names
                                               // the name is considered an orphan.
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 
     * Returns an ArrayList of Strings, where each string represents a formatted
     * hour. Each formatted hour
     * 
     * includes the hour number, an indication of whether there is a backup
     * volunteer for that hour, and
     * 
     * lists of hourly treatments and basic tasks performed.
     * 
     * @return ArrayList of formatted hours, where each formatted hour is a String
     */

    public ArrayList<String> formatHour() {
        ArrayList<String> formatHourArray = new ArrayList<>();
        for (Hour hour : hours) {
            StringBuilder formattedHour = new StringBuilder();
            formattedHour.append(hour.getHour());
            if (hour.getBackup()) { // changes output if backup is needed
                formattedHour.append(" [+ backup volunteer]\n");
            } else { // changes output if backup isn't needed
                formattedHour.append("\n");
            }
            // treatments require an animals name
            for (Treatment treatment : hour.getHourlyTreatments()) {
                formattedHour.append("* " + treatment.getDescription() + " (" +
                        treatment.getAnimalName() + ")\n");
            }
            // orphan output and normal animal outputs are different from one another
            for (BasicTasks basicTask : hour.getHourlyTasks()) {
                if (isOrphan(basicTask.getSpecies())) {
                    formattedHour.append("* " + basicTask.getDescription() + " - " +
                            basicTask.getSpecies() + " (orphans)\n");
                } else {
                    formattedHour.append("* " + basicTask.getDescription() + " - " +
                            basicTask.getSpecies() + " (" + basicTask.getCount() + ")\n");
                }
            }
            formatHourArray.add(formattedHour.toString());
        }
        return formatHourArray;
    }

    /**
     * 
     * Outputs a text schedule for the next day, including hours, treatments, and
     * tasks.
     * The schedule is saved to a text file named with the date and "-schedule.txt"
     * appended to it.
     * 
     * @return void
     */
    public void outputTextSchedule() {
        StringBuilder builder = new StringBuilder();
        LocalDate date = LocalDate.now().plusDays(1);
        builder.append("Schedule for " + date.toString() + "\n\n");
        // iterates through the formatted hours and outputs each hour into a file
        for (String hourInfo : formatHour()) {
            builder.append(hourInfo).append("\n");
        }
        try {
            String fileName = date.toString() + "-schedule.txt";
            FileWriter writer = new FileWriter(fileName);
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * Returns the list of hours in the current schedule.
     * 
     * @return an ArrayList of Hour objects representing the hours in the current
     *         schedule.
     */
    public ArrayList<Hour> getHours() {
        return this.hours;
    }

    /**
     * 
     * Returns the list of tasks in the current schedule.
     * 
     * @return an ArrayList of Tasks objects representing the hours in the current
     *         schedule.
     */
    public ArrayList<BasicTasks> getTasks() {
        return this.basicTasks;
    }

    /**
     * 
     * Returns the list of treatments in the current schedule.
     * 
     * @return an ArrayList of Treatment objects representing the hours in the
     *         current
     *         schedule.
     */
    public ArrayList<Treatment> getTreatments() {
        return this.treatments;
    }

    /**
     * 
     * Returns the hashmap of animals in the current schedule.
     * 
     * @return an HashMap of Animal objects representing the animals in the current
     *         schedule.
     */
    public HashMap<String, ArrayList<Animal>> getAnimals() {
        return this.animal;
    }

    /**
     * 
     * Creates a connection to the database using the provided username and
     * password.
     * 
     * @param username the username to use when connecting to the database.
     * @param password the password to use when connecting to the database.
     * 
     * @return void
     */
    private void createConnection(String username, String password) {
        try {
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * Clears the list of hours in the current schedule.
     * Reinitializes the ArrayList with a call to initializeDefaultHours()
     * 
     * @return void
     */
    public void resetHours() {
        this.hours = new ArrayList<Hour>(24);
        initializeDefaultHours();
    }

    /**
     * Creates a dummy schedule by adding all treatments to the first available hour
     * in the schedule.
     * The first available hour is set to have 2 days (2880 minutes) of time
     * available, and then all treatments
     * are added to that hour. Finally, the time available for the first hour is set
     * to 1 hour (60 minutes).
     */
    public void createDummySchedule() {
        // Set the first available hour to have 2 days (2880 minutes) of time available
        hours.get(0).setTimeAvailable(2880);

        // Add all treatments to the first available hour
        for (Treatment treatment : treatments) {
            hours.get(0).addTreatment(treatment);
        }

        // Set the time available for the first hour to 1 hour (60 minutes)
        hours.get(0).setTimeAvailable(60);
    }

}
