[ÍNDICE TEMA 3](I.INDICE.md)

# II. COMUNICACION Y SINCRONIZACION DE HILOS

## INTRODUCCIÓN

Lo más interesante de la programación multi-hilos es que éstos puedan comunicarse unos con otros. Para ello, la forma más común de comunicación entre ellos consiste usualmente en compartir un objeto, sobre el que los dos hilos pueden acceder de manera simultánea.

Al igual que los procesos, los **Thread** tienen herramientas que les permiten comunicarse mediante el intercambio de información a través de variables y objetos en memoria. Como todos los **Thread** pertenecen al mismo proceso, pueden acceder a la memoria asignada a dicho proceso de manera completa, pudiendo utilizar las cualquiera de las variables y objetos del mismo.

No obstante, cuando varios hilos manipulan de manera concurrente objetos comunes , puede ocurrir  que se generen resultados erróneos o incluso el bloqueo o finalización de la ejecución. Una de las soluciones que podemos usar es trabajar con monitores mediante la sincronización.

Esta compartición de datos nos puede servir para comunicar hilos. Utilizaremos objeto compartidas para indicar que un hilo ha terminado una cierta función o no y, por tanto, otro hilo podrá ejecutar la parte de código que estaba bloqueado por esta variable. También podemos compartir datos de un objeto común. Varios hilos deben modificar una propiedad de un objeto. En ambos casos, conviene sincronizar el acceso a los recursos compartidos para evitar inconsistencia de datos o interferencias. Cualquier variable accesible puede ser manipulada por un hilo o el hilo principal. Si este acceso no se controla puede provocar situaciones no esperadas o erróneas.

Java proporciona mecanismos para sincronizar los hilos que tratan estos problemas.

## PROBLEMA

Supongamos una clase que almacena un valor entero como atributo y que tiene tres métodos: uno para incrementar su valor y otro para decrementarlo y el tercero devuelve su valor. El constructor asigna un valor inicial al objeto contador. El código sería el siguiente:

```java
class Contador {
    private int c = 0 ; //atributo contador
   
    Contador (int c) { 
        this. c = c; 
    }
    public void incrementa () { 
        c = c + 1; 
    }
    public void decrementa () { 
        c = c - 1; 
    }
    public int valor() { 
        return c; 
    }
}
```

Ahora vamos a crear dos hilos para probar el objeto compartido. Para ello, definimos dos clases que extienden *Thread*. 

- En la clase **HiloA** se usa el método del objeto contador que incrementa en uno su valor.
- En la clase **HiloB** se usa el método del objeto contador que decrementa en uno su valor.
- Se añade un **sleep()** intencionadamente para probar que un hilo se duerma y mientras el otro haga otra operación con el contador, así la CPU no realiza de una sola vez todo un hilo y después otro y podemos observar mejor el efecto

El hiloA quedaría así: 
```java
class HiloA extends Thread {

  private Contador contador;
  
  public HiloA (String n, Contador C) {
    setName (n) ;
    contador = C;
  }
  
  public void run () {
		for (int j = 0 ; j < 300; j++) {
				contador.incrementa (); //incrementa el contador
				try {
						sleep(100) ;
					}
					catch (InterruptedException e) {}
				}
			}
			System.out.println( getName() + "Contador vale " + 
			    contador.valor());
  }
}
```

Ahora creamos HiloB

```java
class HiloB extends Thread {

  private Contador contador;
  
  public HiloB (String n, Contador C) {
    setName (n) ;
    contador = C;
  }
  
  public void run () {
		for (int j = 0 ; j < 300; j++) {
				contador.decrementa (); //incrementa el contador
				try {
						sleep(100) ;
					}
					catch (InterruptedException e) {}
				}
			}
			System.out.println( getName() + "Contador vale " + 
			    contador.valor());
  }
}
		

```

Únicamente nos falta crear la clase donde desarrollar el método **main()**, en la que inicialmente vamos a crear un objeto de la clase Contador y le vamos a asignar un valor inicial de 100. A continuación, crearemos los dos hilos (A y B) pasándoles dos parámetros: un nombre y el objeto Contador. Y por último los lanzaremos:

```java
public class CompartirInf1 {
  
  public static void main (String[] args)
    Contador cont = new Contador (100) ;
	   HiloA a = new HiloA("HiloA", cont) ;
	   HiloB b = new HiloB("HiloB", cont) ;
	   a.start() ;
       b.start() ;
 }
}
```

Al ejecutar main(), vemos que los resultados son impredecibles, puesto que al usar los dos manera simultánea el mismo objeto, si ambos lo usan y lo sueltan al mismo tiempo, el valor de cont toma valores impredecibles:

```shell
HiloB contador vale 100
HiloA contador vale 247

HiloB contador vale 100
HiloA contador vale 400

HiloA contador vale 343
HiloB contador vale 100
```

Este problema en intolerable en programación, por lo que hemos de articular mecanismos para que los dos hilos sean capaces de acceder de manera coordinada (sincronada) al objeto compartido.

## Sección crítica

