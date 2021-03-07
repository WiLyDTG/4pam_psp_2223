<table width="100%"><thead><tr><th>[INICIO](../README.md) </th><th> [ÍNIDCE UT4](INDICE.html) </th><th> UT 4: SERVICIOS DE RED </th></thead>
<table>
# CLIENTE REST CON SPRING

En el capítulo anterior, hemos visto cómo implementar nuestro servidor REST. Ahora vamos a desarrollar un cliente sencillo que envíe peticiones REST y procese los datos recibidos.

[DESCARGAR CÓDIGO FUENTE](ARCHIVOS/HttpClient.zip)

El procedimiento inicial es el mismo, iremos a [SPRING INIATILZR](https://start.spring.io/) y crearemos nuestro proyecto SPRING. Lo vamos a configurar asi:

![CONFIGURACIÓN](IMAGENES/img_07.png)

No necesitamos añadir dependencias aqui, ya que las añadiremos más tarde a mano en el archivo `pom.xml`. 

No obstante, la dependencia `Spring Reactive Web` incluye una serie de herramientas que podrían ser útiles para nuestro cliente. Dejo como línea de trabajo investigar sobre estas dependencias para crear un cliente web.

### El archivo `pom.xml`
Para nuestro cliente vamos a necesitar las siguientes dependecias:

* **json-simple**: Una herramienta de Google para trabajar con JSON.
* **jackson-databind**: La usaremos para convertir los datos JSON en nuestro POJO (Plain Old Java Object), en este caso, el objeto User.
* **httpclient**: Para procesar las peticiones HTTP. Existe una versión más moderna de esta librería que la que vamos a usar, pero por sencillez de uso, vamos a trabajar con la 4. Dejo como trabajo la opción de desarrollar el cliente con la versión 5.

Con estas tres dependencias, el archivo queda asi:

```java
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.4pam</groupId>
    <artifactId>HttpClient</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>13</maven.compiler.source>
        <maven.compiler.target>13</maven.compiler.target>
    </properties>
    
    <dependencies>      
        <dependency>
            <groupId>com.googlecode.json-simple</groupId>
            <artifactId>json-simple</artifactId>
            <version>1.1.1</version>
        </dependency> 
        
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.11.4</version>
            <type>jar</type>
        </dependency>

        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.13</version>
            <type>jar</type>
        </dependency>   
    </dependencies>
</project>
```

## El objeto User
En primer lugar, vamos a crear una clase para almacenar en objetos los datos JSON recibidos. Este objeto es similar al del servidor y el código es el siguiente:

```java
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pam.httpclient;

/**
 *
 * @author joaquinrios
 */
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    public final Integer id;
    public final String name;
    public final String email;

    public User() {
        id = 0;
        name="";
        email="";
    }
    
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public User(String name,
                String email,
                Integer id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }
}
```

## La clase principal

Para realizar las peticiones, se ha desarrollado un ejemplo que realiza las cuatro operaciones básicas CRUD. Vamos a ver en detalle este archivo para explicar su funcionamiento.

En primer lugar, vamos a usar los siguientes atributos:

* `User usuario`, donde se guardará el usuario que esté siendo procesado en ese momento.
* `ArrayList<User>` listaUsuarios, donde tendremos la lista de usuarios;
* `HttpPost post` , para gestionar las peticiones POST.
* `HttpPost post` , para gestionar las peticiones GET.
* `CloseableHttpClient`, que gestionará las peticiones HTTP.
* `JSONParser jsonParser`, para parsear las respuestas JSON recibidas del servidor.

Para 

Ahora vamos a ver en detalle los métodos.

### Método `User postInserta(User usuarioInsertar)`

Este método recibe un objeto usuario a insertar y lo envía mediante post haciendo uso del tipo de dato `BasicNameValuePair`. El procedimiento es sencillo: extraemos los datos del objeto, los almacenamos en un `ArrayList` de pares Nombre-Valor, lo codificamos y lo enviamos al servidor. A continuación recibimos la respuesta, que será por un lado los datos en formato JSON del servidor y el status HTTP de la misma.
Si la respuesta HTTP del servidor es correcta, la procesamos, leemos y transformamos el JSON, lo guardamos en un objeto. Este objeto lo almacenamos en nuestro `ArrayList` de usuarios y lo devolvemos.
Si la respuesta HTTP del servidor no es correcta, creamos un objeto con id=0 y cuyo nombre contiene el tipo de respuesta errónea HTTP y lo devolvemos.

### Método `postActualiza(User usuarioActualizar) `

Este método, muy parecido al anterior, recibe un objeto usuario a actualizar y lo envía mediante post haciendo uso del tipo de dato `BasicNameValuePair`. 

La creación de los datos del `ArrayList` es similar al método anterior y para generar la ruta cogemos el `id` del usuario. .
Recibimos la respuesta, que será por un lado los datos en formato JSON del servidor y el status HTTP de la misma.
Si la respuesta HTTP del servidor es correcta, la procesamos, leemos y transformamos el JSON, lo guardamos en un objeto y lo devolvemos.
Si la respuesta HTTP del servidor no es correcta, creamos un objeto con id=0 y cuyo nombre contiene el tipo de respuesta errónea HTTP y lo devolvemos.

### Método `getBorra(int id)`
Este método recibe el id  del usuario a borrar, y genera la URL para enviar al servidor REST. De éste recibe la respuesta HTTP, y si es correcta, devuelve un mensaje de "BORRADO OK". En caso de recibir un error de HTTP, devuelve el mensaje de error con el número de error http (ej: ERROR 404).


### Método `getUsuario(int id)`
Método similar al anterior que  recibe el id  del usuario a buscar, y genera la URL para enviar al servidor REST. De éste recibe la respuesta HTTP, y si es correcta, devuelve objeto con el usuario. En caso de recibir un error de HTTP, devuelve el mensaje de error con el número de error http (ej: ERROR 404).

### Método `getListaUsuarios()`
Método que devuelve un `ArrayList` con todos los usarios de la base de datos. Para ello usamos el tipo de dato JSONArray que almacena un array de elementos JSON, cada uno de ellos conteniendo los datos de cada usuario.

### Método `readAll(Reader rd)`
Método que devuelve un string con los datos leidos del Reader del servidor , para que puedan ser parseados como datos JSON.

Con los métodos anteriores desarrollado, el código final quedará de la siguiente manera:

```java
package com.pam.httpclient;

/**
 *
 * @author joaquinrios
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ApacheHttpClient {

    private static User usuario;
    private static ArrayList<User> listaUsuarios;
    private static HttpPost post = new HttpPost();
    private static HttpGet get = new HttpGet();
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final JSONParser jsonParser = new JSONParser();

    public static void main(String[] args) {

        listaUsuarios = getListaUsuarios();

        usuario = postInserta(new User("jorge", "pepito@gmail.com" , 0 ));
        System.out.println(usuario.getId());
        

        usuario = getUsuario(4);
        System.out.println("----------------------");
        System.out.println("ID : " + usuario.getId());
        System.out.println("Nombre : " + usuario.getName());
        System.out.println("Email : " + usuario.getEmail());
        

        System.out.println(getBorra(2));
        

        var sdsd = postActualiza(new User("juan", "juan@mail.com", 8));
        if (sdsd.getId() == 0) {
            System.out.println("ERROR AL ACTUALIZAR");
        } else {
            System.out.println("ACTUALIZADO CORRECTO");
        }
        
        /*
        for (User pepe : listaUsuarios) {
            System.out.println("----------------------");
            System.out.println("ID : " + pepe.getId());
            System.out.println("Nombre : " + pepe.getName());
            System.out.println("Email : " + pepe.getEmail());
        }
        */

        try {
            httpClient.close();
        } catch (IOException ex) {
            Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static User postInserta(User usuarioInsertar) {

        //Nuestra petición POST
        post = new HttpPost("http://localhost:8080/users/add");
        //Creamos la lista de valores a enviar
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("name", usuarioInsertar.getName()));
        urlParameters.add(new BasicNameValuePair("email", usuarioInsertar.getEmail()));
        //El objeto devuelto por el servidor REST
        User output = new User();
        
        try {
            //Codificamos los datos para enviarlos
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            //Ejecutamos la consulta
            var response = httpClient.execute(post);
            //Leemos la respuesta en crudo
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
            //Comprobamos el tipo de respuesta
            int status = response.getStatusLine().getStatusCode();
            //La respuesta tiene status correcto
            if (status >= 200 && status < 300) {
                String jsonText = readAll(br);
                try {
                    var json = (JSONObject) jsonParser.parse(jsonText);
                    listaUsuarios.add(usuarioInsertar);
                } catch (ParseException ex) {
                    Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            // Recibimos un error HHTP
            } else {
                var nns = response.getStatusLine().toString();
                output = new User(nns, nns, 0);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    public static User postActualiza(User usuarioActualizar) {
        
        User output = new User();
        post = new HttpPost("http://localhost:8080/users/user/" + usuarioActualizar.getId());
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("name", usuarioActualizar.getName()));
        urlParameters.add(new BasicNameValuePair("email", usuarioActualizar.getEmail()));

        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            var response = httpClient.execute(post);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                String jsonText = readAll(br);
                try {
                    var json = (JSONObject) jsonParser.parse(jsonText);
                    output = usuarioActualizar;
                    // Aqui falta por actualizar el ArrayList de usuarios con los valores 
                    // modificados del usuario
                } catch (ParseException ex) {
                    Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                var nns = response.getStatusLine().toString();
                output = new User(nns, "", 0);
            }

        } catch (IOException ex) {
            Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;

    }

    public static String getBorra(int id) {
        var textResponse = "";
        String url = "http://localhost:8080/users/user/" + id;
        try {
            get = new HttpGet(url);
            var response = httpClient.execute(get);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                textResponse = "BORRADO OK";
            } else {
                textResponse = "ERROR " + status;
                //textResponse = response.getStatusLine().toString();
            }
        } catch (IOException ex) {
            Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return textResponse;
    }

    public static User getUsuario(int id) {

        var url = "http://localhost:8080/users/user?id=" + id;
        User elusuario = new User();
        String textResponse = "";

        try {

            get = new HttpGet(url);
            var response = httpClient.execute(get);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                String jsonText = readAll(br);
                try {
                    var json = (JSONObject) jsonParser.parse(jsonText);
                    var idusu = Integer.parseInt(json.get("id").toString());
                    elusuario = new User(json.get("name").toString(),
                            json.get("email").toString(),
                            idusu);
                    usuario = elusuario;
                } catch (ParseException ex) {
                    Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                textResponse = "ERROR " + status;
                //textResponse = response.getStatusLine().toString();
            }

        } catch (IOException ex) {
            Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return elusuario;
    }

    public static ArrayList<User> getListaUsuarios()  {
        ArrayList<User> listaUsuariosDevolver = new ArrayList<>();
            User elusuario = new User();
            var url = "http://localhost:8080/users/all/";
            String textResponse = "";
            
        try {
            get = new HttpGet(url);
            var response = httpClient.execute(get);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                try {
                    var jsonText = readAll(br);
                    JSONArray listaUsuariosJSON = (JSONArray) jsonParser.parse(jsonText);
                    Iterator i = listaUsuariosJSON.iterator();
                     //Vamo recorriendo el JSONArray
                    while (i.hasNext()) {
                       //jason guarda un usuario
                        var json = (JSONObject) i.next();
                        //obtenemos el id parseando el campo correspondiente
                        var idusu = Integer.parseInt(json.get("id").toString());
                        usuario = new User(json.get("name").toString(),
                                json.get("email").toString(),
                                idusu);
                        listaUsuariosDevolver.add(usuario);
                    }
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } else {
                textResponse = response.getStatusLine().toString();
            }
        } catch (IOException ex) {
            Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaUsuariosDevolver;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
```