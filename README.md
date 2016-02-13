mercury (DOCUMENTACIÓN EN CONSTRUCCIÓN)
======================

> Proyecto final del curso Developing Android Apps de Udacity.

## ¿En qué consiste la aplicación?
- La app centra su ámbito en el mundo de la música, obteniendo los artistas más populares del momento y elaborando un ranking con los mismos.
- Este ranking es configurable por género, pudiendo ser modificado el número de artistas que lo conforman.
- En la pantalla del ranking disponemos de un botón flotante que al ser pulsado muestra la última vez que se actualizó el ranking y el género al que pertenece el mismo.
- El ranking se actualizará cada vez que cambiemos el género o número de artistas que lo conforman (siempre y cuando se disponga de acceso a red).
- La popularidad de los artistas es algo cambiante. Para llegar a un equilibrio entre acceso a red y actualización de datos la aplicación sigue una política de actualización automática diaria (siempre y cuando se tenga acceso a red), comparando el día última actualización con el día en curso (no se tiene en cuenta la hora).
- Al pulsar en un artista del ranking se mostrará su ficha donde podremos ver con más detalle su información. La ficha del artista también dispone de un botón para compartir el artista y el puesto que ocupa en el ranking.
- En la ficha del artista aparece un botón flotante. Si tenemos instalado Spotify en nuestro dispositivo, al pulsar el botón se abrirá la app de Spotify con la página del artista lista para la reproducción. En caso de no tener Spotify en el terminal, el botón abrirá en el navegador web el reproductor web de Spotify con la página del artista.
- Por último, citar que se ha añadido una screen denominada "Acerca de..." con un poco información del desarrollador de la aplicación y con sus respectivos enlaces a GitHub y Twitter (en caso de tener instalada la app de Twitter se abrirá el link en la misma).

## ¿De dónde sacamos la información?
- La información 

## Clean architecture 
- No

## ¿Qué librerías hemos utilizado?
- <a href="http://square.github.io/retrofit" target="_blank">Retrofit</a>: cliente REST para Android y Java, desarrollada por Square. Permite hacer peticiones y gestionar diferentes tipos de parámetros y parsear automáticamente la respuesta a un POJO.
- <a href="http://square.github.io/okhttp" target="_blank">OKHttp</a>: cliente HTTP+SPDY con soporte para Android y Java.
- <a href="https://github.com/google/gson" target="_blank">Gson</a>: conversor de objetos Java en su representación JSON y viceversa.
- <a href="http://square.github.io/picasso" target="_blank">Picasso</a>: permite cargar y tratar imágenes de la red en una app Android.
- <a href="https://github.com/PaNaVTEC/ViewThreadDecorator" target="_blank">ViewThreadDecorator</a>: facilita la decoración de vistas y soluciona problemas con el threading en la implementación del MV*.

## ¿De donde puedo sacar el APK para instalarlo en mi dispositivo?
- Para descargar el apk hacer click aquí <a href="https://github.com/ricardonavarrom/mercury/blob/master/mercury.apk?raw=true"><img src="https://github.com/ricardonavarrom/mercury/blob/master/doc/download.png" alt="download mercury" width="100px" height="100px"/></a>

## ¿Cómo puedo instalar el proyecto en mi equipo?
- Clonar el proyecto en el equipo.
- Obtener una API key de <a href="https://developer.echonest.com/account/register" target="_blank">EchoNest</a>.
- Dirigirse al módulo app y en el fichero build.gradle añadir la API key obtenida en el parámetro ECHONEST_API_KEY.
- Sincronizar los cambios con Graddle.
- Lanzar la aplicación.

## Algunos screenshot
<img src="https://github.com/ricardonavarrom/mercury/blob/master/doc/screenshot1.png" width="200px" />
<img src="https://github.com/ricardonavarrom/mercury/blob/master/doc/screenshot2.png" width="200px" />
<img src="https://github.com/ricardonavarrom/mercury/blob/master/doc/screenshot3.png" width="200px" />
<img src="https://github.com/ricardonavarrom/mercury/blob/master/doc/screenshot4.png" width="200px" />
<img src="https://github.com/ricardonavarrom/mercury/blob/master/doc/screenshot5.png" width="400px" />
<img src="https://github.com/ricardonavarrom/mercury/blob/master/doc/screenshot6.png" width="400px" />

## Vídeo demo
<a href="http://www.youtube.com/watch?feature=player_embedded&v=hhVmuRycZME" target="_blank"><img src="http://img.youtube.com/vi/hhVmuRycZME/0.jpg" alt="mercury" width="400px" height="300px"/></a>

### Autor
| [![twitter/ricardonavarrom](https://avatars3.githubusercontent.com/u/2845589?v=3&s=70)](https://twitter.com/ricardonavarrom "Follow @ricardonavarrom on Twitter") |
|---|
| [@ricardonavarrom](https://twitter.com/ricardonavarrom) |