/**
 * Class Name: Priority Node
 * 
 * Author: Alina Kenny
 * Date: Dec 2, 2024
 * 
 * Purpose:
 * Creates the priority nodes for the linekd list and priority  queue (generic).
 * 
 * Features:
 * - each node has data <E>, int priority, and the link to that node
 * - get ans set methods
 * - method to pop, getFront, etc.
 * - method to sort the word into the linked priority queue when it is pushed into the queue
 * 
 *  
 * Dependencies:
 * PriorityNode
 * 
 **/

package TP;

import java.util.NoSuchElementException;


//the higher the priority number, the more urgent
public class PriorityQueue<E> {

    private int manyNodes;
    private PriorityNode<E> front;
    private PriorityNode<E> rear;

    public PriorityQueue() {
        front = null;
        rear = null;
        manyNodes = 0;
    }

    public boolean isEmpty() {
        return (front == null);
    }

    public void push(E element) {

        int priority = ((Patient) element).getPriority(); //get priority from Patient
        PriorityNode<E> newNode = new PriorityNode<>(element, priority);

        //when no element in the queue (first element pushed)
        if (front == null) {
           
            front = newNode;
        
        //when the priority pushed is larger than the front node (new priority number pushed to queue greater than current priorities)
        } else if (front.getPriority() < newNode.getPriority()) {
            newNode.setLink(front);
            front = newNode;
        }

        //when the priority pushed is smaller than the front node, then it loops through and when it finds the node with the same priority, it loops will the end of that priority list.
        else {
            PriorityNode<E> current = front;
            while (current.getLink() != null && (current.getLink().getPriority() > newNode.getPriority() || current.getLink().getPriority() == newNode.getPriority())) {
                current = current.getLink();
                
            }
            //insert the new node at the end of the correct priority
            newNode.setLink(current.getLink());
            current.setLink(newNode);
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

     public PriorityNode<E> peek() {
        return front;
     }

     public PriorityNode<E> pop() {
        front = front.getLink();
        return front;
     }

     public void print() {
        PriorityNode<E> cursor = front;
        for(int i = 0; i < manyNodes; i++) {
            System.out.println(cursor.getData());
            System.out.println(cursor.getPriority());
            cursor = cursor.getLink();
        }
     }

}
