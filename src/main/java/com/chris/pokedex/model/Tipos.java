package com.chris.pokedex.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tipos")
public class Tipos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id_tipo;


    private String nombre;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doble_daño_de",
            joinColumns = @JoinColumn(name = "id_tipo1"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo2")
    )
    private List<Tipos> dobleDañoDe;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doble_daño_a",
            joinColumns = @JoinColumn(name = "id_tipo1"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo2")
    )
    private List<Tipos> dobleDañoA;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "mitad_daño_de",
            joinColumns = @JoinColumn(name = "id_tipo1"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo2")
    )
    private List<Tipos> mitadDañoDe;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "mitad_daño_a",
            joinColumns = @JoinColumn(name = "id_tipo1"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo2")
    )
    private List<Tipos> mitadDañoA;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sin_daño_de",
            joinColumns = @JoinColumn(name = "id_tipo1"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo2")
    )
    private List<Tipos> sinDañoDe;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sin_daño_a",
            joinColumns = @JoinColumn(name = "id_tipo1"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo2")
    )
    private List<Tipos> sinDañoA;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pokemon_tipo",
            joinColumns = @JoinColumn(name = "id_tipo"),
            inverseJoinColumns = @JoinColumn(name = "id_pokemon")
    )
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

    public List<Tipos> getDobleDañoDe() {
        return dobleDañoDe;
    }

    public void setDobleDañoDe(List<Tipos> dobleDañoDe) {
        this.dobleDañoDe = dobleDañoDe;
    }

    public List<Tipos> getDobleDañoA() {
        return dobleDañoA;
    }

    public void setDobleDañoA(List<Tipos> dobleDañoA) {
        this.dobleDañoA = dobleDañoA;
    }

    public List<Tipos> getMitadDañoDe() {
        return mitadDañoDe;
    }

    public void setMitadDañoDe(List<Tipos> mitadDañoDe) {
        this.mitadDañoDe = mitadDañoDe;
    }

    public List<Tipos> getMitadDañoA() {
        return mitadDañoA;
    }

    public void setMitadDañoA(List<Tipos> mitadDañoA) {
        this.mitadDañoA = mitadDañoA;
    }

    public List<Tipos> getSinDañoDe() {
        return sinDañoDe;
    }

    public void setSinDañoDe(List<Tipos> sinDañoDe) {
        this.sinDañoDe = sinDañoDe;
    }

    public List<Tipos> getSinDañoA() {
        return sinDañoA;
    }

    public void setSinDañoA(List<Tipos> sinDañoA) {
        this.sinDañoA = sinDañoA;
    }

    public List<Pokeapi> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokeapi> pokemons) {
        this.pokemons = pokemons;
    }
}
