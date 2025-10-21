package com.chris.pokedex.DTO.Tipos;

import java.util.List;

public class TiposDTO {
    private Long id_tipo;
    private String nombre;
    private List<TiposResumenDTO> pokemons;

    public TiposDTO(Long id_tipo, String nombre, List<TiposResumenDTO> pokemons) {
        this.id_tipo = id_tipo;
        this.nombre = nombre;
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

    public List<TiposResumenDTO> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<TiposResumenDTO> pokemons) {
        this.pokemons = pokemons;
    }
}
