package com.chris.pokedex.repository;

import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.model.Tipos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

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

    public Optional<Tipos> findTipoById(Long id_tipo){
        String sql = "SELECT id_tipo, nombre FROM tipos WHERE id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs->{
            if(rs.next()){
                Tipos t = new Tipos();
                t.setId_tipo(rs.getLong("id_tipo"));
                t.setNombre(rs.getString("nombre"));
                return Optional.of(t);
            }
            return Optional.empty();
                });
    }

    public List<Tipos> findDobleDanioDeByTipo(Long id_tipo){
        String sql = "SELECT t1.id_tipo, t1.nombre FROM tipos t "+
                "LEFT JOIN `doble_daño_de` ddd ON t.id_tipo = ddd.id_tipo1 "+
                "LEFT JOIN tipos t1 ON ddd.id_tipo2 = t1.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
            List<Tipos> tipos = new ArrayList<>();
            while(rs.next()){
                Tipos t = new Tipos();
                t.setId_tipo(rs.getLong("id_tipo"));
                t.setNombre(rs.getString("nombre"));
                tipos.add(t);
            }
            return tipos;
        });
    }

    public List<Tipos> findDobleDanioAByTipo(Long id_tipo){
        String sql = "SELECT t1.id_tipo, t1.nombre FROM tipos t "+
                "LEFT JOIN `doble_daño_a` dda ON t.id_tipo = dda.id_tipo1 "+
                "LEFT JOIN tipos t1 ON dda.id_tipo2 = t1.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
            List<Tipos> tipos = new ArrayList<>();
            while(rs.next()){
                Tipos t = new Tipos();
                t.setId_tipo(rs.getLong("id_tipo"));
                t.setNombre(rs.getString("nombre"));
                tipos.add(t);
            }
            return tipos;
        });
    }

    public List<Tipos> findMitadDanioDeByTipo(Long id_tipo){
        String sql = "SELECT t1.id_tipo, t1.nombre FROM tipos t "+
                "LEFT JOIN `mitad_daño_a` mdd ON t.id_tipo = mdd.id_tipo1 "+
                "LEFT JOIN tipos t1 ON mdd.id_tipo2 = t1.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
            List<Tipos> tipos = new ArrayList<>();
            while(rs.next()){
                Tipos t = new Tipos();
                t.setId_tipo(rs.getLong("id_tipo"));
                t.setNombre(rs.getString("nombre"));
                tipos.add(t);
            }
            return tipos;
        });
    }

    public List<Tipos> findMitadDanioAByTipo(Long id_tipo){
        String sql = "SELECT t1.id_tipo, t1.nombre FROM tipos t "+
                "LEFT JOIN `mitad_daño_a` mda ON t.id_tipo = mda.id_tipo1 "+
                "LEFT JOIN tipos t1 ON mda.id_tipo2 = t1.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
            List<Tipos> tipos = new ArrayList<>();
            while(rs.next()){
                Tipos t = new Tipos();
                t.setId_tipo(rs.getLong("id_tipo"));
                t.setNombre(rs.getString("nombre"));
                tipos.add(t);
            }
            return tipos;
        });
    }

    public List<Tipos> findSinDanioDeByTipo(Long id_tipo){
        String sql = "SELECT t1.id_tipo, t1.nombre FROM tipos t "+
                "LEFT JOIN `sin_daño_de` sdd ON t.id_tipo = sdd.id_tipo1 "+
                "LEFT JOIN tipos t1 ON sdd.id_tipo2 = t1.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
            List<Tipos> tipos = new ArrayList<>();
            while(rs.next()){
                Tipos t = new Tipos();
                t.setId_tipo(rs.getLong("id_tipo"));
                t.setNombre(rs.getString("nombre"));
                tipos.add(t);
            }
            return tipos;
        });
    }

    public List<Tipos> findSinDanioAByTipo(Long id_tipo){
        String sql = "SELECT t1.id_tipo, t1.nombre FROM tipos t "+
                "LEFT JOIN `sin_daño_de` sda ON t.id_tipo = sda.id_tipo1 "+
                "LEFT JOIN tipos t1 ON sda.id_tipo2 = t1.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
            List<Tipos> tipos = new ArrayList<>();
            while(rs.next()){
                Tipos t = new Tipos();
                t.setId_tipo(rs.getLong("id_tipo"));
                t.setNombre(rs.getString("nombre"));
                tipos.add(t);
            }
            return tipos;
        });
    }

    public List<Pokeapi> findPokeByTipo(Long id_tipo){
        String sql = "SELECT p.id_pokemon, p.nombre FROM pokemons p "+
                "INNER JOIN pokemon_tipo pt ON p.id_pokemon = pt.id_pokemon "+
                "INNER JOIN tipos t ON pt.id_tipo = t.id_tipo "+
                "WHERE t.id_tipo = ?";
        return jdbcTemplate.query(sql, new Object[]{id_tipo}, rs -> {
            List<Pokeapi> pokemons = new ArrayList<>();
            while(rs.next()){
                Pokeapi p = new Pokeapi();
                p.setId_pokemon(rs.getLong("id_pokemon"));
                p.setNombre(rs.getString("nombre"));
                pokemons.add(p);
            }
            return pokemons;
        });
    }
}