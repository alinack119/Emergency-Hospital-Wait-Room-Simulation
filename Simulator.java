/**
 * Class Name: Simulator
 * 
 * Author: Alina Kenny
 * Date: Dec 2, 2024
 * 
 * Purpose:
 * Goes through the logic of the simulation of an Emergency Room
 * 
 * Features:
 * - all variables needed for the simulaiton at the top
 * - simulator method goes through interations
 * - printToFile prints each interval data to file
 * - printStat prints the overall statistics to the file
 *  
 * Dependencies:
 * None
 * 
 **/

package TP;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;

public class Simulator {

    private int numDoctors = 3;
    private int doctorTime = 3;
    private int numNurse = 2;
    private int nurseTime = 2;
    private int numRoom = 4;
    private int numAdmin = 1;
    private int adminTime = 2;
    private int numQue = 3;
    private double[] arrivalProb = {0.2, 0.3, 0.5}; //= {0.1, 0.3, 0.5}; //has to be in asceding order
    private int intervalTime = 1;
    private int totalTime = 70;
    private int totalP = 0; //total patient
    private double totalWait;
    private double averageWait;

    private PrintWriter pw;
    private static String file = "simulationOut.txt";
    

    //stacks for staff 

    private LinkedStack<Staff> doctors = new LinkedStack<>();
    private LinkedStack<Staff> nurses = new LinkedStack<>();
    private LinkedStack<Staff> admins = new LinkedStack<>();
    private Room[] rooms = new Room[numRoom];

    //queues used in simulation
    private LinkedQueue<Patient> arrivals = new LinkedQueue<>(); //when patient arrives before sorted by priority
    private PriorityQueue<Patient> waitroom = new PriorityQueue<>(); //patients sorted by priority
    private LinkedQueue<Patient> checkout = new LinkedQueue<>(); //patients waiting to see admin
    private LinkedQueue<Patient> beingCheckedout = new LinkedQueue<>(); ////patients currently getting checkoutOut (seeing admin)
    private LinkedQueue<Staff> adminWorking = new LinkedQueue<>(); //admin who are currently seeing a patient (working)
    private LinkedQueue<Patient> goingHome = new LinkedQueue<>(); //patients going home (done with examination)




    public void patientArrival(int currentTime) {
        totalP++;
        double rand = Math.random(); //0 - 1
        //System.out.println(rand);

        Patient P = new Patient("P" + totalP, currentTime, rand); //currentTime = arrivalTime, rand = probability
        arrivals.add(P); //adds to ordinary queue

       

    }

    public void patientSort() {
        Patient patient = arrivals.remove();

        // System.out.println(patient);
        // System.out.println(patient.getProbability());
        for(int i = 0; i <= arrivalProb.length-1; i++){
            if(patient.getProbability() <= arrivalProb[i]) { //compare to the arrival probability
                //(numque - i) --> set the priority higher for arrival prob lower
                //this value will be higher for when during the beginning of the loop of the array, it takes the lower probability which is for higher probability patients
                patient.setPriority(numQue - i); //numQue acts as the number of different possible priority numbers there could be
                break;
            }
            

        }

        waitroom.push(patient); //push method in priorityQueue class sorts by priority

    }

    //finds if patient is waiting in waitroom queue
    public boolean patientAvailable() {
        if(waitroom.peek() != null) {
            return true;
        }
        else {
            return false;
        }
    }

    //returns the next patient in the queue
    public Patient getPatientAvailable() {
        Patient patient = waitroom.remove();
        return patient;
    }

    //finds if doctor is available
    public boolean doctorAvaibale() {
        if(doctors.peek() != null) {
            return true;
        }
        else {
            return false;
        }
    }

    //returns doctor available
    public Staff getDoctortAvailable() {
        Staff doctor = doctors.pop();
        return doctor;
    }

    //finds if nurse is available
    public boolean nurseAvailable() {
        if(nurses.peek() != null) {
            return true;
        }
        else {
            return false;
        }
    }

    //returns nurse available
    public Staff getNurseAvailable() {
        Staff nurse = nurses.pop();
        return nurse;
    }

    //finds if admin is available
    public boolean adminAvailable() {
        if(admins.peek() != null) {
            return true;
        }
        else {
            return false;
        }
    }

    //returns admin available
    public Staff getAdminAvailable() {
        Staff admin = admins.pop();
        return admin;
    }


