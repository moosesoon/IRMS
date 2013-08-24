/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */

package tlb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;

@Entity(name="Payment")
public class PaymentEntity implements Serializable {
    @Id
    private Long id;
    @Temporal(value = TemporalType.DATE)
    private Date datePaid;
    private double paidAmt;

    public PaymentEntity(){
        setId(System.nanoTime());
    }

    public void create(Date datePaid, double paidAmt){
        this.datePaid = datePaid;
        this.paidAmt = paidAmt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public double getPaidAmt() {
        return paidAmt;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public void setPaidAmt(double paidAmt) {
        this.paidAmt = paidAmt;
    }
}
