/**
 * Class Name: LinkedStack
 * 
 * Author: Alina Kenny
 * Date: Oct 23, 2024
 * 
 * Purpose:
 * This class is a generic interface using a linked list for implementing stacks.
 * 
 * Features:
 * - creates a linked stack of generic elements
 * - cloneable class
 * - methods to modify and access the linked stack
 *  
 * Dependencies:
 * This class depends on the Node class.
 * 
 **/

 package TP;

 import java.util.EmptyStackException;
 
 
 public class LinkedStack<E> implements Cloneable{
 
     private Node<E> top;
 
     public LinkedStack() {
         top = null;
 
     }
 
     public LinkedStack<E> clone() {
         LinkedStack<E> answer;
 
         try{
             answer = (LinkedStack<E>) super.clone();
         }
         catch (CloneNotSupportedException e) {
             throw new RuntimeException ("This class does not implement Cloneable");
         }
 
         answer.top = Node.listCopy(top);
         return answer;
     }
 
     public boolean isEmpty() {
         return (top == null);
     }
 
    //  public E peek() {
    //      if (top == null) {
    //          throw new EmptyStackException();
    //      }
 
    //      return top.getData();
    //  }

    public Node<E> peek(){
        return top;
    }
    
 
     public E pop() {
         E answer;
 
         if (top == null) {
             throw new EmptyStackException();
         }
 
         answer = top.getData();
         top = top.getLink();
         return answer;
     }
 
     public void push (E item) {
         top = new Node<>(item, top);
 
     }
 
     public int size() {
         return Node.listLength(top);
     }
 
     
 
 }
 