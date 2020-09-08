package dk.dd.udpc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

    // buffers for the messages
    private static byte[] dataIn = new byte[256];
    
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
        BufferedImage fileData = ImageIO.read(new File(System.getProperty("user.dir") + "/" + "test.jpg"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(fileData, "jpg", outputStream);
        outputStream.flush();
        byte[] fileDataBuffer = outputStream.toByteArray();

        while(fileDataBuffer != null)
        {
            sendRequest(serverIP, fileDataBuffer);
            receiveResponse();
        }
        clientSocket.close();
    }
    
    public static void sendRequest(InetAddress serverIP, byte[] fileDataBuffer) throws IOException
    {
        requestPacket = new DatagramPacket(fileDataBuffer, fileDataBuffer.length, serverIP, serverPort);
        clientSocket.send(requestPacket);
        System.out.println("Data sent to server!");
    }
    
    public static void receiveResponse() throws IOException
    {
        //clientSocket = new DatagramSocket();
        responsePacket = new DatagramPacket(dataIn, dataIn.length);
        clientSocket.receive(responsePacket);
        System.out.println("Response from Server: Thanks for the data");
    }    
}
