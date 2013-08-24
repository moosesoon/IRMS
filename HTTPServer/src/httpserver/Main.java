package httpserver;
import java.io.*;
import java.net.*;

public class Main {
    Socket s;

    public Main(){}

    public static void main(String[] args) {
        Main client = new Main();
        client.doMain(args);
    }

    public void doMain(String[] args){
                try{
            //Make sure a proper argument is passed in
            if(args.length == 0 || args.length > 1){
                try{
                    int CHECK_INT = Integer.parseInt(args[0]);
                } catch(NumberFormatException nfe){
                    System.err.println("An int is not found");
                    System.exit(-1);
                }
                System.out.println("Invalid argument: Please enter a port number");
                System.exit(0);
            }

            //Create server socket
            ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));

            while(true){
                System.out.println("HTTP Server: Waiting for connection....");

                //Establish 3 way hs
                s = ss.accept();

                System.out.println("HTTP Server: CONNECTED! with " + s.getInetAddress().getHostName());

                //Initialise Input / Output
                InputStream is = s.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                DataOutputStream dout = new DataOutputStream(s.getOutputStream());

                //Read in input from the client
                String in = br.readLine();
                if(in.compareTo("") != 0){
                    //split the line
                    String[] temp = in.split(" ");
                    //Retrieve the file from server
                    String filename = "C:\\Users\\Stanley\\Downloads" + temp[1];
                    File f = new File(filename);
                    byte[] fileByte = new byte[(int)f.length()];

                    if(temp[0].equals("GET") || temp[0].equals("HEAD")){ //Get request
                        doGet(dout,temp,f,fileByte);
                    } else if(temp[0].equals("POST")){
                        doPost(br,dout,temp,f,fileByte);
                    }
                } else{
                    break;
                }
                //Close the socket
                s.close();
                System.out.println("HTTP Server: Conection Terminated");
            } //End while

            System.out.println("HTTP Server: Server Terminated");
        } catch(Exception ex){
            System.err.println("An unexpected error occured");
            ex.printStackTrace();
        }
    }

    public void doScript(){
        try{
            String method="";
        Process pro = Runtime.getRuntime().exec("/usr/bin/env REQUEST_METHOD=" + method +
                                                             "QUERY_STRING=" + query +
                                                             "CONTENT_TYPE=" + ctype +
                                                             "CONTENT_LENGTH=" + clength +
                                                             "/usr/bin/perl /home/o/ooiwt/a1/todo.pl");

        pro.wait();

        
        if(pro.exitValue() == 0){
            String temp;
            InputStream is = pro.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                DataOutputStream dout = new DataOutputStream(s.getOutputStream());

                dout.writeBytes("HTTP RESPONSE HEADER");
                while((temp = br.readLine()) != null){
                    dout.writeBytes
                }
            //Create input output stream
        //input stream read from process
        //output stream write to socekt
        //write back the header http 200
        //write back the buffered reader input stream to output stream
        }
        } catch(Exception ex){

        }
    }
    public void doPost(BufferedReader br, DataOutputStream dout, String[] temp,
                             File f, byte[] fileByte){
        System.out.println("HTTP Server: POST Request Detected");

        try{
            if(f.canRead()){ //Double check if the file is valid
                FileInputStream fis = new FileInputStream(f);
                fis.read(fileByte);
                System.out.println("HTTP Server: Page " + temp[1] + " retrieved");

                //Retrieved the POST message
                String postMessage = br.readLine(), QUERY_STRING = "";
                String[] temp2;
                int contentLength = 0;

                //Get the post query string
                while(postMessage.length() != 0){
                    System.out.println(postMessage);
                    postMessage = br.readLine();
                    temp2 = postMessage.split(" ");
                    if(temp2[0].equals("Content-Length:")){ //Read the content length, how many bytes to read
                        contentLength = (int) Integer.parseInt(temp2[1]);
                    }
                }

                for(int i = 0; i < contentLength; i++){
                    QUERY_STRING += (char) br.read(); //Read byte by byte of character
                }

                System.out.println("HTTP Server: POST Message is " + postMessage); //Server debug
            }
            //Close the output stream connection
            dout.close();
        } catch(IOException ioe){
            System.err.println("An Input / Output Error has occured");
            ioe.printStackTrace();
        }
    }

    //HEAD is the same as GET. Return only header if it is head
    public static void doGet(DataOutputStream dout, String[] temp,
                             File f, byte[] fileByte){
        System.out.println("HTTP Server: GET Request Detected");
        try{
            if(f.canRead()){ //Double check if the file is valid
                FileInputStream fis = new FileInputStream(f);
                fis.read(fileByte);
                System.out.println("HTTP Server: Page " + temp[1] + " retrieved");

                //Requested file is .pl
                if(temp[1].endsWith(".pl")){
                    //If URL has query string
                    String[] temp2 = temp[1].split("?");
                    String QUERY_STRING;
                    if(temp2.length > 1){ //There is a query string
                        QUERY_STRING = temp2[1];
                        if(QUERY_STRING.length() != 0){ //Query string has parameter
                            doScript(QUERY_STRING);
                        }
                    } //There are no query string
                } else { //Requested file is other than a script file

                }

                //HTTP Succeed Header
                dout.writeBytes("HTTP/1.0 200 OK\n\r");
                dout.writeBytes("Host: localhost\n\r");
                dout.writeBytes("Content-Type=text/html");
                dout.writeBytes("Content-Length=");
                dout.writeBytes("Connection: close\n\r");
                dout.writeBytes("\n\r");

                //Send the file only if its a GET request
                if(temp[0].equals("GET")){
                    dout.write(fileByte);
                    dout.flush();
                }
            } else { //Unable to read, file not found maybe
                dout.writeBytes("HTTP/1.0 404 Not Found\n\r");
                dout.writeBytes("Host: localhost\n\r");
                dout.writeBytes("Connection: close\n\r");
                dout.writeBytes("\n\r");
            }

            //Close the output stream connection
            dout.close();
        } catch(IOException ioe){
            System.err.println("An Input / Output Error has occured");
            ioe.printStackTrace();
        }
    }

    public static void doScript(String query){
    }
}