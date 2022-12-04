# EJERCCIOS
## PON EN MAYÚSCULAS
Basándonos en los archivos [TCPCliente](TCPCliente.java) y [TCPServidor](TCPServidor.java), que envían un mensaje entre cliente y servidor y el ejemplo de los apuntes, crea una aplicación cliente servidor utilizando Sockets en Java utilizando 2 proyectos.
* **Proyecto EcoServidor**:
    - El servidor escuchará en el puerto 6000.
    - Cuando reciba una conexión, leerá constantemente mensajes del cliente, y se los devolverá convertidos a mayúsculas. 
    
    Hola → HOLA

* **Proyecto EcoCliente:**
    - El cliente define un socket que se conecta al servidor (localhost si esta en la mima máquina).
    - Cuando se establece la conexión, se pide al usuario que escriba mensajes en la consola.
    - Los mensajes se envían al servidor y se espera por la respuesta de eco.
    - La comunicación finaliza cuando el servidor recibe la palabra: ciao.

## DICCIONARIO
* Crea un proyecto llamado **diccionarioCliente** que envíe por el puerto 6000 al servidor una palabra escrita por el usuario.
    - Configura un timeout en el cliente de 5 segundos.
    - Si el timeout expira el cliente debe sacar un mensaje para el usuario de “Traducción no encontrada”
* Crea un proyecto llamado **diccionarioServidor** que escuche en el mismo puerto 6000. 
Tendrá una colección [HashMap](https://www.w3schools.com/java/java_hashmap.asp) con palabras en ingles y su traducción al castellano.

    - El servidor recibe la palabra enviada por el cliente y devolverá la traducción de la misma.
    - Si no encuentra la palabra en la colección, el servidor no devolverá nada, haciendo que el TIME-OUT del cliente se consuma.

## CUENTA LETRAS 'a'
Crear dos programas en JAVA (Cliente-Servidor) que usando las clases **DatagramSocket** y **DatagramPacket** del protocolo **UDP** realicen el siguiente procedimiento:
* El programa cliente lee un mensaje por teclado usando su entrada estándar y envía dicho texto  al servidor por un puerto acordado entre los dos programas.
* El programa servidor lee el datagrama con el mensaje y devuelve al cliente el número de apariciones de la letra 'a' en el texto. 
* El programa cliente recibe el datagrama del servidor y muestra el número de repeticiones de la letra 'a'. 

El ejercicio debe mostrar algo parecido a esto:
![FUNCIONAMIENTO UDP](IMG_03_12.png)

## ENVÍO DE OBJETOS (TCP)

Crear una aplicación que permita intercambiar objetos llamados **Persona** entre un cliente y un servidor usando sockets **TCP**. En el ejercicio, el programa servidor crea un objeto **Persona**, asignarle valores y enviarlo al programa cliente. Una vez recibido, el programa cliente debe hacer modificaciones en el objeto y devolverlo otra vez al servidor, que mostrará por pantalla los valores modificados.

Como ayuda, el objeto persona se puede descargar desde [aquí](Persona.java), y te puedes servir de los ejemplos de los apuntes del [apartado VII](VII.OBJETOS_Y_SOCKETS.md).

El resultado debe ser parecido a la siguiente imagen:

![EJERCICIO OBJETOS TCP](IMG_03_16.png)