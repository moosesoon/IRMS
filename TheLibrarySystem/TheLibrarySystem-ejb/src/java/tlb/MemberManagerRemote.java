/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */

package tlb;

import javax.ejb.Remote;
import appHelper.MemberState;
import appHelper.BookState;
import java.util.List;

@Remote
public interface MemberManagerRemote {
    public void createMember(String name, String type, String password);
    public void createContact(String dept, String faculty, String email, int phone);
    public String terminate(Long mid);
    public List<BookState> getAvailableCopy(String name);
    public List<BookState> getMemberLoan();
    public MemberState login(String name, String password);
    public void loanBook(Long bid);
    public void returnBook(Long bid);;
    public boolean findMember(Long mid);
    public void setMember(Long mid);
    public void updateDue(Long bid);
    public void persist();
    public List<MemberState> getMember(String name);
    public void remove();
}
