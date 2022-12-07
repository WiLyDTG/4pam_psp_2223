# IV. PLANIFICACIÓN DE HILOS

Una vez que ya conocemos cómo pueden compartir información los hilos a través de los bloques y métodos sincronizados, vamos a estudiar cómo podemos activar o desactivar hilos de manera organizada en nuestro código, realizando una planificación efectiva de los mismos.

La planificación de hilos precisa de una serie de instrucciones que hagan que algunos hilos bloqueen o liberen un recurso compartido cuando se cumpla cierta condición. Java nos proporciona métodos que hacen que los hilos esperen y continúen su ejecución. Son los métodos que describimos a continuación. Al margen de la sincronización automática, vista en los epígrafes anteriores, se puede lograr una sincronización específica gestionada por el programador que en algunos casos será más eficiente. La gestión de la sincronización se realiza con los métodos `wait`  y `notify` de la clase `Object`. 

El uso de **synchronized**, como hemos vista anteriormente, garantiza que la información que comparten no se vera comprometida y mantendrá la consistencia. Lo que no se garantiza es el orden de ejecución de los hilos, puesto que esta tarea pertenece a la JVM y además podemos incurrir en interbloqueos que hagan que los hilos se bloqueen entre sí y no permitan que el programa concluya de manera correcta.

Java, por defecto, utiliza un planificador apropiativo. Si en un momento dado un hilo que tiene una prioridad mayor a cualquier otro hilo que se está ejecutando, pasa al estado "*Ejecutable*", entonces el sistema elige a este nuevo hilo para su ejecución. Si los hilos tienen la misma prioridad, será el planificador el que asigne a uno u otro el núcleo correspondiente para su ejecución utilizando tiempo compartido, en caso de que el sistema operativo subyacente lo permita.

### La clase Object

La clase `Object` es muy extensa. Aquí sólo nos centraremos en los métodos que nos proporciona, relacionados con los hilos. Son métodos que nos permiten la comunicación y manipulación de hilos. Esta clase, jerárquicamente, se encuentra en la parte superior de todas las clases de Java, por tanto cada clase de Java hereda su funcionalidad. Cada nueva clase incluye los siguientes métodos:

- `wait()`: este método hace que un hilo de ejecución pase a un estado de espera hasta que se le notifique que puede continuar la ejecución -otro hilo llame al método `notify()` o `notifyAll()` del mismo objeto-.
- `notify()`:  despierta sólo a uno de los hilos que realizó una llamada a wait() sobre el mismo objeto notificándole de que ha habido un cambio de estado sobre el objeto. Si varios hilos están esperando el objeto, solo uno de ellos es elegido para ser despertado, la elección es arbitraria.
- `notifyAll()`: funciona de forma similar a notify(), pero notifica que pueden continuar la ejecución todos los hilos en espera. Los hilos entrarán en pugna y uno de ellos se aporopiará.

Estos métodos son utilizados cuando ejecutamos la programación concurrente de diferentes hilos (programación multihilo), y sirven para hacer esperar un hilo que otro acabe de realizar alguna tarea. También que, una vez terminada esta tarea, notifique al hilo a la espera de que puede continuar su ejecución. Estos métodos únicamente pueden ser llamados dentro de métodos sincronizados:

```java
synchronized public void metodeSincronizado(){
   //... metodos wait() , notify() y notifyAll()
}
```

O dentro de  bloques de código sincronizados:

```java
public void metode(){
   synchronized (this) {
       //... metodos wait() , notify() y notifyAll()
     }
//...
}
```

> ¿Cuándo utilizar **notifyAll()** y **notify()**?
Debe utilizarse **notify()** cuando se quiere que continúe solo un hilo. Si no, debería utilizarse **notifyAll()**. Si están bien programados, los hilos reanudados volverán a comprobar si pueden continuar su ejecución y, si no es así, volverán a ejecutar **wait()** para esperar una nueva ocasión. 
Por tanto, no debería suponer ningún problema que más de un hilo se reanude con **notifyAll()**. Pero que esto sea aceptable o no depende del programa. El uso de **notify()** en lugar de **notifyAll( )** podría hacer que solo se reanude un hilo que, una vez verificado el estado, decida que no puede continuar y haga **wait()**, mientras que otro de los hilos podría haber continuado. Esto podría incluso provocar que uno o más hilos queden indefinidamente a la espera para continuar de una notificación que no llega nunca. Esto no es un interbloqueo o **deadlock**. De hecho, solo hay un objeto de bloqueo implicado. Los hilos no están a la espera de que se libere
un objeto de bloqueo, sino de una notificación para seguir que no llega nunca.

