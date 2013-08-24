/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Stanley
 */
@Stateless
@LocalBean
public class StatelessBean {

    private Integer secretNumber = 0;
    
    public Integer getSecretNumber(){
        return ++secretNumber;
    }
}
