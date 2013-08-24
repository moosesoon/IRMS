/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package appHelper;
import java.io.Serializable;

public class MemberContactState implements Serializable{
    //Member fields
    private Long mid;
    private String name, type, password;
    //Contact fields
    private Long cid;
    private String dept, faculty, email;
    private int phone;

    public MemberContactState(Long mid, String name, String type, String password,
                              Long cid, String dept, String faculty, String email, int phone){
        this.mid        = mid;          this.cid        = cid;
        this.name       = name;         this.dept       = dept;
        this.type       = type;         this.faculty    = faculty;
        this.password   = password;     this.email      = email;
                                        this.phone      = phone;
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

    public Long getMid() {
        return mid;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getPhone() {
        return phone;
    }

    public String getType() {
        return type;
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

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setType(String type) {
        this.type = type;
    }
}
