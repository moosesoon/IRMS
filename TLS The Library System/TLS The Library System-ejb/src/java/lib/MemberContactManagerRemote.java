/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import javax.ejb.Remote;
import appHelper.MemberContactState;
import java.util.List;

/**
 *
 * @author Stanley
 */
@Remote
public interface MemberContactManagerRemote {
    public void createMember(String name, String type, String password);
    public void createContact(String dept, String faculty, String email, int phone);
    public void terminate();
    public void persist();
    public List<MemberContactState> getMember();
    public void remove();
}