Se denomina **sección crítica** a una región de código en la cual se accede de forma ordenada a variables y recursos compartidos, de forma que se puede diferenciar de aquellas zonas de código que se pueden ejecutar de forma asíncrona. Este concepto se puede aplicar tanto a hilos como a procesos concurrentes, la única condición es que compartan datos o recursos.

Cuando un proceso está ejecutando su sección crítica, ningún otro proceso puede ejecutar su correspondiente **sección crítica**, ordenando de esta forma la ejecución concurrente. Esto significa:

- Si otro proceso quiere ejecutar su sección crítica, se bloqueará hasta que el primer proceso finalice la ejecución de su sección crítica.
- Se establece una relación antes-después en el orden de ejecución de la sección crítica. Esto garantiza que los cambios en los datos son visibles a todos los procesos.

El problema de la sección crítica consiste en diseñar un protocolo que permita a los procesos cooperar. Cualquier solución al problema de la sección crítica debe cumplir:

- **Exclusión mutua**: si un proceso está ejecutando su sección crítica, ningún otro proceso puede ejecutar su sección crítica. Es la característica principal.
- **Progreso**: si ningún proceso está ejecutando su sección crítica y hay varios procesos que quieren entrar en su sección crítica, solo aquellos procesos que están esperando para entrar pueden participar en la decisión de quién entra definitivamente. Esta decisión no puede posponerse indefinidamente, esperando a un proceso más prioritario, por ejemplo.
- **Espera limitada**: debe existir un número limitado de veces que se permite a otros procesos entrar en su sección crítica después de que otro proceso haya solicitado entrar en la suya y antes de que se le conceda. En caso contrario se produciría inanición.

En definitiva, el código correspondiente a la sección crítica debe terminar en un tiempo determinado y deben existir mecanismos internos del sistema que eviten la inanición, para que el resto de procesos solo tengan que esperar un período determinado de tiempo para entrar a ejecutar sus correspondientes secciones críticas.

## SOLUCIÓN I AL PROBLEMA A: **MÉTODOS SINCRONIZADOS**

Una segunda forma de evitar que esto suceda es hacer que las operaciones de incremento y decremento del objeto contador se hagan de forma atómica y excluyente, es decir, si estamos realizando la suma nos aseguramos que nadie realice la resta hasta que no terminemos la suma.

Para ello tenemos que identificar qué métodos van a acceder a objetos comparativos y añadirles la palabra **synchronized** en la definición. 

El uso de métodos sincronizados implica que no es posible invocar dos métodos sincronizados del mismo objeto a la vez. Cuando un hilo está ejecutando un método sincronizado de un objeto, los demás hilos que invoquen a métodos sincronizados para el mismo objeto se bloquean hasta que el primer hilo termine con la ejecución del método.

En nuestro caso , deberíamos modificar la clase "Contador" y sincronizar los métodos que sean compartidos por los hilos, quedando la clase así:

```java
class Contador {
  private int c = 0 ; //atributo contador
 
  Contador (int c) { 
  	this. c = c; 
  }
  public synchronized void incrementa () { 
  	c++;
  }
  public synchronized void decrementa () { 
  	c--; 
  }
  public synchronized int valor() { 
  	return c; 
  }
} // CONTADOR
```

## SOLUCIÓN II AL PROBLEMA A: **BLOQUES SINCRONIZADOS**

Por último, podemos optar por no tocar el código del objeto compartido y atacar directamente sobre los hilos.

