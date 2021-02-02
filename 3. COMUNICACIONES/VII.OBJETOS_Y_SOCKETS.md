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

Las clases a la que pertenecen estos objetos deben implementar la interfaz **Serializable.**