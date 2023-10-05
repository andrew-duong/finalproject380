package edu.ucalgary.oop;

import java.util.regex.*;
import java.util.ArrayList;
import java.util.ArrayList.*;

/**
 * This is class for the Animals in the Database.
 * Each animal has a species, name and id.
 * If the animal is considered an orphan, it must also be recorded how many orphans
 * there are in that instance.
 */

public class Animal {
    private String species;
    private String name;
    private int animalID;
    private boolean orphan = false;
    private int orphanCount = 0;

    /**
     * Constructs a new Animal object with the specified species, name, and ID.
     *
     * @param species  the species of the animal
     * @param name     the name of the animal
     * @param animalID the ID of the animal
     * @throws IllegalArgumentException if the name or species does not match the expected
     *                                  format
     */
    public Animal(String species, String name, int animalID) throws IllegalArgumentException {
        try{
            this.species = AnimalTimes.valueOf(species.toUpperCase()).toString();
        }catch (Exception e) {
                throw new IllegalArgumentException("Invalid species type");
        }

        String[] nameList = name.split("\\s*(?:,|\\band\\b)\\s*");

        if (nameList.length > 1) {
            this.orphanCount = nameList.length;
            this.orphan = true;
        }

        this.name = name;

        this.animalID = animalID;
    }

    /**
     * Returns the ID of the animal.
     *
     * @return the ID of the animal
     */
    public int getAnimalID() {
        return this.animalID;
    }

    /**
     * Returns the species of the animal.
     *
     * @return the species of the animal
     */

    public String getSpecies() {
        return this.species;
    }

    /**
     * Returns the name of the animal.
     *
     * @return the name of the animal
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns true if the animal is an orphan, false otherwise.
     *
     * @return true if the animal is an orphan, false otherwise
     */
    public Boolean getOrphan() {
        return this.orphan;
    }

    /**
     * Returns the number of orphaned siblings the animal has.
     *
     * @return the number of orphaned siblings the animal has
     */
    public int getOrphanCount() {
        return this.orphanCount;
    }
}
