## PLAZO DE ENTREGA: 15-12-2022

## PRIORIDAD DE HILOS

Escribe una clase llamada *Orden* que cree dos hilos y fuerce que la escritura del segundo sea siempre anterior a la escritura por pantalla del primero.

Ejemplo de ejecución:

```bash
Hola, soy el hilo creado en segundo lugar
Hola, soy el hilo creado en primer lugar
```
> NOTA: No se puede usar *sleep()* para demorar la ejecución del primer hilo

## CARRERA DE RELEVOS

Crea una aplicación en JAVA que simule una carrera de relevos con los siguientes requisitos:

- Habrá una clase **arbitro** que tendrá el atributo *num_corredores*
- Habrá una clase que extienda *thread* que llamaremos **corredor**. El método *run()* inicialmente dejará el hilo en espera de recibir alguna señal para comenzar a correr. Una vez creados los threads, se indicará que comience la carrera, con lo que uno de los threads deberá empezar a correr.
- Cuando un thread termina de correr pone algún mensaje en pantalla y espera un par de segundos, pasando el testigo a otro de los hilos para que comience a correr, y terminando su ejecución (la suya propia).
- Cuando el último thread termine de correr, el padre mostrará un mensaje indicando que todos los hijos han terminado.

Ejemplo de ejecución:

```bash
Todos los hilos creados.
Doy la salida!
Soy el thread 1, corriendo ...
Terminé. Paso el testigo al hijo 2 
Soy el thread 2, corriendo ...
Terminé. Paso el testigo al hijo 3 
Soy el thread 3, corriendo ...
Terminé. Paso el testigo al hijo 4 
Soy el thread 4, corriendo ...
Terminé!
Todos los hilos terminaron.
```

## BANCO

Crea una aplicación con las siguientes clases:

- ***Saldo***, una clase con un atributo que nos indica el saldo, el constructor que da un valor inicial al saldo.  La clase posee varios métodos: uno para obtener el saldo y otro para dar valor al saldo, en estos dos métodos añade un **sleep**() aleatorio. Y otro método que reciba una cantidad y se la añada al saldo, este método debe informar de quién añade cantidad al saldo, la cantidad que añade, el estado inicial del saldo (antes de añadir la cantidad) y el estado final del saldo después de añadir la cantidad. Define los parámetros necesarios que debe de recibir este método y defínelo como  **synchronized**.
- **Cliente**, clase que extiende **Thread.** Desde el método *run()* hemos de usar el método de la clase *Saldo* que añade la cantidad al saldo. Averigua los parámetros que se necesita en el constructor. No debe visualizar nada en pantalla.
- **Main**, El programa principal crea un objeto *Saldo* asignándole un valor inicial. Visualiza el saldo inicial. Crea varios hilos que compartan ese objeto *Saldo.* A cada hilo le damos un nombre y le asignamos una cantidad. Lanzamos los hilos y esperamos a que finalicen para visualizar el saldo final del objeto *Saldo.*

## ADIVINA NÚMERO

Se trata de simular el juego para adivinar un número. Se crearán varios hilos, los hilos son los jugadores que tienen que adivinar el número. Habrá un árbitro que generará el número a adivinar, comprobará la jugada del jugador y averiguará a qué jugador le toca jugar. El número tiene que estar comprendido entre 1 y 10, usa la siguiente fórmula para generar el número: 

```bash
1 + (int) (10 *Math.randomO);
```

Se definen 3 clases:

- **Árbitro**: Contiene el número a adivinar, el turno y muestra el resultado. 
Se definen los siguientes atributos: el número total de jugadores, el tumo, el número a adivinar y si el juego acabó o no. 
En el constructor se recibe el número de jugadores que participan y se inicializan el número a adivinar y el turno. 
Tiene varios métodos: uno que devuelve el turno, otro que indica si el juego se acabó o no y el tercer método que comprueba la jugada del jugador y averigua a quien le toca a continuación, este método recibirá el identificador de jugador y el número que ha jugado; deberá definirse como synchronized, así cuando un jugador está haciendo la jugada, ningún otro podrá interferir. 
En este método se indicará cuál es el siguiente turno y si el juego ha finalizado porque algún jugador ha acertado el número.
- **Jugador**: Extiende *Thread*. 
Su constructor recibe un identificador de jugador y el árbitro, todos los hilos comparten el árbitro. 
El jugador dentro del método *run()* comprobará si es su turno, en ese caso generará un número aleatorio entre 1 y 10 y creará la jugada usando el método correspondiente del árbitro. Este proceso se repetirá hasta que el juego se acabe.
- **Main**: Esta clase inicializa el árbitro indicándole el número de jugadores y lanza los hilos de los jugadores, asignando un identificador a cada hilo y enviándoles el objeto árbitro que tienen que compartir.

