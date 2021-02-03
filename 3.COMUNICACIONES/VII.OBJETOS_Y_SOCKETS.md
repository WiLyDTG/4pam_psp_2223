# ENVÍO DE OBJETOS A TRAVÉS DE SOCKETS

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

// must implement Serializable in order to be sent
public class Message implements Serializable{
    private final String text;

    public Message(String text) {
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
        // need host and port, we want to connect to the ServerSocket at port 7777
        Socket socket = new Socket("localhost", 7777);
        System.out.println("Connected!");

        // get the output stream from the socket.
        OutputStream outputStream = socket.getOutputStream();
        // create an object output stream from the output stream so we can send an object through it
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        // make a bunch of messages to send.
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("Hello from the other side!"));
        messages.add(new Message("How are you doing?"));
        messages.add(new Message("What time is it?"));
        messages.add(new Message("Hi hi hi hi."));

        System.out.println("Sending messages to the ServerSocket");
        objectOutputStream.writeObject(messages);

        System.out.println("Closing socket and terminating program.");
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
        // don't need to specify a hostname, it will be the current machine
        ServerSocket ss = new ServerSocket(7777);
        System.out.println("ServerSocket awaiting connections...");
        Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
        System.out.println("Connection from " + socket + "!");

        // get the input stream from the connected socket
        InputStream inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        // read the list of messages from the socket
        List<Message> listOfMessages = (List<Message>) objectInputStream.readObject();
        System.out.println("Received [" + listOfMessages.size() + "] messages from: " + socket);
        // print out the text of every message
        System.out.println("All messages:");
        listOfMessages.forEach((msg)-> System.out.println(msg.getText()));

        System.out.println("Closing sockets.");
        ss.close();
        socket.close();
    }
}
```

