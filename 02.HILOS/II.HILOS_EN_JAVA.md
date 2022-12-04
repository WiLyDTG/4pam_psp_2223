[ÍNDICE TEMA 3](02.HILOS/I.INDICE.md)

## I. PROGRAMACIÓN MULTIHILO
La programación multihilo permite realizar diferentes hilos de ejecución a la vez, es decir, permite realizar diferentes tareas en una aplicación de forma concurrente. La mayoría de lenguajes de programación permiten trabajar con hilos y realizar programación multihilo. También son multihilo la mayoría de las aplicaciones que utilizamos en nuestros ordenadores (editores de texto, navegadores, editores gráficos...), lo que nos da la sensación de mayor agilidad de ejecución.
En un editor de texto, por ejemplo, un hilo puede estar controlando la ortografía del texto, otro puede estar atento a las pulsaciones sobre los iconos de la interfaz gráfica y el otro guardando el documento.

### Los hilos

La programación tradicional está diseñada para trabajar de forma secuencial, por lo que cuando termina un proceso se pone en marcha otro. Los hilos son una forma de ejecutar paralelamente diferentes partes de un mismo programa. En Java los hilos son conocidos como **threads**.

> **Definición de hilo**:  
> Un hilo o thread es la unidad de procesamiento más pequeña que puede ser planificada por un sistema operativo.

Los hilos nos permiten ejecutar concurrentemente diferentes tareas con un gasto mínimo de recursos puesto que todos los hilos de un mismo proceso comparten el espacio de memoria y su creación consume poco tiempo de procesador. Son entidades ligeras.

De la misma forma que un sistema operativo permite ejecutar diferentes procesos a la vez por concurrencia o paralelismo, dentro de un proceso habrá uno o varios hilos en ejecución.


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

Extender la clase `Thread` es el procedimiento más sencillo, pero no siempre es posible. Si la clase ya hereda de alguna otra clase padre, no será posible heredar también de la clase Thread` (recuerda que Java no permite la herencia múltiple), por lo que habrá que recurrir al otro procedimiento.

Implementar `Runnable` siempre es posible, es el procedimiento más general y también el más flexible.


### La clase Thread
En Java, un hilo se representa mediante una instancia de la clase **java.lang.thread**. Este objeto `Thread` se emplea para iniciar, detener o cancelar la ejecución del hilo de ejecución. Gracias a esta clase, podemos crear y ejecutar nuevos hilos, detenerlos, reanudar su ejecución, esperar la finalización de los hilos creados, etc. La funcionalidad de `Thread` se complementa con la funcionalidad específica de la clase objeto destinada a resolver la sincronización entre hilos. La sincronización se encapsula en la clase `Object` para que cualquier ejecución disponga siempre de los métodos y mecanismos necesarios para llevarla a cabo.  

Veamos la especificación de los principales constructores y métodos de esta clase.

**Constructores:**
- `Thread()`: reserva memoria para crear un objeto Thread.
- `Thread(Runnable target)`: reserva memoria para la creación de un nuevo objeto de la clase Thread que gestionará la ejecución del objeto Runnable pasado por parámetro.
- `Thread(Runnable target, String name)`: reserva memoria para la creación de un nuevo objeto de la clase Thread que gestionará la ejecución del objeto Runnable pasado por parámetro. El objeto creado podrá identificarse con el nombre pasado en el segundo parámetro.
- `Thread(String name)`: reserva memoria para crear un objeto Thread identificado con el nombre pasado por parámetro.
- `Thread(ThreadGroup group, Runnable target)`: reserva memoria para un nuevo objeto Thread que gestionará la ejecución del objeto Runnable (target) y que pertenece al grupo de hilos (group).
- `Thread(ThreadGroup group, Runnable target, String name)`: reserva memoria para un nuevo objeto Thread identificado con el nombre name, que pertenece al grupo de hilos (group) y que gestionará la ejecución del objeto Runnable target.
- `Thread(ThreadGroup group, String name)`: crea un nuevo objeto Thread identificado por name y que pertenece al grupo de hilos concreto (group) .


**Métodos más importantes:**
- `static Thread currentThread()`: nos devuelve el hilo que se está ejecutando.
- `string getName()`: devuelve el nombre del hilo.
- `void setName(String nombre)`: asigna un nombre al hilo.
- `int getPriority()`: nos dice la prioridad de ejecución del hilo.
- `void setPriority(int Prioridad)`: asigna la prioridad de ejecución del hilo.
- `ThreadGroup getThreadGroup()`: devuelve el grupo de hilos al que pertenece el hilo.
- `boolean isAlive()`: mira si el hilo está activo. Es decir, si ha habido una llamada inicial a su método start() y todavía no ha finalizado la ejecución.
- `boolean isDaemon()`: nos indica si el hilo es un demonio o no.
- `void setDaemon(boolean on)`: indica si el hilo será un demonio o un hilo de usuario. Los demonios están ligados a su creador, si su creador termina su ejecución, los hijos demonios también finalizan.
-` void join()`: Detiene la ejecución del hilo que hace la llamada y espera la finalización del hilo invocado.
- `void join(long Millis)`: espera la finalización del hilo invocado, pero una vez pasado el tiempo del parámetro (expresado en milisegundos), se continuará la ejecución.
- `void run()`: Representa el inicio de ejecución del hilo. Esta ejecución comienza inmediatamente después de haber llamado el método star().
- `static void sleep(long Millis)`: detiene el hilo invocado, durante el número de milisegundos indicados en el parámetro.
- `void start()`: pone un hilo en estado de preparado para ejecutarse.
- `static void yield()`: hace que el hilo que se está ejecutando pase a estado de preparado. Sale del procesador.
- `static void sleep(long Millis)`: hace que un hilo que se está ejecutando se duerma durante un tiempo, que se establece en milisegundos. El hilo no pierde ninguna propiedad de bloqueo.
- `String toString()`: devuelve una cadena que representa el hilo, nombre prioridad y su grupo.

Puede encontrar más información sobre métodos, propiedades y constructores de la clase Thread en:
[http://docs.oracle.com/javase/6/docs/api/java/lang/Thread.html](http://docs.oracle.com/javase/6/docs/api/java/lang/Thread.html)

#### CREACIÓN DE HILOS MEDIANTE HERENCIA DE `THREAD`

Para definir y crear un hilo extendiendo la clase thread, haremos lo siguiente:

 - Crear una nueva clase que herede de la clase thread.
 - Redefinir en la nueva clase el método run() con el código asociado al hilo.
 - Crear un objeto de la nueva clase thread. Éste será realmente el hilo.
  - Una vez creado el hilo, para ponerlo en marcha o iniciarlo invocamos al método `start()` del objeto thread.

El siguiente código define una clase Hilo1 que extiende Thread

```java
public class HiloBasico extends Thread {
    public HiloBasico(String name) {
        //mediante esta instrucción, le decimos al constructor el 
        //nombre del hilo usando el constructor de la clase Thread
        //asi podemos obtener su nombre con el método getName()
        super(name);
    }

