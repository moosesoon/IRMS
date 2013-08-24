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
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity(name="Members")
public class MemberEntity implements Serializable {
    @Id
    private Long id;
    private String name, type, password;

    //Relationship
    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    private ContactEntity contact;
    @OneToMany(mappedBy="member") //
    private Collection<BookEntity> books = new ArrayList<BookEntity>();
    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    private FineEntity fine;
    @OneToMany(cascade={CascadeType.PERSIST},mappedBy="member")
    private Collection<ReservationEntity> reservations = new ArrayList<ReservationEntity>();

    public MemberEntity(){
        setId(System.nanoTime());
    }

    public void create(String name, String type, String password){
        this.name        = name;
        this.type        = type;
        this.password    = password;
    }

    public ContactEntity getContact() {
        return contact;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public void setContact(ContactEntity contact) {
        this.contact = contact;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(Collection<BookEntity> books) {
        this.books = books;
    }

    public FineEntity getFine() {
        return fine;
    }

    public void setFine(FineEntity fine) {
        this.fine = fine;
    }

    public void setReservations(Collection<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public Collection<ReservationEntity> getReservations() {
        return reservations;
    }
}
