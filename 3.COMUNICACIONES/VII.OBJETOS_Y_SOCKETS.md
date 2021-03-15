# ENVÍO DE OBJETOS A TRAVÉS DE SOCKETS

<hr>
[VOLVER AL ÍNDICE](I.INDICE.md)
---

La versatilidad de los **stream** es inmensa, pero hasta ahora sólo hemos visto la funcionalidad básica, el envío de cadenas de caracteres entre programas cliente y servidor. Pero los **stream** soportan diversos tipos de datos como son los bytes, los tipos de datos primitivos, caracteres localizados y objetos.

Aquí vamos a ver la manera de intercambiar objetos entre programas emisor y receptor o entre programas cliente y servidor usando sockets.



## **OBJETOS EN SOCKETS TCP**

Las clases **ObjectlnputStream** y **ObjectOutputStream** nos permiten enviar objetos a través de sockets TCP. Utilizaremos los métodos **readObject()** para leer el objeto del stream y **writeObject()** para escribir el objeto al stream. Usaremos el constructor que admite un **InputStream** y un **OutputStream.** Para preparar el flujo de salida para escribir objetos escribimos:

```java
ObjectOutputStream outObjeto = new ObjectOutputStream(socket.getOutputStream());
```
Para preparar el flujo de entrada para leer objetos escribimos:
```java
ObjectInputStream inObjeto = new ObjectInputStream(socket.getInputStream());
```

Las clases a la que pertenecen estos objetos deben implementar la interfaz **Serializable.** Podemos ver el funcionamiento con un ejemplo.

En primer lugar, vamos a crear el objeto a enviar, en este caso una cadena de texto.

```java
import java.io.Serializable;

// La clase del objeto a enviar implementa Serializable
@SuppressWarnings("serial")
public class Message implements Serializable{
    private final String text;

    public Message(String text) {
			super();
      this.text = text;
    }

    public String getText() {
        return text;
    }
}
```

La clase cliente debe hacer uso de la clase anterior. En este caso, vamos a crear un **ArrayList** de objetos *message* para enviarlos al servidor:

```java
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class objectSocketClient {

    public static void main(String[] args) throws IOException {
      // Coenctamos a un servidor local por el puerto 7777
      Socket socket = new Socket("localhost", 7777);
      System.out.println("¡Connectado!");

      // Creamos el OutputStream para enviar
      OutputStream outputStream = socket.getOutputStream();
      // Creamos un ObjectOutputStream para enviar el objeto usando el anterior
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

      // make a bunch of messages to send.
      List<Message> messages = new ArrayList<>();
      messages.add(new Message("Saludos desde el Cliente"));
      messages.add(new Message("¿Qué tal por allí?"));
      messages.add(new Message("Espero que bien"));
      messages.add(new Message("Corto y cierro."));

      System.out.println("Enviando mensajes al ServerSocket");
      objectOutputStream.writeObject(messages);

      System.out.println("Cerrando socket finalizando programa.");
      socket.close();
    }
}
```

El programa servidor recibirá esta información y la mostrará por pantalla:

```java
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
      // No es necesario especificar un hostname, será la propia máquina
      ServerSocket ss = new ServerSocket(7777);
      System.out.println("ServerSocket esperando conexiones...");
      // El servidor se queda a la espera de aceptar una conexión  
      Socket socket = ss.accept(); 
      System.out.println("Conectado desde " + socket + "!");

      // Obtenemos el  input stream del socket conectado
      InputStream inputStream = socket.getInputStream();
      // Creamos un DataInputStream para leer los datos de él.
      ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

      // casteamos el objeto leido
      List<Message> listOfMessages = (List<Message>) objectInputStream.readObject();
      System.out.println("Se han recibido [" + listOfMessages.size() 
                         + "] mensajes de: " + socket);
      // mostramos el contenido de cada mensaje
      System.out.println("Mensajes recibidos:");
      listOfMessages.forEach((msg)-> System.out.println(msg.getText()));

      System.out.println("Cerrando sockets.");
      ss.close();
      socket.close();
    }
}
```

Como vemos, el procedimiento es muy parecido a lo visto anteriormente, con la particularidad de que hay que incluir los ObjectInputStream en el cliente y el servidor y "castear" el objeto en el servido.

El resultado de ejecutar los programas es el siguiente:

![image-20210203234024695](IMAGENES/IMG_03_17.png)

## ENVÍO DE OBJETOS USANDO UDP

Para intercambiar objetos en sockets UDP utilizaremos las clases **ByteArrayOutputStream** y **ByteArraylnputStream.** En lugar de implementar el *ObjectOutputStream* sobre un socket, lo que hacemos en su lugar es crear un array de bytes, para que pueda ser troceado en datagramas y luego enviado a las capas inferiores del protocolo IP, y luego crear el *ObjectOutputStream*, sobre el que escribiremos el objeto a enviar. El siguiente ejemplo muestra el proceso de empaquetado y envío de un objeto de tipo persona que tiene dos atributos (String nombre , int edad).

```java
Persona persona = new Persona ("Maria", 22) ;

//CONVERTIMOS OBJETO A BYTES
ByteArrayOutputStream bs = new ByteArrayOutputStream();
ObjectOutputStream out = new ObjectOutputStream (bs) ;
out.writeObject(persona); //escribir objeto Persona en el stream
out.close(); //cerrar stream
byte[] bytes =  bs.toByteArray() ; // objeto en bytes 
```

El servidor recibirá la información en un array de bytes que deberá recomponer:

```java
// RECIBO DATAGRAMA
byte[] recibidos = new byte[1024] ;
DatagramPacket paqRecibido = new DatagramPacket (recibidos, recibidos.length) ;
socket.receive (paqRecibido); //recibo el datagrama
// CONVERTIMOS BYTES A OBJETO
ByteArrayInputStream baits = new ByteArrayInputStream (recibido) ;
ObjectInputStream in = new ObjectInputStream(baits) ;
Persona persona = (Persona) in.readObject();//obtengo objeto
in.close();
```

## BIBLIOGRAFÍA

* [https://gist.github.com/chatton/14110d2550126b12c0254501dde73616](https://gist.github.com/chatton/14110d2550126b12c0254501dde73616)
* 