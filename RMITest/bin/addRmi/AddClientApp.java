/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package addRmi;
import java.rmi.*;
/**
 *
 * @author Stanley
 */
public class AddClientApp {
    public static void main(String args[]){
        try{
            //Set up the url
            String url = "rmi://" + args[0] + "/AddServer";
            
            //Obtain reference to remote object
            AddServer as = (AddServer) Naming.lookup(url);
            
            //Display number in the client
            System.out.println("First Number is: "+ args[1]);
            double n1 = Double.valueOf(args[1]).doubleValue();
            System.out.println("Second Number is: "+ args[2]);
            double n2 = Double.valueOf(args[2]).doubleValue();
            
            //Invoke the method in AddServer
            double result = as.add(n1, n2);
            System.out.println("Result is: " + result);
            
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
