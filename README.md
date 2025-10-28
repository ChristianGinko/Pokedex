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

<h3>
  "/api/pokemon"
</h3>
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

<h3>
  "/api/pokemon/{id_pokemon}"
</h3>
El endpoint para contar con todos los datos del pokémon. Una vez que conoces el id del pokémon cuyas estadísticas deseas ver, solo lo agregas al final y te traerá ese pokémon con sus tipos, habilidades, y hasta la liga correspondiente. Funciona así:

PokemonController (Controller):
```js
    @GetMapping("/{id_pokemon}")
    public ResponseEntity<Pokeapi> getById(@PathVariable Long id_pokemon){
        try{
            return ResponseEntity.ok(service.getPokeCompleto(id_pokemon));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
```

PokeapiService (Service):
```js
    public Pokeapi getPokeCompleto(Long id_pokemon) {
        Pokeapi pokemon = repository.findPokeById(id_pokemon)
                .orElseThrow(()-> new RuntimeException("Pokemon no encontrado"));

        pokemon.setTipos(repository.findTipoByPokemon(id_pokemon));
        pokemon.setHabilidades(repository.findHabilidadByPokemon(id_pokemon));
        pokemon.setLiga(repository.findLigaByPokemon(id_pokemon)
                .orElseThrow(()-> new RuntimeException("Liga no encontrada"))
        );
        return pokemon;
    }
```

PokemonRepository (Repository):
```js
    public Optional<Pokeapi> findPokeById(Long id_pokemon){
        String sql = "SELECT id_pokemon, nombre FROM pokemons WHERE id_pokemon = ?";

        return jdbcTemplate.query(sql, new Object[]{id_pokemon}, rs -> {
            if (rs.next()) {
                Pokeapi p = new Pokeapi();
                p.setId_pokemon(rs.getLong("id_pokemon"));
                p.setNombre(rs.getString("nombre"));
                return Optional.of(p);
            }
            return Optional.empty();
                });
    }

    public List<Tipos> findTipoByPokemon(Long id_pokemon){
        String sql = "SELECT t.id_tipo, t.nombre "+
                "FROM tipos t "+
                "INNER JOIN pokemon_tipo pt ON t.id_tipo = pt.id_tipo "+
                "INNER JOIN pokemons p ON pt.id_pokemon = p.id_pokemon "+
                "WHERE p.id_pokemon = ?";
        return jdbcTemplate.query(sql, new Object[]{id_pokemon}, rs -> {
            List<Tipos> tipos = new ArrayList<>();
            while(rs.next()){
                Tipos t = new Tipos();
                t.setId_tipo(rs.getLong("id_tipo"));
                t.setNombre(rs.getString("nombre"));
                tipos.add(t);
            }
            return tipos;
        });
    }

    public List<Habilidades> findHabilidadByPokemon(Long id_pokemon){
        String sql = "SELECT h.id_habilidad, h.nombre "+
                "FROM habilidades h "+
                "INNER JOIN pokemon_habilidad ph ON h.id_habilidad = ph.id_habilidad "+
                "INNER JOIN pokemons p ON ph.id_pokemon = p.id_pokemon "+
                "WHERE p.id_pokemon = ?";
        return jdbcTemplate.query(sql, new Object[]{id_pokemon}, rs -> {
            List<Habilidades> habilidades = new ArrayList<>();
            while(rs.next()){
                Habilidades h = new Habilidades();
                h.setId_habilidad(rs.getLong("id_habilidad"));
                h.setNombre(rs.getString("nombre"));
                habilidades.add(h);
            }
            return habilidades;
        });
    }

    public Optional<Ligas> findLigaByPokemon(Long id_pokemon){
        String sql = "SELECT l.id_liga, l.nombre "+
                "FROM ligas l "+
                "INNER JOIN pokemons p ON l.id_liga = p.id_liga "+
                "WHERE p.id_pokemon = ?";
        return jdbcTemplate.query(sql, new Object[]{id_pokemon}, rs-> {
            if(rs.next()){
                Ligas l = new Ligas();
                l.setId_liga(rs.getLong("id_liga"));
                l.setNombre(rs.getString("nombre"));
                return Optional.of(l);
            }
            return Optional.empty();
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

Tipos (Model):
```js
public class Tipos {
    private Long id_tipo;
    private String nombre;
```

Habilidades (Model):
```js
public class Habilidades {
    private Long id_habilidad;
    private String nombre;
```

Ligas (Model):
```js
public class Ligas {
    private Long id_liga;
    private String nombre;
```
