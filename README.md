<!-- logo -->
<p align="center">
  <img src="https://archives.bulbagarden.net/media/upload/4/4b/Pok%C3%A9dex_logo.png">
</p>


<h1>
  Descripción
</h1>
Pokédex es un proyecto de API hecho para fanáticos y no tan fanáticos de Pokémon. Se trata de un JSON abierto hecho para recabar la mayor cantidad de información posible sobre los pokémons de todas las generaciones. Incluso, también la idea es tener información sobre los tipos con sus fortalezas y debilidades, las descripciones de cada habilidad, y hasta que los usuarios puedan traer la lista por generación. Si bien la API ya cuenta con bastante info lista para ser utilizada, se encuentra en constante desarrollo con el objetivo de brindar cada vez más información.


<h1>
  ¿Cómo funciona?
</h1>
Como ocurre con toda API, se maneja por distintos endpoints con el objetivo de que los usuarios puedan recabar solamente la información que les haga falta en el momento. La estructura es bastante sencilla de entender: el controller le envía una petición al service correspondiente, el service al repository, y el repository al o a los models que haga falta, ya que en el caso de los datos completos se necesita hacer consultas a más de uno. A continuación se detallará cada endpoint y cómo funciona.   


<h2>
  "/api/pokemon"
</h2
El endpoint principal. Trae la lista completa con todos los pokémons de cada generación, solo con el id y el nombre del mismo. La idea es que, a través del PokemonController, el usuario realice un pedido que pase por el PokeapiService, luego por el PokemonRepository, y finalmente llegue al model Pokeapi para recabar los datos. Funciona así:

<h3>
  PokemonController (Controller):
</h3>

A través de la función getAll, el PokemonController le envía una petición a la función getAllPokes del PokeapiService.

```js
@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokeapiService service;

    public PokemonController(PokeapiService service){
        this.service = service;
    }

    @GetMapping
    public List<Pokeapi> getAll(){
        return service.getAllPokes();
    }
}
```

<h3>
PokeapiService (Service):
</h3>

Recibe la petición de parte del PokemonController. A raíz de ello, utiliza la función getAllPokes para realizar otra petición a la función findAll del PokemonRepository.

```js
@Service
public class PokeapiService {

    private PokemonRepository repository;

    @Autowired
    public PokeapiService(PokemonRepository repository) {
        this.repository = repository;
    }

    public List<Pokeapi> getAllPokes(){
        return repository.findAll();
    }
}
```

<h3>
  PokemonRepository (Repository):
</h3>

Recibe la petición del PokeapiService. Debido a ello, utiliza la función findAll para realizar la consulta SQL y traer los datos desde Pokeapi.

```js
@Repository
public class PokemonRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PokemonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Pokeapi> findAll() {
        String sql = "SELECT id_pokemon, nombre FROM pokemons";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Pokeapi p = new Pokeapi();
            p.setId_pokemon(rs.getLong("id_pokemon"));
            p.setNombre(rs.getString("nombre"));
            return p;
        });
    }
}
```

<h3>
Pokeapi (Model):
</h3>

