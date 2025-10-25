package com.chris.pokedex.controller;

import com.chris.pokedex.repository.PokemonRepository;
import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.repository.Services.PokeapiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokeapiService service;

    public PokemonController(PokeapiService service){
        this.service = service;
    }

    @GetMapping
    public List<Pokeapi> getAll(){
        return service.getAllPokes();
    }

    @GetMapping("/{id_pokemon}")
    public ResponseEntity<Pokeapi> getById(@PathVariable Long id_pokemon){
        try{
            return ResponseEntity.ok(service.getPokeCompleto(id_pokemon));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}