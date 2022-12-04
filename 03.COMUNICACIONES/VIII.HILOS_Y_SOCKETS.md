#  HILOS Y SOCKETS

<hr>
[VOLVER AL ÍNDICE](I.INDICE.md)
---

En los contenidos vistos previamente, los programas servidores únicamente son capaces de atender a un cliente en cada momento. Esto está bien para entender el procedimiento pero lo común es que un programa servidor sea capaz de atender a varios clientes de forma simultánea. Para poder implementar esta característica, tenemos que hacer uso de la programación multi-hilo, y cada uno de los clientes que se conecte será atendido por un hilo del programa servidor.

El esquema básico en sockets TCP sería:

* construir un servidor con la clase **ServerSocket** e invocar al método **accept()** para esperar las peticiones de conexión de los clientes.

* Cuando un cliente se conecta, el método **accept()** devuelve un objeto **Socket,** éste se usará para crear un hilo cuya misión es atender a este cliente. 

* Después se vuelve a invocar a **accept()** para esperar a un nuevo cliente; habitualmente la espera de conexiones se hace dentro de un bucle infinito.

* La espera de clientes se suele imbuir dentro de un bucle infinito para que el Server esté siempre activo.


Vamos a ver el ejemplo anterior de la aplicacion en la que el cliente envía una cadena de caracteres al servidor y el servidor se la devuelve en mayúsculas, hasta que recibe un asterisco, momento en que finalizará la comunicación con el cliente.

Este ejemplo debe incluir la clase que gestionará los hilos, y dentro de ella haremos el procedimiento de recepción y envío de datos.

El código fuente sería el siguiente:

```java
package HILOS;

import java.io.*;
import java.net.*;

public class HiloServidor extends Thread {
	BufferedReader fentrada;
	PrintWriter fsalida;
	Socket socket = null;

	// CONSTRUCTOR
	public HiloServidor(Socket s) throws IOException {
		socket = s;
		// creamos streams de entrada y salida
		fsalida = new PrintWriter(socket.getOutputStream(), true);
		fentrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void run() {
		String cadena = "";
		System.out.println("COMUNICO CON: " + socket.toString());
		while (!cadena.trim().equals("*")) {
			try {
				cadena = fentrada.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // obtener cadena
			// enviar mayúscula
			fsalida.println(cadena.trim().toUpperCase());
		} 

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
````

El código del archivo servidor queda más sencillo, pues solo tiene que establecer la coumunicación, crear  los hilos y lanzarlos:

```java
package HILOS;

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

El cliente no difiere mucho de un cliente normal, lo que pasa es que aqui lo ejecutaremos desde varios terminales.

```java
package HILOS;
import java.io.*;
import java.net.*;

public class Cliente {
  public static void main(String[] args) throws IOException {
	String Host = "localhost";
	int Puerto = 6000;// puerto remoto
	Socket Cliente = new Socket(Host, Puerto);
		
	// CREO FLUJO DE SALIDA AL SERVIDOR	
	PrintWriter fsalida = new PrintWriter (Cliente.getOutputStream(), true);
	// CREO FLUJO DE ENTRADA AL SERVIDOR	
	BufferedReader fentrada =  new BufferedReader
	     (new InputStreamReader(Cliente.getInputStream()));
		 
	// FLUJO PARA ENTRADA ESTANDAR
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	String cadena, eco="";
		
	
	do{ 
		System.out.print("Introduce cadena: ");
		cadena = in.readLine();
		fsalida.println(cadena);
		eco=fentrada.readLine();			
		System.out.println("  =>ECO: "+eco);	
	} while(!cadena.trim().equals("*"));
		
	fsalida.close();
	fentrada.close();
	System.out.println("Fin del envío... ");
	in.close();
	Cliente.close();
	}
}
```



