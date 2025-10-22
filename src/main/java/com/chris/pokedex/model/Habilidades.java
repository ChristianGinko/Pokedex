package com.chris.pokedex.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "habilidades")
public class Habilidades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_habilidad;

    private String nombre;

    private String efecto;

    private String efecto_corto;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pokemon_habilidad",
            joinColumns = @JoinColumn(name = "id_habilidad"),
            inverseJoinColumns = @JoinColumn(name = "id_pokemon")
    )
    private List<Pokeapi> pokemons;

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
