<table width="100%"><thead><tr><th>[INICIO](../README.md) </th><th> [ÍNIDCE UT4](INDICE.html) </th><th> UT 4: SERVICIOS DE RED </th></thead>
<table>

# SERVICIOS WEB: REST

## ¿Que es un web service?

Un servicio web es una colección de protocolos abiertos y estándares utilizados para intercambiar datos entre aplicaciones o sistemas. Las aplicaciones de software escritas en varios lenguajes de programación y ejecutándose en varias plataformas pueden usar servicios web para intercambiar datos a través de redes informáticas como Internet de una manera similar a la comunicación entre procesos en una sola computadora. Esta interoperabilidad (por ejemplo, entre Java y Python, o aplicaciones de Windows y Linux) se debe al uso de estándares abiertos. A quien solicita la información se le llama cliente y a quien envía la información se le llama servidor.

## ¿Que es REST?

**REST** es una arquitectura basada en estándares web y utiliza el protocolo **HTTP**, donde cada componente es un recurso y se accede a él mediante una interfaz común utilizando métodos estándares de HTTP. **REST** fue introducido por primera vez por Roy Fielding en 2000.

Los servicios web **RESTful** son básicamente servicios web basados en arquitectura **REST**. Los servicios web RESTful son ligeros, altamente escalables y gestionables, y se utilizan con mucha frecuencia para crear API para aplicaciones basadas en web.


Relacionados con los anteriores encontramos el término RESTful api.


## ¿Qué es un API?

La palabra viene de **A**pplication **P**rogramming **I**nterface, y no es más que un programa que permite que otros programas se comuniquen con un programa en específico, por ejemplo Facebook. A diferencia de los web services, las API no necesariamente deben comunicarse entre una red, pueden usarse entre dos aplicaciones en una misma computadora.

## ¿REST vs RESTful?

*¿Qué es rest?*, es una arquitectura para aplicaciones basadas en redes (como Internet), sus siglas significan **RE**presentational **S**tate **T**ransfer y por otro lado RESTful web service o RESTful api, son programas basados en REST. Pero muchas veces se usan como sinónimos (REST y RESTful).

## ¿Que hace que un web service sea REST?

Usualmente los RESTful web service tienen estas características:

- Están asociados a información
- Permiten listar, crear, leer, actualizar y borrar información
- Para las operaciones anteriores necesitan una URL y un método HTTP para accederlas
- Usualmente regresan la información en formato JSON.
- Devuelven códigos de respuesta HTML, por ejemplo 200, 201, 404, etc

## Métodos HTTP

Cuando solicitamos una página web, podemos hacerlo por diferentes métodos, el más común es el GET, es el que usamos cuando digitamos una dirección en nuestro navegador, en ocasiones utilizamos POST, cuando enviamos un formulario con datos, pero las aplicaciones pueden usar otros métodos como PATCH, PUT, etc.

- Listar y leer: Usan el método **GET**
- Crear: Usan el método **POST**
- Actualizar: Usan el método **PATCH** para actualizar y **PUT** para reemplazar.
- Borrar: Usan el método **DELETE**

## Algunos códigos de estado HTML

Cuando se recibe una página HTML, también se recibe un código de estado HTML (solo uno), en los web service *RESTful*, estos se usan para saber el estado de la ejecución del servicio, y estos son algunos de los usados:

- 200 (Ok), cuando una operación fue exitosa.
- 201 (Created), cuando se creó un registro (¿recuerdan? Con el método POST)
- 403 (Forbidden), cuando intentamos leer un registro para el que no tenemos acceso, por ejemplo los datos del perfil de otro usuario.
- 404 (Not found), cuando intentamos leer un registro que no existe.

## Conclusiones

1. Para efectos prácticos **REST** y **RESTful** pueden entenderse como sinónimos aunque no lo son.
2. La diferencia entre RESTful web service y RESTful api, es que el api no necesariamente se debe ejecutar en una red, puede ser en una misma computadora.

REFERENCIA:

* [https://codigonaranja.com/restful-web-service](https://codigonaranja.com/restful-web-service)
* 