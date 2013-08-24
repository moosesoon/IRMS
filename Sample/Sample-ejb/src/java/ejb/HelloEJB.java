/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;

/**
 *
 * @author Stanley
 */
@Stateless
public class HelloEJB implements HelloEJBRemote {

    @Override
    public String sayHello() {
        return "Hello";
    }
    
}