    @Override
    public void run() {
        for (int i=0; i<5; i++) {
          System.out.printf("[%s] -> C = %d\n",
            Thread.currentThread().getName(),
            i
          );
        }
        System.out.printf("[%s] -> TERMINADO.\n",
            Thread.currentThread().getName()
        );
    }
}
```

El programa principal debe crear y ejecutar dicho hilo de la siguiente manera

```java
public class HiloBasicoMain {
    public static void main(String[] args) throws InterruptedException {
        HiloBasico Hilo1 = new HiloBasico("Hilo 1");
        HiloBasico Hilo2 = new HiloBasico("Hilo 2");
        HiloBasico Hilo3 = new HiloBasico("Hilo 3");
        Hilo1.start();
        Hilo2.start();
        Hilo3.start();
        System.out.println("[Hilo main] -> TERMINADO."); 
    }
}
```

El resultado mostrado por pantalla será similar al siguiente:

```shell
[Hilo 1] -> C = 0
[Hilo 1] -> C = 1
[Hilo 1] -> C = 2
[Hilo 1] -> C = 3
[Hilo 1] -> C = 4
[Hilo 1] -> TERMINADO.
[Hilo 2] -> C = 0
[Hilo 2] -> C = 1
[Hilo 2] -> C = 2
[Hilo 2] -> C = 3
[Hilo 2] -> C = 4
[Hilo 2] -> TERMINADO.
[Hilo main] -> TERMINADO.
[Hilo 3] -> C = 0
[Hilo 3] -> C = 1
[Hilo 3] -> C = 2
[Hilo 3] -> C = 3
[Hilo 3] -> C = 4
[Hilo 3] -> TERMINADO.
```




### La interfaz Runnable

En el lenguaje Java, tener que heredar de la clase Thread para crear clases con esta funcionalidad comporta un problema no menor, ya que Java no soporta la herencia múltiple y por tanto nos resultará imposible hacer que la nueva clase extienda ninguna otro que no sea Thread. 
Es decir, nos quedará muy limitada la implementación de clases `Thread` usando herencia. Para evitar esta limitación podemos utilizar la Interfaz `Runnable`. La interfaz `Runnable` fuerza la existencia del método `run` pero no limita la herencia. De hecho, los objetos Runnable no tienen la capacidad de ejecutarse como un hilo independiente. Por eso es necesario utilizar un objeto `Thread` para gestionar su ejecución.

En el siguiente ejemplos vemos la creación de objetos mediante el uso de esta interfaz: En primer lugar creamos la clase que va a implementar cada hilo. Aquí no podemos usar los métodos de la clase Thread, por lo que para ponerle nombre al hilo debemos crear una constante de la clase para almacenarlo. 

```java
class HiloRunnable implements Runnable {

