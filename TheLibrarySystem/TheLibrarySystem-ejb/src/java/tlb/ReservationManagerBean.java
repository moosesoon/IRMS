/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */

package tlb;

import appHelper.ReservationState;
import javax.ejb.Stateless;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Remove;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ReservationManagerBean implements ReservationManagerRemote {
    @PersistenceContext
    private EntityManager em;
    private MemberEntity memberEntity;
    private BookEntity bookEntity;
    private ReservationEntity reservationEntity;
    private Collection<ReservationEntity> reservations = new ArrayList<ReservationEntity>();

    public ReservationManagerBean(){}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addReservation(Long mid, Long bid, Date reserveDate){
        Query q = em.createQuery("SELECT m FROM Members m");
        q.getResultList();
        q = em.createQuery("SELECT b FROM Book b");
        q.getResultList();

        //Find the member and book entity
        memberEntity = em.find(MemberEntity.class, mid);
        bookEntity = em.find(BookEntity.class, bid);

        //Check if book is on loan
        if(bookEntity != null){
            if(bookEntity.getLoan() != null){
                reservationEntity = new ReservationEntity();
                reservationEntity.create(reserveDate);
                reservationEntity.setBook(bookEntity);
                reservationEntity.setMember(memberEntity);
                //Set up for books
                reservations = bookEntity.getReservations();
                reservations.add(reservationEntity);
                bookEntity.setReservations(reservations);
                //Set up for members
                reservations = memberEntity.getReservations();
                reservations.add(reservationEntity);
                memberEntity.setReservations(reservations);

                em.persist(reservationEntity);
                System.out.println("ReservationManagerBean: Reservation added");
            } else{
                em.clear();
                System.out.println("ReservationManagerBean: Book not on loan, unable to reserve");
            }
        } else{
            em.clear();
            System.out.println("ReservationManagerBean: Book does not exist");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delReservation(Long rid){
        Query q = em.createQuery("SELECT r FROM Reservation r");
        q.getResultList();
        reservationEntity = em.find(ReservationEntity.class, rid);
        //Remove link from book
        bookEntity = reservationEntity.getBook(); //Must exist, because of M - 1 relationship (No need check for null)
        reservations = bookEntity.getReservations();
        reservations.remove(reservationEntity);
        //Remove link from member
        memberEntity = reservationEntity.getMember(); //Must exist, because of M - 1 relationship (No need check for null)
        reservations = memberEntity.getReservations();
        reservations.remove(reservationEntity);
        //Remove reservation entity
        em.remove(reservationEntity);
        System.out.println("ReservationManagerBean: Reservation deleted");
    }

    public List<ReservationState> getReservation(Long mid){
        List reservationStateList = new ArrayList();
        List result;
        Query q = em.createQuery("SELECT m FROM Members m WHERE m.id=:mid");
        q.setParameter("mid", mid);

        result = q.getResultList();
        if(result.size() == 0){
            System.out.println("There are no result");
            return reservationStateList;
        }

        for(Object m: result){
            MemberEntity me = (MemberEntity) m;
            for(Object r: me.getReservations()){
                ReservationEntity re = (ReservationEntity) r;
                ReservationState rs = new ReservationState(re);
                reservationStateList.add(rs);
            }
        }
        System.out.println("ReservationManagerBean: Reservation list returned");
        return reservationStateList;
    }

    @Remove
    public void remove(){
        System.out.println("ReservationManagerBean:remove()");
    }
}
