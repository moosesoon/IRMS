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
import javax.persistence.ManyToOne;

@Entity(name="Reservation")
public class ReservationEntity implements Serializable {
    @Id
    private Long id;
    @Temporal(value = TemporalType.DATE)
    private Date reserveDate;
    @ManyToOne
    private MemberEntity members = new MemberEntity();
    @ManyToOne
    private BookEntity book = new BookEntity();

    public ReservationEntity(){
        setId(System.nanoTime());
    }

    //Create method: Used to initialise the member entity variables.
    //Assumptions: All values are inherently correct
    public void create(Date reserveDate){
        this.reserveDate = reserveDate;
    }

    public BookEntity getBook() {
        return book;
    }

    public Long getId() {
        return id;
    }

    public MemberEntity getMembers() {
        return members;
    }
    
    public Date getReserveDate() {
        return reserveDate;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMembers(MemberEntity members) {
        this.members = members;
    }

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }
}
