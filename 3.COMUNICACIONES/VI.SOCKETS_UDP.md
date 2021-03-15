# SOCKETS UDP

<hr>
[VOLVER AL ÍNDICE](I.INDICE.md)
---

### PROTOCOLO UDP

El protocolo **UDP**, al igual que el TCP, opera en el nivel de transporte dentro de la capa de protocolos IP, y es otro de los componentes fundamentales de la pila IP. 

En este nivel, la información se separa en paquetes (o  mensajes) se denominan "datagramas" (datagrams en inglés).

Las funcionalidades que ofrece al nivel de aplicación lo asemeja con TCP, pero su funcionamiento es diferente en algunos aspectos:

* En primer lugar, se trata de un protocolo no orientado a conexion, por lo que orfece mas rapidez que TCP, ya que no es
  necesario realizar el "*handshake*" para establecer la conexión.

* Su fiabilidad es menor, no está garantizado que lleguen todos los mensajes.

* El orden de llegada de los paquetes en el mismo orden
  que fueron enviados, puede no respetarse.

* La longitud máxima de cada paquete es de 64 KB como maximo.

![DATAGRAMA UDP](IMAGENES/IMG_03_09.png)

Al ser mucho mas ligero y eficiente que TCP, es muy extendido su uso en la programación distribuida.

También usado de manera frecuente en la transmisión de vídeo y voz a través de una red. Esto es porque no hay tiempo para enviar de nuevo paquetes perdidos cuando se está escuchando a alguien o viendo un vídeo en tiempo real.

Si usamos sockets UDP, al no crear una conexión (como es el caso de socket TCP), los mensajes o paquetes se gestionan de forma individual y no se garantiza la recepción o envío del mensaje como si ocurre en TCP.

Las dos clases de uso común son:

### Clase **DatagramPacket**

Implementa los objetos (paquetes) que se enviarán y  recibirán en un DatagramSocket. 

El constructor de esta clase puede recibir las siguientes configuraciones de inicio:

| MÉTDODO                                                      | DESCRIPCIÓN                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| **DatagramPacket(byte[] buf, int length)**                   | Constructor para datagramas recibidos. Se especifica la cadena de bytes en la que alojar el mensaje (bu!) y la longitud (length) de la misma. |
| **DatagramPacket(byte[] buf, int offset, int length)**       | Constructor para datagramas recibidos. Se especifica la cadena de bytes en la que alojar el mensaje, la longitud de la misma y el offset (offset) dentro de la cadena. |
| **DatagramPacket(byte[] buf, int length, lnetAddress addrss, int port)** | Constructor para el envío de datagramas. Se especifica la cadena de bytes a enviar (**buf**), la longitud (**length**), el número de puerto de destino (**port**) y el el *host* especificado en la dirección **addrss**. |
| **DatagramPacket(byte[] buf, int offset, int length, lnetAddress address, int port)** | Constructor para el envío de datagramas. Igual que el anterior pero se especifica un *offset* dentro de la cadena de bytes. |

Los métodos de esta clase son:

|MÉTDODO       |      DESCRIPCIÓN           |
|--------------|----------------------------|
| **InetAddress getAddress ()** | Devuelve la dirección IP del hostal cual se le envía el datagrama o del que el datagrama se recibió. |
| **byte[] getData()** | Devuelve el mensaje contenido en el datagrama tanto recibido como enviado. |
| **int getLength ()** | Devuelve la longitud de los datos a enviar o a recibir. |
| **int getPort())** | evuelve el número de puerto de la máquina remota a la que se le va a enviar el datagrama o del que se recibió el datagrama. |
| **setAddress (InetAddress addr)** | Establece la dirección IP de la máquina a la que se envía el datagrama. |
| **setData (byte [buf])** | Establece el búfer de datos para este paquete. |
| **setLength (int length)**| Ajusta la longitud de este paquete. |
| **setPort (int Port)** | Establece el número de puerto del host remoto al que este datagrama se envía. |

### Clase **DatagramSocket**

Implementa una comunicación UDP. Envía y recibe objeto de tipo *DatagramPacket*.

El constructor se puede inicializar de las siguientes formas:

|  CONSTRUCTOR  |        DESCRIPCIÓN                |
|---------------|-----------------------------------|
| **DatagramSocket()** | Construye un socket para datagramas, el sistema elige un puerto de los que están libres. |
| **DatagramSocket (int port)** |
| **DatagramSocket (int port, InetAddress ip)** | Permite especificar, además del puerto, la dirección local a la que se va a asociar el socket. |

Los métodos más usados son:

|    MÉTDODO    |        DESCRIPCIÓN                |
|---------------|-----------------------------------|
| **receive (DatagramPacket paquete)**| Recibe un DatagramPacket del socket, y llena paquete con los datos que recibe (mensaje, longitud y origen). Puede lanzar la excepción *IOException*. |
| **send(DatagramPacket paquete)** | Envía un DatagramPacket a través del socket. El argumento paquete contiene el mensaje y su destino. Puede lanzar la excepción *IOException*. |
| **close()**| Se encarga de cerrar el socket. |
| **int getLocalPort()** | Devuelve el número de puerto en el host local al que está enlazado el socket, -1 si el socket está cerrado y O si no está enlazado a ningún puerto. |
| **int getPort()**| Devuelve el número de puerto al que está conectado el socket, -1 si no está conectado. |
| **connect(lnetAddress address, int port)** | Conecta el socket a un puerto remoto y una dirección IP concretos, el socket solo podrá enviar y recibir mensajes desde esa dirección. |
| **setSoTimeout(int timeout)** | Permite establecer un tiempo de espera límite. Entonces el método **receive()** se bloquea durante el tiempo fijado. Si no se reciben datos en el tiempo fijado se lanza la excepción *InterruptedIOException*. |

El funcionamiento de las aplicaciones usando UDP se puede mostrar gráficamente usando la siguiente imagen:

![FUNCIONAMIENTO UDP](IMAGENES/IMG_03_10.png)

Y su explicación sería de la siguiente manera:

#### UDPServidor.java:

1. Creamos un **DatagramSocket** asociado a un puerto local para escuchar peticiones de clientesy queda a la espera de recibir peticiones.
2. Creamos un **DatagramPacket** donde guardar los datos a enviar.
3. Mediante el método **receive()**, el servidor recibe las peticiones  del socket. Como en el contenido del datagrama va incluido el puerto y la IP del cliente emisor, el servidor puede conocer la dirección del emisor del datagrama.
4. El servidor construye su **DatagramPacket** con el mensaje a enviar y los datos obtenidos del cliente.
5. Utilizamos el método **send()** para enviar la respuesta al cliente.
6. El servidor permanece a la espera de recibir más peticiones.

El código sería el siguiente:

```java
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
```

[UDPServidor.java](CODIGO/UDPServidor.java)

#### UDPCliente.java:

1. El cliente crea otro **DatagramSocket** para comunicarse con el servidor, del que necesita conocer su IP y el puerto por el que escucha. 
2. Creamos un **DatagramPacket** donden guardar los datos a enviar.
3. Con el método **send()**, enviamos la petición en forma de datagrama.
4. Creamos un segundo **DatagramPacket** donde guardar los datos a enviar. 
6. El cliente recibe la respuesta del servidor usando el método **receive()** del socket. 

Y el código es:

```java
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
```

[UDPCliente.java](CODIGO/UDPCliente.java)


Para demostrar el funcionamiento de esta metodología vamos a ver un ejemplo básico de comunicación cliente-servidor.

![FUNCIONAMIENTO UDP](IMAGENES/IMG_03_11.png)
