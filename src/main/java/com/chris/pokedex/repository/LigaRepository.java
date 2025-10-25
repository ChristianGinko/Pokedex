package com.chris.pokedex.repository;

import com.chris.pokedex.model.Ligas;
import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.model.Tipos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LigaRepository {
    private static final String URL = "jdbc:mysql://topoha.duckdns.org:3399/pokeapi";
    private static final String USER = "chris";
    private static final String PASSWORD = "chris1210";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Ligas> findAll() {
        List<Ligas> ligas = new ArrayList<>();
        String sql = "SELECT id_liga, nombre FROM ligas";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Ligas l = new Ligas();
                l.setId_liga(rs.getLong("id_liga"));
                l.setNombre(rs.getString("nombre"));
                ligas.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ligas;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Ligas findById(Long id_liga) {
        String sql = "SELECT l.id_liga, l.nombre, p.id_pokemon, p.nombre " +
                "FROM ligas l " +
                "INNER JOIN pokemons p ON l.id_liga = p.id_liga " +
                "WHERE l.id_liga = ?";

        return jdbcTemplate.query(sql, new Object[]{id_liga}, rs ->{
            Ligas ligas = null;
            List<Pokeapi> pokemons = new ArrayList<>();

            while (rs.next()) {
                if (ligas == null) {
                    ligas = new Ligas();
                }
                Long id_pokemon = rs.getLong("id_pokemon");
                if (id_pokemon != 0) {
                    pokemons.add(new Pokeapi());
                }
            }
            return ligas;
        }
        );
    }
}
