/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */

package appHelper;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import tlb.BookEntity;
import tlb.FineEntity;
import tlb.ContactEntity;
import tlb.MemberEntity;

public class MemberState implements Serializable {
    //Member Fields
    private Long mid;
    private String name, type, password;
    //Contact Entity
    private ContactState contactState;
    //Fine Entity
    private FineState fineState;
    //Reservations Entity
    

    public MemberState(MemberEntity me){
        this.mid         = me.getId();          this.setContactState(new ContactState(me.getContact()));
        this.name        = me.getName();        this.setFineState(new FineState(me.getFine()));
        this.type        = me.getType();
        this.password    = me.getPassword();
    }

    public ContactState getContactState() {
        return contactState;
    }

    public FineState getFineState() {
        return fineState;
    }

    public Long getMid() {
        return mid;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public void setContactState(ContactState contactState) {
        this.contactState = contactState;
    }

    public void setFineState(FineState fineState) {
        this.fineState = fineState;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }
}
