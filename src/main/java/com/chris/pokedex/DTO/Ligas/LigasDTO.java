package com.chris.pokedex.DTO.Ligas;

import java.util.List;

public class LigasDTO {
    private Long id_liga;
    private String nombre;
    private List<LigasResumenDTO> pokemons;

    public LigasDTO(Long id_liga, String nombre, List<LigasResumenDTO> pokemons) {
        this.id_liga = id_liga;
        this.nombre = nombre;
        this.pokemons = pokemons;
    }

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

    public List<LigasResumenDTO> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<LigasResumenDTO> pokemons) {
        this.pokemons = pokemons;
    }
}
