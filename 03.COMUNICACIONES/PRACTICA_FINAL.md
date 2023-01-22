# PRÁCTICA FINAL

El siguiente proyecto simula un sistema electoral básico, donde hay una serie de hilos que simulan mesas electorales que generan unas votaciones (números aleatorios) donde cada número representa un candidato.

Las características del proyecto son las siguientes:

- Cada hilo contabiliza las respuestas en una mesa electoral, cuyo identificador se le
  pasa en el constructor. Las votaciones son un número que representa un candidato o bien 0 que es un voto NULO.  

- Se quiere contabilizar el número de votaciones para cada zona electoral, y el número de votos que ha obtenido cada candidato en total, independientemente de la zona. También el número de votos nulos. Los resultados se recopilan en un objeto de clase ResultadosVotacion, que tiene métodos `synchronized` para registrar/contabilizar cada voto, y para consultar los resultados de la votación. 

- Junto con el identificador de su zona, a cada hilo se le pasa el objeto de la clase ResultadosVotacion, que comparten todos.

-  Para contabilizar el número de votos de cada candidato se utiliza un `HashMap<Integer, Integer >` , que guarda un conjunto de pares (clave, valor), donde la clave es un `Integer` que representa a cada candidato, y el valor es otro `Integer` que contiene el número de votos obtenidos. La clase `Hashmap`tiene una característica relevante para esta aplicación: permite utilizar `null` como valor para la clave.

- Asociadas a este valor, se registran las respuestas nulas. El programa principal lanza hilos de recuento para varias zonas. Cada uno proporciona un número de votos  al azar entre cien y doscientos, y cada voto es un número que representa a cada candidato o `null`para un voto nulo.
