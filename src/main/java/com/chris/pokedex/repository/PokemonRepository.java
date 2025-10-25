package com.chris.pokedex.repository;

import com.chris.pokedex.model.Habilidades;
import com.chris.pokedex.model.Ligas;
import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.model.Tipos;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PokemonRepository {
     private static final String URL = "jdbc:mysql://topoha.duckdns.org:3399/pokeapi";
     private static final String USER = "chris";
     private static final String PASSWORD = "chris1210";

     private Connection getConnection () throws SQLException {
         return DriverManager.getConnection(URL, USER, PASSWORD);
     }

     public List<Pokeapi> findAll() {
         List<Pokeapi> pokemons = new ArrayList<>();
         String sql = "SELECT id_pokemon, nombre FROM pokemons";

         try (Connection conn = getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql);
              ResultSet rs = stmt.executeQuery()
         ) {
             while (rs.next()) {
                 Pokeapi p = new Pokeapi();
                 p.setId_pokemon(rs.getLong("id_pokemon"));
                 p.setNombre(rs.getString("nombre"));
                 pokemons.add(p);
             }

         } catch (SQLException e) {
             e.printStackTrace();
         }
         return pokemons;
     }

    public Pokeapi findById(Long id_pokemon) {
        String sql = "SELECT p.id_pokemon, p.nombre, " +
                "t.id_tipo, t.nombre AS t_nombre, " +
                "h.id_habilidad, h.nombre AS h_nombre, " +
                "l.id_liga, l.nombre AS l_nombre " +
                "FROM pokemons p " +
                "LEFT JOIN pokemon_tipo pt ON p.id_pokemon = pt.id_pokemon " +
                "LEFT JOIN tipos t ON pt.id_tipo = t.id_tipo " +
                "LEFT JOIN pokemon_habilidad ph ON p.id_pokemon = ph.id_pokemon " +
                "LEFT JOIN habilidades h ON ph.id_habilidad = h.id_habilidad " +
                "LEFT JOIN ligas l ON p.id_liga = l.id_liga " +
                "WHERE p.id_pokemon = ?";

        Pokeapi pokemon = null;
        Ligas liga = null;

        // Evitar duplicados
        Map<Long, Tipos> tiposMap = new HashMap<>();
        Map<Long, Habilidades> habilidadesMap = new HashMap<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id_pokemon);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                if (pokemon == null) {
                    pokemon = new Pokeapi();
                    pokemon.setId_pokemon(rs.getLong("id_pokemon"));
                    pokemon.setNombre(rs.getString("nombre"));

                    Long idLiga = rs.getLong("id_liga");
                    if (idLiga != 0) {
                        liga = new Ligas();
                        liga.setId_liga(idLiga);
                        liga.setNombre(rs.getString("l_nombre"));
                        pokemon.setLiga(liga);
                    }
                }

                Long idTipo = rs.getLong("id_tipo");
                if (idTipo != 0 && !tiposMap.containsKey(idTipo)) {
                    Tipos tipo = new Tipos();
                    tipo.setId_tipo(idTipo);
                    tipo.setNombre(rs.getString("t_nombre"));
                    tiposMap.put(idTipo, tipo);
                }

                Long idHabilidad = rs.getLong("id_habilidad");
                if (idHabilidad != 0 && !habilidadesMap.containsKey(idHabilidad)) {
                    Habilidades habilidad = new Habilidades();
                    habilidad.setId_habilidad(idHabilidad);
                    habilidad.setNombre(rs.getString("h_nombre"));
                    habilidadesMap.put(idHabilidad, habilidad);
                }
            }

            if (pokemon != null) {
                pokemon.setTipos(new ArrayList<>(tiposMap.values()));
                pokemon.setHabilidades(new ArrayList<>(habilidadesMap.values()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pokemon;
    }

     public void save(Pokeapi pokemon) {
         String sql = "INSERT INTO pokemons (nombre) VALUES (?)";

         try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

             stmt.setString(1, pokemon.getNombre());
             stmt.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

     public void update(Pokeapi pokemon) {
         String sql = "UPDATE pokemons SET nombre =  ? WHERE id_pokemon = ?";

         try(Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setString(1, pokemon.getNombre());
             stmt.setLong(2, pokemon.getId_pokemon());
             stmt.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

     public void delete(Pokeapi pokemon) {
         String sql = "DELETE FROM pokemons WHERE id_pokemon = ?";

         try(Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setLong(1, pokemon.getId_pokemon());
             stmt.executeUpdate();
         } catch(SQLException e) {
             e.printStackTrace();
         }
     }
}