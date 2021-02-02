# SOCKETS CON JAVA

## INTRODUCCIÓN A LOS SOCKETS



Los protocolos TCP y UDP utilizan el concepto de *socket* para favorecer un canal de  comunicación fiable entre aplicaciones o procesos. el concepto recuerda mucho a las tuberías o *pipes* vistos en el tema de procesos. 

La comunicación mediante sockets se sustenta en el concepto de transmisión de un mensaje entre un conector de una aplicación y un conector de otra, a este conector es a lo que llamamos *socket*. 

JAVA nos permite un nivel de abstracción mayor o menor dependiendo del nivel en el que nos encotremos dentro de la pila de comunicaciones TCP/IP.

![COMUNICACIÓN A TRAVÉS DE SOCKETS](/Users/joaquinrios/Desktop/psp_4pam/3. COMUNICACIONES/IMAGENES/IMG_03_04.png)

Algunos aspectos a destacar acerca de los sockets son los siguientes:

* Cada socket tiene un número de puerto asociado a él.

* Este puerto identifica el proceso que esta enviando o recibiendo la información a través del socket.

* Cuando un proceso local quiere comunicarse con un proceso remoto, los dos establecen sus propios números de puerto.

* Cada vez que la información se envía entre ellos, la computadora sabe a que proceso debe recibir la información comprobando el puerto de destino.

  Ejemplo: Sl host A, tiene un proceso PA escuchando en el puerto 33. Cada vez que el A reciba un mensaje con puerto de destino 33, ese mensaje lo recibirá el proceso PA.

### Comunicación Cliente – Servidor

- Servidor permanece escuchando en un puerto dado, esperando peticiones de un Cliente.

  - Hay puertos estándar para servicios estándar: http, ftp, ssh, dns, ...
  - Cuando recibe una petición la procesa y envía la respuesta.
  - Rol pasivo en la comunicación 

- Cliente, que debe saber la dirección IP y el puerto del Servidor le envía mensajes y espera la respuesta.

  - El puerto del cliente se asigna aleatoriamente (> 1023 que son los estándar).
  - Rol activo en la comunicación

- Cuando un cliente se conecta a un servidor, se establece el socket de comunicación
  por el que el cliente manda peticiones al servidor y el servidor envía las respuestas apropiadas al cliente

    Ej: protocolo http (web)

- Una vez establecida la conexión, el servidor sigue escuchando por el mismo puerto a otros clientes que pueden establecer otras conexiones.

### Tipos de Sockets

- **Sockets TCP**: 

  - orientados a la conexión. → se establece una conexión fiable
  - Los mensajes llegan ordenados y sin errores (se confirma la recepción de cada mensaje).
  - Se usan cuando la integridad de los datos enviados es más importante que la velocidad.
  - Más lento que conexiones UDP.

  Ej: FTP → la integridad de los ficheros subidos o descargados es muy importante

- **Sockets UDP**: 

  - No orientado a la conexión → no se establece una conexión fiable.
  - Se pueden perder mensajes y / o llegar desordenados (no se confirma nada)
  - Se usa cuando la velocidad es más importante que la integridad
  - Más rápido que TCP

  Ej: Streaming → es más importante recibir el vídeo rápido que perder un fotograma 


## SOCKETS EN JAVA

Dentro del paquete java.net podemos encontrar las clases **ServerSocket** y **Socket** para trabajar con sockets.

Clase **ServerSocket** : 

- Implementa el socket en el lado del servidor
- Escucha peticiones de conexión de clientes en un puerto


Clase **Socket**: 

- Implementa el socket en el lado del cliente.
- mantiene la comunicación entre cliente y servidor

Vamos a estudiar los dos protocolos del nivel de transporte y cómo se pueden gestionar con JAVA.


## REFERENCIAS

[Wikipedia](https://es.wikipedia.org/wiki/Protocolo_de_datagramas_de_usuario)

