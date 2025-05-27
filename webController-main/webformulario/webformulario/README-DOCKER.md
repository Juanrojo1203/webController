# 🐳 Configuración de MySQL con Docker

## 📋 Requisitos Previos
- Docker Desktop instalado
- Docker Compose disponible

## 🚀 Instrucciones de Uso

### 1. Levantar la Base de Datos
```bash
# Desde la carpeta del proyecto (donde está docker-compose.yml)
docker-compose up -d
```

### 2. Verificar que los Contenedores Estén Corriendo
```bash
docker-compose ps
```

Deberías ver:
- `farmacia-mysql` (puerto 3306)
- `farmacia-phpmyadmin` (puerto 8080)

### 3. Acceder a la Base de Datos

#### Desde la Aplicación Spring Boot:
- **Host**: localhost
- **Puerto**: 3306
- **Base de Datos**: farmacia
- **Usuario**: root
- **Contraseña**: admin123

#### Desde phpMyAdmin (Interfaz Web):
- **URL**: http://localhost:8080
- **Usuario**: root
- **Contraseña**: admin123

### 4. Iniciar la Aplicación Spring Boot
```bash
# La aplicación se conectará automáticamente a MySQL
mvn spring-boot:run
```

## 🔧 Comandos Útiles

### Detener los Contenedores
```bash
docker-compose down
```

### Ver Logs de MySQL
```bash
docker-compose logs mysql
```

### Reiniciar Solo MySQL
```bash
docker-compose restart mysql
```

### Eliminar Todo (Datos Incluidos)
```bash
docker-compose down -v
```

## 📊 Estructura de la Base de Datos

La base de datos se inicializa automáticamente con:
- Tabla `productos` con productos de farmacia de ejemplo
- Tabla `categorias` con categorías predefinidas
- Tabla `carousel_slides` con slides para el carrusel
- Datos de ejemplo para testing

## 🔍 Solución de Problemas

### Error de Conexión
1. Verificar que Docker esté corriendo
2. Verificar que el puerto 3306 no esté ocupado
3. Reiniciar los contenedores: `docker-compose restart`

### Cambiar Contraseña
Editar `docker-compose.yml` y cambiar `MYSQL_ROOT_PASSWORD`

### Puerto Ocupado
Cambiar el puerto en `docker-compose.yml`:
```yaml
ports:
  - "3307:3306"  # Usar puerto 3307 en lugar de 3306
```

Y actualizar `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/farmacia
```
