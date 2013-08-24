/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package textbooksearch;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.util.*;
/**
 *
 * @author Stanley
 */
public class TextbookSearchServerImpl extends UnicastRemoteObject implements TextbookSearchServer{
    private Vector<Textbook> tbk;

    //To create a temporary storage for an instance of the server and store relevant data
    public TextbookSearchServerImpl() throws RemoteException{
        try{
            //Strings for capturing the br readline, Textbook object and initialise list of textbook
            String code, name;
            tbk = new Vector<Textbook>();
            Textbook temp;


            //Set up file stream reader to read the file and store course.txt
            BufferedReader br1 = new BufferedReader(new FileReader("textbooksearch\\course.txt"));

            //Set up file stream reader to read the file and store textbook.txt
            BufferedReader br2 = new BufferedReader(new FileReader("textbooksearch\\textbook.txt"));

            //Store the name / code / copies in an array list
            while((code = br1.readLine()) != null){
                if((name = br2.readLine()) != null){ //Have both code and name
                    temp = new Textbook(name, Integer.valueOf(code).intValue(), name.split(" ").length);
                    tbk.add(temp);
                } else{ //Have only the code, no textbook
                    temp = new Textbook(Integer.valueOf(code).intValue(), 0);
                    tbk.add(temp);
                }
            }
        } catch(Exception ex){
            System.err.println("An unexpected error has occured");
            ex.printStackTrace();
        }
    }

    //input : course code (int)
    //output: boolean (true for success, false for failure)
    //Data Structure: Vector (O(n))
    public boolean search(int courseCode) throws RemoteException{
        for(int i = 0; i < tbk.size(); i++){
            if(tbk.get(i).getCode() == courseCode){
                System.out.println();
                System.out.println("search(): course code " +courseCode + " ==> true");
                return true;
            }
        }
        System.out.println();
        System.out.println("search(): course code " + courseCode + " ==> false");
        return false;
    }

    //input : course code (int)
    //output: Textbook for the course (String)
    //Data Structure: Vector (O(n))
    public String getTextbook(int courseCode) throws RemoteException{
        for(int i = 0; i < tbk.size(); i++){
            if(tbk.get(i).getCode() == courseCode){
                System.out.println("getTextbook(): " + courseCode + " textbook is " + "\"" + tbk.get(i).getName() + "\"");
                return tbk.get(i).getName();
            }
        }
        return "NIL";
    }

    //input : textbook (String)
    //output: Number of Available copies (int)
    //Data Structure: Vector (O(n))
    public int checkCopies(String textbook) throws RemoteException{
        for(int i = 0; i < tbk.size(); i++){
            if(tbk.get(i).getName().equalsIgnoreCase(textbook)){
                System.out.println("checkCopies(): \"" + textbook + "\" ==> " + tbk.get(i).getCopies() + " copies");
                return tbk.get(i).getCopies();
            }
        }
        return 0;
    }
}
