//Server Application
package addRmi;
import java.rmi.Naming;
/**
 *
 * @author Stanley
 */
public class AddServerApp {
    public static void main(String[] args){
        try{
            AddServerImpl addServerImpl = new AddServerImpl();
            Naming.rebind("AddServer", addServerImpl);
            System.out.println("AddServer has started!");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
