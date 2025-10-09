package com.chris.pokedex.controller;

import com.chris.pokedex.PokemonService;
import com.chris.pokedex.model.Pokeapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pokemon")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @PostMapping
    public Pokeapi createPokemon(@RequestBody Pokeapi pokemon){
        return pokemonService.createPoke(pokemon);
    }

    @GetMapping
    public List<Pokeapi> getAllPokemon(){
        return pokemonService.getAllPoke();
    }

    @GetMapping("{id}")
    public Pokeapi searchPokeById(@PathVariable("id") Long id){
        return pokemonService.getPokeById(id);
    }

    @DeleteMapping ("{id}")
    public void deletePokeById(@PathVariable("id") Long id){
        pokemonService.deletePokemon(id);
    }

    @DeleteMapping("/limpiar")
    public ResponseEntity<Void> limpiarPokemons() {
        pokemonService.limpiarPokemons();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Pokeapi> updatePokemon(
            @PathVariable Long id,
            @RequestBody Pokeapi partialUpdate) {

        Pokeapi updatedPokemon = pokemonService.updatePartialPokemon(id, partialUpdate);
        return ResponseEntity.ok(updatedPokemon);
    }


}