    private final String nombre;
    
    HiloRunnable (String nombre) {
        this.nombre = nombre;
        System.out.printf ("[%s] creado\n",
            this.nombre
        );
    }

    @Override
    public void run() {
        for (int i=0; i<5; i++) {
            System.out.printf("[%s] -> C = %d\n", 
                this.nombre,
                i
            );
        }
        System.out.printf("[%s] -> TERMINADO.\n",
            this.nombre
        ); 
    }

}
```

La aplicación principal lanza tres hilos diferentes:

```java
public class HiloRunnableMain {
    public static void main(String[] args) {
        //Creamos las instancias de los tres hilos
        Thread h1 = new Thread(
            new HiloRunnable("Hilo 1")
        );
        Thread h2 = new Thread(
            new HiloRunnable("Hilo 2")
        );
        Thread h3 = new Thread(
            new HiloRunnable("Hilo 3")
        );
            
        //Los ejecutamos
        h1.start();
        h2.start();
        h3.start();
        System.out.println("[Hilo main] -> TERMINADO."); 
    
    }

}
```

El resultado mostrado por pantalla será similar al siguiente:

```shell
[Hilo 1] creado
[Hilo 2] creado
[Hilo 3] creado
[Hilo 1] -> C = 0
[Hilo 1] -> C = 1
[Hilo 1] -> C = 2
[Hilo 1] -> C = 3
[Hilo 1] -> C = 4
[Hilo 1] -> TERMINADO.
[Hilo main] -> TERMINADO.
[Hilo 3] -> C = 0
[Hilo 3] -> C = 1
[Hilo 3] -> C = 2
[Hilo 3] -> C = 3
[Hilo 3] -> C = 4
[Hilo 3] -> TERMINADO.
[Hilo 2] -> C = 0
[Hilo 2] -> C = 1
[Hilo 2] -> C = 2
[Hilo 2] -> C = 3
[Hilo 2] -> C = 4
[Hilo 2] -> TERMINADO.
```



### EJEMPLO DE MÉTODOS:  `join`

Vamos a probar uno de los métodos que ofrece la clase Thread: `join`.  Para ello vamos a crear un ejemplo muy sencillo que  lanza dos hilos. Cada uno de ellos hace pausas de duración aleatoria de ente 10 y 500 milis, utilizando el método sleep de la clase Thread.


```java
import java.util.Random;

class HiloJoin implements Runnable { 
  private final String nombre;
  
  HiloJoin(String nombre) { 
   this.nombre = nombre;
  }
  
  @Override
  public void run () {
    System.out.printf("[%s] CREADO.\n", this.nombre); 
	Random r = new Random();
    for (int i =0; i<5; i++) {
      int pausa = 10 + r.nextInt(500-10); 
		  System.out.printf("[%s] hace pausa de %d ms.\n", this.nombre, pausa ); 
		  try {
			  Thread.sleep (pausa );
      } 
		  catch (InterruptedException e) {
        System.out.printf ("[%s] interrumpido.\n" , this.nombre ); 
      }
    }
    System.out.printf("[%s] terminado.\n", this.nombre); 
  }
}
```

Al llamar a `sleep` la ejecución del hilo se ve detenida, y durante ese periodo de tiempo se podría interrumpir, cosa que debe ser controlada con un `try-catch` tal y como veremos en el ejemplo posterior. 

El hilo principal utiliza el método `join` para esperar a que terminen los dos hilos lanzados, por lo que siempre terminará el último. 
 
```java 
public class HiloJoinMain {
    public static void  main (String[] args) {
        Thread h1 = new Thread(new HiloJoin("Hilo 1")); 
        Thread h2 = new Thread(new HiloJoin("Hilo 2")); 
        h1.start();
        h2.start();
        try {
            h1.join();
            h2.join();
        }
        catch (InterruptedException e) {
            System.out.println("[Hilo main] terminado.");
        }             
        System.out.println("[Hilo main] terminado.");
    }
}
```

El método `join` debe ser controlado mediante un `try-catch` por nuestra parte, pues alguno de los procesos a los que espera que finalicen puede interrumpirse y la aplicación principal debe dar respuesta a ese problema.

El resultado por consola será parecido al siguiente:

```shell
[Hilo 1] CREADO.  
[Hilo 2] CREADO.  
[Hilo 1] hace pausa de 219 ms. 
[Hilo 2] hace pausa de 138 ms. 
[Hilo 2] hace pausa de 326 ms. 
[Hilo 1] hace pausa de 388 ms.
[Hilo 2] hace pausa de 25 ms.
[Hilo 2] hace pausa de 157 ms.
[Hilo 1] hace pausa de 346 ms.
[Hilo 2] hace pausa de 351 ms.
[Hilo 1] hace pausa de 38 ms.
[Hilo 1] hace pausa de 200 ms.
[Hilo 2] terminado.
[Hilo 1] terminado.
[Hilo main] terminado.
```

El resultado variará en cada ejecución, pero la última linea siempre será `[Hilo main] terminado.`



### EJEMPLO: Interrumpir un hilo

Las interrupciones son una característica muy básica para la interacción de subprocesos que se puede entender como un simple mensaje de interrupción que un subproceso envía a otro subproceso. El subproceso receptor puede preguntar explícitamente si ha sido interrumpido llamando al método Thread.interrupted() o si se interrumpe implícitamente mientras pasa su tiempo dentro de un método como sleep() que lanza una excepción en caso de una interrupción.

Echemos un vistazo más de cerca a las interrupciones con el siguiente ejemplo de código:

```java
package hilos;

