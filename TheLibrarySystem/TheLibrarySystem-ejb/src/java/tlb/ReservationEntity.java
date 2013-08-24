/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */

package tlb;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name="Reservation")
public class ReservationEntity implements Serializable {
    @Id
    private Long id;
    @Temporal(value=TemporalType.DATE)
    private Date reserveDate;
    @ManyToOne
    private MemberEntity member = new MemberEntity();
    @ManyToOne
    private BookEntity book = new BookEntity();

    public ReservationEntity(){
        setId(System.nanoTime());
    }

    public void create(Date reserveDate){
        this.reserveDate = reserveDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    public BookEntity getBook() {
        return book;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }
}
