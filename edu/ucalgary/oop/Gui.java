/**
The Gui class represents a graphical user interface for a scheduling application.
It contains functionality for logging in, displaying a schedule, and generating a new schedule.
The Gui class implements the ActionListener interface for handling user events.
Ethan Bensler, Liam Brennan, Andrew Duong, Joseph Duong

*/

package edu.ucalgary.oop;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

public class Gui implements ActionListener {

    private ScheduleBuilder scheduleBuilder;
    private JFrame log = new JFrame("Login");
    private JPanel top = new JPanel();
    private JTextField user = new JTextField(20);
    private JPasswordField pass = new JPasswordField(20);
    private JTable table;
    private String[] columnName = { "Hour", "Task Description", "Backup" };
    private Object[][] data;
    private Object[] tasks;
    private TableCellEditor editor;
    private JComboBox<String> hourComboBox;
    private String userName;
    private String password;

    /**
     * The main method creates a new Gui object.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Gui gui = new Gui();
    }

    /**
     * The Gui constructor creates a new JFrame for logging in.
     * It sets the JFrame's size, default close operation, and creates a JPanel for
     * the login form.
     * It then adds JTextFields for the username and password, along with JLabels
     * for each.
     * It creates a JButton for logging in and adds it to the form.
     * Finally, it adds the form to the JFrame and sets it to visible.
     */
    public Gui() {
        log.setSize(600, 500);
        log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel login = new JPanel(); // creating panels for the layout
        JPanel top = new JPanel();
        JPanel mid = new JPanel();
        JPanel bot = new JPanel();

        user.setPreferredSize(new Dimension(200, 30)); // setting default dimensions
        pass.setPreferredSize(new Dimension(200, 30));

        JLabel us = new JLabel("Username:"); // labels for the input fields
        JLabel pa = new JLabel("Password:");

        login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS)); // base layout

        JButton logButton = new JButton("Login");
        logButton.setActionCommand("success"); // adding functionality to the button
        logButton.addActionListener(this);

        top.add(us); // adding elements to the correct panels
        top.add(user);
        mid.add(pa);
        mid.add(pass);
        bot.add(logButton);
        login.add(top); // adding panels to the main panel
        login.add(mid);
        login.add(bot);

        log.add(login); // adding main panel to frame and making it visible
        log.pack();
        log.setVisible(true);
    }

    /**
     * The loginSuccess method creates a new JFrame for displaying the schedule.
     * It sets the JFrame's size, default close operation, and creates JButtons for
     * generating and exporting the schedule.
     * It then creates two JPanels, one for the task labels and one for the task
     * input fields, and adds them to a single JPanel.
     * Finally, it adds the JPanels to the JFrame and sets it to visible.
     */
    public void loginSuccess() {
        JFrame frame = new JFrame("Schedule Prototyping"); // initializing the schedule frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JButton gen = new JButton("Regenerate"); // creating buttons
        JButton text = new JButton("Export as .txt");
        JButton reset = new JButton("Reset To Initial");
        top.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
        top.add(gen);
        top.add(text);
        top.add(reset);

        gen.setActionCommand("gen"); // giving buttons functionality
        text.setActionCommand("text");
        reset.setActionCommand("reset");
        gen.addActionListener(this);
        text.addActionListener(this);
        reset.addActionListener(this);

        String[] hours = new String[24];
        for (int i = 0; i < hours.length; i++) {
            hours[i] = String.format("%02d:00", i);
        }
        this.hourComboBox = new JComboBox<>(hours);

        hourComboBox.addPopupMenuListener(new PopupMenuListener() { // adds event listeners to all comboboxes in the
                                                                    // table
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

                int row = table.getSelectedRow();
                if (row == -1) {
                    return;
                }
                String selectedOption = (String) hourComboBox.getSelectedItem();
                int newStart = selectedOption.substring(0, 2).equals("00")
                        ? 0
                        : Integer.parseInt(selectedOption.substring(0, 2));
                if (tasks[row] instanceof Treatment) {
                    Treatment treat = (Treatment) tasks[row];
                    if (treat.getStartHour() != newStart) {
                        treat.setStartHour(newStart);
                        treat.setMaxWindow(1);
                        scheduleBuilder.updateSQL(treat.getTreatmentID(), newStart);
                    }
                }
            }
        });

        editor = new DefaultCellEditor(hourComboBox);

        DefaultTableModel model = new DefaultTableModel(new Object[][] {}, columnName); // initialize table/model
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 600));
        JPanel panel = new JPanel();

        builder(); // builds the table

        panel.add(scrollPane); // adding panels & elements to the frame
        frame.add(top, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true); // set the frame to be visible while hiding the login frame
        log.setVisible(false);
    }

    /**
     * 
     * This method is called when a button is clicked in the user interface.
     * It handles the different actions that can be performed based on the
     * button that was clicked.
     * 
     * @param e the ActionEvent object that contains information about the event is
     *          used
     *          to get the proper action command
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("success".equals(e.getActionCommand())) { // login button
            try { // tries connecting to the database using the inputted credentials, if
                  // successful, will create and diplay the schedule builder
                this.userName = user.getText();
                this.password = new String(pass.getPassword());
                scheduleBuilder = new ScheduleBuilder(user.getText(), new String(pass.getPassword()));
                loginSuccess();
            } catch (Exception d) { // if the credentials are incorrect, will allow the user to try again and gives
                                    // them an alert
                JOptionPane.showMessageDialog(null, "Incorrect username or password. Please try again.");
            }
            // text button, this will invoke the method that creates the text file
        } else if ("text".equals(e.getActionCommand())) {
            // collects
            // asks for user confirmation before making text file
            int res = JOptionPane.showConfirmDialog(null,
                    "Are you sure you would like to export this schedule?" + "\n" + findBackupsNeeded()); // for
            // functionality for what to do depending on user input
            if (res == JOptionPane.YES_OPTION) {
                scheduleBuilder.outputTextSchedule();
            }
        }
        // generate button functionality
        else if ("gen".equals(e.getActionCommand())) {
            scheduleBuilder.resetHours();
            builder();
        }
        // reset button functionality
        else if ("reset".equals(e.getActionCommand())) {
            resetDefaultScheduleBuilder();
            builder();
        }
    }

    /**
     * Generates a schedule using the `scheduleBuilder` object and populates a table
     * with the generated data.
     * If an exception is caught during the generation process, an error message is
     * displayed and the method returns.
     */
    public void builder() {
        try {
            scheduleBuilder.generateSchedule();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Schedule could not be generated automatically, please manually configure your schedule.");
            scheduleBuilder.createDummySchedule();
        }
        // initialize arrays to be used
        this.data = new Object[(scheduleBuilder.getTreatments().size() + scheduleBuilder.getTasks().size())][3];

        this.tasks = new Object[(scheduleBuilder.getTreatments().size() + scheduleBuilder.getTasks().size())];
        ArrayList<Hour> hours = scheduleBuilder.getHours();
        int index = 0;

        // populates the arrays with the correct data, "data" is for the table, "tasks"
        // is for parsing and regenerating the schedule
        for (Hour hour : hours) {
            for (Treatment treatment : hour.getHourlyTreatments()) {
                data[index][0] = hour.getHour();
                tasks[index] = treatment;
                data[index][1] = treatment.getDescription() + " (" + treatment.getAnimalName() + ")";
                if (hour.getBackup()) {
                    data[index][2] = "Backup Required";
                } else {
                    data[index][2] = "";
                }
                index++;
            }
            for (BasicTasks basicTask : hour.getHourlyTasks()) {
                data[index][0] = hour.getHour();
                tasks[index] = basicTask;
                data[index][1] = basicTask.getDescription() + " (" + basicTask.getCount() + ")";
                if (hour.getBackup()) {
                    data[index][2] = "Backup Required";
                } else {
                    data[index][2] = "";
                }
                index++;
            }
        }
        DefaultTableModel model = (DefaultTableModel) table.getModel(); // setting the values for the table using "data"
        model.setRowCount(0);
        for (Object[] row : data) {
            model.addRow(row);
        }
        table.getColumnModel().getColumn(0).setCellEditor(editor); // formatting the columns of the table
        TableColumn column = table.getColumnModel().getColumn(1);
        column.setPreferredWidth(300);
        column = table.getColumnModel().getColumn(2);
        column.setPreferredWidth(150);

    }

    /**
     * 
     * Resets the scheduleBuilder object to a new instance of ScheduleBuilder,
     * with the username and password passed as arguments to the constructor.
     */
    public void resetDefaultScheduleBuilder() {
        this.scheduleBuilder = new ScheduleBuilder(this.userName, this.password);
    }

    /**
     * Returns a string containing the hours for which a backup volunteer is
     * required.
     * If no backup is required, an empty string is returned.
     *
     * @return a string with the hours for which a backup volunteer is required, or
     *         an empty string if no backup is needed.
     */
    private String findBackupsNeeded() {
        String backups = "";
        ArrayList<Hour> hours = scheduleBuilder.getHours();
        for (Hour hour : hours) {
            if (hour.getBackup()) {
                if (backups == "") {
                    backups += "A Backup Volunteer is required for the following hours:\n";
                }
                backups += hour.getHour() + "\n";
            }
        }
        return backups;
    }
}
