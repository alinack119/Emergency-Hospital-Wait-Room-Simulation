/**
 * Class Name: ERsimulation
 * 
 * Author: Alina Kenny
 * Date: Dec 2, 2024
 * 
 * Purpose:
 * main method to run the simulation
 * 
 * Features:
 * - method to get intital values 
 * - method to set up the values needed for the simulation and initialize stacks
 *  
 * Dependencies:
 * Simulator
 * 
 **/

package TP;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.Scanner;


public class ERsimulation {

    
    private static Simulator sim = new Simulator();

    public static void main(String[] args) {

        getInput(); //can either comment out becasue there are values hardcoded as well.
        initSetup();


        sim.simulate();
        
    }

    //get input values
    private static void getInput() {
        Scanner keyboard = new Scanner(System.in);
        try {

            System.out.println("Number of doctors: ");
            sim.setNumDoctors(keyboard.nextInt());
            if (sim.getNumDoctors() <= 0) {
                throw new Exception("Number of doctors needs to be greater than 0.");
            }

            System.out.println("Time for examination by doctor: ");
            sim.setDoctorTime(keyboard.nextInt());
            if (sim.getDoctorTime() <= 0) {
                throw new Exception("Number needs to be greater than 0.");
            }

            System.out.println("Number of nurses: ");
            sim.setNumNurse(keyboard.nextInt());
            if (sim.getNumNurse() <= 0) {
                throw new Exception("Number needs to be greater than 0.");
            }

            System.out.println("Time for examination by nurses: ");
            sim.setNurseTime(keyboard.nextInt());
            if (sim.getNurseTime() <= 0) {
                throw new Exception("Number needs to be greater than 0.");
            }

            System.out.println("Number of admin: ");
            sim.setNumAdmin(keyboard.nextInt());
            if (sim.getNumAdmin() <= 0) {
                throw new Exception("Number needs to be greater than 0.");
            }

            System.out.println("Time taken by nurses: ");
            sim.setAdminTime(keyboard.nextInt());
            if (sim.getAdminTime() <= 0) {
                throw new Exception("Number needs to be greater than 0.");
            }

            System.out.println("Number of rooms: ");
            sim.setNumRoom(keyboard.nextInt());
            if (sim.getNumRoom() <= 0) {
                throw new Exception("Number needs to be greater than 0.");
            }

            System.out.println("Number of queue for priority sorting: ");
            sim.setNumQue(keyboard.nextInt());
            if (sim.getNumQue() <= 2) {
                throw new Exception("Number needs to be greater than or equal to 3.");
            }

            
            System.out.println("Probabilities for each patient priority queue: ");
            double[] arr = new double[sim.getNumQue()];
            int c = 0;
            for (int i = 0; i < sim.getNumQue(); i++) {
                System.out.println("Probability for queue " + (i + 1) + " : ");
                double prob = keyboard.nextDouble();
                
                if (c != 0) {
                    System.out.println(arr[i-1]);
                }


                if (c==0) {// for first probability
                    if (prob > 1 || prob < 0) {
                        throw new Exception ("Probability must be between 0 and 1.");
    
                    } 
                }
                else if (prob < arr[i-1] || (prob > 1 || prob < 0 )) { //for following probability
                    throw new Exception ("Probability must be between 0 and 1 AND greater than the previously entered probability (must be ascending order).");

                }
                c++;
                arr[i] = prob;
            }
            sim.setArrivalProb(arr);


            System.out.println("Time interval to begin the events: ");
            sim.setNumQue(keyboard.nextInt());
            if (sim.getNumQue() <= 0) {
                throw new Exception("Number needs to be greater than or equal to 3.");
            }

            System.out.println("Total time to run simulation: ");
            sim.setNumQue(keyboard.nextInt());
            if (sim.getNumQue() <= 0) {
                throw new Exception("Number needs to be greater than or equal to 3.");
            }
            

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } finally {
            keyboard.close();
        }
    }

    //set up variables and creates the stack
    private static void initSetup() {

        //doctors
        for (int i = 0; i < sim.getNumDoctors(); i++) {
            Staff d = new Staff("D"+(i+1), sim.getDoctorTime(), "d");
            sim.getDoctors().push(d);

        }

        //nurses
        for (int i = 0; i < sim.getNumNurse(); i++) {
            Staff n = new Staff("N"+(i+1), sim.getNurseTime(), "n");
            sim.getNurses().push(n);
            

        }

        //admin
        for (int i = 0; i < sim.getNumAdmin(); i++) {
            Staff aa = new Staff("AA"+(i+1), sim.getAdminTime(), "aa");
            sim.getAdmins().push(aa);

        }

        //room
        for (int i = 0; i < sim.getNumRoom(); i++) {
            Room r = new Room("Room"+(i+1));
            sim.getRooms()[i] = r;

        }


    }

}
