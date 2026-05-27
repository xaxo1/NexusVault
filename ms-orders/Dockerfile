# Etapa 1: Construcción (Usamos una imagen con Java 21 para compilar el código)
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copiamos todo el código fuente al contenedor
COPY . .

# Le damos permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Compilamos SOLO el microservicio actual para generar su archivo .jar
RUN ./mvnw clean package -DskipTests -pl ms-orders -am

# Etapa 2: Ejecución (Usamos una imagen más liviana solo con el entorno de ejecución de Java)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el archivo .jar generado en la etapa 1 hacia esta etapa final
COPY --from=build /app/ms-orders/target/*.jar app.jar

# Comando que se ejecuta al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]
