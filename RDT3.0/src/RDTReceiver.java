/**
 * RDTReceiver : Encapsulate a reliable data receiver that runs
 * over a unreliable channel that may drop packets (but is
 * corruption-free and delivers in order).
 *
 * Ooi Wei Tsang
 * CS2105, National University of Singapore
 * 1 March 2011
 */

import java.io.*;
import java.util.*;

/**
 * RDTReceiver receives a data packet from "below" and pass
 * the byte array contained it it to the "above".
 */
class RDTReceiver {
	UDTReceiver udt;
        byte[] empty = new byte[0];
        int waitFor = 0;

	RDTReceiver(int port) throws IOException
	{
		udt = new UDTReceiver(port);
	}

	/**
	 * recv() reads the next in-order, uncorrupted data packet 
	 * from the layer below and returns the byte array contains 
	 * the data.  It returns null if when there is no more data 
	 * to read (i.e., the transmission is complete).
	 */
	byte[] recv() throws IOException, ClassNotFoundException
	{
            
                DataPacket p = udt.recv();
                //Unpack the packet
                
            // send ACK
                AckPacket ack = new AckPacket(p.seq);
                udt.send(ack);
            // deliver data
                if (p.length > 0) {
                    if(!p.isCorrupted && p.seq == waitFor){
                        byte [] copy = new byte[p.length];
                        System.arraycopy(p.data, 0, copy, 0, p.length);
                        if(waitFor == 0)
                            waitFor = 1;
                        else
                            waitFor = 0;
                        return copy;
                    } else{
                        return empty; //Drop packet
                    }
                } else {
                    return null;
                }
	}

	void close() throws IOException
	{
		udt.close();
	}
}
