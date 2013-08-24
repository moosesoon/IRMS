/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */

package tlb;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity(name="Fine")
public class FineEntity implements Serializable {
    @Id
    private Long id;
    private double amtOwed;
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    private Collection<PaymentEntity> payments = new ArrayList<PaymentEntity>();

    public FineEntity(){
        setId(System.nanoTime());
    }

    public void create(double amtOwed){
        this.amtOwed = amtOwed;
    }

    public double getAmtOwed() {
        return amtOwed;
    }

    public Long getId() {
        return id;
    }

    public void setAmtOwed(double amtOwed) {
        this.amtOwed = amtOwed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<PaymentEntity> getPayments() {
        return payments;
    }

    public void setPayments(Collection<PaymentEntity> payments) {
        this.payments = payments;
    }
}
