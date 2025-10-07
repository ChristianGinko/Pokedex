package com.chris.pokedex;

import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.repository.PokemonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class PokemonService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Transactional
    public void limpiarPokemons() {
        // 1️⃣ Romper relaciones
        List<Pokeapi> pokemons = pokemonRepository.findAll();
        for (Pokeapi pokemon : pokemons) {
            pokemon.getHabilidades().clear();
            pokemonRepository.save(pokemon);
        }

        // 2️⃣ Borrar todos los pokémon
        pokemonRepository.deleteAll();

        // 3️⃣ Reiniciar autoincrement
        jdbcTemplate.execute("ALTER TABLE pokemon ALTER COLUMN id RESTART WITH 1");
    }

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