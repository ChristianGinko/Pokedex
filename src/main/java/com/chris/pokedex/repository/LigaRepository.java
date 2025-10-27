package com.chris.pokedex.repository;

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
