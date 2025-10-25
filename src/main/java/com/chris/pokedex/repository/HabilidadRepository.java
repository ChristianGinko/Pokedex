package com.chris.pokedex.repository;

import com.chris.pokedex.model.Habilidades;
import com.chris.pokedex.model.Pokeapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HabilidadRepository {
    private static final String URL = "jdbc:mysql://topoha.duckdns.org:3399/pokeapi";
    private static final String USER = "chris";
    private static final String PASSWORD = "chris1210";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Habilidades> findAll() {
        List<Habilidades> habilidades = new ArrayList<>();
        String sql = "SELECT id_habilidad, nombre FROM habilidades";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Habilidades h = new Habilidades();
                h.setId_habilidad(rs.getLong("id_habilidad"));
                h.setNombre(rs.getString("nombre"));
                habilidades.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return habilidades;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Habilidades findById(Long id_habilidad) {
        String sql = "SELECT h.id_habilidad, h.nombre, h.efecto, h.efecto_corto, " +
                "p.id_pokemon AS p_id, p.nombre AS p_nombre " +
                "FROM habilidades h " +
                "LEFT JOIN pokemon_habilidad ph ON h.id_habilidad = ph.id_habilidad " +
                "LEFT JOIN pokemons p ON ph.id_pokemon = p.id_pokemon " +
                "WHERE h.id_habilidad = ?";

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
            } else {
                // Si no existe la habilidad, devolvemos null
                return null;
            }

            return habilidad;
        });
    }
}
