Basándonos en los archivos [TCPCliente](CODIGO/TCPCliente.java) y [TCPServidor](CODIGO/TCPServidor.java), que envian un mensaje entre cliente y servidor y el ejemplo de los apuntes, crea una aplicación cliente servidor utilizando Sockets en Java utilizando 2 poyectos
* **Proyecto EcoServidor**:
    - El servidor escuchará en el puerto 6000.
    - Cuando reciba una conexión, leerá constantemente mensajes del cliente, y se los devolverá convertidos a mayúsculas. 
    
    Hola → HOLA

* **Proyecto EcoCliente:**
    - El cliente define un socket que se conecta al servidor (localhost si esta en la mima máquina).
    - Cuando se establece la conexión, se pide al usuario que escriba mensajes en la consola.
    - Los mensajes se envían al servidor y se espera por la respuesta de eco.
    - La comunicación finaliza cuando el servidor recibe la palabra: ciao