package tcps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Dora Di
 *
 * 1. Create a server socket and bind it to a specific port number
 * 2. Listen for a connection from the client and accept it. This results in a client socket, created on the server, for the connection.
 * 3. Read data from the client via an InputStream obtained from the client socket
 * 4. Send data to the client via the client socket’s OutputStream.
 * 5. Close the connection with the client.
 *
 * The steps 3 and 4 can be repeated many times depending on the protocol agreed between the server and the client.
 */

public class TCPS {
    public static final int PORT = 6666;
    public static ServerSocket serverSocket = null; // Server gets found
    public static Socket openSocket = null;         // Server communicates with the client

    public static Socket configureServer() throws UnknownHostException, IOException {
        // get server's own IP address
        String serverIP = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Server ip: " + serverIP);

        // create a socket at the predefined port
        serverSocket = new ServerSocket(PORT);

        // Start listening and accepting requests on the serverSocket port, blocked until a request arrives
        openSocket = serverSocket.accept();
        System.out.println("Server accepts requests at: " + openSocket);

        return openSocket;
    }

    public static void main(String[] args) throws IOException
    {
        openSocket = configureServer();
        while(true)
        {
            try
            {
                new ClientHandler(serverSocket.accept()).start();
            }
            catch(Exception e)
            {
                System.out.println(" Connection fails: " + e);
            }
            finally
            {
                openSocket.close();
                System.out.println("Connection to client closed");

                serverSocket.close();
                System.out.println("Server port closed");
            }
        }
    }

    public class ClientHandler extends Thread
    {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }

        @Override
        public void run()
        {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    }
                    out.println(inputLine);
                }

                in.close();
                out.close();
                clientSocket.close();
            }
            catch (IOException ex) {
                // Handle exception
            }

        }
    }


}