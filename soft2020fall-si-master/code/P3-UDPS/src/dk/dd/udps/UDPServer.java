package dk.dd.udps;

import java.io.IOException;
import java.net.*;

/**
 *
 * @author Dora
 */
public class UDPServer
{
    private static final int serverPort = 7777;
    
    // buffers for the messages
    private static byte[] dataIn = new byte[128];
    private static byte[] dataOut = new byte[128];
    
    // In UDP messages are encapsulated in packages and sent over sockets
    private static DatagramPacket requestPacket;    
    private static DatagramPacket responsePacket;     
    private static DatagramSocket serverSocket;  
    

    public static void main(String[] args) throws Exception
    {   
        byte[] fileDataIn, fileDataOut;
        try
        {
            String serverIP = InetAddress.getLocalHost().getHostAddress();
            // Opens socket for accepting requests
            serverSocket = new DatagramSocket(serverPort);
            while(true)
            {
               System.out.println("Server " + serverIP + " running ...");
                fileDataIn = receiveRequest();
               if (fileDataIn == null) break;
                // Some file data??
                fileDataOut = new byte[256];
               sendResponse(fileDataOut);
            } 
        }
        catch(Exception e)
        {
            System.out.println(" Connection fails: " + e); 
        }
        finally
        {       
            serverSocket.close();
            System.out.println("Server port closed");
        }
    }
    
    public static byte[] receiveRequest() throws IOException
    {
        requestPacket = new DatagramPacket(dataIn, dataIn.length);
        serverSocket.receive(requestPacket);
        System.out.println("Data received from client!");
        return requestPacket.getData();
    }
    
    //public static String processRequest(byte[] fileDataIn)
    //{


    //}
    
    public static void sendResponse(byte[] fileData) throws IOException
    {
        InetAddress clientIP;
        int clientPort;
    
        clientIP = requestPacket.getAddress();
        clientPort = requestPacket.getPort();
        System.out.println("Client port: " + clientPort);
        dataOut = fileData;
        responsePacket = new DatagramPacket(dataOut, dataOut.length, clientIP, clientPort);
        serverSocket.send(responsePacket);
        System.out.println("Data sent to Client!");
    }    
}
