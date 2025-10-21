package com.chris.pokedex.DTO.Pokemon;

public class PokemonResumenDTO {
    private Long id_pokemon;
    private String nombre;

    public PokemonResumenDTO(Long id_pokemon, String nombre) {
        this.id_pokemon = id_pokemon;
        this.nombre = nombre;
    }

    public Long getId_pokemon() {
        return id_pokemon;
    }

    public void setId_pokemon(Long id_pokemon) {
        this.id_pokemon = id_pokemon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
