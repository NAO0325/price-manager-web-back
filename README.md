# Lista de Precios MS Spring Boot 

Desarrollo de un microservicio implementando clean code y arquitectura hexagonal con Spring Boot.

Esta aplicación utiliza una Base de Datos H2, que es una Base de Datos en memoria.

### Compilación e Inicio
Para compilar y tener todos los recursos, ejecuta el siguiente comando:

```bash
mvn clean install
```

### Para iniciarlo bastará con el comando

```bash
java -jar boot/target/price-manager-web-back-boot-1.0.0.jar
```

### Swagger de la aplicación

Accede a la interfaz de Swagger en el siguiente enlace:

http://localhost:9090/swagger-ui/index.html

### Pruebas

Recomendamos utilizar Postman importando la colección desde este enlace: 

***https://github.com/NAO2503/spring-catalogue-gft/blob/main/src/main/resources/Catalogue.postman_collection_V2.json***

### Ejemplo de petición <font color="green">GET</font>:

http://localhost:9090/api/catalogue/price/findByBrandProductBetweenDate?dateQuery=2020-06-14%2010:00:00&productId=35455&brandId=1