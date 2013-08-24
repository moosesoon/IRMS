//Interface
package addRmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author Stanley
 */
public interface AddServer extends Remote{
    double add(double n1, double n2) throws RemoteException;
}
