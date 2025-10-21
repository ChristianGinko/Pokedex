package com.chris.pokedex.controller;

import com.chris.pokedex.DTO.Pokemon.PokemonResumenDTO;
import com.chris.pokedex.repository.PokemonRepository;
import com.chris.pokedex.DTO.Pokemon.PokemonDTO;
import com.chris.pokedex.PokemonService;
import com.chris.pokedex.model.Pokeapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokemonRepository repository;

    public PokemonController(PokemonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Pokeapi> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id_pokemon}")
    public ResponseEntity<Pokeapi> getById(@PathVariable Long id_pokemon) {
        Optional<Pokeapi> pokemon = repository.findById(id_pokemon);
        return pokemon.map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }
}