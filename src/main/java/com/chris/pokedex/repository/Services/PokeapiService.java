package com.chris.pokedex.repository.Services;

import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokeapiService {

    private PokemonRepository repository;

    @Autowired
    public PokeapiService(PokemonRepository repository) {
        this.repository = repository;
    }

    public List<Pokeapi> getAllPokes(){
        return repository.findAll();
    }

    public Pokeapi getPokeCompleto(Long id_pokemon) {
        Pokeapi pokemon = repository.findPokeById(id_pokemon)
                .orElseThrow(()-> new RuntimeException("Pokemon no encontrado"));

        pokemon.setTipos(repository.findTipoByPokemon(id_pokemon));
        pokemon.setHabilidades(repository.findHabilidadByPokemon(id_pokemon));
        pokemon.setLiga(repository.findLigaByPokemon(id_pokemon)
                .orElseThrow(()-> new RuntimeException("Liga no encontrada"))
        );
        return pokemon;
    }

}