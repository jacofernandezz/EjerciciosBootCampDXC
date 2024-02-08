# 1. Spring

### 1.1 Para el proyecto Banana Music:
	- Activa las dependencias para usar spring jpa y poder conectarte a la bbdd mysql
	- Genera la configuración de spring basada en clases para Song (persistencia y servicio) que permita pasar los tests correspondientes.
		- Añade las anotaciones pertinentes a los tests para que funcionen.
	- Genera la configuración de spring basada en anotaciones para PurchaseOrder (persistencia y servicio) que permita pasar los tests correspondientes.
### 1.2 Dada la base de datos banana_music:
	- Actualiza la configuración para que se conecte al datasource.
	- Anota las entidades báse (Song y PurchaseOrder).
	- Genera la implementación de capa de persistencia basada en repositorios spring jpa para Song.
	- Genera la implementación de capa de persistencia basada en repositorios spring data JpaRepository para PurchaseOrder.
	- Anota las entidades relacionadas (resto y herencia de Song).
### 1.3 Añade perfiles de "dev" y "prod".
	- "dev" debe usas los repositorios en memoria
	- "prod" el datasource de base de datos.
