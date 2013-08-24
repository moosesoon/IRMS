/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.SystemUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Stanley
 */
@Stateless
public class SystemUserSession implements SystemUserSessionLocal {
    @PersistenceContext
    private EntityManager em;
    
    public SystemUser getSystemUser(String userName){
        Query query = em.createQuery("SELECT u FROM SystemUser u WHERE " +
                                     "u.userName = :inUserName");
        query.setParameter("inUserName", userName);
        SystemUser systemUser = null;
        try{
            systemUser = (SystemUser)query.getSingleResult();
        } catch(NoResultException nre){
            nre.printStackTrace();
        }
        return systemUser;
    }
}
