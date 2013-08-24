import java.io.*;
import java.net.*;

public class WebServer {
    
    String CONTENT_TYPE, REQUEST_METHOD, QUERY_STRING, FILE_NAME = "/home/j/jinshen/a1";
    int CONTENT_LENGTH;
    File REQUESTED_FILE;

    public WebServer(){}

    public static void main(String[] args) {
        WebServer client = new WebServer();
        client.doMain(args);
    }

    public void doMain(String[] args){
        int check_int = 0;

        //Validate the argument for a proper socket number
        if(args.length == 0){
            System.out.println("Invalid argument: Please enter a port number");
            System.exit(0);
        }

        try{
            check_int = Integer.parseInt(args[0]);
        } catch(NumberFormatException nfe){
            System.err.println("An int is not found");
            System.exit(-1);
        }
        //End of validation
        
        try{
            Socket SOCKET;
            ServerSocket SERVER_SOCKET = new ServerSocket(check_int);

            while(true){
                System.out.println("HTTP Server: Waiting for connection....");

                //Establish 3 way hs
                SOCKET = SERVER_SOCKET.accept();
                System.out.println("HTTP Server: CONNECTED! with " + SOCKET.getInetAddress().getHostName());
                //End 3 way hs

                //Initialise Input / Output
                InputStream is = SOCKET.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                DataOutputStream OUT_TO_SOCKET = new DataOutputStream(SOCKET.getOutputStream());

                //Read in input from the client
                String in = br.readLine();

                //Make sure there is a header
                if(in != null){
                    //split the line
                    String[] temp = in.split(" ");
                    //Check to see what kind of file format it is
                    if(temp[1].endsWith("pl")){ //Script file
                        CONTENT_TYPE = "application/x-www-form-urlencoded";
                    } else if(temp[1].endsWith("html")){ //hypertext file
                        CONTENT_TYPE = "text/html";
                    } else if(temp[1].endsWith("css")){
                        CONTENT_TYPE = "text/css";
                    } else if(temp[1].endsWith("gif") || temp[1].endsWith("png") ||
                              temp[1].endsWith("ICO")){ //image file
                        CONTENT_TYPE = "image/" + temp[1].substring(temp[1].length()-3);
                    } else if(temp[1].endsWith("jpg")){
                        CONTENT_TYPE = "image/jpeg";
                    }
                    //Retrieve the file from server
                    temp[1] = getQueryString(temp[1]); //call getQueryString, which returns a QueryString if there are any
                    String filename = FILE_NAME + temp[1];
                    REQUESTED_FILE = new File(filename);
                    CONTENT_LENGTH = (int) REQUESTED_FILE.length();

                    //Main program
                    if(temp[0].equals("GET")){
                        REQUEST_METHOD = "GET";
                        if(QUERY_STRING.equals("") && !temp[1].endsWith("pl")){ //No query string
                            doGet(OUT_TO_SOCKET);
                        } else {
                            CONTENT_TYPE = "";
                            CONTENT_LENGTH = 0;
                            doScript("", temp[1], OUT_TO_SOCKET);
                        }
                        //Checks if there is a query string. Returns the url
                    } else if(temp[0].equals("POST")){ //call retrieveQuery, which returns the parameter
                        REQUEST_METHOD = "POST";
                        String parameter = doPost(br);
                        doScript(parameter, temp[1], OUT_TO_SOCKET);
                        //Do Script
                    } else if(temp[0].equals("HEAD")){
                        REQUEST_METHOD = "HEAD";
                        //Do HEAD
                    }
                } else{
                }
                //Close the socket
                SOCKET.close();
                System.out.println("HTTP Server: Conection Terminated");
            } //End
        } catch(Exception ex){
            System.err.println("An Exception has occured");
            ex.printStackTrace();
        }
    } //End do main

    //Start getQuery
    public String getQueryString(String url){
        String[] temp;
        if(url.contains("?")){
            temp = url.split("\\?");
        } else{
            QUERY_STRING = "";
            return url;
        }
        if(temp.length > 1){ //There is a query string
            if(temp[1].length() > 1){ //The query string might be valid
                QUERY_STRING = temp[1];
            } else{
                QUERY_STRING = "";
            }
        } else{
            QUERY_STRING = "";
        }
        return temp[0];
    } //End getQuery

