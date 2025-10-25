package com.chris.pokedex.repository;

import com.chris.pokedex.model.Ligas;
import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.model.Tipos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
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

    public Ligas findById(Long id_liga) {
        String sql = """
            SELECT l.id_liga, l.nombre AS liga_nombre, p.id_pokemon, p.nombre AS pokemon_nombre
            FROM ligas l
            INNER JOIN pokemons p ON l.id_liga = p.id_liga
            WHERE l.id_liga = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{id_liga}, rs -> {
            Ligas liga = null;
            List<Pokeapi> pokemons = new ArrayList<>();
            Set<Long> seenPokemonIds = new HashSet<>();

            while (rs.next()) {
                if (liga == null) {
                    liga = new Ligas();
                    liga.setId_liga(rs.getLong("id_liga"));
                    liga.setNombre(rs.getString("liga_nombre"));
                }

                Long idPokemon = rs.getLong("id_pokemon");
                if (!rs.wasNull() && !seenPokemonIds.contains(idPokemon)) {
                    Pokeapi p = new Pokeapi();
                    p.setId_pokemon(idPokemon);
                    p.setNombre(rs.getString("pokemon_nombre"));
                    pokemons.add(p);
                    seenPokemonIds.add(idPokemon);
                }
            }

            if (liga != null) {
                liga.setPokemons(pokemons);
            }

            return liga;
        });
    }
}
