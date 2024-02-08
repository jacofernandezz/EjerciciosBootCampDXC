# 2. SpringBoot


### 2.0. Previo:
	- Elimina los repositorios InMemory y los perfiles de la configuración.
### 2.1. Actualiza el proyecto para:
	- incorporar las dependencias de SpringBoot necesarias para que sea un proyecto web.
	- incorporar el archivo de propiedades yaml para conectarse a la base de datos mysql.
	- definir el nivel de log a INFO
### 2.2. Implementa:
	- 3 endpoints: "/song", "/user" y "/order" que devuelvan un string.
	- un interceptor para añadir un Header "X-Date" con la fecha actual al llamar a "/order".
	- un filtro para denegar el acceso a la ruta "/user" (Response code 403).
### 2.3. Configura el proyecto para usar dos perfiles: dev (datasource H2) y prod (datasource mysql).
	- define el puerto en 9090 para dev y 8080 para prod.
	- define el perfil activo a dev
	- define el nivel de log a WARN para prod
