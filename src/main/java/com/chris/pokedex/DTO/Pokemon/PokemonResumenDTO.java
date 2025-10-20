package com.chris.pokedex.DTO.Pokemon;

public class PokemonResumenDTO {
    private Long id_pokemon;
    private String nombre;

    public PokemonResumenDTO(Long id, String nombre) {
        this.id_pokemon = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id_pokemon;
    }

    public String getNombre() {
        return nombre;
    }
}
