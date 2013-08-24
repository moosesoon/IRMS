/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignmentt IS2104 EJB
 */

package lps;

import javax.ejb.Remote;
import javax.jms.Message;

@Remote
public interface PaymentManagerRemote{
    public void setMember(Long memberID);
    public void setPayment(double amountPaid);
    public String getName();
    public double getAmtOwed();
    public void doPayment();
    public void doDisplay();
    public void receive();
    public void remove();
}
