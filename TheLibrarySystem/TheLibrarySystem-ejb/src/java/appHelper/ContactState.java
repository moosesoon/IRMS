/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */

package appHelper;
import java.io.Serializable;
import tlb.ContactEntity;

public class ContactState implements Serializable {
    private Long cid;
    private String dept, faculty, email;
    private int phoneNum;

    public ContactState(Long cid, String dept, String faculty, int phoneNum, String email){
        this.cid = cid;
        this.dept = dept;
        this.faculty = faculty;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public ContactState(ContactEntity ce){
        this.cid         = ce.getId();
        this.dept        = ce.getDept();
        this.faculty     = ce.getFaculty();
        this.phoneNum    = ce.getPhone();
        this.email       = ce.getEmail();
    }

    public Long getCid() {
        return cid;
    }

    public String getDept() {
        return dept;
    }

    public String getEmail() {
        return email;
    }

    public String getFaculty() {
        return faculty;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }
}
