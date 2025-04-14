# 🚀 dynamic-sum-api

API REST desarrollada con Java 21 y Spring Boot 3.2 para realizar cálculos con porcentajes dinámicos, registrar un historial de llamadas y manejar la resiliencia ante fallos externos.

---

## 📌 Funcionalidades

- 🧮 Suma de dos números y aplicación de un porcentaje dinámico desde un servicio externo simulado.
- ♻️ Caché del porcentaje por 30 minutos.
- 💥 Tolerancia a fallos del servicio externo mediante uso del valor cacheado.
- 🗃️ Registro asíncrono del historial de llamadas (endpoint, parámetros, resultado, estado).
- 🔎 Consulta de historial con paginación y orden descendente.
- 🧪 Tests unitarios y de integración.
- 📖 Documentación con Swagger UI.

---

## ⚙️ Requisitos

- Java 21
- Maven 3.8+
- Docker y Docker Compose

---

## 🧑‍💻 Ejecución local (modo desarrollo)

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/dynamic-sum-api.git
   cd dynamic-sum-api
   ```

2. Levanta la base de datos PostgreSQL:
   ```bash
   docker-compose up db
   ```

3. Ejecuta la aplicación desde IntelliJ usando el perfil `dev`:
   ```bash
   SPRING_PROFILES_ACTIVE=dev
   ```

4. Accede a:
   ```
   http://localhost:8080/swagger-ui.html
   ```

---

## 🐳 Ejecución con Docker Compose (modo contenedor)

1. Construye y levanta los servicios:
   ```bash
   mvn clean package
   docker-compose up --build
   ```

2. Verifica los servicios:
   - API: [http://localhost:8080](http://localhost:8080)
   - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🔗 Endpoints disponibles

### `/api/calculate` – `POST`
Calcula la suma con porcentaje dinámico aplicado.  
**Parámetros:**
- `num1`: Primer número
- `num2`: Segundo número

Ejemplo:
```http
POST /api/calculate?num1=10&num2=20
```

### `/api/logs` – `GET`
Consulta el historial de llamadas a la API.  
**Parámetros:**
- `page`: número de página (por defecto: 0)
- `size`: tamaño de página (por defecto: 10)

---

## 📚 Documentación Swagger

La documentación interactiva está disponible en:

```
http://localhost:8080/swagger-ui.html
```

Incluye descripción de:
- Endpoints
- Parámetros
- Respuestas esperadas
- DTOs usados en la API

---

## ✅ Tests

Para ejecutar todos los tests:

```bash
mvn test
```

Incluye:
- Tests unitarios con JUnit + Mockito
- Tests de integración con MockMvc

---

## 🧱 Estructura del proyecto

```bash
src/main/java/com/dynamic/dynamicsumapi
├── controller         # Controladores REST
├── service            # Lógica de negocio
├── repository         # Repositorios JPA
├── model
│   ├── entity         # Entidades JPA
│   └── dto            # Objetos de transferencia de datos
├── config             # Configuración de caché, async, swagger
├── exception          # Manejo de errores personalizados
```

---

## 📦 Docker

Incluye:
- `Dockerfile` para el backend
- `docker-compose.yml` con:
  - PostgreSQL 15
  - API Spring Boot

---

## 🧑 Autor

Proyecto desarrollado como parte de un challenge técnico.  
Contacto: [juanalsa@gmail.com]

---