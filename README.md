# BDA-Grupo2-Lab1
# Instrucciones detalladas para configurar y desplegar la aplicación. 

## **Para ejecutar el *back-end***:
1. Crear una base de datos llamada "grupo2bda" en la aplicación pgAdmin 4.

2. Modificar la URL, usuario, contraseña y nombre de la base de datos de PostgreSQL dentro de los archivos `application.properties` dentro de la carpeta “resources” y `Sql2oConfig.java` dentro de la carpeta “config”.

Ejemplo de modificación en `Sql2oConfig.java`:

```
    @Bean
    public Sql2o sql2o() {
        return new Sql2o("jdbc:postgresql://localhost:5433/grupo2bda", "postgres", "1234");
    }
```

Observación:
En la URL, se debe cambiar el puerto a la base de datos creada en pgAdmin4.
El nombre de la base de datos creada en pgAdmin4 y en application.properties deben ser iguales.

3. Ejecutar el backend.

4. Verificar que el puerto donde se realizará el back-end y el front-end se encutra libre al momento de ejecutar el codigo.
> [!note]
> Como pre-definido, se realizará en el puerto 8080 en el back-end
> * En el caso de que se encuentre ocupado ingresar la siguiente linea en el archivo `application.properties`, por ejemplo:
> * server.port=8081

> [!note]
> Como pre-definido, se realizará en el puerto 8080 en el front-end
> * En el caso de que se encuentre ocupado cambiar la siguiente linea en el archivo `.env`, por ejemplo:
> * VITE_BACKEND_PORT = 8081
  


## **Para ejecutar el *front-end***:
1. Abrir una sesión del terminal con el directorio situado en la carpeta del front-end.
2.  Acceder a la URL `http://localhost:5173/`, este URL se encuentra en el documento “WebConfig”

> [!note]
> * Una Orden puede contar con los estados: “enviado” y “pendiente”.
> * Un Producto puede contar con los estados: “disponible” y “agotado”.
> Al iniciar sesión, el token utilizado solo dura 10 minutos.
