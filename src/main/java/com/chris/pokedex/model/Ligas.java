package com.chris.pokedex.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ligas {
    private Long id_liga;
    private String nombre;
    private List<Pokeapi> pokemons;

    public Long getId_liga() {
        return id_liga;
    }

    public void setId_liga(Long id_liga) {
        this.id_liga = id_liga;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pokeapi> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokeapi> pokemons) {
        this.pokemons = pokemons;
    }
}
