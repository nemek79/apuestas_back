### Partimos de una imagen de maven para contruir el jar de la aplicación
FROM maven:3-jdk-8 AS builder

### Copiamos el pom.xml y el src/ en el directorio build del contenedor
COPY pom.xml /build/
COPY src /build/src/

### Cambiamos al directorio build/ para construir el jar
WORKDIR /build/
RUN mvn package

### Partimos de una imagen de openjdk-8 para poder ejecutar el jar
FROM openjdk:8-jdk-alpine

### Cambiamos al directorio app
WORKDIR /app/

### Partiendo de la fase anterior, copiamos el jar a este contenedor y lo llamamos demo.jar
### Nota: maven por defecto, guarda el jar en target
COPY --from=builder /build/target/*.jar apuestas_back.jar

### Indicamos a Docker que al levantar nuestra imagen ejecute el comando java -jar demo.jar
ENTRYPOINT ["java", "-jar", "apuestas_back.jar"]
