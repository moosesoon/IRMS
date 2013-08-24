/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignmentt IS2104 EJB
 */

package tlb;

import java.util.Date;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@MessageDriven(mappedName = "jms/senderQueue", activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
    })
public class PaymentMDBean implements MessageListener {
    @PersistenceContext
    private EntityManager em;
    private MemberEntity memberEntity;
    private FineEntity fineEntity;
    private PaymentEntity paymentEntity;

    @Resource(mappedName="jms/senderQueueConnectionFactory")
    private ConnectionFactory senderQueueConnFac;
    @Resource(mappedName="jms/receiverQueueConnectionFactory")
    private ConnectionFactory receiverQueueConnFac;
    @Resource(mappedName="jms/receiveQueue")
    private Queue rQueue;
    
    public PaymentMDBean() {}

    private void createPayment(Date datePaid, double amtPaid){
        paymentEntity = new PaymentEntity();
        paymentEntity.create(datePaid, amtPaid);
    }

    private void setFine(Long mid){
        memberEntity = em.find(MemberEntity.class, mid);
        if(memberEntity != null){
            fineEntity = memberEntity.getFine();
        } else{
            System.out.println("PaymentMDBean: Unable to find member. Either id is wrong or member not available");
        }
    }

    public void onMessage(Message message) {
        MapMessage msg = null;
        try{
            if(message instanceof MapMessage){
                msg = (MapMessage) message;
                Thread.sleep(1000);
                payFine(msg);
            } else{
                System.out.println("PaymentMDBean: Wrong message type!");
            }
        } catch(InterruptedException ie){
            System.out.println("PaymentMDBean: InterrruptedException at onMessage()");
        }
    }

    public void updateFine(){
        if(fineEntity != null){
            memberEntity.setFine(fineEntity);
            fineEntity.getPayments().add(paymentEntity);
            fineEntity.setPayments(fineEntity.getPayments());
            em.merge(memberEntity);
        } else{
            em.clear();
            System.out.println("PaymentMDBean: No fine to update!");
        }
    }

    public void payFine(MapMessage msg){
        Connection conn = null;
        Session session = null;
        MessageProducer producer = null;
        MapMessage replyMsg = null;
        Connection queueConn = null;

        Calendar c = Calendar.getInstance();
        Date datePaid = c.getTime();
        double amtPaid = 0.0;
        Long mid;
        String name, amtOwed;

        try{
            mid = new Long(msg.getString("memberID"));
            conn = senderQueueConnFac.createConnection();
            
            setFine(mid);
            if(fineEntity != null){
                 if(msg.getString("amountPaid") == null){ //Has only memberID;
                     //Just get amount owed
                     name = memberEntity.getName();
                     amtOwed = Double.toString(memberEntity.getFine().getAmtOwed());
                } else{
                    amtPaid = Double.parseDouble(msg.getString("amountPaid"));
                    System.out.println("PaymentMDBean: Payment Entity Setup!");
                    createPayment(datePaid, amtPaid);
                    fineEntity.setAmtOwed(fineEntity.getAmtOwed() - amtPaid);
                    updateFine();
                    System.out.println("PaymentMDBean: Fine and Payment successful updated!");
                    setFine(mid);
                    name = memberEntity.getName();
                    amtOwed = Double.toString(memberEntity.getFine().getAmtOwed());
                }
                 //Send back message
                 conn = receiverQueueConnFac.createConnection();
                 session = conn.createSession(false, 0);
                 conn.start();
                 producer = session.createProducer(rQueue);
                 replyMsg = session.createMapMessage();
                 replyMsg.setString("memberName", name);
                 replyMsg.setString("amtOwed", amtOwed);
                 producer.send(replyMsg);
                 System.out.println("PaymentMDBean: Message replied!");
            } else{
                System.out.println("PaymentMDBean: No fine entity!");
            }
           
        } catch(NumberFormatException nfe){
            System.out.println("PaymentMDBean: NumberFormatException at payFine()");
        } catch(JMSException jex){
            System.out.println("PaymentMDBean: JMSException at payFine()");
        } finally{
            if(conn != null){
                try{
                    conn.close();
                }catch(JMSException jex){
                    System.out.println("PaymentMDBean: JMSException at payFine()");
                }
                conn = null;
            }
            if(queueConn != null){
                try{
                    queueConn.close();
                }catch(JMSException jex){
                    System.out.println("PaymentMDBean: JMSException at payFine()");
                }
                queueConn = null;
            }
        }
    }
}
