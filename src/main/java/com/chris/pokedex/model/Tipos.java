package com.chris.pokedex.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tipos {
    private Long id_tipo;
    private String nombre;
    private List<Tipos> dobleDanioDe;
    private List<Tipos> dobleDanioA;
    private List<Tipos> mitadDanioDe;
    private List<Tipos> mitadDanioA;
    private List<Tipos> sinDanioDe;
    private List<Tipos> sinDanioA;
    private List<Pokeapi> pokemons;

    public Long getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(Long id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Tipos> getDobleDanioDe() {
        return dobleDanioDe;
    }

    public void setDobleDanioDe(List<Tipos> dobleDanioDe) {
        this.dobleDanioDe = dobleDanioDe;
    }

    public List<Tipos> getDobleDanioA() {
        return dobleDanioA;
    }

    public void setDobleDanioA(List<Tipos> dobleDanioA) {
        this.dobleDanioA = dobleDanioA;
    }

    public List<Tipos> getMitadDanioDe() {
        return mitadDanioDe;
    }

    public void setMitadDanioDe(List<Tipos> mitadDanioDe) {
        this.mitadDanioDe = mitadDanioDe;
    }

    public List<Tipos> getMitadDanioA() {
        return mitadDanioA;
    }

    public void setMitadDanioA(List<Tipos> mitadDanioA) {
        this.mitadDanioA = mitadDanioA;
    }

    public List<Tipos> getSinDanioDe() {
        return sinDanioDe;
    }

    public void setSinDanioDe(List<Tipos> sinDanioDe) {
        this.sinDanioDe = sinDanioDe;
    }

    public List<Tipos> getSinDanioA() {
        return sinDanioA;
    }

    public void setSinDanioA(List<Tipos> sinDanioA) {
        this.sinDanioA = sinDanioA;
    }

    public List<Pokeapi> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokeapi> pokemons) {
        this.pokemons = pokemons;
    }
}
