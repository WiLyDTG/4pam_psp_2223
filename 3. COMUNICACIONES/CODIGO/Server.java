/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplotcp;

/**
 *
 * @author profesor
 */
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    //initialize socket and input stream 

    private Socket socket = null;
    private ServerSocket miSocket = null;
    private DataInputStream in = null;
    // Create a Logger 
    Logger logger = null;

    // constructor with port 
    public Server(int port) {
        // starts server and waits for a connection 
        try {
            miSocket = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = miSocket.accept();
            System.out.println("Client accepted");

            // takes input from the client socket 
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            String line = "";

            // reads message from client until "Over" is sent 
            while (!line.equals("Over")) {
                try {

                    line = in.readUTF();
                    System.out.println(line);

                } catch (IOException i) {
                    System.out.println(i);
                }
            }

            System.out.println("Closing connection");

            // close connection 
            socket.close();
            in.close();
        } catch (IOException i) {
            logger.log(Level.SEVERE, i.getMessage(), i);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(5000);
    }
}
