package com.chris.pokedex.repository;

import com.chris.pokedex.model.Habilidades;
import com.chris.pokedex.model.Ligas;
import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.model.Tipos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        return jdbcTemplate.query(sql, new Object[]{id_pokemon}, rs -> {
            Pokeapi pokemon = null;
            Ligas liga = null;
            Map<Long, Tipos> tiposMap = new HashMap<>();
            Map<Long, Habilidades> habilidadesMap = new HashMap<>();

            while(rs.next()){
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
            if (pokemon != null) {
                pokemon.setTipos(new ArrayList<>(tiposMap.values()));
                pokemon.setHabilidades(new ArrayList<>(habilidadesMap.values()));
            }
        }

            return pokemon;
        });
    }
}
