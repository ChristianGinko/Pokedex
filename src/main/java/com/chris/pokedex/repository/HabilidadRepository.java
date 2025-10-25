package com.chris.pokedex.repository;

import com.chris.pokedex.model.Habilidades;
import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.repository.RowMappers.HabilidadesRowMapper;
import com.chris.pokedex.repository.RowMappers.PokeapiRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    public Optional<Habilidades> findHabilidadById(Long id_habilidad){
        String sql = "SELECT id_habilidad, nombre, efecto, efecto_corto FROM habilidades WHERE id_habilidad = ?";
        return jdbcTemplate.query(sql, new HabilidadesRowMapper(), id_habilidad)
                .stream(). findFirst();
    }

    public List<Pokeapi> findPokemonsByHabilidad(Long id_habilidad){
        String sql = "SELECT p.id_pokemon, p.nombre "+
                "FROM pokemons p "+
                "INNER JOIN pokemon_habilidad ph ON p.id_pokemon = ph.id_pokemon "+
                "INNER JOIN habilidades h ON ph.id_habilidad = h.id_habilidad "+
                "WHERE id_habilidad = ?";
        return jdbcTemplate.query(sql, new PokeapiRowMapper(), id_habilidad);
    }
}