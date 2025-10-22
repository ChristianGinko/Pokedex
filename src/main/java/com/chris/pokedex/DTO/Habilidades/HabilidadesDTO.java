package com.chris.pokedex.DTO.Habilidades;

import java.util.List;

public class HabilidadesDTO {
    private Long id_habilidad;
    private String nombre;
    private String efecto;
    private String efecto_corto;
    private List<HabilidadesResumenDTO> pokemons;

    public HabilidadesDTO(Long id_habilidad, String nombre, String efecto, String efecto_corto, List<HabilidadesResumenDTO> pokemons) {
        this.id_habilidad = id_habilidad;
        this.nombre = nombre;
        this.efecto = efecto;
        this.efecto_corto = efecto_corto;
        this.pokemons = pokemons;
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

    public List<HabilidadesResumenDTO> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<HabilidadesResumenDTO> pokemons) {
        this.pokemons = pokemons;
    }
}
