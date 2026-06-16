# NexusVault - Marketplace de Economía Circular para Cosas Digitales

¡Buenas! Este es nuestro proyecto para la **Evaluación Parcial 2** de **Desarrollo Fullstack I**. 

NexusVault es una plataforma tipo marketplace donde los usuarios pueden subir los juegos, skins, keys o cuentas digitales que ya no usan para cambiarlas con otros usuarios o venderlas. La idea es que no se queden botadas y armar una economía circular digital que sea segura.

---

## Integrantes
* **Nikki Alvarado**
* **Alexander Oyarzún**

**Profesor:** José Luis Silva González  
**Sección:** 002D

---

## Cómo está armado el proyecto (Arquitectura)

Para que el sistema se la banque y no se caiga todo junto, lo armamos con microservicios independientes:

1.  **Bases de Datos Separadas (Database-per-service):** Cada microservicio tiene su propia base de datos lógica en MySQL 8. Ningún servicio se mete a la mala en la base de datos de otro.

2.  **Capas CSR:** Todo el código está ordenado por carpetas según lo pedido por el profe:
    * **Controller:** Recibe las peticiones HTTP (los endpoints).
    * **Service:** Tiene toda la lógica del negocio y las transacciones (`@Transactional`).
    * **Repository:** Se conecta a la base de datos usando Spring Data JPA.

3.  **Tablas automáticas con Flyway:** No creamos las tablas a mano en Workbench. Usamos Flyway para que se creen y se actualicen solas cuando corre el código.

4.  **Validaciones:** Usamos las anotaciones de Java (`@NotBlank`, `@Email`, `@Min`, `@NotNull`) en los controladores para que no metan datos vacíos o correos malos.

5.  **Manejo de Errores y Resiliencia:** Si algo falla, usamos `@ControllerAdvice` para mandar respuestas ordenadas (como errores 404 o 400). Además, configuramos un `.onErrorResume()` en el `WebClient` de la billetera por si el servicio de notificaciones se cae, así la app no muere en cascada.

6.  **Logs:** Metimos `@Slf4j` con Lombok para que la consola nos tire avisos limpios (`log.info` y `log.warn`) y saber qué está pasando.

---

## Los 10 Microservicios

<<<<<<< HEAD
Acá están los 10 servicios obligatorios que pide la pauta, cada uno en su puerto:
=======
Acá están los 10 servicios solicitados, cada uno en su puerto:
>>>>>>> 87ee1c614bd3ff5a4ad530ab11546266f87f5986

| Servicio | Puerto | ¿Qué hace? |
| :--- | :--- | :--- |
| `ms-auth` | *8079* | Login, registro y creación de los tokens JWT. |
| `ms-users` | *8076* | Maneja los perfiles de los usuarios y sus roles. |
| `ms-catalog` | *8081* | Muestra los juegos y artículos digitales que hay disponibles. |
| `ms-inventory` | *8082* | Revisa el stock y quién es el dueño de cada código/key. |
| `ms-orders` | *8083* | Crea y maneja los trueques o las compras de los usuarios. |
| `ms-payments` | *8084* | Procesa los pagos (simulación de pasarela de pago). |
| `ms-wallet` | *8075* | Revisa la plata virtual de los usuarios, abonos y transferencias. |
| `ms-notifications` | *8084* | Manda los correos y avisos (funciona síncrono y asíncrono). |
| `ms-reports` | *8077* | Saca las métricas e historial para ver cómo va el negocio. |
| `ms-admin` | *8085* | Panel para moderadores y auditorías del sistema. |

---

## Tecnologías que usamos

<<<<<<< HEAD
* Java 17 + Spring Boot 3
=======
* Java 21 + Spring Boot 3
>>>>>>> 87ee1c614bd3ff5a4ad530ab11546266f87f5986
* Spring WebFlux (`WebClient` para conectar los microservicios)
* Spring Data JPA / Hibernate
* MySQL 8 (corriendo en Docker)
* Flyway (Migraciones)
* Lombok (`@Slf4j`, getters, setters)
* Git / GitHub (Usamos GitFlow para trabajar los dos sin pisarnos las ramas)

---

## Cómo hacerlo correr en tu PC (Paso a Paso)

### Requisitos:
<<<<<<< HEAD
Tener instalado **Java 17**, **Docker / Docker Desktop** y **Git**.

### Paso 1: Clonar esto
```bash
=======
Tener instalado **Java 21**, **Docker / Docker Desktop** y **Git**.

### Paso 1: Clonar esto

>>>>>>> 87ee1c614bd3ff5a4ad530ab11546266f87f5986
git clone [https://github.com/xaxo1/NexusVault.git](https://github.com/xaxo1/NexusVault.git)
cd nexusvault

### Paso 2:
#Abre la carpeta del proyecto en VS Code.

#Abre el archivo mvnw (o los de cada microservicio).

#En la esquina inferior derecha de la barra de estado de VS Code, haz clic donde dice CRLF y cámbialo a LF. Luego guarda el archivo.

### Paso 3: (Si hay problemas con algun docker antiguo)
docker compose down

### Paso 4: Levantar el ecosistema 
docker compose up -d --build 

docker compose ps ### Revisar el estado de los contenedores

<<<<<<< HEAD
=======
```bash

>>>>>>> 87ee1c614bd3ff5a4ad530ab11546266f87f5986
