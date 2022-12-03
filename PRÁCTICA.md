Esta es la práctica de procesos, se debe subir y compartir en el repositorio de Github correspondiente.

### FECHA DE ENTREGA: 9-12-2022

### COMUNICACIÓN DE PROCESOS: FIFO

Crear una aplicación en C que lance dos procesos:
- El proceso padre irá pidiendo indefinidamente un número por teclado y lo enviará usando FIFO de un proceso padre al proceso hijo. 
- el proceso hijo lo recibirá, generará la tabla de multiplicar del mismo y devolverá el resultado por un segundo FIFO al padre, el cual la mostrará por pantalla lo recibido por el hijo.
- Hay que crear dos FIFOs, uno para la comunicación padre-hijo y otra para la comunicación hijo-padre.
- El programa termina si el usuario escribe 0.
- hay que usar las conversiones de entero a carácter en C