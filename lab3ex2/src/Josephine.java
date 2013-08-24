/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
/**
 *
 * @author Stanley
 */
class ListNode <E>{
    protected E element;
    protected ListNode <E> next;
    
    public ListNode(E item){ element = item; next = null;}
    public ListNode(E item, ListNode<E> n){ element = item; next = n;}
    public ListNode <E> getNext(){ return this.next;}
    public E getElement(){ return this.element;}
}

class PrinceList <E>{
    private ListNode<E> head = null;
    private ListNode<E> tail = null;
    private int numNode = 0;
    
    public ListNode <E> getHead(){ return this.head;}
    public ListNode <E> getTail(){ return this.tail;}
    public int getSize(){ return this.numNode;}
    public ListNode <E> getFrom(ListNode <E> e,int index){
        ListNode <E> temp = null;
        if(e == null){
            temp = this.tail;
            for(int i = 0; i<index; i++){
                temp = temp.next;
            }
        } else{
            temp = e;
            for(int i = 0; i<index; i++){
                temp = temp.next;
            }
        }
        return temp;
    }
    
    public void addLast(E item){
        if(head == null){
            tail = new ListNode(item);
            head = tail;
            tail.next = head;
        } else{
            tail.next = new ListNode(item, head);
            tail = tail.next;
        }
        numNode++;
    }
    
    //Remove the node after the node in the argument
    public E removeAfter(ListNode <E> curr) throws NoSuchElementException{
        E temp;
        if(curr != null){ //Current is not null, so we have to check if its next is null
            if(curr.next != null){ //If next is null, we cannot remove, since there is no next node
                temp = curr.next.getElement();
                if(curr.next == head){ this.head = curr.next.next;}
                if(curr.next == tail){ this.tail = curr;}
                curr.next = curr.next.next; //Switch the pointer to point to the next next node
                numNode--;
                return temp;
            } else throw new NoSuchElementException("No next node to remove");
        } else{ //If current is null, means we are going to remove the head, removeFirst
            if(head != null){
                temp = head.getElement();
                head = head.next;
                tail.next = head; //Re-link the tail to the new head
                numNode--;
                return temp;
            } else throw new NoSuchElementException("No next node to remove");
        }
    }
    
    public String printLast(){
        ListNode<E> ln = tail;
        return ln.getElement() + "";
    }
}

public class Josephine {
    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        PrinceList<Integer> pl = null;
        ListNode<Integer> l = null;
        int t, n, k, count = 1;
        String output="";
        
        t = scn.nextInt();
        while(t > 0){
            pl = new PrinceList<Integer>();
            n = scn.nextInt();
            k = scn.nextInt();
            while(n > 0){ //Add the number of princes
                pl.addLast(count);
                count++;
                n--;
            }
            l = pl.getFrom(null, k-1);
            if(pl.getSize() > 1) output += pl.removeAfter(l) + " ";
            while(pl.getSize() > 1){
                l = pl.getFrom(l, k-1);//Why -1? Because we are removing AFTER the position
                output += pl.removeAfter(l) + " "; 
            }
            output += pl.printLast() + " ";
            output += "\n";
            count = 1; //Reset the prince number to 1
            t--;
        }
        System.out.println(output);
    }
}
