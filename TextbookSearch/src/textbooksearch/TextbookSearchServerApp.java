/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package textbooksearch;
import java.rmi.Naming;
/**
 *
 * @author Stanley
 */
public class TextbookSearchServerApp {
    public static void main(String args[]){
        try{
            TextbookSearchServerImpl tsserver;
            tsserver = new TextbookSearchServerImpl();
            Naming.rebind("TextbookServer", tsserver); //Bind to registry
            System.out.println("Initialising TextbookSearchServer!");
            System.out.println("TextbookSearchServer started!");
        }catch(Exception ex){
            System.err.println("An unexpected error has occurred");
            ex.printStackTrace();
        }
    }
}
