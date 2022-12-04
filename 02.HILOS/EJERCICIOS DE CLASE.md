### EJERCICIO 1
Un hilo debe generar un número al azar entre cero y cien, que deben intentar adivinar otros diez hilos. Si un hilo acierta el número, debe terminar su ejecución inmediatamente. Y el resto de los hilos deben también terminar su ejecución en cuanto propon gan un número y se les avise de que otro hilo ya ha acertado el número. 

Se propone utilizar una clase **NumeroOculto** con un método int **propuestaNumero (int num)** que devuelva los siguientes valores: 

  a) -1 si el juego ya ha terminado porque un hilo ha adivinado el número.  
  b) 1 si el número propuesto (num) es el número oculto.   
  c) 0 en otro caso. 
  
No hace falta crear una clase para el hilo que genera el número al azar. Es el hilo inicial, que ejecuta el método main, y que crea el resto de los hilos.

### EJERCICIO 2

El código a continuación simula el proceso de cobro de un supermercado, donde unos clientes van con un carro lleno de productos y una máquina de cobro automático (llamada Cajera)  les cobra los productos. La cajera procesa la compra cliente a cliente, es decir que primero procesa al cliente 1, luego al cliente 2 y así sucesivamente. 

Para ello se ha definido una clase "Cajera" y una clase "Cliente" formado por un array de enteros que representa el tiempo que la máquina de cobro automático tarda en pasar cada uno de los productos que ha comprado y. Es decir, que si tenemos un array con [1,3,5] quiere decir que el cliente ha comprado 3 productos y que la la máquina de cobro tarda en procesar el producto 1 '1 segundo', el producto 2 '3 segundos' y el producto 3 en '5 segundos', con lo cual tardara en cobrar al cliente toda su compra '9 segundos'.

Aqui tenemos la clase Cajera
```java
public class Cajera {

	private String nombre;

	// FALTA CREAR Constructor, getter y setter

	public void procesarCompra(Cliente cliente, long timeStamp) {

		System.out.println("La cajera " + this.nombre + 
				" COMIENZA A PROCESAR LA COMPRA DEL CLIENTE " + cliente.getNombre() + 
				" EN EL TIEMPO: " + (System.currentTimeMillis() - timeStamp) / 1000	+
				"seg");

		for (int i = 0; i < cliente.getCarroCompra().length; i++) { 
				this.esperarXsegundos(cliente.getCarroCompra()[i]); 
				System.out.println("Procesado el producto " + (i + 1) +  
				" ->Tiempo: " + (System.currentTimeMillis() - timeStamp) / 1000 + 
				"seg");
		}

		System.out.println("La cajera " + this.nombre + " HA TERMINADO DE PROCESAR " + 
				cliente.getNombre() + " EN EL TIEMPO: " + 
				(System.currentTimeMillis() - timeStamp) / 1000 + "seg");

	}


	private void esperarXsegundos(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

}
```

Aqui la clase Cliente:
```java
public class Cliente {

	private String nombre;
	private int[] carroCompra;

	// FALTA CREAR Constructor, getter y setter

}
```


Aqui tenemos el programa Main, donde creamos dos máquinas de cobro que procesan dos clientes:
```java
public class Main {

	public static void main(String[] args) {

		Cliente cliente1 = new Cliente("Cliente 1", new int[] { 2, 2, 1, 5, 2, 3 });
		Cliente cliente2 = new Cliente("Cliente 2", new int[] { 1, 3, 5, 1, 1 });

		Cajera cajera1 = new Cajera("Cajera 1");
		Cajera cajera2 = new Cajera("Cajera 2");

		// Tiempo inicial de referencia
		long initialTime = System.currentTimeMillis();

		cajera1.procesarCompra(cliente1, initialTime);
		cajera2.procesarCompra(cliente2, initialTime);
	}
}
```


SE PIDE
- Construir los métodos faltantes de cada clase.
- Convertir los clientes en hilos que extiendan la clase Thread.
