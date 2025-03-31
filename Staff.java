/**
 * Class Name: Staff
 * 
 * Author: Alina Kenny
 * Date: Dec 2, 2024
 * 
 * Purpose:
 * Creates an object Staf with necessary variables (for doctors, nurses, and admin assistants)
 * 
 * Features:
 * - Name, examTime, totalTime, isWorking, currentCycle, type, and patient
 * - get and set methods
 *  
 * Dependencies:
 * None
 * 
 **/

package TP;

public class Staff {

    private String name;
    private int examTime;
    private int totalTime = 0;
    private boolean isWorking = false;
    private int currentCycle = 0;
    private String type; //either 'n', 'd', 'aa' (set in initSetup() method)
    private Patient patient; //for printFile


    public Staff(String n, int t, String s) {
        name = n;
        examTime = t;
        type = s;
    }

    public String getName() {
        return name;
    }

    public int getExamTime() {
        return examTime;
    }

    public int getTotalTime() {
        return totalTime;
    }
    
    public boolean getIsWorking() {
        return isWorking;
    }

    public int getCurrentCycle() {
        return currentCycle;
    }
    
    public void setName(String n) {
        name = n;
    }

    public void setExamTime(int t) {
        examTime = t;
    }

    public void setTotalTime(int t) {
        totalTime = t;
    }

    public void setIsWorking(boolean b) {
        isWorking = b;
    }

    public void setCurrentCycle(int i) {
        currentCycle = i;
    }

    public void addCurrentCycle() {
        currentCycle++;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


}
