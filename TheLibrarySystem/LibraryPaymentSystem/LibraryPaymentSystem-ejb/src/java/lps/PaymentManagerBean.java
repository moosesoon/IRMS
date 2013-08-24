/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignmentt IS2104 EJB
 */

package lps;

import javax.ejb.Stateful;
import javax.ejb.Remove;
import javax.jms.ConnectionFactory;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.Queue;
import javax.jms.MessageProducer;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.MapMessage;
import javax.jms.JMSException;
import javax.annotation.Resource;

@Stateful
public class PaymentManagerBean implements PaymentManagerRemote {
    @Resource(mappedName="jms/senderQueueConnectionFactory")
    private ConnectionFactory senderQueueConnFac;
    @Resource(mappedName="jms/receiverQueueConnectionFactory")
    private ConnectionFactory receiverQueueConnFac;
    @Resource(mappedName="jms/senderQueue")
    private Queue sQueue;
    @Resource(mappedName="jms/receiveQueue")
    private Queue rQueue;

    private double amtOwed, amtPaid;
    private String name;
    private Long mid;

    public void setMember(Long memberID){
        mid = memberID;
    }

    public void setPayment(double amountPaid){
        amtPaid = amountPaid;
    }

    public String getName(){
        
        return name;
    }

    public double getAmtOwed(){
        return amtOwed;
    }

    public void doDisplay(){
        Connection senderQueueConn = null;
        Session session = null;
        MapMessage message = null;
        MessageProducer producer = null;

        try{
            senderQueueConn = senderQueueConnFac.createConnection();
            session = senderQueueConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            senderQueueConn.start();
            producer = session.createProducer(sQueue);
            message = session.createMapMessage();

            message.setString("memberID", mid.toString());
            System.out.println("PaymentManagerBean: Sending ID, Awaiting reply....");
            producer.send(message);
            
        }catch(JMSException jex){
            System.err.println("PaymentManagerBean: A jms exception has occured in doDisplay()");
            jex.printStackTrace();
        }finally{
            if(senderQueueConn != null){
                try{
                    senderQueueConn.close();
                } catch(JMSException jex){
                    System.err.println("PaymentManagerBean: A jms exception has occured in doDisplay()");
                    jex.printStackTrace();
                }
            }
        }
    }

    public void doPayment(){
        Connection senderQueueConn = null;
        Session session = null;
        MapMessage message = null;
        Queue replyQueue = null;
        MessageProducer producer = null;
        MessageConsumer consumer = null;

        try{
            senderQueueConn = senderQueueConnFac.createConnection();
            session = senderQueueConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            senderQueueConn.start();
            producer = session.createProducer(sQueue);
            message = session.createMapMessage();

            message.setString("memberID", mid.toString());
            message.setString("amountPaid", Double.toString(amtPaid));
            System.out.println("PaymentManagerBean: Sending amount, Awaiting reply....");
            producer.send(message);
            
        }catch(JMSException jex){
            System.err.println("PaymentManagerBean: A jms exception has occured in doPayment()");
            jex.printStackTrace();
        } finally{
            if(senderQueueConn != null){
                try{
                    senderQueueConn.close();
                } catch(JMSException jex){
                    System.err.println("PaymentManagerBean: A jms exception has occured in doPayment()");
                    jex.printStackTrace();
                }
            }
        }
    }

    @Remove
    public void remove(){
        System.out.println("PaymentManageBean: remove()");
    }

    public void receive(){
        Connection receiverQueueConn = null;
        Session session = null;
        MessageConsumer consumer = null;
        Message message = null;
        //Wait for reply
        try{
            receiverQueueConn = receiverQueueConnFac.createConnection();
            session = receiverQueueConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            consumer = session.createConsumer(rQueue);
            receiverQueueConn.start();

            message = consumer.receive();
            System.out.println("PaymentManagerBean: Message Received!");

            if(message instanceof MapMessage){
                MapMessage msg = (MapMessage) message;
                name = msg.getString("memberName");
                amtOwed = Double.parseDouble(msg.getString("amtOwed"));
            } else{
            System.out.println("PaymentManagerBean: Wrong message type");
            }
         } catch(JMSException jex){
            System.out.println("PaymentManagerBean: A jms exception has occurred in PaymentListener");
            jex.printStackTrace();
         }
    }
}
