/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */

package tlb;
import appHelper.MemberState;
import appHelper.BookState;
import appHelper.FineState;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Calendar;
import javax.ejb.Remove;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ejb.Stateless;

@Stateless
public class MemberManagerBean implements MemberManagerRemote {
    @PersistenceContext
    EntityManager em;

    private MemberEntity memberEntity;
    private ContactEntity contactEntity;
    private BookEntity bookEntity;
    private FineEntity fineEntity;
    private Collection<BookEntity> books = new ArrayList<BookEntity>();
    private Collection<ReservationEntity> reservations = new ArrayList<ReservationEntity>();

    public MemberManagerBean(){}

    public void createMember(String name, String type, String password){
        memberEntity = new MemberEntity();
        memberEntity.create(name, type, password);
        fineEntity = new FineEntity();
        fineEntity.create(0);
    }

    public void createContact(String dept, String faculty, String email, int phone){
        contactEntity = new ContactEntity();
        contactEntity.create(dept, faculty, email, phone);
    }

    public void persist(){
        memberEntity.setContact(contactEntity);
        memberEntity.setFine(fineEntity);
        memberEntity.setReservations(reservations);
        em.persist(memberEntity);
        System.out.println("MemberManagerBean: Member added");
    }

    /* Get member based on their name. Used to support display members functions
     * */
    public List<MemberState> getMember(String name){
        String param = " WHERE m.name = :name";
        if(name.compareTo("") == 0){
            param = "";
        }
        Query q = em.createQuery("SELECT m FROM Members m" + param);
        if(param.compareTo("") != 0){
            q.setParameter("name", name);
        }
        List stateList = new ArrayList();
        for(Object o : q.getResultList()){
            MemberEntity m = (MemberEntity) o;
            MemberState mcs = new MemberState(m);
            stateList.add(mcs);
        }
        System.out.println("MemberManagerBean: Member list returned");
        return stateList;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public MemberState login(String name, String password){
        List temp;
        MemberState ms = null;
        Query q = em.createQuery("SELECT m FROM Members m WHERE m.name=:name AND m.password=:pass");
        q.setParameter("name", name);
        q.setParameter("pass", password);
        temp = q.getResultList();

        if(temp.size() > 0){
            ms = new MemberState((MemberEntity)temp.get(0));
        }

        System.out.println("MemberManagerBean: Member logined");
        return ms;
    }

    /* Terminate a member. Required to check if the member has any outstanding loans and fines
     * Set return type to String to return status message of the removal.
     * */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String terminate(Long mid){
        String message = "";
        boolean canDelete = true;
        List result;
        memberEntity = em.find(MemberEntity.class, mid);
        
        if(memberEntity != null){
            //Check if the member has any outstanding loan / fine
            fineEntity = memberEntity.getFine();
            if(fineEntity != null){
                if(fineEntity.getAmtOwed() > 0 ){
                    message += "\nMember has outstanding book fines";
                    canDelete = false;
                }
            }
            result = getMemberLoan();
            if(result.size() > 0){
                message += "\nMember has outstanding book loans";
                canDelete = false;
            }
            if(!canDelete){
                return message;
            } else{
                //If no proceed with deletion
                em.remove(memberEntity);
                return "\nMember removed successfully!";
            }
        } else{
            em.clear();
            System.out.println("MemberManagerBean: No member found");
            return "\nNo member found!";
        }
    }

    /* Find member based on id. This is without the use of parameter where m.id = :id
     * Using entitiy manager to search for the memberEntity.
     * If none is found, clear entity manager to prevent unncessary update to DB @ end of method
     * */
    public boolean findMember(Long mid){
        Query q = em.createQuery("SELECT m FROM Members m");
        memberEntity = em.find(MemberEntity.class, mid);
        if(memberEntity == null){
            em.clear();
            System.out.println("MemberManagerBean: No member found!");
            return false;
        }
        return true;
    }

    /* Purpose is only to set the member id for use to extract memberEntity
     *
     * */
    public void setMember(Long mid){
        memberEntity = new MemberEntity();
        memberEntity.setId(mid);
    }

    public void updateDue(Long bid){
        Calendar c = Calendar.getInstance();
        bookEntity = em.find(BookEntity.class, bid);
        c.setTime(bookEntity.getDue());
        c.add(Calendar.DATE, 14);
        bookEntity.setDue(c.getTime());
        em.merge(bookEntity);
        System.out.println("MemberManagerBean: Due date updated!");
    }

    /* To retrieve all available copy of books to borrow based on that particular title
     * */
    public List<BookState> getAvailableCopy(String name){
        Query q = em.createQuery("SELECT b FROM Book b LEFT JOIN b.title t " +
                                 "WHERE b.loan IS NULL AND t.ISBN = :isbn"); //Assuming we use null to mark availability of books
        q.setParameter("isbn", name);
        List result = q.getResultList();
        List bookStateList = new ArrayList();
        if(result.size() < 1){
            System.out.println("MemberManagerBean: No available books");
            return bookStateList;
        }
        for(Object o: q.getResultList()){
            BookEntity be = (BookEntity) o;
            BookState bs = new BookState(be.getId(),be.getCopy(),
                                         be.getLoan(), be.getDue());
            bookStateList.add(bs);
        }
        System.out.println("MemberManagerBean: Available books returned");
        return bookStateList;
    }

    /* To retrieve any loans the user have.
     * Also used to see if the user have any loans, therefore unable to terminate membership
     * */
    public List<BookState> getMemberLoan(){
        List result;
        List bookStateList = new ArrayList();
        if(memberEntity == null){
            return bookStateList;
        }

        Query q = em.createQuery("SELECT b FROM Book b LEFT JOIN b.member m " +
                                 "WHERE m.id = :id");
        
        q.setParameter("id", memberEntity.getId());

        result = q.getResultList();
        if(result.size() == 0){
            System.out.println("MemberManagerBean: No result");
            return bookStateList;
        }

        for(Object o: result){
            BookEntity be = (BookEntity) o;
            BookState bs = new BookState(be);
            bookStateList.add(bs);
        }
        System.out.println("MemberManagerBean: Loan list returned");
        return bookStateList;
    }

    /* Loaning of books by member. Things to set:
     * - MemberEntity to set the books borrowed in. In reverse, books have to set the member
     * - Setup the loan dates and due dates (loan + 14 days)
     * - Update the database based on borrowed book
     * */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void loanBook(Long bid){
        bookEntity = em.find(BookEntity.class, bid);
        //Check the loan date, if the loan date is not null, means someone has borrowed it.
        if(bookEntity.getLoan() == null){
            //Set books for members
            books = memberEntity.getBooks();
            books.add(bookEntity);
            memberEntity.setBooks(books);
            bookEntity.setMember(memberEntity);
            //Set the loan date
            Calendar c = Calendar.getInstance();
            Date temp;
            temp = c.getTime();
            bookEntity.setLoan(temp);
            //Set the due date
            c.add(Calendar.DATE, 14);
            temp = c.getTime();
            bookEntity.setDue(temp);

            em.merge(bookEntity);
            System.out.println("MemberManagerBean: Book borrowed");
        } else{
            em.clear();
            System.out.println("MemberManagerBean: Book already borrowed!");
        }
    }

    /* Return book by members. Things to note:
     * - Retrieve all involving entities based on returning book entity. (Member, Fine)
     * - Check dates if today's date of return is before or after due date. If it is after due date (today.after(duedate))
     * calculate how many days it has exceeded and charge $1 / day
     * - Set the fine amount into FineEntity
     * - Remove return book from MemberEntity's book collection
     * - Set the book back to member
     * - Set the fine back to member
     * - Disassociate book from member
     * - Reset loan and due date (To null) (Good or Bad?)
     * */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void returnBook(Long bid){
        Date today, dueDate;
        Calendar c = Calendar.getInstance();
        today = c.getTime();
        int diff = 0;

        bookEntity = em.find(BookEntity.class, bid);
        dueDate = bookEntity.getDue();
        System.out.println(dueDate.toString());
        memberEntity = bookEntity.getMember();
        fineEntity = memberEntity.getFine();
        if(today.after(dueDate)){ //Late. Calculate the amount owed
            while(dueDate.before(today)){ //+1 day until the day matches. Very inefficient
                System.out.print("hi ");
                diff++;
                c.setTime(dueDate);
                c.add(Calendar.DATE, 1);
                dueDate = c.getTime();
            }
            fineEntity.setAmtOwed(fineEntity.getAmtOwed()+diff);
        }
        
        //Set books for member
        books = memberEntity.getBooks();
        books.remove(bookEntity);
        memberEntity.setBooks(books);
        memberEntity.setFine(fineEntity);
        bookEntity.setMember(null);
        //Set the loan date
        bookEntity.setLoan(null);
        //Set the due date
        bookEntity.setDue(null);

        em.merge(bookEntity);
        System.out.println("MemberManagerBean: Book returned");
    }

    @Remove
    public void remove(){
        em.clear();
        System.out.println("MemberManagerBean:remove()");
    }

}
