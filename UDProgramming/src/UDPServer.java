/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author Stanley
 */
public class UDPServer {
    public static void main(String args[]) throws Exception{
        //Create serverside socket
        DatagramSocket ds = new DatagramSocket(9876);
        System.out.println("Server started");
        //Create byte array
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        
        while(true){
            //Create receive packet
            DatagramPacket recv = new DatagramPacket(receiveData, receiveData.length);
            ds.receive(recv);
            
            String sen = new String(recv.getData());
            System.out.println("Client sends: " + sen);
            
            //Modify string
            String modSen = sen.toUpperCase();
            sendData = modSen.getBytes();
            
            //Acquire ipaddress, port
            InetAddress ip = recv.getAddress();
            int port = recv.getPort();
            
            //Create send packet
            DatagramPacket send = new DatagramPacket(sendData, sendData.length, ip, port);
            ds.send(send);
            
        }
    }
}
