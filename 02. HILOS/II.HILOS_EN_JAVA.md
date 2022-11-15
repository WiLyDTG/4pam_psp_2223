[ÍNDICE TEMA 3](I.INDICE.md)

## I. PROGRAMACIÓN MULTIHILO
La programación multihilo permite realizar diferentes hilos de ejecución a la vez, es decir, permite realizar diferentes tareas en una aplicación de forma concurrente. La mayoría de lenguajes de programación permiten trabajar con hilos y realizar programación multihilo. También son multihilo la mayoría de las aplicaciones que utilizamos en nuestros ordenadores (editores de texto, navegadores, editores gráficos...), lo que nos da la sensación de mayor agilidad de ejecución.
En un editor de texto, por ejemplo, un hilo puede estar controlando la ortografía del texto, otro puede estar atento a las pulsaciones sobre los iconos de la interfaz gráfica y el otro guardando el documento.

### Los hilos

La programación tradicional está diseñada para trabajar de forma secuencial, por lo que cuando termina un proceso se pone en marcha otro. Los hilos son una forma de ejecutar paralelamente diferentes partes de un mismo programa. En Java los hilos son conocidos como **threads**.

> **Definición de hilo**
Un hilo o thread es la unidad de procesamiento más pequeña que puede ser planificada por un sistema operativo.

Los hilos nos permiten ejecutar concurrentemente diferentes tareas con un gasto mínimo de recursos puesto que todos los hilos de un mismo proceso comparten el espacio de memoria y su creación consume poco tiempo de procesador. Son entidades ligeras.

De la misma forma que un sistema operativo permite ejecutar diferentes procesos a la vez por concurrencia o paralelismo, dentro de un proceso habrá uno o varios hilos en ejecución.

> Los hilos representan exclusivamente las ejecuciones de las instrucciones de un programa que se realizan simultáneamente en el contexto de un mismo proceso. Es decir, compartiendo el acceso a la misma zona de memoria asignada al proceso al que pertenecen. Cada proceso contiene al menos un hilo, aunque puede llegar a contener muchos.

### Relación entre proceso e hilo
Recuerde que los procesos no comparten memoria entre ellos, son independientes, traen información sobre su estado e interactúan con otros procesos a través de mecanismos de comunicación dados por el sistema. Esta comunicación o el cambio de estado de un proceso son mecanismos costosos por el microprocesador. Por eso se llaman entidades pesadas. Los hilos, en cambio, comparten recursos. El cambio de estado y ejecución se realiza mucho más rápidamente y pueden comunicarse entre ellos utilizando los datos de la memoria que comparten. El tiempo de uso del microprocesador dedicado a la gestión de hilos es bastante despreciable.

El hecho de que los hilos de ejecución de un mismo proceso compartan los recursos hace que cualquier hilo pueda modificarlos. Si un hilo modifica un dato en la memoria, el resto de los hilos tienen acceso al nuevo dato de forma inmediata. Cuando hay un proceso ejecutándose, al menos siempre hay un hilo en ejecución. Hablamos de procesos multihilo cuando se ejecutan varios hilos concurrentemente, realizando diferentes tareas y colaborando entre ellos.

Cuando un proceso finaliza, todos sus hilos también lo hacen. De forma equivalente, cuando finalizan todos los hilos de un proceso, el proceso también termina y todos sus recursos son liberados.

Los sistemas operativos actuales admiten la concurrencia, es decir, pueden ejecutar distintos procesos a la vez. Podemos estar navegando por Internet con un navegador, escuchando música con un reproductor y, con un editor de texto, redactando un documento. Cada aplicación que se ejecuta es un proceso y cada uno de estos procesos tiene al menos un hilo de ejecución, pero podrían tener más de uno. El editor de texto podría tener un hilo que se encargara de ver qué tecla estamos picando, otro que vaya comprobando la ortografía y otro que de vez en cuando se active para guardar el documento. Hay que ver que un hilo pertenece a un proceso y que los diferentes hilos pueden realizar tareas diferentes dentro de su proceso.

## II. HILOS EN JAVA
Cuando comenzamos un programa en Java hay un hilo en ejecución, el hilo principal, que es creado por el método `main()`. Este hilo es importante ya que es el encargado, si es necesario, crear el resto de hilos del programa. Se debe programar la aplicación para que el hilo principal sea el último en terminar su ejecución. Esto se consigue haciendo esperar a los hilos creados por el hilo principal que éste último finalice.

Si no se crean más hilos que el principal, sólo existe uno y será el encargado de realizar los llamamientos a los métodos y crear los objetos que indique el programa. 

