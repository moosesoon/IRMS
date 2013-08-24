/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.SystemUser;
import javax.ejb.Local;

/**
 *
 * @author Stanley
 */
@Local
public interface SystemUserSessionLocal {
    public SystemUser getSystemUser(String userName);
}
