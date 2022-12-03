## PLAZO DE ENTREGA: 15-12-2022

### PRÁCTICA 1
Crear una aplicación en C que lance dos procesos:
- El proceso padre irá pidiendo indefinidamente unnúmero por teclado y lo enviará usando FIFO de un proceso padre al proceso hijo. 
- el proceso hijo lo recibirá, generará la tabla de multiplicar del mismo y devolverá el resultado por un segundo FIFO al padre, el cual la mostrará por pantalla lo recibido por el hijo.
- Hay que crear dos FIFOs, uno para la comunicación padre-hijo y otra para la comunicación hijo-padre.
- El programa termina si el usuario escribe 0.