```java
package hiloUnico; 
public class hiloUnico {
   public static void main(String[] args) { 
      for (int x=0;x<10;x++)
         System.out.println("x=" + x);
	} 
}
```

En el ejemplo anterior, cuando se ejecuta el main(), se imprimen los distintos valores de x. Esto ocurre en un único hilo de ejecución.

Pero desde el hilo principal se pueden crear otros hilos que irán ejecutando otros métodos de los objetos instanciados o nuevos métodos que haya que crear, de forma simultánea.

De la misma manera, un hilo creado por `main()` puede crear otros hilos, que a su vez pueden crear nuevos hilos. Los hilos creados dependen directamente de sus creadores, de esta forma se crean jerarquías de padres e hijos de hilos de ejecución.  

Los hilos o threads se pueden implementar o definir de dos formas:

- **Extendiendo la clase thread.**
- **Mediante la interfaz Runnable.**

En ambos casos, se debe proporcionar una definición del método `run()`, ya que este método es el que contiene el código que ejecutará el hilo, es decir, su comportamiento.

Extender la clase thread es el procedimiento más sencillo, pero no siempre es posible. Si la clase ya hereda de alguna otra clase padre, no será posible heredar también de la clase thread (recuerda que Java no permite la herencia múltiple), por lo que habrá que recurrir al otro procedimiento.

Implementar Runnable siempre es posible, es el procedimiento más general y también el más flexible.

#### EJEMPLO
En el siguiente programa de ejemplo lanzamos dos hilos creados  en una clase Hilo, que implementa la interfaz Runnable. Al lanzar el hilo se ejecuta el método `run()`  de dicha clase, que escribe un identificador que se pasa en el constructor.

```java
package lanzahilos; 
class Hilo implements Runnable { 
	private final String nombre;
	Hilo (String nombre) {
		this.nombre = nombre;
	}

	@Override
	public void run() {
		System.out.printf("Hola, soy el hilo: %s.\n" , this.nombre);
		System.out.printf("Hilo %s terminado.\n", this.nombre);
	}
}

public class LanzaHilos {
	public static void main (String[] args) {
	Thread h1 = new Thread (new Hilo ("HI"));
	Thread h2 = new Thread (new Hilo("H2"));
	h1.start();
	h2. start ();
	System.out.println("Hilo principal terminado."); 
	}
		
}
```



### La clase Thread
En Java, un hilo se representa mediante una instancia de la clase **java.lang.thread**. Este objeto `Thread` se emplea para iniciar, detener o cancelar la ejecución del hilo de ejecución. Gracias a esta clase, podemos crear y ejecutar nuevos hilos, detenerlos, reanudar su ejecución, esperar la finalización de los hilos creados, etc. La funcionalidad de `Thread` se complementa con la funcionalidad específica de la clase objeto destinada a resolver la sincronización entre hilos. La sincronización se encapsula en la clase `Object` para que cualquier ejecución disponga siempre de los métodos y mecanismos necesarios para llevarla a cabo.  

Veamos la especificación de los principales constructores y métodos de esta clase.

**Constructores:**. 

- `Thread()`: reserva memoria para crear un objeto Thread.
- `Thread(Runnable target)`: reserva memoria para la creación de un nuevo objeto de la clase Thread que gestionará la ejecución del objeto Runnable pasado por parámetro.
- `Thread(Runnable target, String name)`: reserva memoria para la creación de un nuevo objeto de la clase Thread que gestionará la ejecución del objeto Runnable pasado por parámetro. El objeto creado podrá identificarse con el nombre pasado en el segundo parámetro.
- `Thread(String name)`: reserva memoria para crear un objeto Thread identificado con el nombre pasado por parámetro.
- `Thread(ThreadGroup group, Runnable target)`: reserva memoria para un nuevo objeto Thread que gestionará la ejecución del objeto Runnable (target) y que pertenece al grupo de hilos (group).
- `Thread(ThreadGroup group, Runnable target, String name)`: reserva memoria para un nuevo objeto Thread identificado con el nombre name, que pertenece al grupo de hilos (group) y que gestionará la ejecución del objeto Runnable target.
- `Thread(ThreadGroup group, String name)`: crea un nuevo objeto Thread identificado por name y que pertenece al grupo de hilos concreto (group) .



**Métodos:**

Algunos de los métodos mas comunes sobre hilos son los que se muestran en la tabla b), donde podemos encontrar los métodos mas comunes para el manejo y a planificación de hilos.

![METODOS_THREAD](IMAGENES/img_03.png)
Tabla b). Métodos de hilos de la clase Thread

Otros métodos son los siguientes:

