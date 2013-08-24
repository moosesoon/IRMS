/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package appHelper;
import java.io.Serializable;
import java.util.Date;

public class FinePaymentState implements Serializable {
    //Fine fields
    private Long fid;
    private double amountOwed;
    //Payment fields
    private Long pid;
    private Date datePaid;
    private double amtPaid;

    public FinePaymentState(Long fid, double amountOwed,
                            Long pid, Date datePaid, double amtPaid){
        this.fid        = fid;          this.pid        = pid;
        this.amountOwed = amountOwed;   this.datePaid   = datePaid;
                                        this.amtPaid    = amtPaid;
    }

    public double getAmountOwed() {
        return amountOwed;
    }

    public double getAmtPaid() {
        return amtPaid;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public Long getFid() {
        return fid;
    }

    public Long getPid() {
        return pid;
    }

    public void setAmountOwed(double amountOwed) {
        this.amountOwed = amountOwed;
    }

    public void setAmtPaid(double amtPaid) {
        this.amtPaid = amtPaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
