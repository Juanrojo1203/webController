# Sistema de Gestión de Imágenes - Farmacia Web

## Descripción General

Este documento describe el sistema completo de gestión de imágenes implementado para la aplicación web de farmacia. El sistema ha sido diseñado para ser robusto, escalable y fácil de depurar.

## Arquitectura del Sistema

### Componentes Principales

1. **ImagenService** - Servicio principal para gestión de imágenes
2. **ImagenController** - Controlador REST para servir imágenes
3. **ProductoValidacionService** - Validación de productos e imágenes
4. **WebConfig** - Configuración de recursos estáticos

### Ubicaciones de Imágenes

El sistema busca imágenes en las siguientes ubicaciones (en orden de prioridad):

1. `src/main/resources/static/css/img/`
2. `src/main/resources/static/img/`

### Formatos Soportados

- PNG (.png)
- JPEG (.jpg, .jpeg)
- GIF (.gif)

## Endpoints Disponibles

### Servicio de Imágenes
- `GET /imagen/{nombreImagen}` - Sirve una imagen específica

### Diagnóstico y Depuración
- `GET /test-imagen` - Página de prueba con imágenes de ejemplo
- `GET /debug/imagenes` - Lista todas las imágenes disponibles
- `GET /diagnostico/sistema` - Diagnóstico completo del sistema

## Uso en Templates

### Sintaxis Recomendada
```html
<img th:src="@{'/imagen/' + ${producto.imagen}}" 
     th:alt="${producto.nombre}" 
     onerror="this.style.display='none'" />
```

### Características
- **Fallback automático**: Si una imagen no se encuentra, se oculta automáticamente
- **Cache**: Las imágenes se cachean por 1 hora para mejorar el rendimiento
- **Múltiples formatos**: Busca automáticamente en diferentes formatos

## Configuración

### Application Properties
```properties
# Configuración de recursos estáticos
spring.web.resources.static-locations=classpath:/static/
spring.web.resources.cache.period=3600

# Logging para depuración
logging.level.com.formulario.webformulario.service.ImagenService=DEBUG
logging.level.com.formulario.webformulario.Controller.ImagenController=DEBUG
```

## Productos Configurados

Los siguientes productos están configurados en el sistema:

1. Acetaminofén (`acetaminofen`)
2. Ibuprofeno (`ibuprofeno`)
3. Loratadina (`loratadina`)
4. Omeprazol (`omeprazol`)
5. Amoxicilina (`amoxicilina`)
6. Dolex Forte (`dolex`)
7. Salbutamol (`salbutamol`)
8. Diclofenaco MK (`diclofenaco`)
9. Paracetamol (`paracetamol`)
10. Azitromicina (`azitromicina`)

## Solución de Problemas

### Imágenes No Se Muestran

1. **Verificar ubicación**: Asegúrate de que las imágenes están en las carpetas correctas
2. **Verificar nombres**: Los nombres deben coincidir exactamente (sin extensión en el código)
3. **Verificar formato**: Solo se soportan PNG, JPEG y GIF
4. **Usar diagnóstico**: Visita `/diagnostico/sistema` para ver el estado completo

### Herramientas de Diagnóstico

1. **Diagnóstico Completo**: `/diagnostico/sistema`
   - Muestra el estado de todas las ubicaciones
   - Verifica cada producto individualmente
   - Proporciona enlaces de prueba

2. **Lista de Imágenes**: `/debug/imagenes`
   - Lista todas las imágenes encontradas
   - Proporciona enlaces directos para probar

3. **Página de Prueba**: `/test-imagen`
   - Muestra imágenes de ejemplo
   - Verifica que el sistema funciona correctamente

## Logging

El sistema proporciona logging detallado:

- **INFO**: Operaciones principales
- **DEBUG**: Búsqueda de imágenes y detalles técnicos
- **WARN**: Imágenes faltantes o problemas menores
- **ERROR**: Errores críticos del sistema

## Mantenimiento

### Agregar Nuevas Imágenes

1. Colocar la imagen en `src/main/resources/static/css/img/` o `src/main/resources/static/img/`
2. Usar el nombre sin extensión en el código del producto
3. Verificar con `/diagnostico/sistema`

### Actualizar Productos

1. Modificar `CatalogoController.java`
2. Asegurarse de que las imágenes correspondientes existen
3. Ejecutar validación automática

## Rendimiento

- **Cache**: 1 hora de cache para todas las imágenes
- **Compresión**: Automática por Spring Boot
- **Lazy Loading**: Las imágenes se cargan solo cuando se solicitan

## Seguridad

- **Validación de rutas**: Solo se permiten imágenes en ubicaciones específicas
- **Tipos MIME**: Validación automática de tipos de archivo
- **Error handling**: Manejo seguro de errores sin exposición de información sensible
