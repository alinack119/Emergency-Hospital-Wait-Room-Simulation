/**
 * Class Name: LinkedQueue
 * 
 * Author: Alina Kenny
 * Date: Nov 12, 2024
 * 
 * Purpose:
 * methods to manipulate a linked queue implementation
 * 
 * Features:
 * - created through node class
 * - add method to add a node object at the end of the queue
 * - remove method to remove the first (head) item of the queue
 * - clone method to deep clone the queue
 *  
 * Dependencies:
 * Node class
 * 
 **/

 package TP;

 import java.util.NoSuchElementException;
 
 public class LinkedQueue<E> implements Cloneable {
 
     private int manyNodes;
     private Node<E> front;
     private Node<E> rear;
 
     public LinkedQueue() {
         front = null;
         rear = null;
     }
 
     public boolean isEmpty() {
         return (manyNodes == 0);
     }

     public E getFront() {
        return front.getData();
     }

     public Node<E> getFrontNode() {
        return front;
     }
 
     //add item to end of queue
     public void add(E item) {
         if (isEmpty()) {
             front = new Node<E>(item, null);
             rear = front;
         }
         else {
             rear.addNodeAfter(item);
             rear = rear.getLink();
         }
         manyNodes++;
     }
 
     //remove the node from the front
     public E remove() {
         E answer;
         if (isEmpty()) {
             throw new NoSuchElementException("Queue underflow.");
         }
         answer = front.getData();
         front = front.getLink();
         manyNodes--;
         if (isEmpty()) {
             rear = null;
         }
         return answer;
     }
 
     public int size() {
         return manyNodes;
     }
 
 
     //deep clone method using listCopyWithTail method from node class
     //will create new queue with individual objects and not reference to the original so a deep clone
     public LinkedQueue<E> clone() {
         // LinkedQueue<E> clonedQueue = new LinkedQueue<>();
         LinkedQueue<E> clonedQueue;
         try {
             clonedQueue = (LinkedQueue<E>) super.clone(); //clone the class (queue) //not yet a deep clone
         } catch (CloneNotSupportedException e) {
             throw new RuntimeException ("Not able to clone. Run time Exception");
         }
 
         //using listCopyWithTail to get a deep clone of the linked list
         @SuppressWarnings("unchecked")
         Node<E>[] copiedNodes = Node.listCopyWithTail(this.front); 
 
 
         clonedQueue.front = copiedNodes[0];
         clonedQueue.rear = copiedNodes[1];
 
     
 
         return clonedQueue;
     }
 
     public void printQueue() {
         Node<E> current = front;
         while (current != null) {
             System.out.print(current.getData() + " ");
             current = current.getLink(); 
         }
         System.out.println();
     }
 
     

     public int getLength() {
        int count = 0;
        Node<E> cursor = front;
        while(cursor != null) {
            count++;
            cursor = cursor.getLink();
        }

        return count;
     }
 


 
 
 }
 
