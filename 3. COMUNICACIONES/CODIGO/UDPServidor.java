import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author profesor
 */

public class UDPServidor {

	public static void main(String[] args) {
		// Creamos el DatagramSocket escuchando en el puerto 6500 y la ip local
		try (DatagramSocket mySocket = new DatagramSocket(6500, InetAddress.getLocalHost())) {
			
			System.out.println("Esperando Datagrama ................");
			
			// Recibimos el mensaje de la misma manera que el cliente.
			byte[] buffer = new byte[1024];
			DatagramPacket packetCliente = new DatagramPacket(buffer, buffer.length);
			mySocket.receive(packetCliente);
			
			// Obtenemos el puerto para responder al cliente 
			int destPort = packetCliente.getPort();
			//Obtenemos la IP para responder al cliente
			InetAddress destAddr = packetCliente.getAddress();
			//Obtenemos numero de bytes recibidos
			int bytesRec = packetCliente.getLength();
			//obtengo String de los datos recibidos
			String paquete= new String(packetCliente.getData());
			
			
			//VISUALIZO INFORMACIÓN RECIBIDA 
			System.out.println("Número de Bytes recibidos: " + bytesRec);
			System.out.println("Contenido del Paquete : " + paquete.trim());
			System.out.println("Puerto origen del mensaje: " + destPort);
			System.out.println("IP de origen : " + destAddr.getHostAddress());
			
			// Preparamos la respuesta al cliente con los datos del packetCliente
				// Crear el mensaje de respuesta
			String saludo = "Soy el Servidor - Adios Cliente";
			byte[] message = saludo.getBytes();
				// Crear el DatagramPacket
			DatagramPacket packetServidor = new DatagramPacket(message, message.length, destAddr, destPort);
				// enviarlo por el socket
			mySocket.send(packetServidor);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
