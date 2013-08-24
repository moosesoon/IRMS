/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */

package appHelper;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import tlb.TitleEntity;
import tlb.BookEntity;

public class TitleState implements Serializable {
    //Title fields
    String ISBN, title, author, publisher;

    public TitleState(TitleEntity te){
        this.ISBN = te.getISBN();
        this.title = te.getTitle();
        this.author = te.getAuthor();
        this.publisher = te.getPublisher();
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
}
