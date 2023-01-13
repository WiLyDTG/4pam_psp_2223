Dado el siguiente código:

```java
package hilos;

import java.io.*;
import java.net.*;

public class HiloServidor extends Thread {
	BufferedReader fentrada;
	PrintWriter fsalida;
	Socket socket = null;
	private static int INTENTOS = 5;

	// CONSTRUCTOR
	public HiloServidor(Socket s) throws IOException {
		socket = s;
		// creamos streams de entrada y salida
		fsalida = new PrintWriter(socket.getOutputStream(), true);
		fentrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void run() {
		String cadena = "";
		
		
		// Generamos los numeros
		int number = 1 + (int)(100 * Math.random());
		System.out.println();
		// damos 5 intentos
		int i, guess;
		
		for (i = 0; i < INTENTOS; i++) {
			try {
				
				guess = Integer.parseInt(fentrada.readLine());
				System.out.println(guess);
				// If the number is guessed
				if (number == guess) {
					fsalida.println("1");
					i=10;
					break;
				}
				else if (number > guess
						&& i != INTENTOS - 1) {
					fsalida.println("2");
				}
				else if (number < guess
						&& i != INTENTOS - 1) {
					fsalida.println("3");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // obtener cadena
				
		}
		if (i==10) fsalida.println("10");
		else fsalida.println("0");
		System.out.println("FIN CON: " + socket.toString());

		fsalida.close();
		try {
			fentrada.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
```

y el servidor:

```java
package hilos;

import java.io.*;
import java.net.*;

public class Servidor {
	public static void main(String args[]) throws IOException  {
		ServerSocket servidor;		
		servidor = new ServerSocket(6000);
		System.out.println("Servidor iniciado...");
		
		while (true) {	
			Socket cliente = new Socket();
			cliente=servidor.accept();//esperando cliente	
			HiloServidor hilo = new HiloServidor(cliente);
			hilo.start();		
		}
	}
}
```


Crear una aplicación que use un objeto compartido que contenga el número a adivinar y con las siguientes características:
- Cada cliente dispone de cinco intentos.
- Cuando un cliente lo adivine, los demás dejarán de seguir intentado y finalizarán.
- 
