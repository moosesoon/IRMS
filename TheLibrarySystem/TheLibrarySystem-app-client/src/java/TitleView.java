/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import tlb.BookManagerRemote;
import javax.naming.InitialContext;
import appHelper.BookState;
import appHelper.TitleState;

public class TitleView {
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private Scanner scn = new Scanner(System.in);
    private BookManagerRemote tbm;
    private Validation v = new Validation();

    public TitleView(){
        getSessionBean();
    }

    //T. Display main menu for Titles
    public void mainWindow() throws IOException{
        String choice;
        int temp;

        do{
            System.out.println("\n\n\t\t---===Title Menu===---" +
                               "\n\n-Menu-" +
                               "\n1.Add Title" +
                               "\n2.Add Books" +
                               "\n3.List Titles" +
                               "\n4.List Books" +
                               "\n5.Delete Titles" +
                               "\n6.Delete Books" +
                               "\n0.Go Back to Main Menu");
            System.out.print("Please enter your choice: ");
            choice = scn.next();
            temp = v.validateInput(choice, 0, 7);

            switch(temp){
                case 1:
                    doAddTitle();
                    break;
                case 2:
                    System.out.print("\nPlease enter ISBN of book: ");
                    choice = br.readLine();
                    doAddBook(choice);
                    break;
                case 3:
                    doDisplayTitle();
                    break;
                case 4:
                    System.out.print("\nPlease enter ISBN of book: ");
                    choice = br.readLine();
                    doDisplayBooks(choice);
                    break;
                case 5:
                    System.out.print("\nPlease enter ISBN of title: ");
                    choice = br.readLine();
                    doDeleteTitle(choice);
                    break;
                case 6:
                    System.out.print("\nPlease enter ISBN of book: ");
                    choice = br.readLine();
                    doDeleteBook(choice);
                    break;
                case 0:
                    temp = -1;
                    break;
            }
        }while(temp > 0);
        tbm.remove();
    }

    //1. Add Title
    private void doAddTitle(){
        try{
            String ISBN, title, author, publisher, input;
            int copy;

            //Step 1: Gather input for the title
            System.out.print("\n\n---===Add Title===---" +
                               "\n\nEnter ISBN(Unique): ");
            ISBN = br.readLine();
            System.out.print("Enter Title: ");
            title = br.readLine();
            System.out.print("Enter Author: ");
            author = br.readLine();
            System.out.print("Enter Publisher: ");
            publisher = br.readLine();

            tbm.createTitle(ISBN, title, author, publisher);
            //Step 2: Request the number of copies
            do{
                System.out.print("Enter number of copies of book (0-999): ");
                input = br.readLine();
                copy = v.validateInput(input, -1, 1000);
            } while(copy < 0);
            for(int i = 1; i < copy+1; i++){
                tbm.createBook(i, null, null);
                tbm.addBook();
            }
        } catch(IOException ioe){
        }
        //Step 3: Create the title
        tbm.persist();
    }//1. End of add title

    //2. Add Books
    private void doAddBook(String ISBN) throws IOException{
        String choice;
        int copy = 0, temp = 0;
        List<BookState> stateList = new ArrayList<BookState>();

        //Extract the correct book list and find the correct copy number.
        stateList = tbm.getBooks(ISBN);

        //Might be null because of no matching title or more than 1 title is returned
        if(stateList.isEmpty()){
            System.out.print("\nNo such title exist. Please add the title!");
            return;
        }
        BookState tbs = (BookState) stateList.get(0);
        //Assuming the title is unique per ISBN since assignment text said per title, not per ISBN
        for(Object p: stateList){
            BookState bs = (BookState) p;
            if(bs.getCopy() > copy){
                copy = bs.getCopy();
            }
        }
        copy += 1;

        do{
            System.out.print("Enter number of copies of book(0-999): ");
            choice = br.readLine();
            temp = v.validateInput(choice, -1, 1000);
        } while(temp < 0);
        temp += copy;

        tbm.createTitle(tbs.getTs().getISBN(), tbs.getTs().getTitle(), tbs.getTs().getAuthor(), tbs.getTs().getPublisher());
        for(int i = copy; i < temp; i++){
            tbm.createBook(i, null, null);
            tbm.addBook();
        }
        tbm.merge();
    }

    //3. Display title
    private void doDisplayTitle(){
        try{
            System.out.println("\n\n\t\t---===Title Display===---");
            for(Object o: tbm.getTitles("")){
                TitleState tbs = (TitleState) o;
                System.out.println("\n\nISBN: " + tbs.getISBN());
                System.out.print("Title: " + tbs.getTitle() +
                             "\nAuthor: " + tbs.getAuthor() +
                             "\nPublisher: " + tbs.getPublisher());
            }
            System.out.println("\n\n\t\t---===End of Display===---");
        } catch(Exception ex){
            System.err.println("An unexpected error occured");
            ex.printStackTrace();
        }
    }

    //4. Display all books based on the ISBN
    private void doDisplayBooks(String isbn){
        List<BookState> stateList = new ArrayList<BookState>();
        int count = 0;
        byte[] stop = new byte[1];
        stop[0] = 'q';

        //Extract the correct book list and find the correct copy number.
        if(isbn.equals("")){
            System.out.print("\nCannot be empty");
            return;
        }
        stateList = tbm.getBooks(isbn);

        //Might be null because of no matching title or more than 1 title is returned
        if(stateList.isEmpty()){
            System.out.print("\nNo such title exist. Please add the title!");
            return;
        }
        BookState tbs = stateList.get(0);

        try{
            System.out.println("\n\n\t\t---===Book Display===---");
            System.out.println("\nISBN: " + tbs.getTs().getISBN() +
                             "\nTitle: " + tbs.getTs().getTitle() +
                             "\nAuthor: " + tbs.getTs().getAuthor() +
                             "\nPublisher: " + tbs.getTs().getPublisher());
            System.out.print("5 display at a time");
            for(Object p: stateList){
                if((count%5) == 0 && count != 0){ //Divisible by 0
                    System.out.print("\n\n---Press Any Key to continue next 5 or Q to stop---"); //To separate the amount of listed items
                    System.in.read(stop);
                    if(stop[0] == 'q'){
                        return;
                    }
                }
                BookState bs = (BookState) p;
                System.out.print("\n\nBook ID: " + bs.getBid() +
                                   "\nCopy No: " + bs.getCopy());
                count++;
            }
            System.out.println("\n\n\t\t---===End of Display===---");
        } catch(Exception ex){
            System.err.println("An unexpected error occured");
            ex.printStackTrace();
        }
    }


    //5. Delete Title
    private void doDeleteTitle(String ISBN){
        tbm.delTitle(ISBN);
    }

    //6. Delete Books
    private void doDeleteBook(String ISBN) throws IOException{
        String choice;
        Long bid;
        doDisplayBooks(ISBN);
        do{
            System.out.print("\nPlease enter the ID: "); //Presuming the correct ID
            choice = br.readLine();
            bid = v.validateLong(choice);
        } while(bid < 0);
        tbm.delBook(bid);
    }

    private void getSessionBean(){
        try{
            InitialContext ic = new InitialContext();
            tbm = (BookManagerRemote) ic.lookup(BookManagerRemote.class.getName());
        } catch(Exception ex){
            System.err.println("An unexpected error occured");
            ex.printStackTrace();
        }
    }
}