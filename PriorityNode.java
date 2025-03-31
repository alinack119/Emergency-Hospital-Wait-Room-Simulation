package TP;

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
 * - method to sort the word into the linked priority queue when it is pushed into the queue
 * 
 *  
 * Dependencies:
 * PriorityNode
 * 
 **/


 public class PriorityNode<E>{
 
    private E data;
    private PriorityNode<E> link;
    private int priority;
    


    /**
     * Constructor: PriorityNode
     * 
     * Purpose:
     * Initializes a Node object with the data, and link.
     * 
     * Postcondition:
     * - The data set to parameter
     * - The link set to the parameter
     */
    public PriorityNode(E initialData, PriorityNode<E> initialLink, int p) {
        data = initialData;
        link = initialLink;
        priority = p;


        
    }

    // Constructor with only data
    public PriorityNode(E data) {
        this.data = data;
        this.priority = 0; // Default priority
        this.link = null;
    }

    // Constructor with data and priority
    public PriorityNode(E data, int priority) {
        this.data = data;
        this.priority = priority;
        this.link = null;
    }

    //getters and setter methods
    public E getData() {
        return data;
    }

    public PriorityNode<E> getLink() {
        return link;
    }

    public int getPriority() {
        return priority;
    }

    

    public void setData(E newData) {
       data = newData;
    }

    public void setLink(PriorityNode<E> newLink) {
       link = newLink;
    }

    public void setPriority(int p) {
        priority = p;
    }

    

    
    
    public void addNodeAfter(E element){
        link = new PriorityNode<E>(element, link, priority);
    }

    public void push(E element) {
        

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

    public void removeNodeAfter() {
        link = link.link;
    }

}
    