public class HiloInterrupt implements Runnable {
    public void run() {
        try{
            //Esperamos 290.00 años ;)
            Thread.sleep(Long.MAX_VALUE);
        }catch(InterruptedException e){
            System.out.printf("[%s] ← ¡Interrumpido por una exception!\n",
                    Thread.currentThread().getName()
            );
        }
        while(!Thread.interrupted()){
            // no hacemos nada
        }
        System.out.printf("[%s] ← Interrumpido por segunda vez.\n",
                Thread.currentThread().getName()
        );
    }
}
```

En primer lugar hemos creado una clase que implementa Runnable. Al ser lanzado, quedará  inactivo (sleep) durante mucho tiempo (alrededor de 290.000 años) si nada lo interrumpe. Es por ello que debemos comprobar si el hilo queda interrumpido por haber recibido una `InterruptedException` mediante un `try -> catch`. Luego queda en un bucle ocioso hasta que vuelve a recibir una interrupción y muestra el segundo mensaje.

A continuación vemos el código que incluye el Main:

```java
package hilos;

public class HiloInterruptMain {
    public static void main(String[] args) throws InterruptedException {
        Thread miHilo = new Thread(new HiloInterrupt(), "miHilo");
        miHilo.start();
        System.out.printf("[%s] ← Durmiendo en hilo main durante 5s...\n",
                Thread.currentThread().getName()
        );
        Thread.sleep(5000);
        System.out.printf("[%s] ← Interrumpiendo miHilo.\n",
                Thread.currentThread().getName()
        );
        miHilo.interrupt();
        System.out.printf("[%s] ← Durmiendo en hilo main durante 5s.\n",
                Thread.currentThread().getName()
        );
        Thread.sleep(5000);
        System.out.printf("[%s] ← Interrumpiendo miHilo.\n",
                Thread.currentThread().getName()
        );
        miHilo.interrupt();
    }
}
```

Dentro del método principal, primero lanzamos un nuevo hilo. Para terminar el programa antes de que pasen los 290.000 años , interrumpimos `miHilo` llamando a interrupt() en su variable de instancia. Esto provoca una InterruptedException dentro de la llamada de sleep() y se muestra en consola como "<- ¡Interrumpido por una excepción!". Habiendo registrado la excepción, el subproceso hace una espera ocupada hasta que se establece el indicador interrumpido en el subproceso. Esto nuevamente se establece desde el hilo principal llamando a interrupt() en la variable de instancia del hilo. 



En general, veremos en consola un resultado similar al siguiente:

```shell
[main] ← Durmiendo en hilo main durante 5s...
[main] ← Interrumpiendo miHilo.
[main] ← Durmiendo en hilo main durante 5s.
[miHilo] ← ¡Interrumpido por una exception!
[main] ← Interrumpiendo miHilo.
[miHilo] ← Interrumpido por segunda vez.
```


### REFERENCIA

- https://ioc.xtec.cat/materials/FP/Recursos/fp_dam_m09_/web/fp_dam_m09_htmlindex/WebContent/u1/a1/continguts.html
- [https://jarroba.com/multitarea-e-hilos-en-java-con-ejemplos-thread-runnable/](https://jarroba.com/multitarea-e-hilos-en-java-con-ejemplos-thread-runnable/)


