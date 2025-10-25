package com.chris.pokedex.controller;

import com.chris.pokedex.repository.PokemonRepository;
import com.chris.pokedex.model.Pokeapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokemonRepository repository;

    public PokemonController(PokemonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Pokeapi> getAllPokemons() {
        return repository.findAll();
    }

    @GetMapping("/{id_pokemon}")
    public Pokeapi obtener(@PathVariable Long id_pokemon) {
        return repository.findById(id_pokemon);
    }
}