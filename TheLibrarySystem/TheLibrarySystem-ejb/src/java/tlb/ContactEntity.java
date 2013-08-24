/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package tlb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="Contact")
public class ContactEntity implements Serializable {
    @Id
    private Long id;
    private String dept, faculty, email;
    private int phone;

    public ContactEntity(){
        setId(System.nanoTime());
    }

    //Create method: Used to initialise the member entity variables.
    //Assumptions: All values are inherently correct
    public void create(String dept, String faculty, String email, int phone){
        this.dept    = dept;
        this.faculty = faculty;
        this.email   = email;
        this.phone   = phone;
    }

    //Get / Set Methods
    public String getDept(){
        return dept;
    }

    public void setDept(String dept){
        this.dept = dept;
    }

    public String getFaculty(){
        return faculty;
    }

    public void setFaculty(String faculty){
        this.faculty = faculty;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public int getPhone(){
        return phone;
    }

    public void setPhone(int phone){
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
