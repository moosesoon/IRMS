/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignmentt IS2104 EJB
 */

package tlb;

import java.io.Serializable;
import java.util.Date;
import java.util.Collection;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity(name="Book")
public class BookEntity implements Serializable {
    @Id
    private Long id;
    int copy;
    
    //Relationship
    @Temporal(value = TemporalType.DATE)
    private Date loan;
    @Temporal(value = TemporalType.DATE)
    private Date due;
    @ManyToOne
    private TitleEntity title = new TitleEntity();
    @ManyToOne
    private MemberEntity member = new MemberEntity();
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="book")
    private Collection<ReservationEntity> reservations = new ArrayList<ReservationEntity>();

    public BookEntity(){
        setId(System.nanoTime());
    }

    //Create method: Used to initialise the member entity variables.
    //Assumptions: All values are inherently correct
    public void create(int copy, Date loan, Date due){
        this.copy = copy;
        this.loan = loan;
        this.due  = due;
    }

    public int getCopy() {
        return copy;
    }

    
    public Date getDue() {
        return due;
    }
    
    public Long getId() {
        return id;
    }

    public Date getLoan() {
        return loan;
    }
    
    public TitleEntity getTitle() {
        return title;
    }

    public void setCopy(int copy) {
        this.copy = copy;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLoan(Date loan) {
        this.loan = loan;
    }

    public void setTitle(TitleEntity title) {
        this.title = title;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public Collection<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<ReservationEntity> reservations) {
        this.reservations = reservations;
    }
}
