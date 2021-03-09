|   Estado   | Descripcion           |
|------------|-----------------------|
| Ejecutable (runnable) | Un hilo esta en estado ejecutable cuando se ha ejecutado el método start sobre él, siempre que no esté bloqueado ni muerto. Un hilo ejecutable puede estar ejecutandose o no dependiendo del reparto de tiempos de la CPU. |
| Bloqueado (blocked) | Un hilo que esta en estado ejecutable puede pasar a bloqueado si se ejecuta sobre el método **sleep()** o el método **wait()**. También puede estar bloqueado por estar esperando por una operacion de entrada salida, o bien porque intenta actuar sobre un objeto que esta bloqueado por otro hilo. |
| Muerto (dead) | Un método esta en estado muerto cuando ha finalizado la ejecucion del método **run()** de la clase de destino. O bien se ha ejecutado sobre él el método interrupt. |

Cuando una aplicacion genera varios hilos que actúan sobre un mismo
objeto se pueden producir colisiones que alteren el normal funcionamiento del programa.

Una técnica que evita esta colision es declarar el objeto como sincronizado, con lo cual todos los hilos de ejecucion que incidan sobre el objeto sincronizado bloquean éste mientras actuan, y lo liberan cuando termina, para que otro hilo posteriormente lo gestione. Se puede sincronizar un objeto, tal como indican las siguientes sentencias:

```java
Synchronized (campoTexto) {
	[código que actua sobre el objeto]
}
```

También se puede sincronizar un método:

```java
Synchronized void miMetodo () {
[código del método]
}
```

Para una buena sincronización de un objeto, o de una variable, es necesario que todos los métodos que puedan actuar sobre éstos deben
estar sin-
cronizados. Si todos los métodos de una clase estan sincronizados se
garantiza que sobre un objeto de la clase no puedan actuar dos
métodos

simultaneamente, si bien pudiera ocurrir que sobre varios objetos
distin-
br m nizada.
tos, estén actuando simultaneamente varios hilos de forma
sincronizadd

También se podria crear métodos synchronized
static con lo que
se










