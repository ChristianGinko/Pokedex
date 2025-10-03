package com.chris.pokedex;

import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    public Pokeapi createPoke(Pokeapi poke){
        return pokemonRepository.save(poke);
    }

    public Pokeapi getPokeById(Long id){
        Optional<Pokeapi> optionalPoke = pokemonRepository.findById(id);
        return optionalPoke.get();
    }

    public List<Pokeapi> getAllPoke(){
        return pokemonRepository.findAll();
    }

    public void deletePokemon(Long id){
        pokemonRepository.deleteById(id);
    }
}