/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignemtn IS2104 EJB
 */

package tlb;

import javax.ejb.Remote;
import appHelper.BookState;
import appHelper.TitleState;
import java.util.List;
import java.util.Date;

@Remote
public interface BookManagerRemote {
    public void createTitle(String ISBN, String title, String author, String publisher);
    public void createBook(int copy, Date loan, Date due);
    public List<TitleState> getTitles(String name);
    public List<BookState> getBooks(String name);
    public List<TitleState> getTitleBy(String query, String type);
    public void addBook();
    public void persist();
    public void merge();
    public void delTitle(String ISBN);
    public void delBook(Long bid);
    public void remove();
}
