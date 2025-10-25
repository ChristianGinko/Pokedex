package com.chris.pokedex.repository.RowMappers;

import com.chris.pokedex.model.Tipos;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TiposRowMapper implements RowMapper<Tipos> {
    @Override
    public Tipos mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tipos t = new Tipos();
        t.setId_tipo(rs.getLong("id_tipo"));
        t.setNombre(rs.getString("nombre"));
        return t;
    }
}
