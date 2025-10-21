package com.chris.pokedex.DTO.Pokemon;

import java.util.List;

public class PokemonDTO {
    private Long id_pokemon;
    private String nombre;
    private List<PokemonResumenDTO> tipo;
    private List<PokemonResumenDTO> habilidad;
    private PokemonResumenDTO liga;

    public PokemonDTO(Long id_pokemon, String nombre, List<PokemonResumenDTO> tipo, List<PokemonResumenDTO> habilidad, PokemonResumenDTO liga) {
        this.id_pokemon = id_pokemon;
        this.nombre = nombre;
        this.tipo = tipo;
        this.habilidad = habilidad;
        this.liga = liga;
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

    public List<PokemonResumenDTO> getTipo() {
        return tipo;
    }

    public void setTipo(List<PokemonResumenDTO> tipo) {
        this.tipo = tipo;
    }

    public List<PokemonResumenDTO> getHabilidad() {
        return habilidad;
    }

    public void setHabilidad(List<PokemonResumenDTO> habilidad) {
        this.habilidad = habilidad;
    }

    public PokemonResumenDTO getLiga() {
        return liga;
    }

    public void setLiga(PokemonResumenDTO liga) {
        this.liga = liga;
    }
}
