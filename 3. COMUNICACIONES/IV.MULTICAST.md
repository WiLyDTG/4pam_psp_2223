#  Conexión de múltiples clientes. Hilos
Hasta ahora los programas servidores que hemos creado solo son capaces de atender a un cliente en cada momento, pero lo más típico es que un programa servidor pueda atender a muchos clientes simultáneamente. La solución para poder atender a múltiples clientes está en el multihilo, cada cliente será atendido en un hilo.

Un único servidor con la clase ServerSocket e invocar al método accept() para esperar las peticiones de conexión de los clientes.

Cuando un cliente se conecta, el método accept() devuelve un objeto Socket, éste se usará para crear un hilo cuya misión es atender a este cliente.

Después se vuelve a invocar a accept() para esperar a un nuevo cliente; habitualmente la espera de conexiones se hace dentro de un bucle infinito.
