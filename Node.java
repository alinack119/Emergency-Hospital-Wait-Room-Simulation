package TP;

/**
 * Class Name: Node
 * 
 * Author: Alina Kenny
 * Date: Nov 12, 2024
 * 
 * Purpose:
 * Creates the nodes for the linekd list, linked stack, and linked queue (generic).
 * 
 * Features:
 * - each node has data <E>, and the link to that node
 * - there is also an int count variable to cound the number of reoccuring words
 * - method to search a linked list for a word
 * - method to copy the linked list with the tail (for cloning method in queue)
 * 
 *  
 * Dependencies:
 * none
 * 
 **/


 public class Node<E>{
 
    E data;
    Node<E> link;
    
    


    /**
     * Constructor: Node
     * 
     * Purpose:
     * Initializes a Node object with the data, and link.
     * 
     * Postcondition:
     * - The data set to parameter
     * - The link set to the parameter
     */
    public Node(E initialData, Node<E> initialLink) {
        data = initialData;
        link = initialLink;

    }

    //getters and setter methods
    public E getData() {
        return data;
    }

    public Node<E> getLink() {
        return link;
    }

    

    public void setData(E newData) {
       data = newData;
    }

    public void setLink(Node<E> newLink) {
       link = newLink;
    }



    public void addNodeAfter(E element){
        link = new Node<E>(element, link);
    }

    public static <E> Node<E> listCopy(Node<E> source){
        Node<E> copyHead;
        Node<E> copyTail;

        //Handle the special case of the empty list.
        if (source == null){
            return null;
        }

        //Make the first node for the newly created list.
        copyHead = new Node<E>(source.data, null);
        copyTail = copyHead;

        //Make the rest of the nodes for the newly created list.
        while (source.link != null) {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        //Return the head reference for the new list.
        return copyHead;
    
        
    }



    /**
     * Method: listCopyWithTail
     * 
     * Purpose:
     * copy the list with the head and tail links
     * 
     * @return Node<E> array
     * 
     * Precondition:
     * - Parameter with head of list or queue
     * 
     * Postcondition:
     * - Returns the array with the head and tail nodes and links
     */
    public static <E> Node<E>[ ] listCopyWithTail(Node<E> source) {
       
       Node<E> copyHead;
       Node<E> copyTail;
       Node<E>[ ] answer = new Node[2];

       //if empty list.
       if (source == null)
           return answer; 

       //make the first node for the newly created list.
       copyHead = new Node<E>(source.data, null);
       copyTail = copyHead;
       //make the rest of the nodes for the newly created list.
       while (source.link != null) {
           source = source.link;
           //System.out.println("Copying node with data: " + source.data);
           copyTail.addNodeAfter(source.data);
           copyTail = copyTail.link;
       }
       //return the head and tail references in a small array of two Java objects
       answer[0] = copyHead;
       answer[1] = copyTail;
       return answer; //returns array
   }

    public static <E> int listLength(Node<E> head){
        Node<E> cursor;
        int answer;

        answer = 0;
        for (cursor = head; cursor != null; cursor = cursor.link) {
            answer++;
        }

        return answer;
    }


}
    


