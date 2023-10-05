package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;
import java.sql.*;

public class ScheduleBuilderTest {

    private Animal animal1;
    private Animal animal2;

    private BasicTasks task1;
    private BasicTasks task2;
    private BasicTasks task3;

    private Treatment treatment1;
    private Treatment treatment2;
    private Treatment treatment3;

    private ArrayList<BasicTasks> tasksArray;
    private ArrayList<Treatment> treatmentsArray;

    private Hour hour1;
    private Hour hour2;

    private ScheduleBuilder schedule;

    @Before
    public void start() {
        // YOU WILL HAVE TO HARDCODE YOUR LOGIN CREDENTIALS
        schedule = new ScheduleBuilder("root", "KakarotLumber0!");

        // creates two animals to be used in future tests
        animal1 = new Animal("coyote", "Paul Newman", 1);
        animal2 = new Animal("raccoon", "Nunu and Willump", 2);

        // creates an array of three tasks to be used in future tests
        tasksArray = new ArrayList<>();
        task1 = new BasicTasks(10, "feed coyote", 5, 3, 10, "coyote", 3);
        task2 = new BasicTasks(11, "clean fox cage", 60, 3, 10, "fox", 2);
        task3 = new BasicTasks(11, "Clean porcupine cage", 15, 3, 10, "porcupine", 1);
        tasksArray.add(task1);

        // creates an array of three treatments to be used in future tests
        treatmentsArray = new ArrayList<>();
        treatment1 = new Treatment(2, 1, 4, 2, 1, 20, "rebandage leg wound", "Loner");
        treatment2 = new Treatment(3, 2, 1, 1, 1, 15, "Give vitamin injection", "Ricky Sun");
        treatment3 = new Treatment(1, 3, 1, 1, 1, 60, "Eyedrops", "Willow");
        treatmentsArray.add(treatment1);

        // creates two hours to be used in future tests
        hour1 = new Hour(1, tasksArray, treatmentsArray);
        hour2 = new Hour(2);
    }

    /**
     * Tests that the Treatment constructor creates an object when given valid basic
     * tasks constructor values.
     * It's important to ensure that the Treatment object can be created correctly
     * with valid input.
     */
    @Test
    public void testTreatmentConstructor() {
        assertNotNull("Treatment constructor did not create an object when given valid treatment constructor values.",
                treatment1);
    }

    /**
     * Tests that the Animal constructor creates an object when given valid basic
     * tasks constructor values.
     * It's important to ensure that the Animal object can be created correctly with
     * valid input.
     */
    @Test
    public void testAnimalConstructor() {
        assertNotNull("Animal constructor did not create an object when given valid animal constructor values.",
                animal1);
    }

    /**
     * Tests that the BasicTasks constructor creates an object when given valid
     * basic tasks constructor values.
     * It's important to ensure that the BasicTasks object can be created correctly
     * with valid input.
     */
    @Test
    public void testBasicTasksConstructor() {
        assertNotNull("Treatment constructor did not create an object when given valid basic tasks constructor values.",
                task1);
    }

    /**
     * Tests that the Hour constructor creates an object when given valid hour
     * constructor values.
     * It's important to ensure that the Hour object can be created correctly with
     * valid input.
     */
    @Test
    public void testHourConstructor() {
        assertNotNull("Treatment constructor did not create an object when given valid hour constructor values.",
                hour1);
    }

    /**
     * Tests that the Hour constructor creates an object when given valid hour
     * constructor values.
     * It's important to ensure that the Hour object can be created correctly with
     * valid input.
     */
    @Test
    public void testHourSingleArgumentConstructor() {
        assertNotNull("Treatment constructor did not create an object when given valid hour constructor values.",
                hour2);
    }

    /**
     * Tests the getSpecies() method of the Animal class.
     * It's important to ensure that the method returns the correct species value.
     */
    @Test
    public void testAnimalGetSpecies() {
        assertEquals("Species getter is incorrect", "coyote", animal1.getSpecies());
    }

