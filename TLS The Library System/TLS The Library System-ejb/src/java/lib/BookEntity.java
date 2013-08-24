/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package lib;

import java.util.Date;
import java.io.Serializable;
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
    @Temporal(value = TemporalType.DATE)
    private Date loan;
    @Temporal(value = TemporalType.DATE)
    private Date due;
    @ManyToOne
    private MemberEntity members = new MemberEntity();
    @ManyToOne
    private TitleEntity title = new TitleEntity();
    @OneToMany(cascade={CascadeType.PERSIST}, mappedBy="book")
    private Collection<ReservationEntity> reservation = new ArrayList<ReservationEntity>();

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

    public MemberEntity getMembers() {
        return members;
    }

    public Collection<ReservationEntity> getReservation() {
        return reservation;
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

    public void setMembers(MemberEntity members) {
        this.members = members;
    }

    public void setReservation(Collection<ReservationEntity> reservation) {
        this.reservation = reservation;
    }

    public void setTitle(TitleEntity title) {
        this.title = title;
    }
}
