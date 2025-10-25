package com.chris.pokedex.repository;

import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.model.Tipos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TipoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TipoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tipos> findAll() {
        String sql = "SELECT id_tipo, nombre FROM tipos";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Tipos t = new Tipos();
            t.setId_tipo(rs.getLong("id_tipo"));
            t.setNombre(rs.getString("nombre"));
            return t;
        });
    }

    public Tipos findById(Long id_tipo) {
        String sql = "SELECT t.id_tipo, t.nombre, " +
                "t1.id_tipo AS dd_id, t1.nombre AS dd_nombre, " +
                "t2.id_tipo AS da_id, t2.nombre AS da_nombre, " +
                "t3.id_tipo AS md_id, t3.nombre AS md_nombre, " +
                "t4.id_tipo AS ma_id, t4.nombre AS ma_nombre, " +
                "t5.id_tipo AS sd_id, t5.nombre AS sd_nombre, " +
                "t6.id_tipo AS sa_id, t6.nombre AS sa_nombre, " +
                "p.id_pokemon AS p_id, p.nombre AS p_nombre " +
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

        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
            Tipos tipoPrincipal = null;
            Map<Long, Tipos> dobleDanioDeMap = new HashMap<>();
            Map<Long, Tipos> dobleDanioAMap = new HashMap<>();
            Map<Long, Tipos> mitadDanioDeMap = new HashMap<>();
            Map<Long, Tipos> mitadDanioAMap = new HashMap<>();
            Map<Long, Tipos> sinDanioDeMap = new HashMap<>();
            Map<Long, Tipos> sinDanioAMap = new HashMap<>();
            Map<Long, Pokeapi> pokemonsMap = new HashMap<>();

            while(rs.next()) {
                if (tipoPrincipal == null) {
                    tipoPrincipal = new Tipos();
                    tipoPrincipal.setId_tipo(rs.getLong("id_tipo"));
                    tipoPrincipal.setNombre(rs.getString("nombre"));
                }

                // Doble daño de
                Long ddId = rs.getLong("dd_id");
                if (!rs.wasNull() && !dobleDanioDeMap.containsKey(ddId)) {
                    Tipos dd = new Tipos();
                    dd.setId_tipo(ddId);
                    dd.setNombre(rs.getString("dd_nombre"));
                    dobleDanioDeMap.put(ddId, dd);
                }

                // Doble daño a
                Long daId = rs.getLong("da_id");
                if (!rs.wasNull() && !dobleDanioAMap.containsKey(daId)) {
                    Tipos da = new Tipos();
                    da.setId_tipo(daId);
                    da.setNombre(rs.getString("da_nombre"));
                    dobleDanioAMap.put(daId, da);
                }

                // Mitad daño de
                Long mdId = rs.getLong("md_id");
                if (!rs.wasNull() && !mitadDanioDeMap.containsKey(mdId)) {
                    Tipos md = new Tipos();
                    md.setId_tipo(mdId);
                    md.setNombre(rs.getString("md_nombre"));
                    mitadDanioDeMap.put(mdId, md);
                }

                // Mitad daño a
                Long maId = rs.getLong("ma_id");
                if (!rs.wasNull() && !mitadDanioAMap.containsKey(maId)) {
                    Tipos ma = new Tipos();
                    ma.setId_tipo(maId);
                    ma.setNombre(rs.getString("ma_nombre"));
                    mitadDanioAMap.put(maId, ma);
                }

                // Sin daño de
                Long sdId = rs.getLong("sd_id");
                if (!rs.wasNull() && !sinDanioDeMap.containsKey(sdId)) {
                    Tipos sd = new Tipos();
                    sd.setId_tipo(sdId);
                    sd.setNombre(rs.getString("sd_nombre"));
                    sinDanioDeMap.put(sdId, sd);
                }

                // Sin daño a
                Long saId = rs.getLong("sa_id");
                if (!rs.wasNull() && !sinDanioAMap.containsKey(saId)) {
                    Tipos sa = new Tipos();
                    sa.setId_tipo(saId);
                    sa.setNombre(rs.getString("sa_nombre"));
                    sinDanioAMap.put(saId, sa);
                }

                // Pokémons
                Long pId = rs.getLong("p_id");
                if (!rs.wasNull() && !pokemonsMap.containsKey(pId)) {
                    Pokeapi p = new Pokeapi();
                    p.setId_pokemon(pId);
                    p.setNombre(rs.getString("p_nombre"));
                    pokemonsMap.put(pId, p);
                }


                if (tipoPrincipal != null) {
                    tipoPrincipal.setDobleDanioDe(new ArrayList<>(dobleDanioDeMap.values()));
                    tipoPrincipal.setDobleDanioA(new ArrayList<>(dobleDanioAMap.values()));
                    tipoPrincipal.setMitadDanioDe(new ArrayList<>(mitadDanioDeMap.values()));
                    tipoPrincipal.setMitadDanioA(new ArrayList<>(mitadDanioAMap.values()));
                    tipoPrincipal.setSinDanioDe(new ArrayList<>(sinDanioDeMap.values()));
                    tipoPrincipal.setSinDanioA(new ArrayList<>(sinDanioAMap.values()));
                    tipoPrincipal.setPokemons(new ArrayList<>(pokemonsMap.values()));
                }
            }
            return tipoPrincipal;
        });
    }
}