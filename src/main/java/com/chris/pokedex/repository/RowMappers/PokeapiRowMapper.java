package com.chris.pokedex.repository.RowMappers;

import com.chris.pokedex.model.Pokeapi;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PokeapiRowMapper implements RowMapper<Pokeapi> {
    @Override
    public Pokeapi mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pokeapi p = new Pokeapi();
        p.setId_pokemon(rs.getLong("id_pokemon"));
        p.setNombre(rs.getString("nombre"));
        return p;
    }
}
