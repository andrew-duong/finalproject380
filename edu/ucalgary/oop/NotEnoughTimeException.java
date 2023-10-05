package edu.ucalgary.oop;

public class NotEnoughTimeException extends Exception{
    public NotEnoughTimeException(){
        super("Not enough time in an hour to fit in a task");
    }
}