- `string getName()`: devuelve el nombre del hilo.
- `void setName(String nombre)`: asigna un nombre al hilo.
- `int getPriority()`: nos dice la prioridad de ejecución del hilo.
- `void setPriority(int Prioridad)`: asigna la prioridad de ejecución del hilo.
- `ThreadGroup getThreadGroup()`: devuelve el grupo de hilos al que pertenece el hilo.
- `boolean isDaemon()`: nos indica si el hilo es un demonio o no.
- `void setDaemon(boolean on)`: indica si el hilo será un demonio o un hilo de usuario. Los demonios están ligados a su creador, si su creador termina su ejecución, los hijos demonios también finalizan.
- `void join(long Millis)`: espera la finalización del hilo invocado, pero una vez pasado el tiempo del parámetro (expresado en milisegundos), se continuará la ejecución.
- `String toString()`: devuelve una cadena que representa el hilo, nombre prioridad y su grupo.

Puede encontrar más información sobre métodos, propiedades y constructores de la clase Thread en:
[http://docs.oracle.com/javase/6/docs/api/java/lang/Thread.html](http://docs.oracle.com/javase/6/docs/api/java/lang/Thread.html)

### La interfaz Runnable

En el lenguaje Java, tener que heredar de la clase Thread para crear clases con esta funcionalidad comporta un problema no menor, ya que Java no soporta la herencia múltiple y por tanto nos resultará imposible hacer que la nueva clase extienda ninguna otro que no sea Thread. Es decir, nos quedará muy limitada la implementación de clases hilo, usando herencia. Para evitar esta limitación podemos utilizar la Interfaz Runnable. La interfaz Runnable fuerza la existencia del método run pero no limita la herencia. De hecho, los objetos Runnable no tienen la capacidad de ejecutarse como un hilo independiente. Por eso es necesario utilizar un objeto Thread para gestionar su ejecución.

### La clase Object

La clase Object es muy extensa. Aquí sólo nos centraremos en los métodos que nos proporciona, relacionados con los hilos. Son métodos que nos permiten la comunicación y manipulación de hilos. Esta clase, jerárquicamente, se encuentra en la parte superior de todas las clases de Java, por tanto cada clase de Java hereda su funcionalidad. Cada nueva clase incluye los siguientes métodos:
- `wait()`: este método hace que un hilo de ejecución pase a un estado de espera hasta que se le notifique que puede continuar la ejecución.
- `notify()`: es el método encargado de notificar a un hilo que se encuentra a la espera de que puede continuar la ejecución.
- `notifyAll()`: funciona de forma similar a notify(), pero notifica que pueden continuar la ejecución todos los hilos en espera.

Estos métodos son utilizados cuando ejecutamos la programación concurrente de diferentes hilos (programación multihilo), y sirven para hacer esperar un hilo que otro acabe de realizar alguna tarea. También que, una vez terminada esta tarea, notifique al hilo a la espera de que puede continuar su ejecución. Estos métodos únicamente pueden ser llamados dentro de métodos sincronizados:

```java
synchronized public void metodeSincronizado(){
   //...
}
O dentro de blogs de código sincronizados:
public void metode(){
   synchronized (this) {
      //....
     }
//...
}
```

**EJEMPLO 2**

   El siguiente código define una clase Hilo1 que extiende Thread

```java
public class Hilo1 extends Thread {
	
	//Constructor
	public Hilo1(String nombre) { 
    //mediante esta instrucción, le decimos al constructor el 
    //nombre del hilo usando el constructor de la clase Thread
    //asi podemos obtener su nombre con el método existente getName()
		super(nombre);
		System.out.println("CREANDO HILO: " + getName());
	}

	// metodo run
	public void run() {
		for (int i=0; i<5; i++) 
			System.out.println("Hilo:" + getName() + " C = " + i);
	}
}
```

El programa principal debe crear y ejecutar dicho hilo de la siguiente manera:

```java
public static void main(String[] args) {
		//Creamos las instancias de los tres hilos
		Hilo1 h1 = new Hilo1("Hilo 1");
		Hilo1 h2 = new Hilo1("Hilo 2");
		Hilo1 h3 = new Hilo1("Hilo 3");
			
		//Los ejecutamos
		h1.start();
		h2.start();
		h3.start();

}
```

### REFERENCIA


Un magnífico sitio donde puedes entender la programación multihilos en java es el siguiente:

[https://jarroba.com/multitarea-e-hilos-en-java-con-ejemplos-thread-runnable/](https://jarroba.com/multitarea-e-hilos-en-java-con-ejemplos-thread-runnable/)


