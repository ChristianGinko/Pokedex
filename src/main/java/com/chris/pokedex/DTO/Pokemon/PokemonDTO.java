package com.chris.pokedex.DTO.Pokemon;

import java.util.List;

public class PokemonDTO {
    private Long id_pokemon;
    private String nombre;
    private List<PokemonResumenDTO> tipo;

    public PokemonDTO(
            Long id,
            String nombre,
            List<PokemonResumenDTO> tipo
    ){
        this.id_pokemon = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Long getId() {return id_pokemon;}
    public String getNombre() {return nombre;}
    public List<PokemonResumenDTO> getTipo() {return tipo;}
}
