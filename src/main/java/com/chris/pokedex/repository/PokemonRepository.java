package com.chris.pokedex.repository;

import com.chris.pokedex.model.Habilidades;
import com.chris.pokedex.model.Ligas;
import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.model.Tipos;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
         String sql = "SELECT p.id_pokemon, p.nombre, t.id_tipo, t.nombre, h.id_habilidad, h.nombre, l.id_liga, l.nombre "+
         "FROM pokemons p "+
         "INNER JOIN pokemon_tipo pt ON p.id_pokemon = pt.id_pokemon "+
         "INNER JOIN tipos t ON pt.id_tipo = t.id_tipo "+
         "INNER JOIN pokemon_habilidad ph ON p.id_pokemon = ph.id_pokemon "+
         "INNER JOIN habilidades h ON ph.id_habilidad = h.id_habilidad "+
         "INNER JOIN ligas l ON p.id_liga = l.id_liga "+
         "WHERE p.id_pokemon = ?";
         Pokeapi pokemon = null;
         List<Tipos> tipos = new ArrayList<>();
         List<Habilidades> habilidades = new ArrayList<>();
         Ligas liga = null;

         try (Connection conn = getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql)) {

             stmt.setLong(1, id_pokemon);
             ResultSet rs = stmt.executeQuery();

             while (rs.next()) {

                 if (pokemon == null) {
                     pokemon = new Pokeapi();
                     pokemon.setId_pokemon(rs.getLong("id_pokemon"));
                     pokemon.setNombre(rs.getString("nombre"));

                     if (rs.getLong("id_liga") != 0) {
                         liga = new Ligas();
                         liga.setId_liga(rs.getLong("id_liga"));
                         liga.setNombre(rs.getString("nombre"));
                         pokemon.setLiga(liga);
                     }
                 }

                 Long id_tipo = rs.getLong("id_tipo");
                 if (id_tipo != 0) {
                     Tipos tipo = new Tipos();
                     tipo.setId_tipo(id_tipo);
                     tipo.setNombre(rs.getString("nombre"));
                     if (!tipos.contains(tipo)) tipos.add(tipo);
                 }

                 Long id_habilidad = rs.getLong("id_habilidad");
                 if (id_habilidad != 0) {
                     Habilidades habilidad = new Habilidades();
                     habilidad.setId_habilidad(id_habilidad);
                     habilidad.setNombre(rs.getString("nombre"));
                     if (!habilidades.contains(habilidad)) habilidades.add(habilidad);
                 }
             }

             if (pokemon != null) {
                 pokemon.setTipos(tipos);
                 pokemon.setHabilidades(habilidades);
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