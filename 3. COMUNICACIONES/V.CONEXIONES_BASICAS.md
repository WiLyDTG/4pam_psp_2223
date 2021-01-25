Paquete java.net
Incluye las clases para desarrollar aplicaciones de red
Sockets TPC
Clase ServerSocket : Implementa el socket en el lado del servidor
Escucha peticiones de conexión de clientes en un puerto
Clase Socket: Implementa el socket en el lado del cliente.
mantiene la comunicación entre cliente y servidor

![PROCESO DE COMUNICACIÓN CON SOCKETS EN JAVA](IMAGENES/IMG_03_05.png)

Sockets TPC → Servidor:
Creación del objeto ServerSocket.
Se puede especificar el puerto en el constructor
Asignación de dirección y puerto (.bind).
Escucha en el puerto
Aceptación de conexiones (.accept). 
Esta operación implica la creación de un nuevo socket, que se usa para comunicarse con el cliente que se ha conectado.
Obtener del socket establecido Input y Output Streams para poder comunicarse con el cliente (.getInputStream .getOutputStream)
Envío y recepción de mensajes.(read y write)
Cierre de la conexión (close).

Sockets TPC → Servidor:
```java
ServerSocket server = null;
Socket service = null;
DataInputStream sIn = null;
DataOutputStream sOut = null;
// try-with-resource: Obj dentro se cierran automáticamente al 
//finalizar try, sino habría que hacerlo
try (ServerSocket server = new ServerSocket(portNumber);
    Socket service = server.accept();
    DataInputStream sIn = new DataInputStream(service.getInputStream());
    DataOutputStream sOut = new DataOutputStream(service.getOutputStream());
    )
{
... // Comunicación con el cliente.
} catch (IOException e) {
System.out.println(e);
}
````

Sockets TCP → Cliente:
Creación del objeto Socket.
Se puede especificar dirección y puerto en el constructor
Conexión del socket (connect).
Obtener del socket establecido Input y Output Streams para poder comunicarse con el cliente (.getInputStream .getOutputStream)
Envío y recepción de mensajes.
Cierre de la conexión (close).

Sockets TCP → Cliente:
Socket mySocket = null;
DataInputStream sIn = null;
DataOutputStream sOut = null;
try (
Socket mySocket = new Socket("address", portNumber);
DataInputStream sIn = new DataInputStream(mySocket.getInputStream());
DataOutputStream sOut = new DataOutputStream(mySocket.getOutputStream());
)
{
... // Comunicación Cliete - Servidor
} catch (IOException e) {
System.out.println(e);
}

Sockets TCP → Aplicaciones Cliente-Servidor 
utilizaremos dos proyectos separados
Uno para el Cliente
Otro para el Servidor
Podemos tratar los dos lados de la comunicación de forma indempendiente.
Para ejecutar Aplicaciones Cliente – Servidor
Primero iniciamos el servidor 
Cuando esté escuchando por el puerto y esperando conexiones → Ejecutaremos el Cliente
Ejemplo: Ejemplo1 y 2: ClienteSaluda – ServidorSaluda
Evidentemente se pueden utilizar otros Stream para recibir y enviar información: BufferedReader o PrintStream.
Si la comunicación es asincrona → cliente puede enviar mensajes cuando quiera y/o hay múltiples clientes → no se puede utilizar try-with-resource

Métodos útiles
ServerSocket(int port, int max)
max : n.º máximo de conexiones simultáneas
Socket(InetAddress address, int port)
Clase InetAddress → representa una dirección IP
Clase Socket
int getLocalPort() → puerto local de la conexión.
int getPort() → puerto remoto de la conexión.
InetAddress 	getInetAddress() → devuelve la dirección a la que esta conectado el socket
SocketAddress 	getRemoteSocketAddress()
InetAddress 	getLocalAddress() → devuelve la dirección local del socket
SocketAddress	getLocalSocketAddress()


Clase InetAddress
Representa una Dirección IP en Java
Tiene métodos para conectarse a un servidor DNS y resolver un nombre de host.
Me permite conectarme a un servidor por el nombre de dominio y automáticamente obtiene la IP
Para crear un objeto InetAddress usamos alguno de los métodos estáticos
InetAddress address = InetAddress.getByName("www.myserver.com");
También podemos hacer búsqueda inversa → encontrar el nombre de dominio a partir de la IP
InetAddress address = InetAddress.getByName("201.114.121.65");
Métodos
getHostName() → devuelve el nombre de host
getHostAddress() → devuelve la IP en un String
Ejemplo3: DireccionIP

Clase InetSocketAddress
Implementa la clase abstracta SocketAddress
Representa la dirección de un Socket → dirección IP + puerto
InetSocketAddress(InetAddress addr, int port)
También puede ser : nombre de host + puerto
En ese caso intenta resolver la dirección IP
InetSocketAddress(String hostname, int port)
Métodos
InetAddress 	getAddress()
String 	getHostName()
int 	getPort()


Otros métodos de Socket y ServerSocket
void 	connect(SocketAddress endpoint)
Conecta el socket al servidor especificado
La clase ServerSocket no implementa este método
void 	bind(SocketAddress endpoint)
Enlaza el socket a la dirección + puerto determinado
En la clase ServerSocket, pone al servidor a escuchar en ese puerto
Estos métodos no se pueden utilizar con try-with-resource
Ejemplo4 ClienteSaluda - ServidorSaluda

Tarea 1: Servidor de ECO
Crea una aplicación cliente servidor utilizando Sockets en Java utilizando 2 poyectos
Proyecto EcoServidor:
El servidor escuchará en el puerto 6000
Cuando reciba una conexión, leerá constantemente mensajes del cliente, y se los devolverá convertidos a mayúsculas. Hola → HOLA
Proyecto EcoCliente
El cliente define un socket que se conecta al servidor (localhost si esta en la mima máquina)
Cuando se establece la conexión, se pide al usuario que escriba mensajes en la consola.
Los mensajes se envían al servidor y se espera por la respuesta de eco.
La comunicación finaliza cuando el servidor recibe la palabra: ciao

Socket UDP
No necesitamos establecer una conexión previa
Necesitaremos especificar en cada mensaje que se envíe las dirección IP y el puerto del receptor y del emisor.
Clase DatagramSocket
Implementa una comunicación UDP. 
Envía y recibe objeto de tipo DatagramPacket
Clase DatagramPacket
Implementa los objetos (paquetes) que se enviarán y  recibirán en un DatagramSocket

Socket UDP → Pasos para conectar
1. Crear un DatagramSocket especificando la IP local y el puerto.
En el cliente no es necesario especificar nada, se usará cualquier puerto disponible.
DatagramSocket socket = new DatagramSocket();
En el servidor: 
especificamos el puerto en el que escucha
DatagramSocket socket = new DatagramSocket(portNumber);
También podemos especificar IP y puerto
InetAddress address = ...
DatagramSocket socket = new DatagramSocket(portNumber, address);


Socket UDP → Pasos para conectar
2. Cuando se reciba un paquete (DatagramPacket) podremos enviar mensajes a la dirección y puerto almacenado en el paquete utilizando otro DatagramPacket.
Creamos un objeto DatagramPacket especificando 
El mensaje a enviar como byte array
Su longitud
IP y puerto de destino
Para enviar un objeto DatagramPacket utilizamos el método .send() del objeto DatagramSocket
String text = "Hello";
byte[] message = text.getBytes();
DatagramPacket packetS = new DatagramPacket(message, message.length, InetAddress.getLocalHost(), 2000);
socket.send(packetS);

Socket UDP → Pasos para conectar
Para recibir un objeto DatagramPacket primero creamos un objeto vacío y luego  utilizamos el método .receive() de DatagramSocket para “llenarlo” de datos
byte[] buffer = new byte[1024];
DatagramPacket packetR = new DatagramPacket(buffer, buffer.length);
socket.receive(packetR);
También se puede especificar un timeout en el socket (en milisegundos), para que si el método receive espera más del tiempo especificado en obtener una respuesta → lanza una  excepción InterruptedIOException y puede continuar.
socket.setSoTimeout(2000); // 2 seconds
Try {...
socket.receive(packetR);
} catch (InterruptedIOException e) { }
Al finalizar la comunicación debemos cerrar el socket (DatagramSocket).
Se puede utilizar en un try-with-resource y se cierra automáticamente
Ejemplo 5: ClienteUDPSaluda - ServidorUDPSaluda

Tarea 2: Diccionario
Crea un proyecto llamado DiccionarioCliente que envíe al servidor una palabra escrita por el usuario.
Configura un timeout en el cliente de 5 segundos. 
Si el timeout expira el cliente debe sacar un mensaje para el susuario de “Traducción no encontrada”
Crea un proyecto llamado DiccionarioServidor que escuche en el puerto 6000. Tendrá una colección (HashMap) con palabras en ingles y su traducción al castellano, o lo que quieras.
El servidor recibe la palabra enviada por el cliente y devolverá la traducción de la misma. 
Si no encuentra la palabra en la colección, el servidor no devolverá nada.


Conexiones a Múltiples Clientes
Un servidor que solo acepta conexiones de un cliente no es útil → debe estar preparado para trabajar con múltiples clientes.
Necesitamos crear en el servidor un hilo para tratar con cada cliente.
En aplicaciones TCP (Orientadas a la conexión) el Servidor (ServerSocket) es un bucle que crea un Socket para cada cliente y un hilo (thread) para tratar con él.
En el hilo implementamos la comunicación con el cliente.

Conexiones a Múltiples Clientes
Ejemplo de la Clase Servidor
try (ServerSocket server = new ServerSocket(PORT)) {
System.out.println("Listening...");
while (true) {
Socket service = server.accept();
System.out.println("Conexión establecida");
// Crea el hilo pasandole el socket
ServerThread st = new ServerThread(service);
st.start();
}
} catch (IOException e) { System.out.println(e); }


Conexiones a Múltiples Clientes
Ejemplo de la Clase Servicio (Thread)
public class ServerThread extends Thread {
Socket service;
public ServerThread(Socket s) {
service = s; }
@Override
public void run() {
DataInputStream socketIn = null;
DataOutputStream socketOut = null;
try{
socketIn = new DataInputStream(service.getInputStream());
socketOut = new DataOutputStream(service.getOutputStream());
// ... comunicación con el cliente.
} catch (IOException e) {System.out.println(e);
} finally {// Cerrar todos los flujos de datos y el socket}


Conexiones a Múltiples Clientes
Si trabajamos con servidores UDP, no necesitamos usar hilos, ya que cada paquete se envía y recibe sin relación con los otros y no establecemos conexiones distintas con cada cliente. 
Solo necesitamos implementar un bucle que reciba datagramas, y responda.

Conexiones a Múltiples Clientes
Ejemplo Servidor UDP
while (true) {
byte[] sent = new byte[1024];
byte[] received = new byte[1024];
DatagramPacket datagramReceived = new DatagramPacket(received, received.length);
mySocket.receive(datagramReceived);
...
InetAddress remoteIP = datagramReceived.getAddress();
int remotePort = datagramReceived.getPort();
DatagramPacket datagramSent = new DatagramPacket (sent, sent.length, remoteIP, remotePort);
mySocket.send(datagramSent);
}


Tarea 3: Servidor de Eco Mejorado
Mejora la Tarea 1 con estos dos cambios:
Haz que la conexión cliente servidor sea independiente de las máquinas en las que están ejecutándose.
No utilices “localhost” como IP del servidor, sino que averigüe la IP de la máquina
Pregunta al Usuario por consola la IP del servidor o nombre de host al que conectarse.
Permite más de un cliente conectado al servidor.
El servidor debe hacer eco de los mensajes de diferentes clientes, mandando a cada uno sus propias respuestas.
Llama a los proyectos EcoServidorMejorado y EcoClienteMejorado


Tarea 4: Diccionario Mejorado
Mejora la Tarea 2 con estos cambios:
Haz que la conexión cliente servidor sea independiente de las máquinas en las que están ejecutándose.
No utilices “localhost” como IP del servidor, sino que averigüe la IP de la máquina
Pregunta al Usuario por consola la IP del servidor o nombre de host al que conectarse.
Permite más de un cliente conectado al servidor.
El servidor debe hacer buscar en el diccionario las palabras de cualquier cliente.
El cliente pedirá al usuario palabras hasta que escriba: stop
Llama a los proyectos DiccionarioClienteMejorado y DiccionarioServidorMejorado

