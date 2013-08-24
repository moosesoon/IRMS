/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package textbooksearch;

import javax.naming.*;
import java.util.Properties;
/**
 *
 * @author Stanley
 */
public class TextbookSearchServerApp {
    static final String FACTORY = "java.naming.factory.initial";
    static final String FACTORY_NAME = "com.sun.jndi.cosnaming.CNCtxFactory";
    static final String PROVIDER = "java.naming.provider.url";
    static final String PROVIDER_URL = "iiop://localhost:900";

    public static void main(String args[]){
        try{
            TextbookSearchServerImpl tsserver = new TextbookSearchServerImpl();

            //Create initial context
            Properties props = new Properties();
            props.put(TextbookSearchServerApp.FACTORY, TextbookSearchServerApp.FACTORY_NAME);
            props.put(TextbookSearchServerApp.PROVIDER, TextbookSearchServerApp.PROVIDER_URL);
            InitialContext ic = new InitialContext(props);

            //Bind the server
            ic.rebind("TextbookServer", tsserver);

            System.out.println("Initialising TextbookServer!");
            System.out.println("TextbookSearchServer started!");
            
        } catch(Exception ex){
            System.out.println("Unexpected error has occured");
            ex.printStackTrace();
        }
    }
}