    /**
     * Tests the getName() method of the Animal class.
     * It's important to ensure that the method returns the correct name value.
     */
    @Test
    public void testAnimalGetName() {
        assertEquals("Name getter is incorrect", "Paul Newman", animal1.getName());
    }

    /**
     * Tests the getAnimalID() method of the Animal class.
     * It's important to ensure that the method returns the correct animal ID value.
     */
    @Test
    public void testAnimalGetAnimalID() {
        assertEquals("Animal ID getter is incorrect", 1, animal1.getAnimalID());
    }

    /**
     * Tests the getOrphan() method of the Animal class for both orphan and
     * non-orphan animals.
     * It's important to ensure that the method returns the correct orphan status.
     */
    @Test
    public void testAnimalGetOrphan() {
        assertFalse("Orphan getter for non-orphan animal is incorrect", animal1.getOrphan());
        assertTrue("Orphan getter for orphan animal is incorrect", animal2.getOrphan());
    }

    /**
     * Tests the getOrphanCount() method of the Animal class for both orphan and
     * non-orphan animals.
     * It's important to ensure that the method returns the correct orphan count.
     */
    @Test
    public void testAnimalGetOrphanCount() {
        assertEquals("Orphan count getter for non-orphan animal is incorrect", 0, animal1.getOrphanCount());
        assertEquals("Orphan count getter for orphan animal is incorrect", 2, animal2.getOrphanCount());
    }

    /**
     * Tests the getTaskID() method of the BasicTasks class.
     * It's important to ensure that the method returns the correct task ID value.
     */
    @Test
    public void testBasicTasksGetTaskID() {
        assertEquals("ID getter is wrong", 10, task1.getTaskID());
    }

    /**
     * Tests the getDescription() method of the BasicTasks class.
     * It's important to ensure that the method returns the correct task
     * description.
     */
    @Test
    public void testBasicTasksGetDescription() {
        assertEquals("Description getter is wrong", "feed coyote", task1.getDescription());
    }

    /**
     * Tests the getDuration() method of the BasicTasks class.
     * It's important to ensure that the method returns the correct task duration.
     */
    @Test
    public void testBasicTasksGetDuration() {
        assertEquals("Duration getter is wrong", 5, task1.getDuration());
    }

    /**
     * Tests the getMaxWindow() method of the BasicTasks class.
     * It's important to ensure that the method returns the correct maximum window
     * value for the task.
     */
    @Test
    public void testBasicTasksGetMaxWindow() {
        assertEquals("Window getter is wrong", 3, task1.getMaxWindow());
    }

    /**
     * Tests the getPrepTime() method of the BasicTasks class.
     * It's important to ensure that the method returns the correct preparation time
     * value for the task.
     */
    @Test
    public void testBasicTasksGetPrepTime() {
        assertEquals("Preptime getter is wrong", 10, task1.getPrepTime());
    }

    /**
     * Tests the getSpecies() method of the BasicTasks class.
     * It's important to ensure that the method returns the correct species value
     * for the task.
     */
    @Test
    public void testBasicTasksGetSpecies() {
        assertEquals("Species getter is wrong", "coyote", task1.getSpecies());
    }

    /**
     * Tests the getCount() method of the BasicTasks class.
     * It's important to ensure that the method returns the correct count value for
     * the task.
     */
    @Test
    public void testBasicTasksGetCount() {
        assertEquals("Species getter is wrong", 3, task1.getCount());
    }

    /**
     * Tests the getAnimalName() method of the Treatment class.
     * It's important to ensure that the method returns the correct animal name
     * value for the treatment.
     */
    @Test
    public void testTreatmentGetName() {
        assertEquals("Name getter is wrong", "Loner", treatment1.getAnimalName());
    }

    /**
     * Tests the getTreatmentID() method of the Treatment class.
     * It's important to ensure that the method returns the correct treatment ID
     * value.
     */
    @Test
    public void testTreatmentGetTreatmentID() {
        assertEquals("TreatmentID getter is wrong", 2, treatment1.getTreatmentID());
    }

