package tcps;

import java.io.IOException;
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

    public void start() throws UnknownHostException, IOException {
        // get server's own IP address
        String serverIP = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Server ip: " + serverIP);

        // create a socket at the predefined port
        serverSocket = new ServerSocket(PORT);

        while(true)
        {
            // Start listening and accepting requests on the serverSocket port, blocked until a request arrives
            openSocket = serverSocket.accept();
            new Client(openSocket).start();
            System.out.println("Server accepts requests at: " + openSocket);
        }

    }

    public class Client extends Thread
    {
        private Socket openSocket = null;

        public Client(Socket socket)
        {
            openSocket = socket;
        }

        @Override
        public void run()
        {
            try {
                String request, response;

                // two I/O streams attached to the server socket:
                Scanner in;         // Scanner is the incoming stream (requests from a client)
                PrintWriter out;    // PrintWriter is the outcoming stream (the response of the server)
                in = new Scanner(openSocket.getInputStream());
                out = new PrintWriter(openSocket.getOutputStream(),true);
                // Parameter true ensures automatic flushing of the output buffer

                // Server keeps listening for request and reading data from the Client,
                // until the Client sends "stop" requests
                while(in.hasNextLine())
                {
                    request = in.nextLine();

                    if(request.equals("stop"))
                    {
                        out.println("Good bye, client!");
                        System.out.println("Log: " + request + " client!");
                        break;
                    }
                    else
                    {
                        // server responses
                        response = new StringBuilder(request).reverse().toString();
                        out.println(response);
                        // Log response on the server's console, too
                        System.out.println("Log: " + response);
                    }
                }
            }
            catch (IOException ex) {
                // Handle exception
            }

        }
    }
    
    public static void main(String[] args) throws IOException
    {
        TCPS server = new TCPS();
        server.start();
    }



}