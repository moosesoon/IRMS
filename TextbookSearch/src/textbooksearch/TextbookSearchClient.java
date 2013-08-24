/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package textbooksearch;
import java.rmi.*;
import java.util.*;
/**
 *
 * @author Stanley
 */
public class TextbookSearchClient {
    public TextbookSearchClient(){}

    public static void main(String args[]){
        TextbookSearchClient tsc = new TextbookSearchClient();
        if(args.length != 1){
            System.err.println("Argument array out of bound");
            System.exit(-1); //exit on exception
        }
        tsc.search(tsc.connect(args[0]));

    }

    public TextbookSearchServer connect(String ip){
        try{
            String serverURL;
            serverURL = "rmi://" + ip + "/TextbookServer";

            //Obtain reference from interface
            TextbookSearchServer tss;
            tss = (TextbookSearchServer) Naming.lookup(serverURL);
            return tss;
        } catch(Exception ex){
            System.out.println("An unexpected error has occured");
            ex.printStackTrace();
            return null;
        }

    }

    //The main function: Search for textbook in course
    //No validation yet
    public void search(TextbookSearchServer tss){
        //If connection failed
        if(tss == null){
            System.out.println("Connection error. Client exits");
            System.exit(0);
        }

        Scanner scn = new Scanner(System.in);
        String code, name;
        int copies;
        boolean hasCode;

        do{
            System.out.print("Enter course code ('Q' or 'q' to exit): ");
            code = scn.next();

            //exit if q is entered
            if(code.equalsIgnoreCase("q")){
                System.out.print("Goodbye");
                System.exit(0);
            }

            try{
                //Query server with code
                hasCode = tss.search(Integer.valueOf(code).intValue());
                if(hasCode){
                    System.out.println("Course code " + code + " found");

                    //Find textbook
                    name = tss.getTextbook(Integer.valueOf(code).intValue());
                    if(name.equalsIgnoreCase("NIL")){
                        System.out.println("Textbook for course code " + code + " is not available");
                    } else {
                        System.out.println("Textbook for course code " + code + " is \"" + name + "\"");
                        //Find copies
                        copies = tss.checkCopies(name);
                        System.out.println("There are " + copies + " copies of Textbook " + "\"" + name + "\"");
                    }

                } else {
                    System.out.println("Course code not found");
                    continue;
                }
                
            } catch(Exception ex){
                System.err.println("Unexpected error occured");
                ex.printStackTrace();
            }
        } while(true);
    }
}
