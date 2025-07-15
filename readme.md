# 🛒 Sistema de Gestión de Productos y Órdenes

Proyecto de e‑commerce básico construido con **Java 21 / Spring Boot 3** (backend) y **HTML + CSS + JavaScript** (frontend). Permite listar productos, gestionar un carrito y generar órdenes.

---

## 🚀 Funcionalidades


### Backend
 
- Endpoints REST para **Product**, **Order** y **Client**
- Seguridad con Spring Security (HTTP Basic)
- DTOs para exponer entidades ( request y response - uso de Records, mapper)
- Validación de stock y errores claros (Http status code, global advice excepcions)
- Persistencia MySQL (con JPA)
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

1. Autenticación de usuarios (cargados en memoria):


**ROLE_ADMIN** = usr: **admin** / pass: **admin123** --> Puede acceder a página de administrator `gestion.html`

**ROLE_USER** = usr: **user** / pass: **user123** --> Solo para poder comprar en la página principal `index.html`


2. Crear el archivo `.env` en la raíz del proyecto backend con:

```dotenv
SPRING_DATASOURCE_PASSWORD=root
SPRING_DATASOURCE_USER=root
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/techlabdb?useSSL=false&serverTimezone=UTC
```



---



## 🛠️ Pasos para correr en local

### 🔙 Backend

```bash
cd backend
./mvnw spring-boot:run  # Requiere JDK 21
```

### 🌐 Frontend

```bash
cd ../frontend
```

Abrir `index.html` directamente en el navegador o utilizar una extensión como **Live Server** (por ejemplo, en VS Code) para facilitar el desarrollo.

---

## 🔍 Como usar la aplicación

1. 🔐 Iniciar sesión como **administrador** usando las credenciales mencionadas más arriba.

2. 🧪 **Datos de prueba**:
   - **Master data** (📦 productos y 👤 clientes) se generan automáticamente cada vez que se inicia el sistema.
   - **Transactional data** (🧾 órdenes y 📄 líneas de orden) se crean por cada compra realizada desde el frontend.

3. 🛒 Para **realizar una compra**:
   - Abrir `index.html`.
   - Agregar productos al carrito.
   - Hacer clic en **Comprar**.

4. 📋 Para **visualizar las órdenes generadas**:
   - Abrir `gestion.html`.
   - Ir a la sección de **Órdenes**.

5. ⚙️ Desde `gestion.html` se pueden realizar operaciones **CRUD** sobre:
   - `PRODUCTOS`
   - `ORDENES`

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
## 📦 Mejoras futuras

- JWT y refresh tokens
- Registro y roles de usuario
- Paginación, filtros y búsqueda

---

## 🧑‍💻 Autor

Damian Zulcovsky — proyecto demostrativo.

## 📄 Licencia

MIT

