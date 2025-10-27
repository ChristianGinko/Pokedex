/*
package com.chris.pokedex.repository.RowMappers;

import com.chris.pokedex.model.Ligas;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LigasRowMapper implements RowMapper<Ligas> {
    @Override
    public Ligas mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ligas l = new Ligas();
        l.setId_liga(rs.getLong("id_liga"));
        l.setNombre(rs.getString("nombre"));
        return l;
    }
}
*/