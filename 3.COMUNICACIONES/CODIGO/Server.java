import java.net.*;
import java.io.*;

/**
 *
 * @author profesor
 */

public class Server {
    // initialize socket and input stream

    private Socket socket = null;
    private ServerSocket miSocket = null;
    private DataInputStream in = null;

    // constructor with port
    public Server(int port) {
        // starts server and waits for a connection
        try {
            miSocket = new ServerSocket(port);
            System.out.println("Servidor iniciado");

            System.out.println("Esperando al cliente ...");

            socket = miSocket.accept();
            System.out.println("Cliente aceptado");

        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public void salir() {
        // starts server and waits for a connection
        try {
            System.out.println("Cerrando connexion");
            // close connection
            socket.close();
            in.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public void leerDeCliente() {
        // Leemos del cliente 
        try {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String line = "";

        // reads message from client until "Over" is sent 
        while (!line.equals("FIN")) {
            try {
                line = in.readUTF();
                System.out.println(line);

            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Server server = new Server(5000);
        server.leerDeCliente();
        server.salir();
    }
}
