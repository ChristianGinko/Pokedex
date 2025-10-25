package com.chris.pokedex.controller;

import com.chris.pokedex.DTO.Pokemon.PokemonDTO;
import com.chris.pokedex.DTO.Pokemon.PokemonResumenDTO;
import com.chris.pokedex.PokemonService;
import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.repository.PokemonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokemonRepository repository;
    private final PokemonService service;

    public PokemonController(PokemonRepository repository, PokemonService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    public List<PokemonResumenDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(p -> new PokemonResumenDTO(p.getId_pokemon(), p.getNombre()))
                .toList();

    }

    @GetMapping("/{id_pokemon}")
    public ResponseEntity<PokemonDTO> getById(@PathVariable Long id_pokemon) {
        Optional<Pokeapi> pokemon = repository.findById(id_pokemon);
        return pokemon.map(value -> ResponseEntity.ok(service.toDTO(value)))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }
}