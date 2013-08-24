/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import tlb.MemberManagerRemote;
import javax.naming.InitialContext;
import appHelper.BookState;
import java.text.SimpleDateFormat;

public class LoanView {
    Scanner scn = new Scanner(System.in);
    private Validation v = new Validation();
    private MemberManagerRemote mmr;

    public LoanView(){
        this.getSessionBean();
    }

    public void mainWindow(){
        String choice;
        int temp;

        do{
            System.out.println("\n\n\t\t---===Loan Menu===---" +
                               "\n\n-Menu-" +
                               "\n1.Borrow Books" +
                               "\n2.Return Books" +
                               "\n0.Go Back to Main Menu");
            System.out.print("Please enter your choice: ");
            choice = scn.next();
            temp = v.validateInput(choice, 0, 7);

            switch(temp){
                case 1:
                    doBorrowBooks();
                    break;
                case 2:
                    doReturnBook();
                    break;
                case 0:
                    temp = -1;
                    break;
            }
        }while(temp > 0);
        mmr.remove();
    }
    
    //1. Borrow Book (Version 1.0: Without checking if the book has been borrowed)
    //This is on the assumption that by checking if loan is null, it is free to be borrowed
    private void doBorrowBooks(){
        String choice, temp;
        Long bid, mid;
        int copy;
        Hashtable<Integer, Long> ht = new Hashtable<Integer, Long>();
        List stateList = new ArrayList();

        System.out.print("\n\n\t\t---===Borrow Books===---");

        //Step 1: Prompt for ISBN of the book
        System.out.print("\nEnter the ISBN of the book: ");
        choice = scn.next();

        //Step 2: Admin prompted for Member Identification
        do{
            System.out.print("Enter the memberID: ");
            temp = scn.next();
            mid = v.validateLong(temp);
        } while(mid < 0);

        //Step 2.5: Check if member has more than 6 copies of borrowed books
        if(!mmr.findMember(mid)){
            System.out.print("\n\nNo such user exist. End loan.");
            return;
        }
        stateList = mmr.getMemberLoan();
        if(stateList.size() > 6){ //EMPTY
            System.out.print("\n\nUser " + mid + " has exceeded borrowing limit!");
            return;
        }
        
        //Step 3: List out the number of books
        stateList = mmr.getAvailableCopy(choice);
        System.out.print("\n\n\t\t---===List of Available Copy===---");
        if(stateList.size() == 0){
            System.out.print("\n\nThere are no copies available to be loaned");
            return;
        } else{
            for(Object p : stateList){
                BookState bs = (BookState) p;
                System.out.print("\n\nBook ID: " + bs.getBid() +
                                 "\nCopy Number: " + bs.getCopy());
                ht.put(Integer.valueOf(bs.getCopy()), bs.getBid()); //Allow user to pick out the copy number instead of keying id
            }
        }
        //Step 4: Select a book to borrow
        do{
            System.out.print("\nEnter the copy number: ");
            choice = scn.next();
            copy = v.validateInput(choice, 0, 1000);
        } while(copy < 0);

        //Setup the entities to be added
        bid = ht.get(Integer.valueOf(copy));
        if(bid == null || bid == 0){
            System.out.print("\nThere are no such copy number");
        }
        mmr.loanBook(bid);
    }

    public void doReturnBook(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String temp;
        Long bid, mid;
        List stateList = new ArrayList();


        System.out.print("\n\n\t\t---===Return Books===---");
        //Get returning member id
        do{
            System.out.print("\nEnter the memberID: ");
            temp = scn.next();
            mid = v.validateLong(temp);
        } while(mid < 0);

        if(!mmr.findMember(mid)){
            System.out.print("\nNo such user exist. End return!");
            return;
        }
        //Display list of borrowed books
        stateList = mmr.getMemberLoan();
        System.out.print("\n\t\t---===Borrowed Books List (" + mid + ")===---");
        if(stateList.size() == 0){
            System.out.print("\nUser " + mid + " has no borrowed books. Nothing to return");
            return;
        } else{
            for(Object b: stateList){
                BookState bs = (BookState) b;
                System.out.print("\n\nBookID: " + bs.getBid() +
                                 "\nCopy No: " + bs.getCopy() +
                                 "\nLoan Date: " + sdf.format(bs.getLoan()) +
                                 "\nDue Date: " + sdf.format(bs.getDue()));
            }
        }
        do{
            System.out.print("\n\nEnter the Book ID number: ");
            temp = scn.next();
            bid = v.validateLong(temp);
        } while(bid < 0);

        mmr.returnBook(bid);
    }

    private void getSessionBean(){
        try{
        InitialContext ic = new InitialContext();
        mmr = (MemberManagerRemote) ic.lookup(MemberManagerRemote.class.getName());
        } catch(Exception ex){
            System.err.println("An unexpected error has occured");
            ex.printStackTrace();
        }
    }
}
