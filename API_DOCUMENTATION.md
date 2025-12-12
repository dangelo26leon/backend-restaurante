# 🍽️ API REST - Sistema de Gestión de Restaurante

## 📋 Descripción
API REST completa para gestión de pedidos de un restaurante con autenticación JWT, roles (Admin, Mesero, Cocinero) y todas las operaciones CRUD necesarias.

## 🔧 Configuración

### Base de datos MySQL
La base de datos se crea automáticamente. Asegúrate de tener MySQL corriendo y actualiza las credenciales en `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/restaurante?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=tuPassword
```

### Ejecutar el proyecto
```bash
./mvnw spring-boot:run
```

## 👥 Usuarios de Prueba (Se crean automáticamente)

| Rol | Email | Contraseña |
|-----|-------|------------|
| Admin | admin@restaurante.com | admin123 |
| Mesero | mesero@restaurante.com | mesero123 |
| Cocinero | cocinero@restaurante.com | cocinero123 |

---

## 🔐 Autenticación

### POST /api/auth/login
Login de usuario

**Request:**
```json
{
    "email": "admin@restaurante.com",
    "password": "admin123"
}
```

**Response:**
```json
{
    "success": true,
    "message": "Inicio de sesión exitoso",
    "data": {
        "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "refreshToken": "550e8400-e29b-41d4-a716-446655440000",
        "tokenType": "Bearer",
        "usuario": {
            "id": 1,
            "nombre": "Admin",
            "apellido": "Sistema",
            "email": "admin@restaurante.com",
            "rol": "ADMIN"
        }
    },
    "timestamp": "2025-12-10T12:00:00"
}
```

### POST /api/auth/register (Solo Admin)
Registrar nuevo usuario

**Request:**
```json
{
    "nombre": "Carlos",
    "apellido": "López",
    "email": "carlos@restaurante.com",
    "password": "password123",
    "rol": "MESERO"
}
```

### POST /api/auth/refresh-token
Renovar token de acceso

**Request:**
```json
{
    "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
}
```

### POST /api/auth/logout
Cerrar sesión (invalida el refresh token)

**Request:**
```json
{
    "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
}
```

---

## 👤 Usuarios (Solo Admin)

### GET /api/usuarios
Listar todos los usuarios

**Headers:**
```
Authorization: Bearer {accessToken}
```

### GET /api/usuarios/{id}
Obtener usuario por ID

### POST /api/usuarios
Crear usuario

**Request:**
```json
{
    "nombre": "Pedro",
    "apellido": "Martínez",
    "email": "pedro@restaurante.com",
    "password": "password123",
    "rol": "COCINERO"
}
```

### PUT /api/usuarios/{id}
Actualizar usuario

**Request:**
```json
{
    "nombre": "Pedro Updated",
    "apellido": "Martínez García",
    "email": "pedro.new@restaurante.com",
    "rol": "MESERO"
}
```

### DELETE /api/usuarios/{id}
Eliminar usuario

---

## 🪑 Mesas

### GET /api/mesas
Listar todas las mesas

### GET /api/mesas/{id}
Obtener mesa por ID

### GET /api/mesas/disponibles
Listar mesas disponibles

### POST /api/mesas (Solo Admin)
Crear mesa

**Request:**
```json
{
    "numero": 11,
    "estado": "DISPONIBLE"
}
```

### PUT /api/mesas/{id} (Solo Admin)
Actualizar mesa

**Request:**
```json
{
    "numero": 11,
    "estado": "OCUPADA"
}
```

### DELETE /api/mesas/{id} (Solo Admin)
Eliminar mesa

---

## 📁 Categorías

### GET /api/categorias
Listar todas las categorías

**Response:**
```json
{
    "success": true,
    "message": "Operación exitosa",
    "data": [
        { "id": 1, "nombre": "Entradas" },
        { "id": 2, "nombre": "Platos Principales" },
        { "id": 3, "nombre": "Postres" },
        { "id": 4, "nombre": "Bebidas" },
        { "id": 5, "nombre": "Ensaladas" }
    ],
    "timestamp": "2025-12-10T12:00:00"
}
```

### GET /api/categorias/{id}
Obtener categoría por ID

### POST /api/categorias (Solo Admin)
Crear categoría

**Request:**
```json
{
    "nombre": "Sopas"
}
```

### PUT /api/categorias/{id} (Solo Admin)
Actualizar categoría

**Request:**
```json
{
    "nombre": "Sopas y Cremas"
}
```

### DELETE /api/categorias/{id} (Solo Admin)
Eliminar categoría

---

## 🍽️ Platos

### GET /api/platos
Listar todos los platos

**Response:**
```json
{
    "success": true,
    "message": "Operación exitosa",
    "data": [
        {
            "id": 1,
            "nombre": "Lomo Saltado",
            "precio": 25.00,
            "descripcion": "Lomo fino salteado con cebolla, tomate y papas fritas",
            "categoriaId": 2,
            "categoriaNombre": "Platos Principales",
            "disponible": true
        }
    ],
    "timestamp": "2025-12-10T12:00:00"
}
```

### GET /api/platos/{id}
Obtener plato por ID

### GET /api/platos/categoria/{categoriaId}
Listar platos por categoría

### GET /api/platos/disponibles
Listar platos disponibles

### POST /api/platos (Solo Admin)
Crear plato

**Request:**
```json
{
    "nombre": "Ají de Gallina",
    "precio": 22.50,
    "descripcion": "Pollo deshilachado en salsa de ají amarillo",
    "categoriaId": 2,
    "disponible": true
}
```

### PUT /api/platos/{id} (Solo Admin)
Actualizar plato

**Request:**
```json
{
    "nombre": "Ají de Gallina Especial",
    "precio": 24.00,
    "descripcion": "Pollo deshilachado en salsa de ají amarillo con huevo",
    "disponible": true
}
```

