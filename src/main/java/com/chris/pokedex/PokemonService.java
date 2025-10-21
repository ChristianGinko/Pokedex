package com.chris.pokedex;

import com.chris.pokedex.DTO.Pokemon.PokemonDTO;
import com.chris.pokedex.DTO.Pokemon.PokemonResumenDTO;
import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.repository.PokemonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PokemonService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PokemonRepository pokemonRepository;

    public PokemonDTO toDTO(Pokeapi pokemon) {
        return new PokemonDTO(
                pokemon.getId_pokemon(),
                pokemon.getNombre(),
                pokemon.getTipo().stream()
                        .map(t -> new PokemonResumenDTO(t.getId_tipo(), t.getNombre()))
                        .collect(Collectors.toList()),
                pokemon.getHabilidad().stream()
                        .map(h -> new PokemonResumenDTO(h.getId_habilidad(), h.getNombre()))
                        .collect(Collectors.toList())
        );
    }

}