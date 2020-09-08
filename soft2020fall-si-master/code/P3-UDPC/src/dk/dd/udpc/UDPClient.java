package dk.dd.udpc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 *
 * @author Dora Di
 */
public class UDPClient 
{
    private static final int serverPort = 7777;

    // Some file data??
    public static byte[] fileData = new byte[256];

    // buffers for the messages
    private static byte[] dataIn = new byte[256];
    private static byte[] dataOut = new byte[256];  
    
    // In UDP messages are encapsulated in packages and sent over sockets
    private static DatagramPacket requestPacket;    
    private static DatagramPacket responsePacket;  
    private static DatagramSocket clientSocket;
    
    public static void main(String[] args) throws IOException
    {
        // Enter server's IP address as a parameter from Run/Edit Configuration/Application/Program Arguments
        clientSocket = new DatagramSocket(); 
        InetAddress serverIP = InetAddress.getByName(args[0]);
        System.out.println(serverIP);
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Type message: ");

        while(fileData != null)
        {
            sendRequest(serverIP);            
            receiveResponse();
        }
        clientSocket.close(); 
    }
    
    public static void sendRequest(InetAddress serverIP) throws IOException
    {
        //clientSocket = new DatagramSocket();
        dataOut = fileData;
        requestPacket = new DatagramPacket(dataOut, dataOut.length, serverIP, serverPort);
        clientSocket.send(requestPacket);
        System.out.println("Data sent to server!");
    }
    
    public static void receiveResponse() throws IOException
    {
        //clientSocket = new DatagramSocket();
        responsePacket = new DatagramPacket(dataIn, dataIn.length);
        clientSocket.receive(responsePacket);
        String message = new String(responsePacket.getData(), 0, responsePacket.getLength());       
        System.out.println("Response from Server: Thanks for the data");
    }    
}