### DELETE /api/platos/{id} (Solo Admin)
Eliminar plato

---

## 📝 Pedidos

### GET /api/pedidos
Listar pedidos (Admin/Cocinero: todos, Mesero: solo los suyos)

**Response:**
```json
{
    "success": true,
    "message": "Operación exitosa",
    "data": [
        {
            "id": 1,
            "fecha": "2025-12-10T12:30:00",
            "estado": "PENDIENTE",
            "mesaId": 1,
            "mesaNumero": 1,
            "meseroId": 2,
            "meseroNombre": "Juan Pérez",
            "detalles": [
                {
                    "id": 1,
                    "platoId": 3,
                    "platoNombre": "Lomo Saltado",
                    "cantidad": 2,
                    "precioUnitario": 25.00,
                    "subtotal": 50.00
                },
                {
                    "id": 2,
                    "platoId": 8,
                    "platoNombre": "Chicha Morada",
                    "cantidad": 2,
                    "precioUnitario": 5.00,
                    "subtotal": 10.00
                }
            ],
            "total": 60.00
        }
    ],
    "timestamp": "2025-12-10T12:00:00"
}
```

### GET /api/pedidos/{id}
Obtener pedido por ID

### GET /api/pedidos/mis-pedidos
Listar mis pedidos (para meseros)

### GET /api/pedidos/dashboard/cocina (Solo Admin/Cocinero)
Dashboard de cocina - pedidos pendientes y en proceso

### POST /api/pedidos (Admin/Mesero)
Crear nuevo pedido

**Request:**
```json
{
    "mesaId": 1,
    "detalles": [
        {
            "platoId": 3,
            "cantidad": 2
        },
        {
            "platoId": 8,
            "cantidad": 2
        },
        {
            "platoId": 6,
            "cantidad": 1
        }
    ]
}
```

**Response:**
```json
{
    "success": true,
    "message": "Pedido creado exitosamente",
    "data": {
        "id": 1,
        "fecha": "2025-12-10T12:30:00",
        "estado": "PENDIENTE",
        "mesaId": 1,
        "mesaNumero": 1,
        "meseroId": 2,
        "meseroNombre": "Juan Pérez",
        "detalles": [
            {
                "id": 1,
                "platoId": 3,
                "platoNombre": "Lomo Saltado",
                "cantidad": 2,
                "precioUnitario": 25.00,
                "subtotal": 50.00
            },
            {
                "id": 2,
                "platoId": 8,
                "platoNombre": "Chicha Morada",
                "cantidad": 2,
                "precioUnitario": 5.00,
                "subtotal": 10.00
            },
            {
                "id": 3,
                "platoId": 6,
                "platoNombre": "Tres Leches",
                "cantidad": 1,
                "precioUnitario": 10.00,
                "subtotal": 10.00
            }
        ],
        "total": 70.00
    },
    "timestamp": "2025-12-10T12:30:00"
}
```

### PUT /api/pedidos/{id}/estado
Actualizar estado del pedido

**Estados válidos y transiciones:**
- `PENDIENTE` → `EN_PROCESO` (Cocinero/Admin)
- `PENDIENTE` → `TERMINADO` (Cocinero/Admin - si es rápido)
- `EN_PROCESO` → `TERMINADO` (Cocinero/Admin)
- `TERMINADO` → `ENTREGADO` (Mesero del pedido/Admin)

**Request (Cocinero empieza a preparar):**
```json
{
    "estado": "EN_PROCESO"
}
```

**Request (Cocinero termina el pedido):**
```json
{
    "estado": "TERMINADO"
}
```

**Request (Mesero entrega el pedido):**
```json
{
    "estado": "ENTREGADO"
}
```

---

## 🔄 Flujo de Estados del Pedido

```
PENDIENTE ──────┬────────────────────► EN_PROCESO ────► TERMINADO ────► ENTREGADO
                │                                              │
                └──────────────────────────────────────────────┘
                        (directo si es muy rápido)
```

### Reglas de negocio:
1. **Al crear pedido**: La mesa pasa a estado `OCUPADA`
2. **Al entregar pedido**: La mesa vuelve a estado `DISPONIBLE`
3. **Cocinero**: Solo puede cambiar a `EN_PROCESO` o `TERMINADO`
4. **Mesero**: Solo puede cambiar a `ENTREGADO` (y solo sus propios pedidos)
5. **Admin**: Puede hacer cualquier cambio de estado

---

## ⚠️ Códigos de Error

| Código | Significado |
|--------|-------------|
| 400 | Bad Request - Datos inválidos |
| 401 | Unauthorized - No autenticado |
| 403 | Forbidden - Sin permisos |
| 404 | Not Found - Recurso no encontrado |
| 409 | Conflict - Recurso duplicado |
| 500 | Internal Server Error |

**Ejemplo de error de validación:**
```json
{
    "success": false,
    "message": "Error de validación",
    "errors": {
        "email": "El email debe ser válido",
        "password": "La contraseña es obligatoria"
    },
    "timestamp": "2025-12-10T12:00:00"
}
```

---

## 📁 Estructura del Proyecto

```
src/main/java/com/restaurante/backend/
├── controller/          # Controladores REST
├── dto/                 # Data Transfer Objects
│   └── auth/           # DTOs de autenticación
├── exception/          # Excepciones personalizadas
├── mapper/             # Mappers Entity <-> DTO
├── models/             # Entidades JPA
│   └── enums/          # Enumeraciones
├── repository/         # Repositorios JPA
├── security/           # Configuración de seguridad
├── service/            # Interfaces de servicios
│   └── impl/           # Implementaciones
└── util/               # Utilidades (DataInitializer)
```
