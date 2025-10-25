package com.chris.pokedex.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Habilidades {
    private Long id_habilidad;
    private String nombre;
    private String efecto;
    private String efecto_corto;
    private List<Pokeapi> pokemons;

    public Habilidades() {
    }

    public Long getId_habilidad() {
        return id_habilidad;
    }

    public void setId_habilidad(Long id_habilidad) {
        this.id_habilidad = id_habilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEfecto() {
        return efecto;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    public String getEfecto_corto() {
        return efecto_corto;
    }

    public void setEfecto_corto(String efecto_corto) {
        this.efecto_corto = efecto_corto;
    }

    public List<Pokeapi> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokeapi> pokemons) {
        this.pokemons = pokemons;
    }
}