    //Start doGet
    public void doGet(DataOutputStream OUT_TO_SOCKET){
        try{
            if(REQUESTED_FILE.canRead()){
                FileInputStream fis = new FileInputStream(REQUESTED_FILE);
                byte[] fileBytes = new byte[CONTENT_LENGTH];
                fis.read(fileBytes);

                //HTTP Succeed Header
                OUT_TO_SOCKET.writeBytes("HTTP/1.0 200 OK\r\n");
                OUT_TO_SOCKET.writeBytes("Content-Type: " + CONTENT_TYPE + "\r\n");
                OUT_TO_SOCKET.writeBytes("\r\n");
                OUT_TO_SOCKET.write(fileBytes, 0, CONTENT_LENGTH);
                OUT_TO_SOCKET.flush();
            } else{
                OUT_TO_SOCKET.writeBytes("HTTP/1.0 404 Not Found\n\r");
                OUT_TO_SOCKET.writeBytes("Connection: close\n\r");
                OUT_TO_SOCKET.writeBytes("\r\n");
            }
        } catch(Exception ex){
            System.err.println("An Exception has occured");
            ex.printStackTrace();
        }
    } //End doGet

    //Start do post
    //This is to get the body of the post request
    public String doPost(BufferedReader br){
        String parameter = "";
        String[] temp;
        try{
            String postMessage = br.readLine();
            //Get the post query string
            while(postMessage.length() != 0){
                System.out.println(postMessage);
                postMessage = br.readLine();
                temp = postMessage.split(" ");
                if(temp[0].equals("Content-Length:")){ //Read the content length, how many bytes to read
                    CONTENT_LENGTH = (int) Integer.parseInt(temp[1]);
                } else if(temp[0].equals("Content-Type:")){
                    CONTENT_TYPE = temp[1];
                }
            }
            //Read the body
            for(int i = 0; i < CONTENT_LENGTH; i++){
                    parameter += (char) br.read(); //Read byte by byte of character
            }
        } catch(Exception ex){
            System.err.println("An Exception has occured");
            ex.printStackTrace();
        }
        return parameter;
    } //End do post

    //Start Doscript. Only do perl script
    public void doScript(String parameter, String filename, DataOutputStream OUT_TO_SOCKET){
        try{
            Process pro = Runtime.getRuntime().exec("/usr/bin/env REQUEST_METHOD=" + REQUEST_METHOD +
                                                             " QUERY_STRING=" + QUERY_STRING +
                                                             " CONTENT_TYPE=" + CONTENT_TYPE +
                                                             " CONTENT_LENGTH=" + CONTENT_LENGTH +
                                                             " /usr/bin/perl /home/j/jinshen/a1" + filename);
            //Set up input output streams for the process
            InputStream is = pro.getInputStream();
            BufferedReader processReader = new BufferedReader(new InputStreamReader(is));

            DataOutputStream processOut = new DataOutputStream(pro.getOutputStream());
            if(REQUEST_METHOD.equals("POST")){
                    
                processOut.writeBytes(parameter);
                processOut.flush();
            } else{}
            CONTENT_TYPE = "text/html";
            //Output the results back to host. Header first, followed by output stream from reader
            OUT_TO_SOCKET.writeBytes("HTTP/1.0 200 OK\r\n");
            OUT_TO_SOCKET.writeBytes("Content-Type: " + CONTENT_TYPE + "\r\n");
            OUT_TO_SOCKET.writeBytes("\r\n");

            String temp2 = processReader.readLine(); //Output the result to client
            while((temp2 = processReader.readLine()) != null){
                OUT_TO_SOCKET.writeBytes(temp2);
                OUT_TO_SOCKET.flush();
            }
            
            pro.destroy();
        } catch(Exception ex){
            System.err.println("An Exception has occured");
            ex.printStackTrace();
        }
    }//End Doscript
}
