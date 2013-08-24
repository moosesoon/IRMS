/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package lib;
import appHelper.MemberContactState;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import javax.ejb.Stateful;

@Stateful
public class MemberContactManagerBean implements MemberContactManagerRemote {
    @PersistenceContext
    EntityManager em;
    
    private MemberEntity memberEntity;
    private ContactEntity contactEntity;

    public MemberContactManagerBean(){}
    
    public void createMember(String name, String type, String password){
        memberEntity = new MemberEntity();
        memberEntity.create(name, type, password);
    }
    public void createContact(String dept, String faculty, String email, int phone){
        contactEntity = new ContactEntity();
        contactEntity.create(dept, faculty, email, phone);
    }
    
    public void persist(){
        memberEntity.setContact(contactEntity);
        em.persist(memberEntity);
    }
    
    public List<MemberContactState> getMember(){
        Query q = em.createQuery("SELECT m FROM Members m");
        List stateList = new ArrayList();
        for(Object o : q.getResultList()){
            MemberEntity m = (MemberEntity) o;
            MemberContactState mcs = new MemberContactState(m.getId(), m.getName(), m.getType(), m.getPassword(),
                                                            m.getContact().getId(),
                                                            m.getContact().getDept(),
                                                            m.getContact().getFaculty(),
                                                            m.getContact().getEmail(),
                                                            m.getContact().getPhone());
            stateList.add(mcs);
        }
        return stateList;
    }

    public void terminate(){
        em.remove(memberEntity);
    }

    @Remove
    public void remove(){
        System.out.println("MemberContactManagerBean:remove()");
    }
    
}
