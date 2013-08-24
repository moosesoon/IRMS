/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package lib;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="Payment")
public class PaymentEntity implements Serializable {
    @Id
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date datePaid;
    private double amountPaid;

    public PaymentEntity(){
        setId(System.nanoTime());
    }

    //Create method: Used to initialise the member entity variables.
    //Assumptions: All values are inherently correct
    public void create(double amountPaid, Date datePaid){
        this.amountPaid = amountPaid;
        this.datePaid   = datePaid;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
