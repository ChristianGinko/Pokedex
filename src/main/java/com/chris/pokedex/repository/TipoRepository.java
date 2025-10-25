package com.chris.pokedex.repository;

import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.model.Tipos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TipoRepository {
    private static final String URL = "jdbc:mysql://topoha.duckdns.org:3399/pokeapi";
    private static final String USER = "chris";
    private static final String PASSWORD = "chris1210";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Tipos> findAll() {
        List<Tipos> tipos = new ArrayList<>();
        String sql = "SELECT id_tipo, nombre FROM tipos";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Tipos t = new Tipos();
                t.setId_tipo(rs.getLong("id_tipo"));
                t.setNombre(rs.getString("nombre"));
                tipos.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipos;
    }

    public Tipos findById(Long id_tipo) {
        String sql = "SELECT t.id_tipo, t.nombre, t1.id_tipo AS dd_id, t1.nombre AS dd_nombre, t2.id_tipo AS da_id, t2.nombre AS da_nombre, t3.id_tipo AS md_id, t3.nombre AS md_nombre, t4.id_tipo AS ma_id, t4.nombre AS ma_nombre, t5.id_tipo AS sd_id, t5.nombre AS sd_nombre, t6.id_tipo AS sa_id, t6.nombre AS sa_nombre, p.id_pokemon AS p_id, p.nombre AS p_nombre " +
                "FROM tipos t " +
                "LEFT JOIN `doble_daño_de` dd ON t.id_tipo = dd.id_tipo1 " +
                "LEFT JOIN tipos t1 ON dd.id_tipo2 = t1.id_tipo " +
                "LEFT JOIN `doble_daño_a` da ON t.id_tipo = da.id_tipo1 " +
                "LEFT JOIN tipos t2 ON da.id_tipo2 = t2.id_tipo " +
                "LEFT JOIN `mitad_daño_de` md ON t.id_tipo = md.id_tipo1 " +
                "LEFT JOIN tipos t3 ON md.id_tipo2 = t3.id_tipo " +
                "LEFT JOIN `mitad_daño_a` ma ON t.id_tipo = ma.id_tipo1 " +
                "LEFT JOIN tipos t4 ON ma.id_tipo2 = t4.id_tipo " +
                "LEFT JOIN `sin_daño_de` sd ON t.id_tipo = sd.id_tipo1 " +
                "LEFT JOIN tipos t5 ON sd.id_tipo2 = t5.id_tipo " +
                "LEFT JOIN `sin_daño_a` sa ON t.id_tipo = sa.id_tipo1 " +
                "LEFT JOIN tipos t6 ON sa.id_tipo2 = t6.id_tipo " +
                "INNER JOIN pokemon_tipo pt ON t.id_tipo = pt.id_tipo " +
                "INNER JOIN pokemons p ON pt.id_pokemon = p.id_pokemon " +
                "WHERE t.id_tipo = ?";

        Tipos tipoPrincipal = null;
        List<Tipos> dobleDanioDe = new ArrayList<>();
        List<Tipos> dobleDanioA = new ArrayList<>();
        List<Tipos> mitadDanioDe = new ArrayList<>();
        List<Tipos> mitadDanioA = new ArrayList<>();
        List<Tipos> sinDanioDe = new ArrayList<>();
        List<Tipos> sinDanioA = new ArrayList<>();
        List<Pokeapi> pokemons = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id_tipo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (tipoPrincipal == null) {
                    tipoPrincipal = new Tipos();
                    tipoPrincipal.setId_tipo(rs.getLong("id_tipo"));
                    tipoPrincipal.setNombre(rs.getString("nombre"));
                }

                // Doble daño de
                Long ddId = rs.getLong("dd_id");
                if (!rs.wasNull()) {
                    Tipos dd = new Tipos();
                    dd.setId_tipo(ddId);
                    dd.setNombre(rs.getString("dd_nombre"));
                    if (!dobleDanioDe.contains(dd)) dobleDanioDe.add(dd);
                }

                // Doble daño a
                Long daId = rs.getLong("da_id");
                if (!rs.wasNull()) {
                    Tipos da = new Tipos();
                    da.setId_tipo(daId);
                    da.setNombre(rs.getString("da_nombre"));
                    if (!dobleDanioA.contains(da)) dobleDanioA.add(da);
                }

                // Mitad daño de
                Long mdId = rs.getLong("md_id");
                if (!rs.wasNull()) {
                    Tipos md = new Tipos();
                    md.setId_tipo(mdId);
                    md.setNombre(rs.getString("md_nombre"));
                    if (!mitadDanioDe.contains(md)) mitadDanioDe.add(md);
                }

                // Mitad daño a
                Long maId = rs.getLong("ma_id");
                if (!rs.wasNull()) {
                    Tipos ma = new Tipos();
                    ma.setId_tipo(maId);
                    ma.setNombre(rs.getString("ma_nombre"));
                    if (!mitadDanioA.contains(ma)) mitadDanioA.add(ma);
                }

                // Sin daño de
                Long sdId = rs.getLong("sd_id");
                if (!rs.wasNull()) {
                    Tipos sd = new Tipos();
                    sd.setId_tipo(sdId);
                    sd.setNombre(rs.getString("sd_nombre"));
                    if (!sinDanioDe.contains(sd)) sinDanioDe.add(sd);
                }

                // Sin daño a
                Long saId = rs.getLong("sa_id");
                if (!rs.wasNull()) {
                    Tipos sa = new Tipos();
                    sa.setId_tipo(saId);
                    sa.setNombre(rs.getString("sa_nombre"));
                    if (!sinDanioA.contains(sa)) sinDanioA.add(sa);
                }

                // Pokémons
                Long id_pokemon = rs.getLong("p_id");
                if (!rs.wasNull()) {
                    Pokeapi p = new Pokeapi();
                    p.setId_pokemon(id_pokemon);
                    p.setNombre(rs.getString("p_nombre"));
                    if (!pokemons.contains(p)) pokemons.add(p);
                }
            }

            if (tipoPrincipal != null) {
                tipoPrincipal.setDobleDanioDe(dobleDanioDe);
                tipoPrincipal.setDobleDanioA(dobleDanioA);
                tipoPrincipal.setMitadDanioDe(mitadDanioDe);
                tipoPrincipal.setMitadDanioA(mitadDanioA);
                tipoPrincipal.setSinDanioDe(sinDanioDe);
                tipoPrincipal.setSinDanioA(sinDanioA);
                tipoPrincipal.setPokemons(pokemons);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoPrincipal;
    }
}
