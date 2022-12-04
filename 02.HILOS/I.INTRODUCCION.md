[ÍNDICE TEMA 3](02.HILOS/I.INDICE.md)


## DEFINICIÓN 

Los hilos, o *Threads*, son la unidad básica de utilización de la CPU, y más concretamente de un core del procesador. Así un thread se puede definir como la secuencia de código que está en ejecución circunscrita al contexto de trabajo de un proceso.

La diferencia entre un hilo y un proceso es que los procesos son independientes, tienen su espacio de direcciones de memoria separado y compiten en igualdad de condiciones entre ellos por el uso de la CPU, tanto si comparten padre como si son independientes.

Sin embargo, en un hilo, lo que hace el proceso es crear un espacio privado de direcciones dentro de su espacio de direcciones, por lo que la pugna por competir por el tiempo de procesador se verá en “inferioridad de condiciones” respecto a un proceso padre–hijo.

Algunas características de los hilos son:

- Un hilo no puede existir independientemente de un proceso.
- Un hilo no puede ejecutarse por si solo.
- Dentro de cada proceso puede haber varios hilos ejecutándose.
- Un único hilo es similar a un programa secuencial; por si mismo no nos ofrece nada nuevo. Es la habilidad de ejecutar varios hilos dentro de un proceso lo que ofrece algo nuevo y útil,
ya que cada uno de estos hilos puede ejecutar actividades diferentes al mismo tiempo.

Como consecuencia de compartir el espacio de memoria, los hilos aportan las siguientes
ventajas sobre los procesos:

- Se consumen menos recursos en el lanzamiento y la ejecución de un hilo que en el
lanzamiento y ejecución de un proceso.
- Se tarda menos tiempo en crear y terminar un hilo que un proceso.
- La conmutación entre hilos del mismo proceso es bastante más rápida que entre procesos.

Es por esas razones, por lo que a los hilos se les denomina también procesos ligeros.

Se aconseja utilizar hilos en una aplicación cuando:

- La aplicación maneja entradas de varios dispositivos de comunicación.
- La aplicación debe poder realizar diferentes tareas a la vez.
- Interesa diferenciar tareas con una prioridad variada. Por ejemplo, una prioridad alta
para manejar tareas de tiempo crítico y una prioridad baja para otras tareas.
- La aplicación se va a ejecutar en un entorno multiprocesador.

![IMAGEN 1: ESTRUCTURA DE HILOS Y PROCESOS](02.HILOS/IMAGENES/img_01.png)

Hablamos de multihilo cuando se ejecutan varias tareas paralelamente dentro de una misma aplicación. En este caso no son procesos diferentes sino que dichas tareas se ejecutan dentro del mismo proceso del Sistema Operativo. A cada una de estas tareas se le conoce como hilo o thread (en algunos contextos también como procesos ligeros).

El uso de varios hilos dentro de una aplicación se conoce como **Programación Concurrente**.

## ESTADOS DE UN HILO

Una vez que hemos visto una pequeña introducción a los hilos, podemos entrar a ver cómo funcionan a nivel organizativo:

A) Inicialmente, se crea la instancia de la clase que implementa el hilo (Thread).

B) Al invocar al método **start()** el hilo pasa a estado *ejecutable* (*Runnable*) a la espera de que el gestor de JVM le conceda acceso y permisos para su ejecución.

C) Cuando eso sucede, el hilo pasa a estado *ejecutándose*.

D) De ese estado puede salir a un estado *no ejecutable* en caso de se innvoque la método *sleep* o parecido.

E) El hilo termina cuando acaba de ejecutarse el código del mismo o bien se invoca al método *stop*. (esta segunda opción está descartada por no poder usar el método)

Gráficamente el resultado es el siguiente:

![IMAGEN 2: Estads de un proceso](02.HILOS/IMAGENES/img_02.png)

## Relación entre proceso e hilo
Recuerde que los procesos no comparten memoria entre ellos, son independientes, traen información sobre su estado e interactúan con otros procesos a través de mecanismos de comunicación dados por el sistema. Esta comunicación o el cambio de estado de un proceso son mecanismos costosos por el microprocesador. Por eso se llaman entidades pesadas. Los hilos, en cambio, comparten recursos. El cambio de estado y ejecución se realiza mucho más rápidamente y pueden comunicarse entre ellos utilizando los datos de la memoria que comparten. El tiempo de uso del microprocesador dedicado a la gestión de hilos es bastante despreciable.

El hecho de que los hilos de ejecución de un mismo proceso compartan los recursos hace que cualquier hilo pueda modificarlos. Si un hilo modifica un dato en la memoria, el resto de los hilos tienen acceso al nuevo dato de forma inmediata. Cuando hay un proceso ejecutándose, al menos siempre hay un hilo en ejecución. Hablamos de procesos multihilo cuando se ejecutan varios hilos concurrentemente, realizando diferentes tareas y colaborando entre ellos.

Cuando un proceso finaliza, todos sus hilos también lo hacen. De forma equivalente, cuando finalizan todos los hilos de un proceso, el proceso también termina y todos sus recursos son liberados.

Los sistemas operativos actuales admiten la concurrencia, es decir, pueden ejecutar distintos procesos a la vez. Podemos estar navegando por Internet con un navegador, escuchando música con un reproductor y, con un editor de texto, redactando un documento. Cada aplicación que se ejecuta es un proceso y cada uno de estos procesos tiene al menos un hilo de ejecución, pero podrían tener más de uno. El editor de texto podría tener un hilo que se encargara de ver qué tecla estamos picando, otro que vaya comprobando la ortografía y otro que de vez en cuando se active para guardar el documento. Hay que ver que un hilo pertenece a un proceso y que los diferentes hilos pueden realizar tareas diferentes dentro de su proceso.

## REFERENCIAS Y RECURSOS USADOS
* Imagen 1:
    * [Libro "Programación de Servicios y Procesos" Ed. ra-ma](https://www.ra-ma.es/libro/programacion-de-servicios-y-procesos-grado-superior_49240/)
* Imagen 2:
    * [https://www.educative.io/edpresso/how-to-use-threads-in-java](https://www.educative.io/edpresso/how-to-use-threads-in-java)
