package edu.ucalgary.oop;

/**
 * 
 * An enum class representing different types of animals and their properties.
 * Ethan Bensler, Liam Brennan, Andrew Duong, Joseph Duong
 * 
 */
public enum AnimalTimes {
    /**
     * 
     * The porcupine animal type with a duration of 5 mins, no preparation time, a start
     * time at 19, a maximum window of 3 hours,
     * a crepuscular activity, and 10 mins of cleaning required.
     */
    PORCUPINE {
        public String toString(){
            return "porcupine";
        }

        public int duration() {
            return 5;
        }

        public int prepTime() {
            return 0;
        }

        public int startHour() {
            return 19;
        }

        public int maxWindow() {
            return 3;
        }

        public String activity() {
            return "crespuscular";
        }

        public int cleaning() {
            return 10;
        }
    },

    /**
     * 
     * The beaver animal type with a duration of 5 mins, no preparation time, a start
     * time at 8, a maximum window of 3 hours,
     * a diurnal activity, and 5 mins of cleaning required.
     */
    BEAVER {
        public String toString(){
            return "beaver";
        }
        public int duration() {
            return 5;
        }

        public int prepTime() {
            return 0;
        }

        public int startHour() {
            return 8;
        }

        public int maxWindow() {
            return 3;
        }

        public String activity() {
            return "diurnal";
        }

        public int cleaning() {
            return 5;
        }
    },
    /**
     * 
     * The fox animal type with a duration of 5 mins, a preparation time of 5 mins, a
     * start time at 0, a maximum window of 3 hours,
     * a nocturnal activity, and 5 mins of cleaning required.
     */
    FOX {
        public String toString(){
            return "fox";
        }
        public int duration() {
            return 5;
        }

        public int prepTime() {
            return 5;
        }

        public int startHour() {
            return 0;
        }

        public int maxWindow() {
            return 3;
        }

        public String activity() {
            return "nocturnal";
        }

        public int cleaning() {
            return 5;
        }
    },
    /**
     * 
     * The coyote animal type with a duration of 5 mins, a preparation time of 10 mins, a
     * start time at 19, a maximum window of 3 hours,
     * a crepuscular activity, and 5 mins of cleaning required.
     */
    COYOTE {
        public String toString(){
            return "coyote";
        }
        public int duration() {
            return 5;
        }

        public int prepTime() {
            return 10;
        }

        public int startHour() {
            return 19;
        }

        public int maxWindow() {
            return 3;
        }

        public String activity() {
            return "crespuscular";
        }

        public int cleaning() {
            return 5;
        }
    },
    /**
     * 
     * The racoon animal type with a duration of 5 mins, no preparation time, a
     * start time at 0, a maximum window of 3 hours,
     * a nocturnal activity, and 5 mins of cleaning required.
     */
    RACCOON {
        public String toString(){
            return "raccoon";
        }
        public int duration() {
            return 5;
        }

        public int prepTime() {
            return 0;
        }

        public int startHour() {
            return 0;
        }

        public int maxWindow() {
            return 3;
        }

        public String activity() {
            return "nocturnal";
        }

        public int cleaning() {
            return 5;
        }
    };
        /**
     * 
     * Abstract classes for each animal type.
     */
    public abstract int duration();

    public abstract int prepTime();

    public abstract int startHour();

    public abstract int maxWindow();

    public abstract String activity();

    public abstract int cleaning();

}
