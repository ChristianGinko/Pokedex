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

<h3>
  "/api/tipo"
</h3>
La lista de todos los tipos posibles. Al igual que ocurre con la lista completa de pokémons, en este endpoint solo se podrá ver el id y el nombre del tipo. Funciona así:

TipoController (Controller):
```js
    @GetMapping
    public List<Tipos> getAll(){
        return service.getAllTipos();
    }
```

TipoService (Service):
```js
    public List<Tipos> getAllTipos(){
        return repository.findAll();
    }
```

TipoRepository (Repository):
```js
    public List<Tipos> findAll() {
        String sql = "SELECT id_tipo, nombre FROM tipos";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Tipos t = new Tipos();
            t.setId_tipo(rs.getLong("id_tipo"));
            t.setNombre(rs.getString("nombre"));
            return t;
        });
    }
```

Tipos (Model)
```js
public class Tipos {
    private Long id_tipo;
    private String nombre;
```

<h3>
  "/api/tipo/{id_tipo}"
</h3>
Este endpoint trae toda la data de un determinado tipo, desde fortalezas y debilidades hasta incluso la lista completa de los pokémons que son de ese determinado tipo. Funciona así:

TipoController (Controller):
```js
    @GetMapping("/{id_tipo")
    public ResponseEntity<Tipos> getById(@PathVariable Long id_tipo){
        try{
            return ResponseEntity.ok(service.getTipoCompleto(id_tipo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
```

TipoService (Service):
```js
    public Tipos getTipoCompleto(Long id_tipo){
        Tipos tipos = repository.findTipoById(id_tipo)
                .orElseThrow(()-> new RuntimeException("Tipo no encontrado"));

        tipos.setDobleDanioDe(repository.findDobleDanioDeByTipo(id_tipo));
        tipos.setDobleDanioA(repository.findDobleDanioAByTipo(id_tipo));
        tipos.setMitadDanioDe(repository.findMitadDanioDeByTipo(id_tipo));
        tipos.setMitadDanioA(repository.findMitadDanioAByTipo(id_tipo));
        tipos.setSinDanioDe(repository.findSinDanioDeByTipo(id_tipo));
        tipos.setSinDanioA(repository.findSinDanioAByTipo(id_tipo));
        tipos.setPokemons(repository.findPokeByTipo(id_tipo));
        return tipos;
    }
```

TipoRepository (Repository):
```js
    public Optional<Tipos> findTipoById(Long id_tipo){
        String sql = "SELECT id_tipo, nombre FROM tipos WHERE id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs->{
            if(rs.next()){
                Tipos t = new Tipos();
                t.setId_tipo(rs.getLong("id_tipo"));
                t.setNombre(rs.getString("nombre"));
                return Optional.of(t);
            }
            return Optional.empty();
                });
    }

    public List<Tipos> findDobleDanioDeByTipo(Long id_tipo){
        String sql = "SELECT t1.id_tipo, t1.nombre FROM tipos t "+
                "LEFT JOIN `doble_daño_de` ddd ON t.id_tipo = ddd.id_tipo1 "+
                "LEFT JOIN tipos t1 ON ddd.id_tipo2 = t1.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
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

    public List<Tipos> findDobleDanioAByTipo(Long id_tipo){
        String sql = "SELECT t1.id_tipo, t1.nombre FROM tipos t "+
                "LEFT JOIN `doble_daño_a` dda ON t.id_tipo = dda.id_tipo1 "+
                "LEFT JOIN tipos t1 ON dda.id_tipo2 = t1.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
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

    public List<Tipos> findMitadDanioDeByTipo(Long id_tipo){
        String sql = "SELECT t1.id_tipo, t1.nombre FROM tipos t "+
                "LEFT JOIN `mitad_daño_a` mdd ON t.id_tipo = mdd.id_tipo1 "+
                "LEFT JOIN tipos t1 ON mdd.id_tipo2 = t1.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
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

    public List<Tipos> findMitadDanioAByTipo(Long id_tipo){
        String sql = "SELECT t1.id_tipo, t1.nombre FROM tipos t "+
                "LEFT JOIN `mitad_daño_a` mda ON t.id_tipo = mda.id_tipo1 "+
                "LEFT JOIN tipos t1 ON mda.id_tipo2 = t1.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
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

    public List<Tipos> findSinDanioDeByTipo(Long id_tipo){
        String sql = "SELECT t1.id_tipo, t1.nombre FROM tipos t "+
                "LEFT JOIN `sin_daño_de` sdd ON t.id_tipo = sdd.id_tipo1 "+
                "LEFT JOIN tipos t1 ON sdd.id_tipo2 = t1.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
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

    public List<Tipos> findSinDanioAByTipo(Long id_tipo){
        String sql = "SELECT t1.id_tipo, t1.nombre FROM tipos t "+
                "LEFT JOIN `sin_daño_de` sda ON t.id_tipo = sda.id_tipo1 "+
                "LEFT JOIN tipos t1 ON sda.id_tipo2 = t1.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
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

    public List<Pokeapi> findPokeByTipo(Long id_tipo){
        String sql = "SELECT p.id_pokemon, p.nombre FROM pokemons p "+
                "INNER JOIN pokemon_tipo pt ON p.id_pokemon = pt.id_pokemon "+
                "INNER JOIN tipos t ON pt.id_tipo = t.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
            List<Pokeapi> pokemons = new ArrayList<>();
            while(rs.next()){
                Pokeapi p = new Pokeapi();
                p.setId_pokemon(rs.getLong("id_pokemon"));
                p.setNombre(rs.getString("nombre"));
                pokemons.add(p);
            }
            return pokemons;
        });
    }
```

Tipos (Model):
```js
public class Tipos {
    private Long id_tipo;
    private String nombre;
    private List<Tipos> dobleDanioDe;
    private List<Tipos> dobleDanioA;
    private List<Tipos> mitadDanioDe;
    private List<Tipos> mitadDanioA;
    private List<Tipos> sinDanioDe;
    private List<Tipos> sinDanioA;
    private List<Pokeapi> pokemons;
```

Pokeapi (Model):
```js
public class Pokeapi {
    private Long id_pokemon;
    private String nombre;
```
