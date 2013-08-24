/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luckyDraw;
import java.rmi.*;
/**
 *
 * @author Stanley
 */
public class LuckyDrawClientApp {
    public static void main(String args[]){
        try{
            //Find rmi server name
            String serverURL = "rmi://" + args[1] + "/LuckyDrawServer";
            
            //Obtain reference
            LuckyDrawServer lds;
            lds = (LuckyDrawServer) Naming.lookup(serverURL);
            System.out.println("rmi://" + args[1] + ":" + args[2] + "/LuckyDrawServer");
            
            //Register client
            String id = lds.register(args[0], args[3]);
            String prize = lds.getPrize(id);
            
            //Print result
            System.out.println("The prize that I won is " + id + " " + prize);
            System.out.println(lds.thankYou(args[0], args[3]));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
