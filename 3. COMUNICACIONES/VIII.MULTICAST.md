#  Conexión de múltiples clientes. Hilos
En los contenidos vistos previamente, los programas servidores únicamente son capaces de atender a un cliente en cada momento. Esto está bien para entender el procedimiento pero lo común es que un programa servidor sea capaz de atender a varios clientes de forma simultánea. Para poder implementar esta característica, tenemos que hacer uso de la programación multi-hilo, y cada uno de los clientes que se conecte será atendido por un hilo del programa servidor.

El esquema básico en sockets TCP sería construir un único servidor con la clase **ServerSocket** e invocar al método **accept()** para esperar las peticiones de conexión de los clientes. Cuando un cliente se conecta, el método **accept()** devuelve un objeto **Socket,** éste se usará para crear un hilo cuya misión es atender a este cliente. Después se vuelve a invocar a **accept()** para esperar a un nuevo cliente; habitualmente la espera de conexiones se hace dentro de un bucle infnito:

s

Un único servidor con la clase **ServerSocket** e invocar al método **accept()** para esperar las peticiones de conexión de los clientes.

Cuando un cliente se conecta, el método accept() devuelve un objeto Socket, éste se usará para crear un hilo cuya misión es atender a este cliente.

Después se vuelve a invocar a **accept()** para esperar a un nuevo cliente; habitualmente la espera de conexiones se hace dentro de un bucle infinito.
