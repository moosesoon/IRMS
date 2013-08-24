/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package textbooksearch;

import java.rmi.RemoteException;
import javax.rmi.PortableRemoteObject;
import java.io.*;
import java.util.*;
/**
 *
 * @author Stanley
 */
public class TextbookSearchServerImpl extends PortableRemoteObject implements TextbookSearchServer{
    private Hashtable tbk;

    //To create a temporary storage for an instance of the server and store relevant data
    public TextbookSearchServerImpl() throws RemoteException{
        super();
        try{

            //Strings for capturing the br readline, Textbook object and initialise list of textbook
            String code, name;
            tbk = new Hashtable();

            //Set up file stream reader to read the file and store course.txt
            BufferedReader br1 = new BufferedReader(new FileReader("textbooksearch\\course.txt"));

            //Set up file stream reader to read the file and store textbook.txt
            BufferedReader br2 = new BufferedReader(new FileReader("textbooksearch\\textbook.txt"));

            //Store the name / code / copies in an array list
            while((code = br1.readLine()) != null){
                if((name = br2.readLine()) != null){ //Have both code and name
                    tbk.put(code, name);
                } else{ //Have only the code, no textbook
                    tbk.put(code, "NIL");
                }
            }
        } catch(Exception ex){
            System.err.println("An unexpected error has occured");
            ex.printStackTrace();
        }
    }

    //input : course code (int)
    //output: boolean (true for success, false for failure)
    //Data Structure: Hashing (O(1))
    public boolean search(int courseCode) throws RemoteException{
        String temp = (String) tbk.get(Integer.toString(courseCode));
        if(temp != null){ //No such object, means no such course
            System.out.println();
            System.out.println("search(): course code " + courseCode + " ==> true");
            return true;
        } else{
        System.out.println();
        System.out.println("search(): course code " + courseCode + " ==> false");
        return false;
        }
    }

    //input : course code (int)
    //output: Textbook for the course (String)
    //Data Structure: Hashing (O(1))
    public String getTextbook(int courseCode) throws RemoteException{ //Already have the code
        String temp = (String) tbk.get(Integer.toString(courseCode));

        if(!temp.equalsIgnoreCase("NIL")){
            System.out.println("getTextbook(): " + courseCode + " textbook is " + "\"" + temp + "\"");
            return temp;
        } else{
            return "NIL";
        }
    }

    //input : textbook (String)
    //output: Number of Available copies (int)
    //Data Structure: Hashing (O(1))
    public int checkCopies(String textbook) throws RemoteException{
        return textbook.split(" ").length;
    }
}
