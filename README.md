<!-- logo -->
<p align="center">
  <img src="https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png">
</p>

<h2>
  Descripción
</h2>
Pokédex es un proyecto de API hecho para fanáticos y no tan fanáticos de Pokémon. Se trata de un JSON abierto hecho para recabar la mayor cantidad de información posible sobre los pokémons de todas las generaciones. Incluso, también la idea es tener información sobre los tipos con sus fortalezas y debilidades, las descripciones de cada habilidad, y hasta que los usuarios puedan traer la lista por generación. Si bien la API ya cuenta con bastante info lista para ser utilizada, se encuentra en constante desarrollo con el objetivo de brindar cada vez más información.

<h2>
  ¿Cómo funciona?
</h2>
Como ocurre con toda API, se maneja por distintos endpoints con el objetivo de que los usuarios puedan recabar solamente la información que les haga falta en el momento. A continuación se detallará cada endpoint y cómo funciona.

<h1>
  "/api/pokemon"
</h1>
El endpoint principal. Trae la lista completa con todos los pokémons de cada generación, solo con el id y el nombre del mismo. La idea es que, a través del PokemonController, el usuario realice un pedido que pase por el PokeapiService, luego por el PokemonRepository, y finalmente llegue al model Pokeapi para recabar los datos. Funciona así:

PokemonController (Controller):
```js
    @GetMapping
    public List<Pokeapi> getAll(){
        return service.getAllPokes();
    }
```

PokeapiService (Service):
```js
    public List<Pokeapi> getAllPokes(){
        return repository.findAll();
    }
```
PokemonRepository (Repository):
```js
    public List<Pokeapi> findAll() {
        String sql = "SELECT id_pokemon, nombre FROM pokemons";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Pokeapi p = new Pokeapi();
            p.setId_pokemon(rs.getLong("id_pokemon"));
            p.setNombre(rs.getString("nombre"));
            return p;
        });
    }
```
Pokeapi (Model):
```js
public class Pokeapi {
    private Long id_pokemon;
    private String nombre;
    private List<Tipos> tipos;
    private List<Habilidades> habilidades;
    private Ligas liga;
```