De igual manera tenemos que identificar qué bloques de código van a trabajar con el  y añadirles la palabra **synchronized**. Java utiliza los bloques synchronized para implementar las [secciones críticas](https://es.wikipedia.org/wiki/Secci%C3%B3n_cr%C3%ADtica) y así lograr la exclusión mutua de los procesos respecto a la variable compartida. En nuestro caso, el bloque incluye todo el código dentro del método *run*(), quedando de la siguiente manera los dos hilos:

```java
class HiloA extends Thread {

  private Contador contador;
  
  public HiloA (String n, Contador C) {
    setName (n) ;
    contador = C;
  }
  
  public void run () {
	  synchronized (contador) {
			for (int j = 0 ; j < 300; j++) {
				contador.incrementa (); //incrementa el contador
				try {
						sleep(100) ;
					}
					catch (InterruptedException e) {}
				}
			}
				System.out.println( getName() + "Contador vale " + 
			    contador.valor());
    }
  }
}
```

```java
class HiloB extends Thread {

  private Contador contador;
  
  public HiloB (String n, Contador C) {
    setName (n) ;
    contador = C;
  }
  
  public void run () {
	  synchronized (contador) {
			for (int j = 0 ; j < 300; j++) {
				contador.decrementa (); //incrementa el contador
				try {
						sleep(100) ;
					}
					catch (InterruptedException e) {}
				}
			}
				System.out.println( getName() + "Contador vale " + 
			    contador.valor());
    }
  }
}
```

Como vemos, el uso de bloques o métodos dependerá de cuanto código sincronizado vayamos a manejar, aunque como norma general, se recomienda evitar la sincronización de bloques de código y sustituirlas siempre que sea posible por la sincronización de métodos, teniendo en cuenta que el uso de *synchronized* consume muchos recursos y no siempre producirá un resultado óptimo.

### SEGUNDO PROBLEMA

Para ver otro de los problemas de compartir información, vamos a ver un ejemplo que simula una cuenta de banco y varios clientes.

Para ello, se define la clase **Cuenta**, define un atributo saldo y tres métodos, uno devuelve el valor del saldo, otro, resta al saldo una cantidad y el tercero realiza las comprobaciones para hacer la retirada de dinero, es decir que el saldo actual sea >= que la cantidad que se quiere retirar; el constructor inicia el saldo actual. También se añade un *sleep()* intencionadamente para probar que un hilo se duerma y mientras el otro haga las operaciones:

```java
class Cuenta {

    private int saldo;

    Cuenta(int s) {
        saldo = s;
    }

    int getSaldo() {
        return saldo;
    }

    void restar(int cantidad) {
        saldo = saldo - cantidad;
    }

    void RetirarDinero(int cant, String nom) {
        if (getSaldo() >= cant) {
            System.out.println(nom + ": SE VA A RETIRAR SALDO (ACTUAL ES: " + getSaldo() + ")");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }

            restar(cant);

            System.out.println("\t" + nom + " retira =>" + cant + " ACTUAL(" + getSaldo() + ")");
        } else {
            System.out.println(nom + " No puede retirar dinero, NO HAY SALDO(" + getSaldo() + ")");
        }
        if (getSaldo() < 0) {
            System.out.println("SALDO NEGATIVO => " + getSaldo());
        }
    }//retirar
}
```

Ahora creamos los hilos que simulan los clientes. Para ello se crea la clase **SacarDinero** que extiende Thread y usa la clase Cuenta para retirar el dinero. El constructor recibe una cadena, para dar nombre al hilo; y la cuenta que será compartida por varias personas. En el método *run()* se realiza un bucle donde se invoca al método *RetirarDinero*() de la clase **Cuenta** varias veces con la cantidad a retirar, en este caso siempre es 10, y el nombre del hilo:

```java
class SacarDinero extends Thread {

    private Cuenta c;

    public SacarDinero(String n, Cuenta c) {
        super(n);
        this.c = c;
    }

    public void run() {
        
        for (int x = 1; x <= 4; x++) {
                c.RetirarDinero(10, getName());
        }
    }// run	
}
```

Por último, en la clase que contiene el método *main*() se define un objeto de la clase **Cuenta** y se le asigna un saldo inicial de 40. A continuación se crean dos objetos de la clase **SacarDinero**, imaginemos que son las dos personas que comparten la cuenta, y se inician los hilos:

```java
public class Principal {
    public static void main(String[] args) {
        Cuenta miCuenta = new Cuenta(50);
        SacarDinero Pepe = new SacarDinero("Pepe" ,miCuenta );
        SacarDinero Juan = new SacarDinero("Juan" ,miCuenta );
        
        Pepe.start();
        Juan.start();
    }
}
```

Si ejecutamos el programa, en alguna de las ejecuciones puede mostrar inconsistencias como que nos deje sacar dinero con saldo 0:

```
Pepe: SE VA A RETIRAR SALDO (ACTUAL ES: 50)
Juan: SE VA A RETIRAR SALDO (ACTUAL ES: 50)
        Juan retira =>10 ACTUAL(30)
Juan: SE VA A RETIRAR SALDO (ACTUAL ES: 30)
        Pepe retira =>10 ACTUAL(30)
Pepe: SE VA A RETIRAR SALDO (ACTUAL ES: 30)
        Pepe retira =>10 ACTUAL(20)
Pepe: SE VA A RETIRAR SALDO (ACTUAL ES: 20)
        Juan retira =>10 ACTUAL(20)
Juan: SE VA A RETIRAR SALDO (ACTUAL ES: 20)
        Juan retira =>10 ACTUAL(10)
Juan: SE VA A RETIRAR SALDO (ACTUAL ES: 10)
        Pepe retira =>10 ACTUAL(10)
Pepe: SE VA A RETIRAR SALDO (ACTUAL ES: 10)
        Juan retira =>10 ACTUAL(0)
        Pepe retira =>10 ACTUAL(-10)
SALDO NEGATIVO => -10
```

Para evitar esta situación la operación de retirar dinero, método RetirarDinero() de la clase Cuenta, debería ser atómica e indivisible, es decir si una persona está retirando dinero, la otra debería ser incapaz de retirarlo hasta que la primera haya realizado la operación. Para ello declaramos el método como **synchronized**. 

Sincronizar métodos permite prevenir inconsistencias cuando un objeto es accesible desde distintos hilos: si un objeto es visible para más de un hilo, todas las lecturas o escrituras de las variables de ese objeto se realizan a través de métodos sincronizados.

Para más información sobre join() puedes consultar el siguiente enlace:
[https://www.geeksforgeeks.org/java-concurrency-yield-sleep-and-join-methods/](https://www.geeksforgeeks.org/java-concurrency-yield-sleep-and-join-methods/)