La salida del programa será similar a ésta:

```bash
NUMERO A ADIVINAR: 3
Jugador1 dice: 9
  Le toca a Jug2
Jugador2 dice: 9
  Le toca a Jug3
Jugador3 dice: 10
  Le toca a Jug1
Jugador1 dice: 4
  Le toca a Jug2
Jugador2 dice: 7
  Le toca a Jug3
Jugador3 dice: 7
 Le toca a Jug1
Jugador1 dice: 6
  Le toca a Jug2
Jugador2 dice: 3
  Jugador 2 gana, adivino el numero!!!
```

## RULETA

Se propone simular el juego de la la ruleta francesa. En este juego, hay una ruleta con 37 números (del 0 al 36). En ella nos econtrams a tres clases:

- La clase **Banca** almacena el dinero y gestiona los pagos y cobros. Inicialmente empieza con 20.000€
- La clase **Crupier**: Gestiona el objeto **Banca** y cada 3 segundos saca un número al azar, recoge las apuestas de los jugadores y cobra o paga según proceda.
- La clase **Jugador**, que se inicializa con 1.000 € y tiene las siguientes reglas.
    - El jugador puede hacer dos tipos de jugadas, según un número generado al azar entre 0 y 1.
    - La jugada 0 es para jugar par o impar.
    - La jugada 1 es para jugar un numero cualquiera (entre 1 y 36)
    - El jugador apostará una cantidad aleatoria entre 10 y 50€.
    - Una vez hecha la jugada, el jugador espera a que el crupier hlance la bola y le procese la apuesta.

Habrá 4 hilos que simulen a cada jugador.

Las normas de la ruleta son las iguientes:

- Si la apuesta es a un número, el jugador gana 30 veces lo apostado.
- Si la apuesta es par, el jugador gana 3 veces lo apostado.
- Si sale el 0, todo el mundo pierde y la banca se queda con todo el dinero.
- El juego termina con la Banca a cero o tras 20 jugadas.

## BLACKJACK

emular el juego de las 21 con las siguientes características:

- Habrá un numero de jugadores (hilos) entre 2 y 6.
- Cada jugador guardara un listado de las cartas jugadas.
- El Crupier recibirá el objeto jugador, llamara al proceso para sacar carta y se la entregará al jugador. Éste ultimo revisara la jugada comprobara si: ha ganado, se ha pasado, sigue jugando.
- El juego termina cuando todos los jugadores han ganado o se han pasado.

## PUENTE COMPARTIDO
Crear una aplicación en JAVA que simule  un sistema que controla el paso de personas por un puente, siempre en la misma dirección, y que debe cumplir las siguientes restricciones:
- No pueden pasar más de tres personas a la vez.
- No puede haber más de 200 kg de peso en ningún momento. 
- Las personas serán programadas como hilos
- El tiempo entre la llegada de dos personas es aleatorio entre 1 y 30 s, y para atravesar el puente aleatorio, entre 10 y 50 s.
- Las personas tienen un peso aleatorio de entre 40 y 120 kg.
- En un objeto de la clase Puente se guarda el estado compartido por todos los hilos: el número de personas que están cruzando el puente y su peso total. Este objeto se pasa a todos los hilos de la clase Persona, en su constructor. Estos lo utilizan como como objeto de bloqueo. En esta clase, y esto es importante, se almacena este objeto en una variable de instancia de tipo `final`. Todos los métodos de esta clase que consultan o modifican su estado son `synchronized`. 

Para simplificar la programación, tiene un método autorizacionPaso que verifica SI se cumplen las condiciones para que una persona entre en el puente y, si es así, actualiza su estado. Se invoca estel método en un bloque sincronizado sobre el propio objeto. El método terminaPaso también se invoca en un bloque sincronizado sobre el propio objeto, y después se llama a notifyAll, para dar la opción de entrar al puente a personas que estén esperando. 