/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package lib;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.Collection;
import java.util.ArrayList;

@Entity(name="Fine")
public class FineEntity implements Serializable {
    @Id
    private Long id;
    private double amountOwed;
    @OneToMany(cascade={CascadeType.PERSIST})
    private Collection<PaymentEntity> payment = new ArrayList<PaymentEntity>();

    public FineEntity(){
        setId(System.nanoTime());
    }

    public void create(double amountOwed){
        this.amountOwed = amountOwed;
    }

    public double getAmountOwed() {
        return amountOwed;
    }

    public Long getId() {
        return id;
    }

    public Collection<PaymentEntity> getPayment() {
        return payment;
    }

    public void setAmountOwed(double amountOwed) {
        this.amountOwed = amountOwed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPayment(Collection<PaymentEntity> payment) {
        this.payment = payment;
    }
}
