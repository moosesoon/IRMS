/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luckyDraw;
import java.rmi.Naming;
/**
 *
 * @author Stanley
 */
public class LuckyDrawServerApp {
    public static void main(String args[]){
        try{
            LuckyDrawServerImpl ldsi = new LuckyDrawServerImpl();
            Naming.rebind("LuckyDrawServer", ldsi);
            System.out.println("Initialising Lucky Draw Server");
            System.out.println("LuckyDrawServer Started");
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
