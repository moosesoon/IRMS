/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tlb;

import javax.ejb.Remote;
import appHelper.ReservationState;
import java.util.Date;
import java.util.List;

@Remote
public interface ReservationManagerRemote {
    public void addReservation(Long mid, Long bid, Date reserveDate);
    public void delReservation(Long rid);
    public List<ReservationState> getReservation(Long mid);
    public void remove();
}
