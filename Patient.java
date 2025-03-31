/**
 * Class Name: Patient
 * 
 * Author: Alina Kenny
 * Date: Dec 2, 2024
 * 
 * Purpose:
 * Creates an object Patient with necessary variables
 * 
 * Features:
 * - Name, arrivalTime, startTime, priority, probability
 * - get and set methods
 *  
 * Dependencies:
 * None
 * 
 **/


package TP;

public class Patient {

    //variables
    private String name;
    private int arrivalTime;
    private int startTime = 0;
    private int priority;
    private double probability;


    public Patient(String n, int t, double p) {
        name = n;
        arrivalTime = t;
        probability = p;
    }

    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getPriority() {
        return priority;
    }

    public double getProbability() {
        return probability;
    }

    public void setName(String n) {
        name = n;
    }

    public void setArrivalTime(int t) {
        arrivalTime = t;
    }

    public void setStartTime(int t) {
        startTime = t;
    }


    public void setPriority(int p) {
        priority = p;
    }

    public void setProbability(double p) {
        probability = p;
    }

}
