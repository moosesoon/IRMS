/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignment IS2104 EJB
 */

package appHelper;
import java.io.Serializable;
import java.util.Date;
import tlb.BookEntity;

public class BookState implements Serializable {
    //Book fields
    private Long bid;
    private int copy;
    private Date loan, due;
    //Member Entity
    private MemberState ms;
    //Title Entity
    private TitleState ts;

    public BookState(Long bid, int copy, Date loan, Date due){
        this.bid     = bid;
        this.copy    = copy;
        this.loan    = loan;
        this.due     = due;
    }

    public BookState(BookEntity be){
        this.bid     = be.getId();          this.ts = new TitleState(be.getTitle());
        this.copy    = be.getCopy();
        this.loan    = be.getLoan();
        this.due     = be.getDue();
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

    public MemberState getMs() {
        return ms;
    }

    public TitleState getTs() {
        return ts;
    }

    public void setMs(MemberState ms) {
        this.ms = ms;
    }

    public void setTs(TitleState ts) {
        this.ts = ts;
    }
}
