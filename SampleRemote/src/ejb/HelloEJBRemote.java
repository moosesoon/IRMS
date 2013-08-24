/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Remote;

/**
 *
 * @author Stanley
 */
@Remote
public interface HelloEJBRemote {
    public String sayHello();
}
