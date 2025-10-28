Endpoint: "/api/pokemon"
Este endpoint devuelve la lista completa de pokemons.
A través de la función getAll, el PokemonController conecta con la función getAllPokes del PokeapiService.
El PokeapiService conecta con la función findAll del PokemonRepository a través de la función getAllPokes.
El PokemonRepository realiza la consulta SQL adecuada y, a través de la función findAll, pide datos del model de Pokeapi.
Pokeapi devuelve los valores a PokemonRepository.
PokemonRepository devuelve los valores a PokeapiService.
PokeapiService devuelve los valores a PokemonController.
PokemonController devuelve la lista completa al usuario.

Endpoint: "/api/pokemon/{id_pokemon}"
Este endpoint devuelve todos los datos de un pokemon determinado.
A través de la función getById, el PokemonController conecta con la función getPokeCompleto del PokeapiService.
El PokeapiService realiza consultas a las funciones findPokeById, findTipoByPokemon, findHabilidadByPokemon y findLigaByPokemon del PokemonRepository.
La función findPokeById realiza una consulta SQL al model Pokeapi, findTipoByPokemon le realiza una consulta SQL al model Tipos, findHabilidadByPokemon le realiza una consulta SQL al model Habilidades, y findLigaByPokemon le realiza una consulta SQL al model Ligas.
Cada model mencionado devuelve los valores pedidos al PokemonRepository.
PokemonRepository le devuelve esos valores a PokeapiService.
PokeapiService le devuelve esos valores a PokemonController.
PokemonController le devuelve el pokemon completo con todos los datos al usuario.
