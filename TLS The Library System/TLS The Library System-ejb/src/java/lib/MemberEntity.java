/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package lib;

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
    private String name, type, password; //Current variables
    @OneToOne(cascade={CascadeType.PERSIST})
    private ContactEntity contact;
    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    private FineEntity fine;
    @OneToMany(cascade={CascadeType.PERSIST}, mappedBy="members")
    private Collection<ReservationEntity> reservation = new ArrayList<ReservationEntity>();
    @OneToMany(cascade={CascadeType.PERSIST}, mappedBy="members")
    private Collection<BookEntity> book = new ArrayList<BookEntity>();

    //Constructor
    public MemberEntity(){
        setId(System.nanoTime());
    }

    //Create method: Used to initialise the member entity variables.
    //Assumptions: All values are inherently correct
    public void create(String name, String type, String password){
        this.name        = name;
        this.type        = type;
        this.password    = password;
    }
    
    public Collection<BookEntity> getBook() {
        return book;
    }

    public ContactEntity getContact() {
        return contact;
    }

    public FineEntity getFine() {
        return fine;
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
    
    public Collection<ReservationEntity> getReservation() {
        return reservation;
    }

    public String getType() {
        return type;
    }

    
    public void setBook(Collection<BookEntity> book) {
        this.book = book;
    }

    public void setContact(ContactEntity contact) {
        this.contact = contact;
    }

    public void setFine(FineEntity fine) {
        this.fine = fine;
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

    public void setReservation(Collection<ReservationEntity> reservation) {
        this.reservation = reservation;
    }

    public void setType(String type) {
        this.type = type;
    }
}