    /**
     * Tests the getAnimalID() method of the Treatment class.
     * It's important to ensure that the method returns the correct animal ID value.
     */
    @Test
    public void testTreatmentGetAnimalID() {
        assertEquals("AnimalID getter is wrong", 1, treatment1.getAnimalID());
    }

    /**
     * Tests the getTaskID() method of the Treatment class.
     * It's important to ensure that the method returns the correct task ID value.
     */
    @Test
    public void testTreatmentGetTaskID() {
        assertEquals("TaskID getter is wrong", 4, treatment1.getTaskID());
    }

    /**
     * Tests the getStartHour() method of the Treatment class.
     * It's important to ensure that the method returns the correct start hour value
     * for the treatment.
     */
    @Test
    public void testTreatmentGetStartHour() {
        assertEquals("StartHour getter is wrong", 2, treatment1.getStartHour());
    }

    /**
     * 
     * Tests the getMaxWindow() method of the Treatment class.
     * It's important to ensure that the method returns the correct max window value
     * for the treatment.
     */
    @Test
    public void testTreatmentGetMaxWindow() {
        assertEquals("Window getter is wrong", 1, treatment1.getMaxWindow());
    }

    /**
     * 
     * Tests the getDuration() method of the Treatment class.
     * It's important to ensure that the method returns the correct duration value
     * for the treatment.
     */
    @Test
    public void testTreatmentGetDuration() {
        assertEquals("Duration getter is wrong", 20, treatment1.getDuration());
    }

    /**
     * 
     * Tests the getDescription() method of the Treatment class.
     * It's important to ensure that the method returns the correct description
     * value for the treatment.
     */
    @Test
    public void testTreatmentGetDescription() {
        assertEquals("Description getter is wrong", "rebandage leg wound", treatment1.getDescription());
    }

    /**
     * 
     * Tests the setStartHour() method of the Treatment class.
     * It's important to ensure that the method sets the correct start hour value
     * for the treatment.
     */
    @Test
    public void testTreatmentSetStartHour() {
        treatment1.setStartHour(4);
        assertEquals("StartHour setter is wrong if the getter was correct", 4, treatment1.getStartHour());
    }

    /**
     * 
     * Tests the setBackup() and getBackup() methods of the Hour class.
     * 
     * It's important to ensure that the backup value is correctly set and
     * retrieved.
     */
    @Test
    public void testSetAndGetBackup() {
        assertFalse("Initial backup value should be false", hour1.getBackup());

        hour1.setBackup(true);
        assertTrue("Backup value should be true after calling setBackup(true)", hour1.getBackup());

        hour1.setBackup(false);
        assertFalse("Backup value should be false after calling setBackup(false)", hour1.getBackup());
    }

    /**
     * 
     * Tests the getHour() method of the Hour class.
     * It's important to ensure that the method returns the correct hour value for
     * the Hour object.
     */
    @Test
    public void testGetHour() {
        assertEquals("getHourlyTasks() should return the list of hourly tasks", "01:00", hour1.getHour());
    }

    /**
     * 
     * Tests the setTask() method of the Hour class.
     * 
     * It's important to ensure that the time available is correctly updated after
     * adding a task.
     * 
     * It's also important to ensure that an exception is thrown when trying to add
     * a task that exceeds the available time.
     */
    @Test
    public void testSetTask() throws NotEnoughTimeException {
        hour1.setTask(task1);
        assertEquals("Time available should be updated after adding a task", 60, hour1.getTimeAvailable());

        try {
            hour1.setTask(task2);
            fail("Should have thrown IllegalArgumentException for adding task that exceeds available time");
        } catch (NotEnoughTimeException e) {
            assertEquals("Time available should not be updated after failing to add a task", 60,
                    hour1.getTimeAvailable());
        }
    }

