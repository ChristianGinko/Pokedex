package com.chris.pokedex.repository.RowMappers;

import com.chris.pokedex.model.Habilidades;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HabilidadesRowMapper implements RowMapper<Habilidades> {
    @Override
    public Habilidades mapRow(ResultSet rs, int rowNum) throws SQLException {
        Habilidades h = new Habilidades();
        h.setId_habilidad(rs.getLong("id_habilidad"));
        h.setNombre(rs.getString("nombre"));
        h.setEfecto(rs.getString("efecto"));
        h.setEfecto_corto(rs.getString("efecto_corto"));
        return h;
    }
}
