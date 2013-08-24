/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */

package tlb;

import appHelper.BookState;
import appHelper.TitleState;
import javax.ejb.Stateless;
import javax.ejb.Remove;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityExistsException;
import javax.persistence.Query;


@Stateless
public class BookManagerBean implements BookManagerRemote {
    @PersistenceContext
    EntityManager em;
    private BookEntity bookEntity;
    private TitleEntity titleEntity;
    private MemberEntity memberEntity;
    private Collection<BookEntity> books = new ArrayList<BookEntity>();
    private Collection<ReservationEntity> reservations = new ArrayList<ReservationEntity>();

    public BookManagerBean(){}

    public void createTitle(String ISBN, String title, String author, String publisher){
        titleEntity = new TitleEntity();
        titleEntity.create(ISBN, title, author, publisher);
        books = new ArrayList<BookEntity>();
    }

    public void createBook(int copy, Date loan, Date due){
        bookEntity = new BookEntity();
        bookEntity.create(copy, loan, due);
    }

    public List<TitleState> getTitles(String name){
        String parameter = " WHERE t.ISBN = :isbn";
        if(name.compareTo("") == 0){
            parameter = "";
        }
        //Create query to get title
        Query q = em.createQuery("SELECT t FROM Title t" + parameter);
        if(parameter.compareTo("") != 0){
            q.setParameter("isbn", name);
        }

        //Create state list to store states
        List stateList = new ArrayList();
        for(Object o: q.getResultList()){
            TitleEntity te = (TitleEntity) o; //Set the object to title entity
            TitleState ts = new TitleState(te);
            stateList.add(ts);
        } //End of getting all title
        System.out.println("BookManagerBean: Title list returned");
        return stateList;
    }

    /* Get Title Entity and store it into the state
     * return the state list of both title and book
     * Code not being updated. Used title book state instead of creating title state
     * */
    public List<BookState> getBooks(String name){
        String parameter = " WHERE t.ISBN = :isbn";
        if(name.compareTo("") == 0){
            parameter = "";
        }
        //Create query to get title
        Query q = em.createQuery("SELECT t FROM Title t" + parameter);
        if(parameter.compareTo("") != 0){
            q.setParameter("isbn", name);
        }

        //Create state list to store states
        List stateList = new ArrayList();
        for(Object o: q.getResultList()){
            TitleEntity te = (TitleEntity) o; //Set the object to title entity
            for(Object p: te.getBooks()){
                BookEntity be = (BookEntity) p;
                BookState bs = new BookState(be);
                stateList.add(bs);
            } //End of getting all books
        } //End of getting all title
        System.out.println("BookManagerBean: Book list returned");
        return stateList;
    }

    /*
     *
     *
     * */
    public List<TitleState> getTitleBy(String query, String type){
        //Create query to get title
        query = query.toLowerCase();
        Query q = em.createQuery("SELECT t FROM Title t WHERE LOWER(t." + type + ") LIKE :query");
        q.setParameter("query", query);

        //Create state list to store states
        List stateList = new ArrayList();
        for(Object o: q.getResultList()){
            TitleEntity te = (TitleEntity) o; //Set the object to title entity
            TitleState ts = new TitleState(te);
            stateList.add(ts);
        } //End of getting all title
        System.out.println("BookManagerBean: Title list returned");
        return stateList;
    }

    /* Books are added by setting the book to a title, since books needs a title (M <-> 1) to exist
     * Books also need a member to exist. (M <-> 1). But initially no member borrow books, therefore set to null (Good or Bad?)
     * */
    public void addBook(){
        bookEntity.setTitle(titleEntity);
        bookEntity.setMember(null);
        bookEntity.setReservations(reservations);
        books.add(bookEntity);
        System.out.println("BookManagerBean: Book added");
    }

    /* Title creation. Did not change the method name to reflect that
     * Code not being updated.
     * */
    public void persist(){
        titleEntity.setBooks(books);
        try{
            em.persist(titleEntity);
            System.out.println("BookManagerBean: Title added");
        } catch(EntityExistsException eex){
            System.out.println("BookManagerBean: Title existed!");
        }
    }

    /* Title update. For example if a book is being deleted, the title will be required to update
     * Code not being updated.
     * */
    public void merge(){
        titleEntity.setBooks(books);
        em.merge(titleEntity);
        System.out.println("BookManagerBean: Title updated");
    }

    /* Deletion of title. Thanks to cascade remove, book entities are not required to be removed one by one.
     * */
    public void delTitle(String ISBN){
        titleEntity = em.find(TitleEntity.class, ISBN);
        if(titleEntity != null){
            em.remove(titleEntity);
            System.out.println("BookManagerBean: Title removed");
        } else{
            em.clear();
            System.out.println("BookManagerBean: Unable to find title");
        }
    }

    /* Delete books by removing the reference of books from both title and member entity first
     * Only then can the book entity be thoroughly removed and updated on the entity manager
     * */
    public void delBook(Long bid){
        bookEntity = em.find(BookEntity.class, bid);
        if(bookEntity != null){
            titleEntity = em.find(TitleEntity.class, bookEntity.getTitle().getISBN());
            //Check if there is a member attached to the book
            if(bookEntity.getMember() != null){
                memberEntity = em.find(MemberEntity.class, bookEntity.getMember().getId());
                //Remove reference to member side
                books = memberEntity.getBooks();
                books.remove(bookEntity);
                memberEntity.setBooks(books);
            }
            //Remove reference to title side
            books = titleEntity.getBooks();
            books.remove(bookEntity);
            titleEntity.setBooks(books);
            //Update all entities involved
            em.remove(bookEntity);
            System.out.println("BookManagerBean: Books removed");
        } else {
            em.clear();
            System.out.println("BookManagerBean: Unable to find books");
        }
    }

    @Remove
    public void remove(){
        System.out.println("BookManagerBean:remove()");
    }
}