    /**
     * 
     * Tests the addTreatment() method of the Hour class.
     * It's important to ensure that the method returns the correct boolean value
     * when adding a treatment to the hour,
     * and that the time available is updated accordingly.
     */
    @Test
    public void testAddTreatment() {
        assertTrue("Treatment should be added and return true", hour1.addTreatment(treatment2));
        assertEquals("Time available should be updated after adding a treatment", 45, hour1.getTimeAvailable());
        assertFalse("Treatment should not be added and return false", hour1.addTreatment(treatment3));
        assertEquals("Time available should not be updated after failing to add a treatment", 45,
                hour1.getTimeAvailable());
    }

    /**
     * 
     * Tests the setTimeAvailable() and getTimeAvailable() methods of the Hour
     * class.
     * It's important to ensure that the methods correctly set and get the time
     * available for the hour.
     */
    @Test
    public void testSetAndGetTimeAvailable() {
        hour1.setTimeAvailable(30);
        assertEquals("Time available should be updated after calling setTimeAvailable()", 30, hour1.getTimeAvailable());
    }

    /**
     * 
     * Tests the getHourlyTasks() method of the Hour class.
     * 
     * It's important to ensure that the method returns the correct list of hourly
     * tasks for the hour.
     */
    @Test
    public void testGetHourlyTasks() throws NotEnoughTimeException {
        hour1.setTask(task3);

        ArrayList<BasicTasks> expectedTasks = new ArrayList<BasicTasks>();
        expectedTasks.add(task1);
        expectedTasks.add(task3);

        assertEquals("getHourlyTasks() should return the list of hourly tasks", expectedTasks, hour1.getHourlyTasks());
    }

    /**
     * 
     * Tests the getHourlyTreatments() method of the Hour class.
     * It's important to ensure that the method returns the correct list of
     * treatments for the hour.
     * 
     */
    @Test
    public void testGetHourlyTreatments() {
        hour1.addTreatment(treatment2);

        ArrayList<Treatment> expectedTreatments = new ArrayList<Treatment>();
        expectedTreatments.add(treatment1);
        expectedTreatments.add(treatment2);

        assertEquals("getHourlyTasks() should return the list of hourly tasks", expectedTreatments,
                hour1.getHourlyTreatments());
    }

    /**
     * 
     * Tests the duration() method of the AnimalTimes enum.
     * It's important to ensure that the method returns the correct duration value
     * for the given animal.
     * 
     */
    @Test
    public void testAnimalTimesDuration() {
        assertEquals("Duration is incorrect", 5, AnimalTimes.valueOf("PORCUPINE").duration());
    }

    /**
     * 
     * Tests the prepTime() method of the AnimalTimes enum.
     * It's important to ensure that the method returns the correct preparation time
     * value for the given animal.
     * 
     */
    @Test
    public void testAnimalTimesPrepTime() {
        assertEquals("Prep time is incorrect", 10, AnimalTimes.valueOf("COYOTE").prepTime());
    }

    /**
     * 
     * Tests the cleaning() method of the AnimalTimes enum.
     * It's important to ensure that the method returns the correct cleaning time
     * value for the given animal.
     * 
     */
    @Test
    public void testAnimalTimesCleaning() {
        assertEquals("Cleaning time is incorrect", 10, AnimalTimes.valueOf("PORCUPINE").cleaning());
    }

    /**
     * 
     * Tests the startHour() method of the AnimalTimes enum.
     * Ensures that the correct start hour is returned for each animal time.
     */
    @Test
    public void testAnimalTimesStartHour() {
        assertEquals("Start hour is incorrect", 19, AnimalTimes.valueOf("COYOTE").startHour());
    }

    /**
     * 
     * Tests the maxWindow() method of the AnimalTimes enum.
     * Ensures that the correct maximum window is returned for each animal time.
     */
    @Test
    public void testAnimalTimesMaxWindow() {
        assertEquals("Max window is incorrect", 3, AnimalTimes.valueOf("FOX").maxWindow());
    }

    /**
     * 
     * Tests the activity() method of the AnimalTimes enum.
     * Ensures that the correct activity is returned for each animal time.
     */
    @Test
    public void testAnimalTimesActivity() {
        assertEquals("Activity is incorrect", "nocturnal", AnimalTimes.valueOf("RACCOON").activity());
    }