Como ejemplo práctico, vamos a ver un programa que implementa dos hilos que muestran por pantalla un string de un objeto compartido.

Es una simple clase que muestra una cadena que recibe como parámetro. Vamos a crear a continuación una clase que extiende Thread y que crea un objeto de esta clase y llama diez veces al método Muestra para mostrar la cadena 10 veces por pantalla.

```java
class ObjetoCompartido {	
  public void Muestra (String s) 
  { 
		System.out.print(s); 
	}
}// ObjetoCompartido
```

Este objeto lo van a compartir los dos hilos que implementan la siguiente clase:

```java
class HiloCadena extends Thread {
    private ObjetoCompartido objeto;
    String cad;

    public HiloCadena (ObjetoCompartido c , String s) {
        this.objeto = c;
        this.cad = s;
    } 

    public void run() {
       for (int j = 0; j < 10; j++) {
				 objeto.Muestra(cad);
			 }
		   System.out.println();
    }//run
}//HiloCadena
```

El programa principal creará los dos hilos y los lanzará

```java
public class  BloqueoHilos {
    public static void main(String[] args)
    {
      {
        ObjetoCompartido com = new ObjetoCompartido();
        HiloCadena a = new HiloCadena (com, "A");
        HiloCadena b = new HiloCadena (com, "B");
        a.start(); 
        b.start();
      } 
    }
}//BloqueoHilos
```

Si ejecutamos este código, el resultado será el siguiente, en la mayoría de los casos:

```shell
AAAAAAAAAA
BBBBBBBBBB
```

Esto es asi porque el planificador de procesos del sistema ejecuta el hilo "a" de manera completa, y cuando este termina, le cede el turno al hilo "b". No existe por tanto ninguna opción por nuestra parte de controlar qué hilo se ejecuta.

Esto se puede solucionar haciendo que cada hilo ejecute una vez el método "Muestra", quede bloqueado y notifique al resto que puede usar la CPU

```java
public void run() {
    synchronized (objeto) {
        for (int j = 0; j < 10; j++) {
            objeto.Muestra(cad);
            objeto.notify(); //aviso que ya he usado el objeto
            try {
			  //el hilo queda a la espera de que alguien lo despierte 
              objeto.wait(); 
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        objeto.notify(); 
    } //fin del bloque synchronized
    System.out.printf("\n[%s] finalizado" , cad);
}
```

El resultado con este código será el siguiente:

![5e9fec3cd8881188e3f096598500d96b.png](5e9fec3cd8881188e3f096598500d96b.png)

Este sencillo caso se garantiza la alternancia de los dos procesos sin necesidad de que se cumpla ninguna condición, ya que son solamente dos hilos y esta alternancia de turnos está garantizada.

Si tenemos un mayor número de hilos y queremos provocar la alternancia entre ellos cuando un hilo quede bloqueado, es preciso que el hilo que no puede continuar libere temporalmente el bloqueo de la **sección crítica** mientras espera a que algún otro hilo modifique la condición y le avise de esa modificación. Este proceso debe ser atómico y cuando el hilo retoma la ejecución lo hace en el mismo punto donde lo dejó (dentro de la sección crítica).

Para ello hacemos uso de los **guarded blocks o bloques de código vigilados** por determinada condición. Hasta que la condición no se cumpla, no hay que pasar a ejecutar dicho bloque de código.

En este caso lo importante es dejar libre el procesador durante el tiempo de espera. Así, el siguiente código sería ineficiente puesto que estaría ocupando la CPU durante la espera:

```java
//A EVITAR
	synchronized (objeto) {	    
	    while(!condicion) {} // Se detiene aquí, 
	    //El bucle se aporpia del objeto y se queda 
	    //comprobando iterativamente la condición
	     
	    System.out.println("La condición se ha cumplido");
	    
	    ...
	    acciones con el objeto compartido
	    ...
	}
```

