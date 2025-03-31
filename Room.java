/**
 * Class Name: Room
 * 
 * Author: Alina Kenny
 * Date: Dec 2, 2024
 * 
 * Purpose:
 * Creates an object Room with necessary variables
 * 
 * Features:
 * - Name, totalTime, occupied, patient, staff, checkByNurse
 * - get and set methods
 *  
 * Dependencies:
 * None
 * 
 **/

package TP;

public class Room {

    private String name;
    private int totalTime = 0;
    private boolean occupied = false;
    private Patient patient = null;
    private Staff staff = null;
    private boolean checkedByNurse = false;

    public Room(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public boolean getOccupied() {
        return occupied;
    }

    public Patient getPatient() {
        return patient;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setName(String n) {
        name = n;
    }

    public void setTotalTime(int t) {
        totalTime = t;
    }

    public void setOccupied(boolean b) {
        occupied = b;
    }

    public void setPatient(Patient p) {
        patient = p;
    }

    public void setStaff(Staff s) {
        staff = s;
    }

    public boolean getCheckedByNurse() {
        return checkedByNurse;
    }

    public void setCheckedByNurse(boolean checkedByNurse) {
        this.checkedByNurse = checkedByNurse;
    }


}
