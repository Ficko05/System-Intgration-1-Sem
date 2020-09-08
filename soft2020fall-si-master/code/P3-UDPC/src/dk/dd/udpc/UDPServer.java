package dk.dd.udpc;



        import java.io.IOException;
        import java.net.*;

/**
 *
 * @author Dora Di
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

        try
        {
            String serverIP = InetAddress.getLocalHost().getHostAddress();
            // Opens socket for accepting requests
            serverSocket = new DatagramSocket(serverPort);
            while(true)
            {
                System.out.println("Server " + serverIP + " running ...");

                dataIn = receiveRequest();
                System.out.println("Received data: " + dataIn.toString());
                sendResponse("/Users/malenehansen/IdeaProjects/SI_UDP/src/123.jpg");

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

        return requestPacket.getData();
    }

    public static String processRequest(String message)
    {
        return message.toUpperCase();
    }

    public static void sendResponse(String fileName) throws IOException
    {
        InetAddress clientIP;
        int clientPort;

        clientIP = requestPacket.getAddress();
        clientPort = requestPacket.getPort();
        System.out.println("Client port: " + clientPort);

        ByteImage img = new ByteImage();
        dataOut = img.imageToBytes(fileName);
        responsePacket = new DatagramPacket(dataOut, dataOut.length, clientIP, clientPort);
        serverSocket.send(responsePacket);
        System.out.println("Data sent back " + responsePacket.getData().toString());
    }
}