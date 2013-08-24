/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package appHelper;
import java.io.Serializable;

public class MemberFineState implements Serializable {
    //Member fields
    private Long mid;
    private String name, type, password;
    //Fine fields
    private Long fid;
    private double amountOwed;

    public MemberFineState(Long mid, String name, String type, String password,
                           Long fid, double amountOwed){
        this.mid        = mid;          this.fid        = fid;
        this.name       = name;         this.amountOwed = amountOwed;
        this.type       = type;
        this.password   = password;
    }

    public double getAmountOwed() {
        return amountOwed;
    }

    public Long getFid() {
        return fid;
    }

    public Long getMid() {
        return mid;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }

    public void setAmountOwed(double amountOwed) {
        this.amountOwed = amountOwed;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
