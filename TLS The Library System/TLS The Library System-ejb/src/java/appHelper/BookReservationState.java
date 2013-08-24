/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package appHelper;
import java.io.Serializable;
import java.util.Date;

public class BookReservationState implements Serializable {
    //Book fields
    private Long bid;
    private int copy;
    private Date loan, due;
    //Reservation fields
    private Long rid;
    private Date reserveDate;

    public BookReservationState(Long bid, int copy, Date loan, Date due,
                                Long rid, Date reserveDate){
        this.bid    = bid;          this.rid            = rid;
        this.copy   = copy;         this.reserveDate    = reserveDate;
        this.loan   = loan;
        this.due    = due;
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

    public Date getReserveDate() {
        return reserveDate;
    }

    public Long getRid() {
        return rid;
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

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
}
