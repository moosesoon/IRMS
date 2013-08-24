/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package appHelper;
import java.io.Serializable;
import java.util.Date;

public class MemberBookState implements Serializable {
    //Member fields
    private Long mid;
    private String name, type, password;
    //Book fields
    private Long bid;
    private int copy;
    private Date loan, due;

    public MemberBookState(Long mid, String name, String type, String password,
                           Long bid, int copy, Date loan, Date due){
        this.mid        = mid;          this.bid    = bid;
        this.name       = name;         this.copy   = copy;
        this.type       = type;         this.loan   = loan;
        this.password   = password;     this.due    = due;
    }

    public Long getBid() {
        return bid;
    }

    public int getCopy() {
        return copy;
    }

    public Date getDue() {
        return due;
    }

    public Date getLoan() {
        return loan;
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

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public void setCopy(int copy) {
        this.copy = copy;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public void setLoan(Date loan) {
        this.loan = loan;
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
