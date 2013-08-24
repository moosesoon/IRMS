/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package luckyDraw;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author Stanley
 */
public interface LuckyDrawServer extends Remote{
    //Purpose: Register the user
    //Input / Output: ip address, name / prizeid
    String register(String ipAddress, String name) throws RemoteException;
    
    //Purpose: generate the prize for the purpose
    //Input / Output: prizeid / prize
    String getPrize(String id) throws RemoteException;
    
    //Purpose: Send message to the client thank you
    //Input / Output: ip address, name / 
    String thankYou(String ipAddress, String name) throws RemoteException;
}
