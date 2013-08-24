/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package tlb;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.Collection;
import java.util.ArrayList;

@Entity(name="Title")
public class TitleEntity implements Serializable {
    @Id
    private String ISBN;
    private String title, author, publisher;
    
    //Relationship
    @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, mappedBy="title")
    private Collection<BookEntity> books = new ArrayList<BookEntity>();

    public TitleEntity(){}

    //Create method: Used to initialise the member entity variables.
    //Assumptions: All values are inherently correct
    public void create(String ISBN, String title, String author, String publisher){
        this.ISBN        = ISBN;
        this.title       = title;
        this.author      = author;
        this.publisher   = publisher;
    }
    
    public String getISBN() {
        return ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(Collection<BookEntity> books) {
        this.books = books;
    }
}
