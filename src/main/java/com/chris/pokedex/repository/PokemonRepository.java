package com.chris.pokedex.repository;

import com.chris.pokedex.model.Habilidades;
import com.chris.pokedex.model.Ligas;
import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.model.Tipos;
import com.chris.pokedex.repository.RowMappers.HabilidadesRowMapper;
import com.chris.pokedex.repository.RowMappers.LigasRowMapper;
import com.chris.pokedex.repository.RowMappers.PokeapiRowMapper;
import com.chris.pokedex.repository.RowMappers.TiposRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

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
