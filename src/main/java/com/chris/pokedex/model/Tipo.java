package com.chris.pokedex.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tipo")
public class Tipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "doble_dano_de",
            joinColumns = @JoinColumn(name = "tipo_id"),
            inverseJoinColumns = @JoinColumn(name = "doble_dano_de_id")
    )
    private Set<Tipo> dobleDanoDe = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "doble_dano_a",
            joinColumns = @JoinColumn(name = "tipo_id"),
            inverseJoinColumns = @JoinColumn(name = "doble_dano_a_id")
    )
    private Set<Tipo> dobleDanoA = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "mitad_dano_de",
            joinColumns = @JoinColumn(name = "tipo_id"),
            inverseJoinColumns = @JoinColumn(name = "mitad_dano_de_id")
    )
    private Set<Tipo> mitadDanoDe = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "mitad_dano_a",
            joinColumns = @JoinColumn(name = "tipo_id"),
            inverseJoinColumns = @JoinColumn(name = "mitad_dano_a_id")
    )
    private Set<Tipo> mitadDanoA = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "sin_dano_de",
            joinColumns = @JoinColumn(name = "tipo_id"),
            inverseJoinColumns = @JoinColumn(name = "sin_dano_de_id")
    )
    private Set<Tipo> sinDanoDe = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "sin_dano_a",
            joinColumns = @JoinColumn(name = "tipo_id"),
            inverseJoinColumns = @JoinColumn(name = "sin_dano_a_id")
    )
    private Set<Tipo> sinDanoA = new HashSet<>();

    // Constructor, getters y setters
    public Tipo() {}

    public Tipo(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Tipo> getDobleDanoDe() {
        return dobleDanoDe;
    }

    public void setDobleDanoDe(Set<Tipo> dobleDanoDe) {
        this.dobleDanoDe = dobleDanoDe;
    }

    public Set<Tipo> getMitadDanoDe() {
        return mitadDanoDe;
    }

    public void setMitadDanoDe(Set<Tipo> mitadDanoDe) {
        this.mitadDanoDe = mitadDanoDe;
    }

    public Set<Tipo> getDobleDanoA() {
        return dobleDanoA;
    }

    public void setDobleDanoA(Set<Tipo> dobleDanoA) {
        this.dobleDanoA = dobleDanoA;
    }

    public Set<Tipo> getMitadDanoA() {
        return mitadDanoA;
    }

    public void setMitadDanoA(Set<Tipo> mitadDanoA) {
        this.mitadDanoA = mitadDanoA;
    }

    public Set<Tipo> getSinDanoDe() {
        return sinDanoDe;
    }

    public void setSinDanoDe(Set<Tipo> sinDanoDe) {
        this.sinDanoDe = sinDanoDe;
    }

    public Set<Tipo> getSinDanoA() {
        return sinDanoA;
    }

    public void setSinDanoA(Set<Tipo> sinDanoA) {
        this.sinDanoA = sinDanoA;
    }
}