package dk.dd.udpc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;
import java.awt.image.*;
import java.sql.SQLOutput;
import javax.imageio.*;

/**
 *
 * @author Dora Di
 */

public class UDPClient
{
    // Client needs to know server identification, <IP:port>
    private static final int serverPort = 7777;

    // buffers for the messages
    private static byte[] dataIn = new byte[1024];
    private static byte[] dataOut = new byte[1024];

    // In UDP messages are encapsulated in packages and sent over sockets
    private static DatagramPacket requestPacket;
    private static DatagramPacket responsePacket;
    private static DatagramSocket clientSocket;

    public static void main(String[] args) throws IOException
    {
        clientSocket = new DatagramSocket();
        InetAddress serverIP = InetAddress.getByName(args[0]);
        System.out.println(serverIP);


        dataOut = imageToBinary("/Users/malenehansen/IdeaProjects/SI_UDP/src/123.jpg");


        sendImage(serverIP);
        receiveResponse();

        clientSocket.close();
    }

    public static void sendImage(InetAddress serverIP) throws IOException
    {

        requestPacket = new DatagramPacket(dataOut, dataOut.length, serverIP, serverPort);
        clientSocket.send(requestPacket);
    }

    public static void receiveResponse() throws IOException
    {
        //clientSocket = new DatagramSocket();
        responsePacket = new DatagramPacket(dataIn, dataIn.length);
        clientSocket.receive(responsePacket);
        dataIn = responsePacket.getData();
        System.out.println("Response from Server: " + dataIn.toString());
    }

    public static byte[] imageToBinary(String fileName) throws IOException {

        File file = new File(fileName);

        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);
        System.out.println("Data sent: " + bytesArray.toString());


        fis.close();

        return bytesArray;


    }

}
