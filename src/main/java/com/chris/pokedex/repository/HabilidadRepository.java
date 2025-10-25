package com.chris.pokedex.repository;

import com.chris.pokedex.model.Habilidades;
import com.chris.pokedex.model.Pokeapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Habilidades findById(Long id_habilidad) {
        String sql = """
            SELECT h.id_habilidad, h.nombre, h.efecto, h.efecto_corto,
                   p.id_pokemon AS p_id, p.nombre AS p_nombre
            FROM habilidades h
            LEFT JOIN pokemon_habilidad ph ON h.id_habilidad = ph.id_habilidad
            LEFT JOIN pokemons p ON ph.id_pokemon = p.id_pokemon
            WHERE h.id_habilidad = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{id_habilidad}, rs -> {
            Habilidades habilidad = null;
            Map<Long, Pokeapi> pokemonsMap = new HashMap<>();

            while (rs.next()) {
                if (habilidad == null) {
                    habilidad = new Habilidades();
                    habilidad.setId_habilidad(rs.getLong("id_habilidad"));
                    habilidad.setNombre(rs.getString("nombre"));
                    habilidad.setEfecto(rs.getString("efecto"));
                    habilidad.setEfecto_corto(rs.getString("efecto_corto"));
                }

                Long idPokemon = rs.getLong("p_id");
                if (!rs.wasNull() && !pokemonsMap.containsKey(idPokemon)) {
                    Pokeapi p = new Pokeapi();
                    p.setId_pokemon(idPokemon);
                    p.setNombre(rs.getString("p_nombre"));
                    pokemonsMap.put(idPokemon, p);
                }
            }

            if (habilidad != null) {
                habilidad.setPokemons(new ArrayList<>(pokemonsMap.values()));
            }

            return habilidad;
        });
    }
}