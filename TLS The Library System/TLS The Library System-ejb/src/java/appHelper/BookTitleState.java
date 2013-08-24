/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package appHelper;
import java.io.Serializable;
import java.util.Date;

public class BookTitleState implements Serializable {
    //Book fields
    private Long bid;
    private int copy;
    private Date loan, due;

    //Title fields
    private String ISBN, title, author, publisher;

    public BookTitleState(Long bid, int copy, Date loan, Date due,
                          String ISBN, String title, String author, String publisher){
        this.bid    = bid;          this.ISBN        = ISBN;
        this.copy   = copy;         this.title       = title;
        this.loan   = loan;         this.author      = author;
        this.due    = due;          this.publisher   = publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public Long getBid() {
        return bid;
    }

    public int getCopy() {
        return copy;
    }

    public Date getDue() {
        return due;
    }

    public Date getLoan() {
        return loan;
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

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public void setCopy(int copy) {
        this.copy = copy;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public void setLoan(Date loan) {
        this.loan = loan;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
