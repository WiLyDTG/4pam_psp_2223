import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPCliente {

	public static void main(String[] args) {
		// El socket del cliente, utiliza la IP local y el puerto disponible que se le asigne
		try (DatagramSocket mySocket = new DatagramSocket()) {
			// Crear el paquete a enviar
			String text = "Soy el cliente - Hola Servidor";
				// array de bytes.
			byte[] message = text.getBytes();
				// creamos el paquete con el mensaje, la longitud, la IP y el puerto
				// La direccion del servidor la conocemos con getLocalHost(),
				// ya que esta en la misma máquina el que cliente.
			DatagramPacket packetCliente = new DatagramPacket(message, message.length, InetAddress.getLocalHost(), 6500);
				// enviamos el paquete por el socket.
			
			mySocket.send(packetCliente);
			
			
			// Recibimos la respuesta del servidor
				// creamos un array de bytes para recibir los datos
			byte[] buffer = new byte[1024];
				// creamos un DatagramPacket vacio con el buffer para guardar los datos recibidos
			DatagramPacket packetServidor = new DatagramPacket(buffer, buffer.length);
				// recibimos el paquete
			mySocket.receive(packetServidor);
			
			//Aunque ya sabemos que es local, obtenemos la IP del servidor 
			InetAddress destAddr = packetServidor.getAddress();
			// Usamos el método getData() para obtener el array de bytes
			// Creamos un objeto String para convertir el buffer de bytes en algo mostrable. 
			// el método trim() nos permite eliminar los elementos vacios del buffer. 
			System.out.println("Número de Bytes recibidos: " + packetServidor.getLength());
			System.out.println("Contenido del Paquete : " + new String(packetServidor.getData()).trim());
			System.out.println("Puerto origen del mensaje: " + packetServidor.getPort());
			System.out.println("IP de origen : " + destAddr.getHostAddress());
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
}
