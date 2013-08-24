/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author Stanley
 */
@Stateful
@LocalBean
public class StatefulBean {
    private Integer secretNumber = 0;
    public Integer getSecretNumber() {
        return ++secretNumber;
    }
}
