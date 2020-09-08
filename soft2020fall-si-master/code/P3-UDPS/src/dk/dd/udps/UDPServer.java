package dk.dd.udps;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.*;

/**
 *
 * @author Dora
 */
public class UDPServer
{
    private static final int serverPort = 7770;
    
    // buffers for the messages
    private static byte[] dataIn = new byte[64768];
    private static byte[] dataOut = new byte[64768];
    
    // In UDP messages are encapsulated in packages and sent over sockets
    private static DatagramPacket requestPacket;    
    private static DatagramPacket responsePacket;     
    private static DatagramSocket serverSocket;  
    

    public static void main(String[] args) throws Exception
    {   
        String messageIn, messageOut;
        try
        {
            String serverIP = InetAddress.getLocalHost().getHostAddress();
            // Opens socket for accepting requests
            serverSocket = new DatagramSocket(serverPort);
            while(true)
            {
                DatagramPacket recv_packet = new DatagramPacket(dataIn, dataIn.length);


                serverSocket.receive(recv_packet);

                byte[] buff = recv_packet.getData();
                ByteArrayInputStream bis = new ByteArrayInputStream(buff);
                System.out.println(bis);
                BufferedImage bImage2 = ImageIO.read(bis);
                ImageIO.write(bImage2, "jpg", new File("output.jpg") );
                System.out.println("resived image is stored in " + System.getProperty("user.dir") + "/output.jpg");
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
    
    public static String receiveRequest() throws IOException
    {
          requestPacket = new DatagramPacket(dataIn, dataIn.length);
          serverSocket.receive(requestPacket);
          String message = new String(requestPacket.getData(), 0, requestPacket.getLength());
          System.out.println("Request: " + message);   
          return message;
    }
    
    public static String processRequest(String message)
    {
        return message.toUpperCase();
    }
    
    public static void sendResponse(String message) throws IOException
    {
        InetAddress clientIP;
        int clientPort;
    
        clientIP = requestPacket.getAddress();
        clientPort = requestPacket.getPort();
        System.out.println("Client port: " + clientPort);
        System.out.println("Response: " + message); 
        dataOut = message.getBytes();
        responsePacket = new DatagramPacket(dataOut, dataOut.length, clientIP, clientPort);
        serverSocket.send(responsePacket);
        System.out.println("Message sent back " + message);
    }    
}
