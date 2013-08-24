//Interface implementation

package addRmi;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 *
 * @author Stanley
 */
public class AddServerImpl extends UnicastRemoteObject implements AddServer{
    public AddServerImpl() throws RemoteException{} //Constructor
    
    public double add(double n1, double n2) throws RemoteException{
        System.out.println("Number added are " + n1 + " " + n2);
        System.out.println("Total is: " + (n1+n2));
        
        return n1+n2;
    }
}
