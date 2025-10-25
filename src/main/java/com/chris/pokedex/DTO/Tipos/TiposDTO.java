package com.chris.pokedex.DTO.Tipos;

import java.util.List;

public class TiposDTO {
    private Long id_tipo;
    private String nombre;
    private List<TiposResumenDTO> dobleDañoDe;
    private List<TiposResumenDTO> dobleDañoA;
    private List<TiposResumenDTO> mitadDañoDe;
    private List<TiposResumenDTO> mitadDañoA;
    private List<TiposResumenDTO> sinDañoDe;
    private List<TiposResumenDTO> sinDañoA;
    private List<TiposResumenDTO> pokemons;

    public TiposDTO(Long id_tipo,
                    String nombre,
                    List<TiposResumenDTO> dobleDañoDe,
                    List<TiposResumenDTO> dobleDañoA,
                    List<TiposResumenDTO> mitadDañoDe,
                    List<TiposResumenDTO> mitadDañoA,
                    List<TiposResumenDTO> sinDañoDe,
                    List<TiposResumenDTO> sinDañoA,
                    List<TiposResumenDTO> pokemons) {
        this.id_tipo = id_tipo;
        this.nombre = nombre;
        this.dobleDañoDe = dobleDañoDe;
        this.dobleDañoA = dobleDañoA;
        this.mitadDañoDe = mitadDañoDe;
        this.mitadDañoA = mitadDañoA;
        this.sinDañoDe = sinDañoDe;
        this.sinDañoA = sinDañoA;
        this.pokemons = pokemons;
    }

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

    public List<TiposResumenDTO> getDobleDañoDe() {
        return dobleDañoDe;
    }

    public void setDobleDañoDe(List<TiposResumenDTO> dobleDañoDe) {
        this.dobleDañoDe = dobleDañoDe;
    }

    public List<TiposResumenDTO> getDobleDañoA() {
        return dobleDañoA;
    }

    public void setDobleDañoA(List<TiposResumenDTO> dobleDañoA) {
        this.dobleDañoA = dobleDañoA;
    }

    public List<TiposResumenDTO> getMitadDañoDe() {
        return mitadDañoDe;
    }

    public void setMitadDañoDe(List<TiposResumenDTO> mitadDañoDe) {
        this.mitadDañoDe = mitadDañoDe;
    }

    public List<TiposResumenDTO> getMitadDañoA() {
        return mitadDañoA;
    }

    public void setMitadDañoA(List<TiposResumenDTO> mitadDañoA) {
        this.mitadDañoA = mitadDañoA;
    }

    public List<TiposResumenDTO> getSinDañoDe() {
        return sinDañoDe;
    }

    public void setSinDañoDe(List<TiposResumenDTO> sinDañoDe) {
        this.sinDañoDe = sinDañoDe;
    }

    public List<TiposResumenDTO> getSinDañoA() {
        return sinDañoA;
    }

    public void setSinDañoA(List<TiposResumenDTO> sinDañoA) {
        this.sinDañoA = sinDañoA;
    }

    public List<TiposResumenDTO> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<TiposResumenDTO> pokemons) {
        this.pokemons = pokemons;
    }
}
