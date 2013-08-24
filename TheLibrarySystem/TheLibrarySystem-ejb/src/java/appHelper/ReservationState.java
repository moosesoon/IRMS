/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package appHelper;

import java.io.Serializable;
import tlb.ReservationEntity;
import java.util.Date;
/**
 *
 * @author Stanley
 */
public class ReservationState implements Serializable {
    private Long rid;
    private Date reserveDate;
    //MemberState
    private MemberState ms;
    //BookState
    private BookState bs;

    public ReservationState(ReservationEntity re){
        this.rid = re.getId();
        this.reserveDate = re.getReserveDate();
        ms = new MemberState(re.getMember());
        bs = new BookState(re.getBook());
    }

    public BookState getBs() {
        return bs;
    }

    public MemberState getMs() {
        return ms;
    }

    public Date getReserveDate() {
        return reserveDate;
    }

    public Long getRid() {
        return rid;
    }

    public void setBs(BookState bs) {
        this.bs = bs;
    }

    public void setMs(MemberState ms) {
        this.ms = ms;
    }

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
}
