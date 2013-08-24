/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luckyDraw;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 *
 * @author Stanley
 */
public class LuckyDrawServerImpl extends UnicastRemoteObject implements LuckyDrawServer{
    
    public LuckyDrawServerImpl() throws RemoteException {}
    
    //Purpose: Register the user
    //Input / Output: ip address, name / prizeid
    public String register(String ipAddress, String name) throws RemoteException{
        String id = "P01";
        System.out.println("LuckyDrawServerImpl: Prize " + id + " awarded to " + name + " at " + ipAddress);
        return "P01";
    }
    
    //Purpose: generate the prize for the purpose
    //Input / Output: prizeid / prize
    public String getPrize(String id) throws RemoteException{
        System.out.println("LuckyDrawServerImpl: Prize " + id + " WASHING MACHINE " + " claimed.");
        return "WASHING MACHINE";
    }
    
    //Purpose: Send message to the client thank you
    //Input / Output: ip address, name / 
    public String thankYou(String ipAddress, String name) throws RemoteException{
        return "The Lucky Draw Server has this message: " + name + ", Congratulations.";
    }
}
