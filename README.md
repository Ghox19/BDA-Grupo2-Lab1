# BDA-Grupo2-Lab1
# Instrucciones detalladas para configurar y desplegar la aplicación. 

## **Para ejecutar el *back-end***:
1. Crear una base de datos llamada "grupo2bda" en la aplicación pgAdmin 4.

2. Modificar la URL, usuario, contraseña y nombre de la base de datos de PostgreSQL dentro de los archivos `application.properties` dentro de la carpeta “resources” y `Sql2oConfig.java` dentro de la carpeta “config”.

Observación:
En la URL, se debe cambiar el puerto a la base de datos creada en pgAdmin4.
El nombre de la base de datos creada en pgAdmin4 y en application.properties deben ser iguales.

3. Ejecutar el backend.
---
Observación:
Para la ejecución de la base de datos, con el objetivo de modificar la base desde Postman es necesario realizar los siguientes cambios en en el archivo SecurityConfig.java
```
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors(cors ->cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/categoria/**", "/cliente/**", "/orden/**", "/detalleOrden/**", "/producto/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
```
Este cambio es necesario debido al uso de cookies dentro del código.
---
## **Para ejecutar el *front-end***:
1. Abrir una sesión del terminal con el directorio situado en la carpeta del front-end.
2. Ejecutar los siguientes comandos:
```
npm install
npm install axios (instalar manualmente axios para asegurar su instalación)
npm run dev 
```
3.  Acceder a la URL `http://localhost:5173/`, este URL se encuentra en el documento “WebConfig”

> [!note]
> * Una Orden puede contar con los estados: “enviado” y “pendiente”.
> * Un Producto puede contar con los estados: “disponible” y “agotado”.
> Al iniciar sesión, el token utilizado solo dura 10 minutos.
