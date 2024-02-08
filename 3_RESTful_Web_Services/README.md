# 3. RESTful Web Services

### 3.0. Los servicios expuestos:
	- Para el catálogo de canciones (catalog):
		- getSongById(Long id): recibe un id de canción a recuperar. Devuelve la cación, si la encuentra.
		- getSongsByKeywords(String keyword): recibe un texto para buscar canciones que tengan el texto en el título o cantante. Devuelve una lista con las canciones encontradas.
		- createSong(Song newSong): recibe una canción. Devuelve la canción persistida. Devuelve un mensaje de confirmación.
		- ResponseEntity saveSongs(Collection<Song> songs): recibe una lista de canciones a actualizar o persistir. 
	- Para el carrito de compra (cart):
		- getBalance(): devuelve el valor del balance del carrito.
		- addItem(PurchaseOrderLineSong item): recibe un item a añadir al carrito. Devuelve un mensaje de confirmación.
		- ResponseEntity removeItem(@Long item): recibe un id de item a retirar del carrito. Devuelve un mensaje de confirmación.
		- getItemCount(): devuelve el número de items
		- empty(): vacia el carrito. Devuelve un mensaje de confirmación.
		- buy(): ejecuta la compra. Devuelve un mensaje de confirmación.
### 3.1. Para el proyecto Banana Music:
	- define los endpoints para "catalog", incluyendo el path, parámetros, body, respuesta...
	- igualmnte, define los endpoints y servicios para "cart".
	- documenta la definción en un documento (md, txt, ppt, doc, o pdf).
### 3.2. Implementa los endpoints.
	- usa data.sql para precargar datos
	- evita que se generen json circulares (debido a las relaciones entre entidades)
### 3.3. Valida la entrada de nuevos recursos, así como su entrega.
	- para ello añade las anotaciones de validación pertinentes
### 3.4. Incorpora una gestión adecuada de excepciones.
### 3.5. Habilita peticiones solo desde localhost:80.
### 3.6. Usa https para el perfil en prod.
### 3.7. Genera la documentación swagger para todos los endpoints.
### 3.8. Incorpora la configuración de locale. Pon por defecto inglés.