La forma correcta es invocar el método *wait*(), lo que bloqueará el hilo hasta que otro haga una llamada a notify(). Sin embargo hay que volver a hacer la comprobación de la condición, porque la notificación no significará necesariamente que se haya cumplido la condición. 

Obviamente el método *wait*() debe ir dentro de un bucle while que comprueba la condición, ya que el método solo se ejecutará cuando se cumpla la condición y el hilo esté activo:

```java
public synchronized bloqueVigilado() {
    while(!condicion) {
        try {
            wait(); // desocupa la CPU
        } catch (InterruptedException e) {}
    }
    System.out.println("La condición se ha cumplido");
}
```

Recordamos que todo esto debe ir dentro de un bloque  *synchronized,*  porque las llamadas a wait() y a notify() siempre operan sobre la variable cerrojo (lock) del objeto (desde distintos hilos) y por tanto debe hacerse de forma sincronizada.

## EJEMPLO: PRODUCTOR/CONSUMIDOR

En los [tutoriales oficiales de Java](http://docs.oracle.com/javase/tutorial/essential/concurrency/index.html) se incluye el siguiente ejemplo clásico del Productor/Consumidor. En este problema hay dos hilos que producen y consumen simultáneamente datos de un mismo buffer o misma variable. El problema es que no se interbloqueen, al tiempo que si el buffer está vacío, el consumidor se quede en espera, y si el buffer está lleno, el productor quede en espera.

El siguiente código es el ejemplo típico de un productor, en el que cada cierto número aleatorio de milisegundos va poniendo en el atributo "*message*" del objeto compartido un texto. Por último asigna la cadena "DONE".

```java
import java.util.Random;

public class Producer extends Thread {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        //El array de cadenas que vamos a intercambiar
        String listaMensajes[] = {
                "El mar es azul",
                "Como tus ojos",
                "En ellos me sumerjo",
                "Y vuelvo a la vida"
        };
        Random random = new Random();

        for (int i = 0; i < listaMensajes.length; i++) {
            drop.put(listaMensajes[i]);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }
        drop.put("FINALIZADO");
    }
}
```

Y el siguiente es el código del consumidor , que va leyendo mensajes del objeto compartido y cuando lea "DONE", termina su ejecución. En cada lectura espera un número aleatorio de milisegundos.

```java
import java.util.Random;

public class Consumer extends Thread {
    private Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        for (String message = drop.take();
             !message.equals("DONE");
             message = drop.take()) {
            System.out.format("MENSAJE RECIBIDO: %s%n", message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }
    }
}
```

Ambos operan sobre la clase compartida Drop, que es la que deja y extrae mensajes. Su funcionamiento es muy sencillo, tiene un atributo *empty* que se modifica cada vez que se lee o se escribe un texto, y un atributo *message* que se modifica cuando se invoca al método *put.* Ambos métodos deben ser sincronizados.

```java
public class Drop {
    // Mensaje enviado del Productor al Consumidor
    private String message;
    // true Si el consumidor debe esperar que el Productor envíe el mensaje
    // false si el productor debe esperar al consumindor para recuperar el mensaje
    private boolean empty = true;

    public synchronized String take() {
        // Espera a que haya un mensaje dispoinble
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        // Cambia el status.
        empty = true;
        // Notify al productor el cambio de estatus
        notifyAll();
        return message;
    }

    public synchronized void put(String message) {
        // Espera a que el mensaje sea leido/recuperado
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        // Cambia el  status.
        empty = false;
        // Guarda el mensaje
        this.message = message;
        // Notifica al consumidor el cambio de estatus
        notifyAll();
    }
}

```

El método main() sólo crea el objeto común, los dos hilos y los lanza:

```java
public class ProducerConsumerExample {
    public static void main(String[] args) {
        Drop drop = new Drop();
        Producer productor = new Producer(drop);
        Consumer consumidor = new Consumer(drop);
        productor.start();
        consumidor.start();
    }
}
```

El resultado será el siguiente:

![38cfdb1add2e846da768749d33b3a0e3.png](38cfdb1add2e846da768749d33b3a0e3.png)

Como vemos, los mecanismos de sincronización garantizan la exclusión mutua en el acceso a los recursos compartidos por varios hilos.
