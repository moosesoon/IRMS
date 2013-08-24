/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.*;
import java.io.*;
import java.util.*;
/**\
 *
 * @author Stanley
 */
public class UDPClient {
    public static void main(String args[]) throws Exception{
        Scanner scn = new Scanner(System.in);
        
        //Create UDP socket
        DatagramSocket ds = new DatagramSocket();
        
        //Specify ip address
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        
        //Create byte stream
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        
        System.out.println("Please enter a string: ");
        String sen = scn.nextLine();
        
        //Create packet to send
        DatagramPacket send = new DatagramPacket(sendData, sendData.length, ip, 9876);
        
        //Send packet
        ds.send(send);
        
        //Create packet to receive
        DatagramPacket recv = new DatagramPacket(receiveData, receiveData.length);
        
        //Receive Packet
        ds.receive(recv);
        
        //Parse packet into string
        String modSen = new String(recv.getData());
        
        //Print result
        System.out.println("Server sends: " + modSen);
        
        //Close socket
        ds.close();
    }
}
