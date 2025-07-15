# 🛒 Sistema de Gestión de Productos y Órdenes

Proyecto de e‑commerce básico construido con **Java 21 / Spring Boot 3** (backend) y **HTML + CSS + JavaScript** (frontend). Permite listar productos, gestionar un carrito y generar órdenes.

---

## 🚀 Funcionalidades


### Backend
 
- Endpoints REST para **Product**, **Order** y **Client**
- Seguridad con Spring Security (HTTP Basic)
- DTOs para exponer entidades
- Validación de stock y errores claros (HTTP 409, 422 …)
- Persistencia MySQL
- Documentación con Swagger UI (`/swagger-ui.html`)


### Frontend

- Listado de productos (`/api/v1/products`)
- Carrito en `localStorage` + sidebar responsivo
- Compra (POST `/api/v1/orders`)
- Autenticación HTTP Basic con re‑login automático
- Bootstrap 5 + diseño mobile‑first
- Vista de administración en `gestion.html`

---

## ⚙️ Tecnologías

| Capa              | Stack                                                                  |
| ----------------- | ---------------------------------------------------------------------- |
| Backend           | Java 21, Spring Boot 3, Spring Security, Spring Data JPA               |
| Base de datos     | MySQL 8                                                                |
| Frontend          | HTML5, CSS3, JavaScript (ES6), Bootstrap 5                             |
| Documentación API | Swagger UI (SpringDoc OpenAPI 2.7.0, compatible con Spring Boot 3.5.x) |

---

## 📁 Estructura del proyecto

```
backend/
├── src/main/java/com/techlab
│   ├── config/            # Seguridad & CORS
│   ├── controller/        # ProductController, OrderController, ClientController, AuthController
│   ├── dto/               # ProductDTO, OrderDTO, OrderLineDTO, ClientDTO
│   ├── entity/            # Product, Order, OrderLine, Client, User
│   ├── repository/        # JpaRepositories
│   └── service/           # Business logic
└── resources/
    └── application.properties

frontend/
├── index.html
├── gestion.html           # Vista de administración
├── css/styles.css
└── js/script.js

```

---

## 🔐 Configuración de seguridad y BD

1. Autenticación de usuarios:
```
ROLE_ADMIN = admin / pass: admin123 --> Puede acceder a página de administrator `gestion.html`
ROLE_USER = user / pass: user123 --> Solo para poder comprar en la página principal `index.html`
```

2. Crear el archivo `.env` en la raíz del proyecto backend con:

```dotenv
SPRING_DATASOURCE_PASSWORD=root
SPRING_DATASOURCE_USER=root
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3307/techlabdb?useSSL=false&serverTimezone=UTC
```

3. Tu `application.properties` debe leer esas variables, por ejemplo:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USER}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```




---

## 🧪 Ejemplos de endpoints

### `GET /api/v1/products`

```json
[
  { "id": 1, "name": "Teclado Mecánico", "price": 35000.0, "stock": 12 },
  { "id": 2, "name": "Mouse Gamer", "price": 18000.0, "stock": 25 }
]
```

### `POST /api/v1/orders`

```json
{
  "userId": 1,
  "lineRequestDTOList": [
    { "productId": 1, "quantity": 2 },
    { "productId": 5, "quantity": 1 }
  ]
}
```

---

## 🗂️ Diagrama de clases UML

El siguiente diagrama muestra la relación entre entidades, DTOs y usuarios:

📄 Ver imagen: `diagrama de clases UML.png`

---

## 🛠 Pasos para correr en local

```bash
# Backend
cd backend
./mvnw spring-boot:run # usa JDK 21

# Frontend
cd ../frontend
# abre index.html o usa un live server
```

---

## 📦 Mejoras futuras

- JWT y refresh tokens
- Registro y roles de usuario
- Paginación, filtros y búsqueda

---

## 🧑‍💻 Autor

Damian Zulcovsky — proyecto demostrativo.

## 📄 Licencia

MIT