Almacena los datos que pide PokemonRepository. En este caso, devuelve el id y el nombre de todos los pokémons.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pokeapi {
    private Long id_pokemon;
    private String nombre;

    public Pokeapi() {
    }

    public Long getId_pokemon() {
        return id_pokemon;
    }

    public void setId_pokemon(Long id_pokemon) {
        this.id_pokemon = id_pokemon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
```

<h2>
  "/api/pokemon/{id_pokemon}"
</h2>

El endpoint para contar con todos los datos del pokémon. Una vez que conoces el id del pokémon cuyas estadísticas deseas ver, solo lo agregas al final y te traerá ese pokémon con sus tipos, habilidades, y hasta la liga correspondiente. Funciona así:

<h3>
  PokemonController (Controller):
</h3>

A través de la función getById, realiza una petición a la función getPokeCompleto de PokeapiService. Además, se agrega "/{id_pokemon}" al endpoint.

```js
@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokeapiService service;

    public PokemonController(PokeapiService service){
        this.service = service;
    }

    @GetMapping("/{id_pokemon}")
    public ResponseEntity<Pokeapi> getById(@PathVariable Long id_pokemon){
        try{
            return ResponseEntity.ok(service.getPokeCompleto(id_pokemon));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
```

<h3>
  PokeapiService (Service):
</h3>

Recibe la petición de la función getById del PokemonController. Acto seguido, y a través de la función getPokeCompleto, envía una petición a las funciones findPokeById, findTipoByPokemon, findHabilidadByPokemon y findLigaByPokemon, todas ellas del PokemonRepository.

```js
@Service
public class PokeapiService {

    private PokemonRepository repository;

    @Autowired
    public PokeapiService(PokemonRepository repository) {
        this.repository = repository;
    }

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

}
```

<h3>
  PokemonRepository (Repository):
</h3>

Recibe la petición de parte de la función getPokeCompleto del PokeapiService. Las funciones llamadas (findPokeById, findTipoByPokemon, findHabilidadByPokemon y findLigaByPokemon) realizan las consultas SQL pertinentes a los models correspondientes.

```js
@Repository
public class PokemonRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PokemonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

}
```

<h3>
  Pokeapi (Model):
</h3>

Recibe la petición de parte de la función findPokeById del PokemonRepository. A raíz de ello, envía los datos completos con id del pokémon, nombre, tipos, habilidades, y liga.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pokeapi {
    private Long id_pokemon;
    private String nombre;
    private List<Tipos> tipos;
    private List<Habilidades> habilidades;
    private Ligas liga;

    public Pokeapi() {
    }

    public Long getId_pokemon() {
        return id_pokemon;
    }

    public void setId_pokemon(Long id_pokemon) {
        this.id_pokemon = id_pokemon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Tipos> getTipos() {
        return tipos;
    }

    public void setTipos(List<Tipos> tipos) {
        this.tipos = tipos;
    }

    public List<Habilidades> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<Habilidades> habilidades) {
        this.habilidades = habilidades;
    }

    public Ligas getLiga() {
        return liga;
    }

    public void setLiga(Ligas liga) {
        this.liga = liga;
    }
}
```

<h3>
  Tipos (Model):
</h3>

Recibe la petición de la función findTipoByPokemon del PokemonRepository. A raíz de ello, envía el id y el nombre de los tipos que corresponden a ese pokémon.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tipos {
    private Long id_tipo;
    private String nombre;

    public Long getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(Long id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
```

<h3>
  Habilidades (Model):
</h3>

Recibe la petición de la función findHabilidadByPokemon del PokemonRepository. A raíz de ello, envía el id y el nombre de las habilidades que corresponden a ese pokémon.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Habilidades {
    private Long id_habilidad;
    private String nombre;

    public Habilidades() {
    }

    public Long getId_habilidad() {
        return id_habilidad;
    }

    public void setId_habilidad(Long id_habilidad) {
        this.id_habilidad = id_habilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
```

<h3>
  Ligas (Model):
</h3>

Recibe la petición de la función findLigaByPokemon del PokemonRepository. A raíz de ello, envía el id y el nombre de la liga correspondiente a ese pokémon.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ligas {
    private Long id_liga;
    private String nombre;

    public Long getId_liga() {
        return id_liga;
    }

    public void setId_liga(Long id_liga) {
        this.id_liga = id_liga;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

```

<h2>
  "/api/tipo"
</h2>

La lista de todos los tipos posibles. Al igual que ocurre con la lista completa de pokémons, en este endpoint solo se podrá ver el id y el nombre del tipo. Funciona así:

<h3>
  TipoController (Controller):
</h3>

A través de la función getAll, realiza una petición a la función getAllTipos de TipoService.

```js
@RestController
@RequestMapping("/api/tipo")
public class TipoController {

    private final TipoService service;

    public TipoController(TipoService service){
        this.service = service;
    }

    @GetMapping
    public List<Tipos> getAll(){
        return service.getAllTipos();
    }

}
```

<h3>
  TipoService (Service):
</h3>

Al haber sido llamada la función getAllTipos de parte de TipoController, la misma llama a la función findAll de TipoRepository.

```js
@Service
public class TipoService {

    private TipoRepository repository;

    @Autowired
    public TipoService(TipoRepository repository){
        this.repository = repository;
    }

    public List<Tipos> getAllTipos(){
        return repository.findAll();
    }

}
```

<h3>
  TipoRepository (Repository):
</h3>

La función findAll de TipoRepository se activa y realiza una consulta SQL al model Tipos en la que pide la lista completa de id y nombre de los tipos.

```js
@Repository
public class TipoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TipoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tipos> findAll() {
        String sql = "SELECT id_tipo, nombre FROM tipos";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Tipos t = new Tipos();
            t.setId_tipo(rs.getLong("id_tipo"));
            t.setNombre(rs.getString("nombre"));
            return t;
        });
    }
}
```

<h3>
  Tipos (Model)
</h3>

El model Tipos responde a la petición de la función findAll de TipoRepository y, acto seguido, envía los id y nombres de todos los tipos.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tipos {
    private Long id_tipo;
    private String nombre;

    public Long getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(Long id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
```

<h2>
  "/api/tipo/{id_tipo}"
</h2>

Este endpoint trae toda la data de un determinado tipo, desde fortalezas y debilidades hasta incluso la lista completa de los pokémons que son de ese determinado tipo. Funciona así:

<h3>
  TipoController (Controller):
</h3>

Utiliza la función getById para llamar a la función getTipoCompleto del TipoService. Además, se agrega "/{id_tipo}" al endpoint.

```js
@RestController
@RequestMapping("/api/tipo")
public class TipoController {

    private final TipoService service;

    public TipoController(TipoService service){
        this.service = service;
    }

    @GetMapping("/{id_tipo")
    public ResponseEntity<Tipos> getById(@PathVariable Long id_tipo){
        try{
            return ResponseEntity.ok(service.getTipoCompleto(id_tipo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
```

<h3>
  TipoService (Service):
</h3>

La función getTipoCompleto es activada desde TipoController. Acto seguido, llama a las funciones findTipoById, findDobleDanioDeByTipo, findDobleDanioAByTipo, findMitadDanioDeByTipo, findMitadDanioAByTipo, findSinDanioDeByTipo, findSinDanioAByTipo y findPokeByTipo que hay en el TipoRepository.

```js
@Service
public class TipoService {

    private TipoRepository repository;

    @Autowired
    public TipoService(TipoRepository repository){
        this.repository = repository;
    }

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

}
```

<h3>
  TipoRepository (Repository):
</h3>

Las funciones findTipoById, findDobleDanioDeByTipo, findDobleDanioAByTipo, findMitadDanioDeByTipo, findMitadDanioAByTipo, findSinDanioDeByTipo, findSinDanioAByTipo y findPokeByTipo son llamadas desde TipoService. Esto hace que, a través de las consultas SQL realizadas en cada una, TipoRepository se comunique con los models Tipos y Pokeapi.

```js
@Repository
public class TipoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TipoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
}
```

<h3>
  Tipos (Model):
</h3>

Las funciones findTipoById, findDobleDanioDeByTipo, findDobleDanioAByTipo, findMitadDanioDeByTipo, findMitadDanioAByTipo, findSinDanioDeByTipo y findSinDanioAByTipo realizan consultas SQL a este model. El primero son datos que ya tiene por defecto la tabla, los demás son reconocidos por tablas intermedias que conectan a los id_tipo.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public Long getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(Long id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Tipos> getDobleDanioDe() {
        return dobleDanioDe;
    }

    public void setDobleDanioDe(List<Tipos> dobleDanioDe) {
        this.dobleDanioDe = dobleDanioDe;
    }

    public List<Tipos> getDobleDanioA() {
        return dobleDanioA;
    }

    public void setDobleDanioA(List<Tipos> dobleDanioA) {
        this.dobleDanioA = dobleDanioA;
    }

    public List<Tipos> getMitadDanioDe() {
        return mitadDanioDe;
    }

    public void setMitadDanioDe(List<Tipos> mitadDanioDe) {
        this.mitadDanioDe = mitadDanioDe;
    }

    public List<Tipos> getMitadDanioA() {
        return mitadDanioA;
    }

    public void setMitadDanioA(List<Tipos> mitadDanioA) {
        this.mitadDanioA = mitadDanioA;
    }

    public List<Tipos> getSinDanioDe() {
        return sinDanioDe;
    }

    public void setSinDanioDe(List<Tipos> sinDanioDe) {
        this.sinDanioDe = sinDanioDe;
    }

    public List<Tipos> getSinDanioA() {
        return sinDanioA;
    }

    public void setSinDanioA(List<Tipos> sinDanioA) {
        this.sinDanioA = sinDanioA;
    }

    public List<Pokeapi> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokeapi> pokemons) {
        this.pokemons = pokemons;
    }
}
```

<h3>
  Pokeapi (Model):
</h3>

La función findPokeByTipo llama al model Pokeapi, que envía el id y el nombre de los pokémons pertenecientes al tipo solicitado.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pokeapi {
    private Long id_pokemon;
    private String nombre;

    public Pokeapi() {
    }

    public Long getId_pokemon() {
        return id_pokemon;
    }

    public void setId_pokemon(Long id_pokemon) {
        this.id_pokemon = id_pokemon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
```

<h2>
  "/api/habilidad"
</h2>

Se trata de la lista completa de habilidades disponibles. En ella podrán ver el id y el nombre de cada una. Funciona así:

<h3>
  HabilidadController (Controller):
</h3>

A través de la función getAll, HabilidadController envía una petición a la función getAllHabilidades de HabilidadService.

```js
@RestController
@RequestMapping("/api/habilidad")
public class HabilidadController {

    private final HabilidadService service;

    public HabilidadController(HabilidadService service){
        this.service = service;
    }

    @GetMapping
    public List<Habilidades> getAll(){
        return service.getAllHabilidades();
    }
}
```

<h3>
  HabilidadService (Service):
</h3>

La función getAllHabilidades es activada desde HabilidadController, y esto hace que HabilidadService le envíe una petición a la función findAll de HabilidadRepository.

```js
@Service
public class HabilidadService {

    private HabilidadRepository repository;

    @Autowired
    public HabilidadService(HabilidadRepository repository){
        this.repository = repository;
    }

    public List<Habilidades> getAllHabilidades(){
        return repository.findAll();
    }
}
```

<h3>
  HabilidadRepository (Repository):
</h3>

La función findAll es activada desde HabilidadService. La misma realiza la consulta SQL adecuada y envía la misma al model Habilidades con tal de conseguir el id_habilidad y el nombre de todas las habilidades.

```js
@Repository
public class HabilidadRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HabilidadRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Habilidades> findAll() {
        String sql = "SELECT id_habilidad, nombre FROM habilidades";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Habilidades h = new Habilidades();
            h.setId_habilidad(rs.getLong("id_habilidad"));
            h.setNombre(rs.getString("nombre"));
            return h;
        });
    }
}
```

<h3>
  Habilidades (Model):
</h3>

Recibe la consulta SQL realizada por la función findAll de HabilidadesRepository. A raíz de ello, envía los id y los nombres de todas las habilidades existentes.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Habilidades {
    private Long id_habilidad;
    private String nombre;

    public Habilidades() {
    }

    public Long getId_habilidad() {
        return id_habilidad;
    }

    public void setId_habilidad(Long id_habilidad) {
        this.id_habilidad = id_habilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
```

<h2>
  "/api/habilidad/{id_habilidad}"
</h2>

En este caso se podrá ver el efecto de una habilidad determinada agregando el id. Además, también traerá la lista completa de pokémons que cuentan con ella. Funciona así:

<h3>
  HabilidadController (Controller):
</h3>

A través de la función getById, HabilidadController envía una petición a la función getHabilidadCompleta de HabilidadService. Además, agrega "/{id_habilidad}" al endpoint.

```js
@RestController
@RequestMapping("/api/habilidad")
public class HabilidadController {

    private final HabilidadService service;

    public HabilidadController(HabilidadService service){
        this.service = service;
    }

    @GetMapping("/{id_habilidad}")
    public ResponseEntity<Habilidades> getById(@PathVariable Long id_habilidad){
        try{
            return ResponseEntity.ok(service.getHabilidadCompleta(id_habilidad));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
```

<h3>
  HabilidadService (Service):
</h3>

La función getHabilidadCompleta es activada desde HabilidadController. A raíz de ello, HabilidadService envía peticiones a las funciones findHabilidadById y findPokemonsByHabilidad de HabilidadRepository.

```js
@Service
public class HabilidadService {

    private HabilidadRepository repository;

    @Autowired
    public HabilidadService(HabilidadRepository repository){
        this.repository = repository;
    }

    public Habilidades getHabilidadCompleta(Long id_habilidad){
        Habilidades habilidades = repository.findHabilidadById(id_habilidad)
                .orElseThrow(()-> new RuntimeException("Habilidad no encontrada"));

        habilidades.setPokemons(repository.findPokemonsByHabilidad(id_habilidad));
        return habilidades;
    }

}
```

<h3>
  HabilidadRepository (Repository):
</h3>

Las funciones findHabilidadById y findPokemonsByHabilidad son activadas desde HabilidadService. Las mismas realizan las consultas SQL pertinentes a los models Habilidades y Pokeapi.

```js
@Repository
public class HabilidadRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HabilidadRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Habilidades> findHabilidadById(Long id_habilidad){
        String sql = "SELECT id_habilidad, nombre, efecto, efecto_corto FROM habilidades WHERE id_habilidad = ?";
        return jdbcTemplate.query(sql, new Object[]{id_habilidad}, rs -> {
            if(rs.next()){
                Habilidades h = new Habilidades();
                h.setId_habilidad(rs.getLong("id_habilidad"));
                h.setNombre(rs.getString("nombre"));
                return Optional.of(h);
            }
            return Optional.empty();
                });
    }

    public List<Pokeapi> findPokemonsByHabilidad(Long id_habilidad){
        String sql = "SELECT p.id_pokemon, p.nombre "+
                "FROM pokemons p "+
                "INNER JOIN pokemon_habilidad ph ON p.id_pokemon = ph.id_pokemon "+
                "INNER JOIN habilidades h ON ph.id_habilidad = h.id_habilidad "+
                "WHERE id_habilidad = ?";
        return jdbcTemplate.query(sql, new Object[]{id_habilidad}, rs -> {
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
}
```

<h3>
  Habilidades (Model):
</h3>

Recibe la consulta SQL de parte de la función findHabilidadById de HabilidadRepository, que le pide id, nombre, efecto y efecto_corto. Envía estos datos en base a la habilidad requerida.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Habilidades {
    private Long id_habilidad;
    private String nombre;
    private String efecto;
    private String efecto_corto;
    private List<Pokeapi> pokemons;

    public Habilidades() {
    }

    public Long getId_habilidad() {
        return id_habilidad;
    }

    public void setId_habilidad(Long id_habilidad) {
        this.id_habilidad = id_habilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEfecto() {
        return efecto;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    public String getEfecto_corto() {
        return efecto_corto;
    }

    public void setEfecto_corto(String efecto_corto) {
        this.efecto_corto = efecto_corto;
    }

    public List<Pokeapi> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokeapi> pokemons) {
        this.pokemons = pokemons;
    }
}
```

<h3>
  Pokeapi (Model):
</h3>

Recibe la petición de parte de la función findPokemonsByHabilidad de HabilidadRepository, que le pide la lista completa de los pokémons que poseen esa habilidad. Acto seguido, envía los datos.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pokeapi {
    private Long id_pokemon;
    private String nombre;

    public Pokeapi() {
    }

    public Long getId_pokemon() {
        return id_pokemon;
    }

    public void setId_pokemon(Long id_pokemon) {
        this.id_pokemon = id_pokemon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
```

<h2>
  "/api/ligas"
</h2>

Este endpoint trae la lista completa de ligas disponibles, solo con su id y nombre. Funciona así:

<h3>
  LigaController (Controller):
</h3>

A través de la función getAll, envía una petición a la función getAllLigas de LigaService.

```js
@RestController
@RequestMapping("/api/ligas")
public class LigasController {

    private final LigaService service;

    public LigasController(LigaService service) {
        this.service = service;
    }
    @GetMapping
    public List<Ligas> getAll(){
        return service.getAllLigas();
    }
}
```

<h3>
  LigaService (Service):
</h3>

La función getAllLigas es activada desde LigasController. Debido a esto, envía una petición a la función findAll de LigaRepository.

```js
@Service
public class LigaService {

    private LigaRepository repository;

    @Autowired
    public LigaService(LigaRepository repository) {
        this.repository = repository;
    }

    public List<Ligas> getAllLigas(){
        return repository.findAll();
    }
}
```

<h3>
  LigaRepository (Repository):
</h3>

La función findAll es activada desde LigaService. Esta realiza la consulta SQL que pide id y nombre de cada liga al model Ligas.
  
```js
@Repository
public class LigaRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LigaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ligas> findAll() {
        String sql = "SELECT id_liga, nombre FROM ligas";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Ligas l = new Ligas();
            l.setId_liga(rs.getLong("id_liga"));
            l.setNombre(rs.getString("nombre"));
            return l;
        });
    }
}
```

<h3>
  Ligas (Model):
</h3>

Recibe la consulta SQL desde LigaRepository. Acto seguido, envía los datos id_liga y nombre pedidos desde allí.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ligas {
    private Long id_liga;
    private String nombre;
    private List<Pokeapi> pokemons;

    public Long getId_liga() {
        return id_liga;
    }

    public void setId_liga(Long id_liga) {
        this.id_liga = id_liga;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
```

<h2>
  "/api/ligas/{id_liga}"
</h2>
Este endpoint está hecho con el objetivo de traer los pokémons por generación. Es decir, se coloca el id de la liga deseada y no solo traerá el id y el nombre de la liga, sino también la lista de pokémons correspondientes a ella. Funciona así:

<h3>
  LigasController (Controller):
</h3>

A través de la función getById, envía una petición a la función getLigaCompleta de LigaService. Además, agrega "/{id_liga}" al endpoint.

```js
@RestController
@RequestMapping("/api/ligas")
public class LigasController {

    private final LigaService service;

    public LigasController(LigaService service) {
        this.service = service;
    }

    @GetMapping("/{id_liga}")
    public ResponseEntity<Ligas> getById(@PathVariable Long id_liga){
        try{
            return ResponseEntity.ok(service.getLigaCompleta(id_liga));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
```

<h3>
  LigaService (Service):
</h3>

La función getLigaCompleta es activada desde LigasController. A raíz de ello, LigaService envía peticiones a las funciones findLigaById y findPokemonsByLiga de LigaRepository.

```js
@Service
public class LigaService {

    private LigaRepository repository;

    @Autowired
    public LigaService(LigaRepository repository) {
        this.repository = repository;
    }

    public Ligas getLigaCompleta(Long id_liga) {
        Ligas liga = repository.findLigaById(id_liga)
                .orElseThrow(()-> new RuntimeException("Liga no encontrada"));

        liga.setPokemons(repository.findPokemonsByLiga(id_liga));
        return liga;
    }
}
```

<h3>
  LigaRepository (Repository):
</h3>

Las funciones findLigaById y findPokemonsByLiga son activadas desde LigaService. Estas realizan las consultas SQL a los models Ligas y Pokeapi.

```js
@Repository
public class LigaRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LigaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Ligas> findLigaById(Long id_liga){
        String sql = "SELECT id_liga, nombre FROM ligas WHERE id_liga = ?";
        return jdbcTemplate.query(sql, new Object[]{id_liga}, rs -> {
            if(rs.next()){
                Ligas l = new Ligas();
                l.setId_liga(rs.getLong("id_liga"));
                l.setNombre(rs.getString("nombre"));
                return Optional.of(l);
            }
            return Optional.empty();
                });
    }

    public List<Pokeapi> findPokemonsByLiga(Long id_liga){
        String sql = "SELECT id_pokemon, nombre FROM pokemons WHERE id_liga = ?";
        return jdbcTemplate.query(sql, new Object[]{id_liga}, rs -> {
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
}
```

<h3>
  Ligas (Model):
</h3>

Recibe la consulta SQL desde LigaRepository, que le pide id_liga, nombre y la lista de los pokémons correspondientes a la liga. Envía los datos en conjunto con el model de Pokeapi.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ligas {
    private Long id_liga;
    private String nombre;
    private List<Pokeapi> pokemons;

    public Long getId_liga() {
        return id_liga;
    }

    public void setId_liga(Long id_liga) {
        this.id_liga = id_liga;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pokeapi> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokeapi> pokemons) {
        this.pokemons = pokemons;
    }
}
```

<h3>
  Pokeapi (Model):
</h3>

Como el model de Ligas carece de los id_pokemon y los nombres de los pokémons, Pokeapi también recibe una consulta SQL para enviar dichos datos. A raíz de ello, Pokeapi envía los datos para completar la tabla de pokémons correspondientes a la liga consultada.

```js
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pokeapi {
    private Long id_pokemon;
    private String nombre;

    public Pokeapi() {
    }

    public Long getId_pokemon() {
        return id_pokemon;
    }

    public void setId_pokemon(Long id_pokemon) {
        this.id_pokemon = id_pokemon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
```
