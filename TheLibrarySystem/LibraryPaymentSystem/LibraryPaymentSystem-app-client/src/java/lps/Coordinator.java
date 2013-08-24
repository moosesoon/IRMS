package lps;
/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignmentt IS2104 EJB
 */
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Coordinator extends Thread implements MessageListener{
    private static Connection queueConn;
    private static Queue queue;
    private int requestOutstanding = 2;
    private static Object waitUntilDone = new Object();

    public Coordinator(Connection c, Queue q){
        queueConn = c;
        queue = q;
    }

    @Override
    public void run(){
        Session session = null;
        MessageConsumer consumer = null;
        System.out.println("\nCoordinator run: Consumer started...");
        try{
            session = queueConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            consumer = session.createConsumer(queue);
            consumer.setMessageListener(this);
            queueConn.start();
            synchronized(waitUntilDone){
                waitUntilDone.wait();
            }
        }catch(JMSException jex){
            System.err.println("An exception has occurred in run(): " + jex.toString());
        }catch(InterruptedException ie){
            System.err.println("An exception has occurred run(): " + ie.toString());
        } finally{

        }
    }

    public void onMessage(Message message){
        TextMessage msg = (TextMessage) message;
        System.out.println("\nCoordinator onMessage(): Processing message from Queue....");
        try{
            System.out.println(msg.getText());
            requestOutstanding--;
        } catch(JMSException jex){
            System.err.println("An exception has occurred in run(): " + jex.toString());
        }
        if(requestOutstanding == 0){
            System.out.println("Exiting Coordinator...");
            synchronized(waitUntilDone){
                waitUntilDone.notify();
            }
        } else{
            System.out.println("\nCoordinator: Waiting for " + requestOutstanding + " message(s)");
        }
    }
}
