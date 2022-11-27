# TRIANAFY

Trianafy es una API de musica desde la que se puede operar con canciones, artistas y listas de reproducción.

Para una información más detallada: http://localhost:8080/swagger-ui/index.html#/

Información general:

1. Artistas:
    - get /artist/: obtén una lista con todos los artistas
    - get /artist/id: obtén el artista con ese id
    - post /artist/: crea un nuevo artista
    - put /artist/id: modifica el artista con ese id
    - delete /artist/id: elimina el artista con ese id

2. Canciones
    - get /song/: obtén una lista con todas las csnciones
    - get /song/id: obtén la canción con ese id
    - post /song/: crea una nueva canción
    - put /song/id: modifica la canción con ese id
    - delete /song/id: elimina la canción con ese id

3. Playlists
    - get /list/: obtén una lista con todas las listas
    - get /list/id: obtén la lista con ese id
    - post /list/: crea un nueva lista
    - put /list/id: modifica la lista con ese id
    - delete /list/id: elimina la lista con ese id
    - get /list/id/song: obtén todas las canciones de una lista
    - get /list/id/song/id: obtén los datos de una canción de una playlist
    - post /list/id/song/id: añade la canción con ese id a la lista con ese id
    - delete /list/id/song/id: elimina la canción con ese id de la lista con ese id