    /**
     * 
     * Tests the resetHours() method of the Schedule class.
     * It's important to ensure that the method clears all hours from the schedule.
     * 
     * @throws NotEnoughTimeException
     */
    @Test
    public void testClearHours() throws NotEnoughTimeException {
        schedule.generateSchedule();
        boolean empty = false;
        boolean full = false;

        for (Hour hour : schedule.getHours()) {
            if (!hour.getHourlyTasks().isEmpty()) {
                full = true;
            }
        }

        assertEquals(true, full);
        schedule.resetHours();

        for (Hour hour : schedule.getHours()) {
            if (hour.getHourlyTasks().isEmpty()) {
                empty = true;
            }
        }
        assertEquals(true, empty);

    }

    /**
     * 
     * Tests the isOrphan() method of the Schedule class.
     * It's important to ensure that the method correctly identifies orphan species
     * in the schedule.
     */
    @Test
    public void testIsOrphan() {
        assertTrue("isOrphan didn't return true for species that was orphan", schedule.isOrphan("fox"));
        assertFalse("isOrphan didn't return false for species that was orphan", schedule.isOrphan("coyote"));
    }

    /**
     * 
     * Tests the insertBasicTasks() method of the Schedule class.
     * It's important to ensure that the method correctly inserts the basic tasks
     * into the schedule.
     */
    @Test
    public void testInsertBasicTasks() {
        ArrayList<BasicTasks> tasks = schedule.getTasks();
        assertEquals("Size of the tasks was incorrect", schedule.getAnimals().keySet().size() * 2, tasks.size());
    }

    /**
     * 
     * Tests the modifyTreatment() method of the Schedule class.
     * It's important to ensure that the method modifies the start hour of the
     * specified treatments correctly.
     */
    @Test
    public void testModifyTreatment() {
        int[] treamentChange = { 1, 2 };
        int[] startChange = { 2, 0 };
        int expectedTreatment1Start = 2;
        int expectedTreatment2Start = 0;
        int actualTreament1Start = -1;
        int actualTreament2Start = -1;
        schedule.modifyTreatment(treamentChange, startChange);
        for (Treatment treament : schedule.getTreatments()) {
            if (treament.getTreatmentID() == 1) {
                actualTreament1Start = treament.getStartHour();
            }
            if (treament.getTreatmentID() == 2) {
                actualTreament2Start = treament.getStartHour();
            }
        }
        assertEquals("modifyTreatment did not modify the start hour of the first treatment", expectedTreatment1Start,
                actualTreament1Start);
        assertEquals("modifyTreatment did not modify the start hour of the second treatment", expectedTreatment2Start,
                actualTreament2Start);
    }

    /**
     * 
     * Tests the generateSchedule() method of the Schedule class.
     * It's important to ensure that the method generates a schedule that contains
     * all basic tasks and treatment tasks.
     * It's also important to ensure that the method throws the
     * NotEnoughTimeException if there isn't enough time to generate the schedule.
     */
    @Test
    public void testGenerateSchedule() throws NotEnoughTimeException {
        schedule.generateSchedule();
        ArrayList<BasicTasks> tasksArrayActual = new ArrayList<>();
        ArrayList<Treatment> treatmentsArrayActual = new ArrayList<>();
        int expectedCount = 0;
        int actualCount = 0;
        for (Hour hour : schedule.getHours()) {
            System.out.println(hour.getHourlyTasks());
            tasksArrayActual.addAll(hour.getHourlyTasks());
            treatmentsArrayActual.addAll(hour.getHourlyTreatments());
        }
        for (BasicTasks taskActual : tasksArrayActual) {
            actualCount += taskActual.getCount();
        }
        for (BasicTasks taskExpected : schedule.getTasks()) {
            expectedCount += taskExpected.getCount();
        }
        assertEquals("The schedule generated did not pull all basic tasks in.", expectedCount, actualCount);
        assertEquals("The schedule generated did not pull all treatment tasks in", schedule.getTreatments().size(),
                treatmentsArrayActual.size());
    }
}