    public boolean roomAvailable() {
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i] != null && !rooms[i].getOccupied()) {
                return true;
            }
            
        }

        return false;
    }

    public Room getRoomAvailable() {
        
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i] != null && !rooms[i].getOccupied()) {
                return rooms[i]; 
            }
        }
        return null; 
    }

    //simulate method which has all the logic for dealing with the resources
    public void simulate() {

        //loop for each interval
        for (int currentTime = 0; currentTime < totalTime; currentTime += intervalTime) {

            patientArrival(currentTime);
            patientSort();

            //new patient
            while (patientAvailable() && nurseAvailable() && roomAvailable()) {
                Room room = getRoomAvailable();
                Patient patient = getPatientAvailable();
                room.setPatient(patient);
                Staff nurse = getNurseAvailable();
                room.setStaff(nurse);

                patient.setStartTime(currentTime);
                //System.out.println(patient.getName() + ", start time: " + patient.getStartTime() + ", arrivalTime: " + patient.getArrivalTime());
                nurse.setIsWorking(true);
                room.setOccupied(true);
                room.setCheckedByNurse(false);

            }

            //check cycle time
            for (int i = 0; i < rooms.length; i++) {
                if (rooms[i] != null && rooms[i].getOccupied() && rooms[i].getStaff() != null) {
                    if ((rooms[i].getStaff().getCurrentCycle() >= rooms[i].getStaff().getExamTime()) && rooms[i].getStaff().getType().equals("n")) {
                        rooms[i].getStaff().setIsWorking(false);
                        rooms[i].getStaff().setCurrentCycle(0);
                        nurses.push(rooms[i].getStaff());
                        //System.out.println("Nurse returned to stack.");
                        rooms[i].setStaff(null);
                        rooms[i].setCheckedByNurse(true);
                        rooms[i].setOccupied(true);
                    } else if (((rooms[i].getStaff().getCurrentCycle() >= rooms[i].getStaff().getExamTime())) && rooms[i].getStaff().getType().equals("d")) {
                        rooms[i].getStaff().setIsWorking(false);
                        rooms[i].getStaff().setCurrentCycle(0);
                        doctors.push(rooms[i].getStaff());
                        rooms[i].setStaff(null);
                        checkout.add(rooms[i].getPatient());
                        rooms[i].setPatient(null);
                        rooms[i].setOccupied(false);
                    
                    }
                }
               
            }
            //for admin
            while(!adminWorking.isEmpty() && adminWorking.getFront().getCurrentCycle() >= adminWorking.getFront().getExamTime()) {
                adminWorking.getFront().setCurrentCycle(0);
                adminWorking.getFront().setIsWorking(false);
                admins.push(adminWorking.getFront());
                adminWorking.remove();
                goingHome.add(beingCheckedout.remove());
                
            }

            //assign doctor
            for (int i = 0; i < rooms.length; i++) {
                if (rooms[i] != null && rooms[i].getOccupied() && rooms[i].getCheckedByNurse() && doctorAvaibale() && (rooms[i].getStaff() == null || rooms[i].getStaff().getType().equals("n"))) {
                    rooms[i].setStaff(getDoctortAvailable());
                    rooms[i].getStaff().setCurrentCycle(0);
                    rooms[i].getStaff().setIsWorking(true);
                }
            }

            //assign admin
            while (checkout.size() > 0 && adminAvailable()) {
                Staff admin = getAdminAvailable();
                adminWorking.add(admin);
                Patient p = checkout.remove();
                beingCheckedout.add(p);
                admin.setPatient(p);
                admin.setIsWorking(true);
                admin.setCurrentCycle(0);
                
            }

            //intervalTime
            for (int i = 0; i < rooms.length; i++) {
                if (rooms[i] != null && rooms[i].getOccupied()) {
                    rooms[i].setTotalTime(rooms[i].getTotalTime() + intervalTime);
                    if (rooms[i].getStaff() != null) {
                        rooms[i].getStaff().setCurrentCycle(rooms[i].getStaff().getCurrentCycle() + intervalTime);
                        //System.out.println(rooms[i].getStaff().getName() + ": " + rooms[i].getStaff().getCurrentCycle());
                        rooms[i].getStaff().setTotalTime(rooms[i].getStaff().getTotalTime() + intervalTime);
                    }
                }
            }

            //for admin time
            Node<Staff> cursor = adminWorking.getFrontNode();
            while (cursor != null && adminWorking.size() > 0) {
                cursor.getData().setCurrentCycle(adminWorking.getFront().getCurrentCycle() + intervalTime);
                //System.out.println(cursor.getData().getName() + ": " + cursor.getData().getCurrentCycle());
                cursor.getData().setTotalTime(adminWorking.getFront().getCurrentCycle() + intervalTime);
                //System.out.println(cursor.getData().getName() + ": " + cursor.getData().getCurrentCycle());
                cursor = cursor.getLink();
            }

            printToFile();






            

        } //end of interval loop


        printStat();
        




    }

    //print method to print each iteration to the output file
    public void printToFile() {

        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))){ //when false it overwrites //when true appends and see each iteration

            //PrintWriter pw = new PrintWriter(new FileWriter(file, false))

            //pw = new PrintWriter(file);
            
            Node<Staff> admin1 = adminWorking.getFrontNode();
            while (admin1 != null && adminWorking.size() > 0) {
                pw.println(admin1.getData().getName() + ": " + admin1.getData().getPatient().getName());
                admin1 = admin1.getLink();
        
            }
    
            pw.println();

            Node<Staff> admin2 = admins.peek();
            while(admin2 != null) {
                pw.println(admin2.getData().getName() + ": available");
                admin2 = admin2.getLink();
    
            }

            pw.println();
    
            Node<Patient> COQ = checkout.getFrontNode();
            pw.println("Checkout queue: ");
            while (COQ != null && checkout.size() > 0) {
                pw.print(COQ.getData().getName() +  ", ");
                COQ = COQ.getLink();
            }

            pw.println();
            pw.println();
    
            for (int i = 0; i < rooms.length; i++) {
                if (rooms[i] != null) {
                    if (rooms[i].getOccupied() && rooms[i].getStaff() != null) {
                        pw.println(rooms[i].getName() + ": " + rooms[i].getPatient().getName() + ", " + rooms[i].getStaff().getName());
    
                    }
                    else if (rooms[i].getOccupied() && rooms[i].getStaff() == null) {
                        pw.println(rooms[i].getName() + ": " + rooms[i].getPatient().getName() + ", waiting for doctor.");
                    }
                    else {
                        pw.println(rooms[i].getName() + ": available");
                    }
                }
                
                
            }
    

            pw.println();

            Node<Staff> doctorS = doctors.peek();
            while(doctorS != null) {
                pw.println(doctorS.getData().getName() + ": available");
                doctorS = doctorS.getLink();
    
            }

            pw.println();
    
            Node<Staff> nurseS = nurses.peek();
            while(nurseS != null) {
                pw.println(nurseS.getData().getName() + ": available");
                nurseS = nurseS.getLink();
    
            }

            pw.println();
    
            // pw.println("Waitroom priority queue: ");
            // PriorityNode<Patient> patientq = waitroom.peek();
            // while (patientq != null && waitroom.size() > 0) {
            //     pw.print(patientq.getData().getName() + ": " + patientq.getData().getPriority() + ", ");  
            //     patientq = patientq.getLink();         
            // }

            // pw.println();

            pw.println("Waitroom priority queue: ");
            PriorityNode<Patient> patientq = waitroom.peek();
            int currentPriority = -1; 

            //print logic to print each priority number patients in seperate lines for clarity
            while (patientq != null && waitroom.size() > 0) {
                int patientPriority = patientq.getData().getPriority();

                if (patientPriority != currentPriority) {
                    if (currentPriority != -1) {
                        pw.println(); 
                    }
                    pw.print("Priority " + patientPriority + ": ");
                    currentPriority = patientPriority;
                }

                pw.print(patientq.getData().getName() + ", ");

                patientq = patientq.getLink();
            }

            pw.println();

            pw.println("-----------------------------------------------------------------------");
           
            pw.println();

            
            pw.flush();

            pw.close();
            
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
  
    

            

    }

    //print method to print the stats at the end of the simulation
    public void printStat() {

        try (FileWriter fw = new FileWriter("simulationOut.txt", true);
            PrintWriter pw = new PrintWriter(fw)){
            
            
            //to check that after the simulation time is over, the staff still in the rooms are returned to their stacks for statistical calculations
            for (int i = 0; i < rooms.length; i++) {
                if (rooms[i].getStaff() != null && rooms[i].getStaff().getType().equals("n")) {
                    nurses.push(rooms[i].getStaff());
                }
                else if (rooms[i].getStaff() != null && rooms[i].getStaff().getType().equals("d")) {
                    doctors.push(rooms[i].getStaff());
                }
            }
            Node<Staff> cursor1 = adminWorking.getFrontNode();
                while (cursor1 != null && adminWorking.size() > 0) {
                    admins.push(cursor1.getData());
                    cursor1 = cursor1.getLink();
                }
            

            //get statistics to print out (assign to instance variable)
            totalP = goingHome.size();
            pw.println("Total patients served: " + totalP);
            totalWait = 0;

            Node<Patient> cursor2 = goingHome.getFrontNode();
            while (cursor2 != null) {
                double wait = cursor2.getData().getStartTime() - cursor2.getData().getArrivalTime();
                totalWait = totalWait + wait;
                cursor2 = cursor2.getLink();
            }

            // for (int i = 0; i < totalP; i++) {
            //     int wait = cursor2.getData().getStartTime() - cursor2.getData().getArrivalTime();
            //     totalWait = totalWait + wait;
            //     cursor2 = cursor2.getLink();
            // }
            
            averageWait = totalWait / totalP;
            pw.println("Average wait: " + averageWait + " seconds");

            pw.println();

            //total time used by resources
            pw.println("Total time for resources: ");

            

            Node<Staff> cursor3 = nurses.peek();
            while (cursor3 != null) {
                pw.println(cursor3.getData().getName() + ": " + cursor3.getData().getTotalTime() + " seconds");
                
                cursor3 = cursor3.getLink();
            }

            pw.println();

            Node<Staff> cursor4 = doctors.peek();
            while (cursor4 != null) {
                pw.println(cursor4.getData().getName() + ": " + cursor4.getData().getTotalTime() + " seconds");
                cursor4 = cursor4.getLink();
            }

            pw.println();

            Node<Staff> cursor5 = admins.peek();
            while (cursor5 != null) {
                pw.println(cursor5.getData().getName() + ": " + cursor5.getData().getTotalTime() + " seconds");
                cursor5 = cursor5.getLink();
            }

            pw.println();
            

            for (int i = 0; i < rooms.length; i ++) {
                pw.println(rooms[i].getName() + ": " + rooms[i].getTotalTime() + " seconds");
            }

            pw.println();
           
            
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }


    //getter and setter methods
    public PriorityQueue<Patient> getWaitroom() {
        return waitroom;
    }

    public void setWaitroom(PriorityQueue<Patient> waitroom) {
        this.waitroom = waitroom;
    }

    public int getNumDoctors() {
        return numDoctors;
    }

    public void setNumDoctors(int numDoctors) {
        this.numDoctors = numDoctors;
    }

    public int getDoctorTime() {
        return doctorTime;
    }

    public void setDoctorTime(int doctorTime) {
        this.doctorTime = doctorTime;
    }

    public int getNumNurse() {
        return numNurse;
    }

    public void setNumNurse(int numNurse) {
        this.numNurse = numNurse;
    }

    public int getNurseTime() {
        return nurseTime;
    }

    public void setNurseTime(int nurseTime) {
        this.nurseTime = nurseTime;
    }

    public int getNumRoom() {
        return numRoom;
    }

    public void setNumRoom(int numRoom) {
        this.numRoom = numRoom;
    }

    public int getNumAdmin() {
        return numAdmin;
    }

    public void setNumAdmin(int numAdmin) {
        this.numAdmin = numAdmin;
    }

    public int getAdminTime() {
        return adminTime;
    }

    public void setAdminTime(int adminTime) {
        this.adminTime = adminTime;
    }

    //stack getter/setter
    public LinkedStack<Staff> getDoctors() {
        return doctors;
    }

    public void setDoctors(LinkedStack<Staff> doctor) {
        this.doctors = doctor;
    }

    public LinkedStack<Staff> getNurses() {
        return nurses;
    }

    public void setNurses(LinkedStack<Staff> nurses) {
        this.nurses = nurses;
    }

    public LinkedStack<Staff> getAdmins() {
        return admins;
    }

    public void setAdmins(LinkedStack<Staff> admins) {
        this.admins = admins;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }

    public int getNumQue() {
        return numQue;
    }

    public void setNumQue(int numQue) {
        this.numQue = numQue;
    }
    public double[] getArrivalProb() {
        return arrivalProb;
    }

    public void setArrivalProb(double[] arrivalProb) {
        this.arrivalProb = arrivalProb;
    }
    
    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTotalP() {
        return totalP;
    }

    public void setTotalP(int totalP) {
        this.totalP = totalP;
    }

    public double getTotalWait() {
        return totalWait;
    }

    public void setTotalWait(double totalWait) {
        this.totalWait = totalWait;
    }

    public double getAverageWait() {
        return averageWait;
    }

    public void setAverageWait(double averageWait) {
        this.averageWait = averageWait;
    }

    public LinkedQueue<Patient> getArrivals() {
        return arrivals;
    }

    public void setArrivals(LinkedQueue<Patient> arrivals) {
        this.arrivals = arrivals;
    }

    public LinkedQueue<Patient> getCheckout() {
        return checkout;
    }

    public void setCheckout(LinkedQueue<Patient> checkout) {
        this.checkout = checkout;
    }

    public LinkedQueue<Patient> getBeingCheckedout() {
        return beingCheckedout;
    }

    public void setBeingCheckedout(LinkedQueue<Patient> beingCheckedout) {
        this.beingCheckedout = beingCheckedout;
    }

    public LinkedQueue<Staff> getAdminWorking() {
        return adminWorking;
    }

    public void setAdminWorking(LinkedQueue<Staff> adminWorking) {
        this.adminWorking = adminWorking;
    }

    public LinkedQueue<Patient> getGoingHome() {
        return goingHome;
    }

    public void setGoingHome(LinkedQueue<Patient> goingHome) {
        this.goingHome = goingHome;
    }








}
