/**
 * RDTSender : Encapsulate a reliable data sender that runs
 * over a unreliable channel that may drop and corrupt packets 
 * (but always delivers in order).
 *
 * Ooi Wei Tsang
 * CS2105, National University of Singapore
 * 12 March 2013
 */
import java.io.*;
import java.util.*;

/**
 * RDTSender receives a byte array from "above", construct a
 * data packet, and send it via UDT.  It also receives
 * ack packets from UDT.
 */
class RDTSender {
	UDTSender udt;
        int seqNumber;
        Timer timer;

	RDTSender(String hostname, int port) throws IOException
	{
		udt = new UDTSender(hostname, port);
                seqNumber = 0;
	}

	/**
	 * send() delivers the given array of bytes reliably and should
	 * not return until it is sure that the packet has been
	 * delivered.
	 */
	void send(byte[] data, int length) throws IOException, ClassNotFoundException
	{
        // create a new packet each time send is called
		DataPacket packet = new DataPacket(data, length, seqNumber);
                //Start timer
                timer = new Timer();
                timer.schedule(new PacketTimer(packet), 0, 800);

        // receive ACK
                while(true){
                    AckPacket ack = udt.recv();
                    if(ack.isCorrupted || (ack.ack == 1 && seqNumber == 0) ||
                                          (ack.ack == 0 && seqNumber == 1)){
                        //Do nothing. Wait for timer to expire
                    } else{
                        //Change to Seq to the next one
                        if(seqNumber == 0){
                            seqNumber = 1;
                        } else{
                            seqNumber = 0;
                        }
                        //Stop timer
                        timer.cancel();
                        break;
                    }
                }
	}

	/**
	 * close() is called when there is no more data to send.
	 * This method creates an empty packet with 0 bytes and
	 * send it to the receiver, to indicate that there is no
	 * more data.
	 * 
	 * This method should not return until it is sure that
	 * the empty packet has been delivered correctly.  It 
	 * catches any EOFException (which signals the receiver
	 * has closed the connection) and close its own connection.
	 */
	void close() throws IOException, ClassNotFoundException
	{
		DataPacket p = new DataPacket(null, 0, seqNumber);
                timer = new Timer();
                timer.schedule(new PacketTimer(p), 0, 800);
		try {
                    while(true){
                        AckPacket ack = udt.recv();
                        if(ack.isCorrupted || (ack.ack == 1 && seqNumber == 0) ||
                                              (ack.ack == 0 && seqNumber == 1)){
                            //Make sure closing packet is received properly
                        } else{
                            //Send last packet to tell receiver I have received that you are closing
                            timer.cancel();
                            break;
                        }
                    }
		} catch (EOFException e) {
        }
		finally {
                        timer.cancel();
                        System.out.println("Receiver has closed the connection");
			udt.close();
		}
	}

        private class PacketTimer extends TimerTask{
            DataPacket p;

            PacketTimer(DataPacket packet){
                p = packet;
            }

            public void run(){
                try{
                    udt.send(p);
                } catch(IOException ex){
                } finally{
                }
            }
        }
}
