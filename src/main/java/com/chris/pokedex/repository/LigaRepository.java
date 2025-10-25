package com.chris.pokedex.repository;

import com.chris.pokedex.repository.RowMappers.LigasRowMapper;
import com.chris.pokedex.repository.RowMappers.PokeapiRowMapper;
import com.chris.pokedex.model.Ligas;
import com.chris.pokedex.model.Pokeapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    public Optional<Ligas> findLigaById(Long id_liga){
        String sql = "SELECT id_liga, nombre FROM ligas WHERE id_liga = ?";
        return jdbcTemplate.query(sql, new LigasRowMapper(), id_liga)
                .stream().findFirst();
    }

    public List<Pokeapi> findPokemonsByLiga(Long id_liga){
        String sql = "SELECT id_pokemon, nombre FROM pokemons WHERE id_liga = ?";
        return jdbcTemplate.query(sql, new PokeapiRowMapper(), id_liga);
    }
}
