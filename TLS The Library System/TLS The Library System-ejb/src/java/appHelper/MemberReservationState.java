/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package appHelper;
import java.io.Serializable;
import java.util.Date;

public class MemberReservationState implements Serializable{
    //Member fields
    private Long mid;
    private String name, type, password;
    //Reservation fields
    private Long rid;
    private Date reserveDate;

    public MemberReservationState(Long mid, String name, String type, String password,
                                  Long rid, Date reserveDate){
        this.mid         = mid;         this.rid         = rid;
        this.name        = name;        this.reserveDate = reserveDate;
        this.type        = type;
        this.password    = password;
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

    public Date getReserveDate() {
        return reserveDate;
    }

    public Long getRid() {
        return rid;
    }

    public String getType() {
        return type;
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

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setType(String type) {
        this.type = type;
    }
}
