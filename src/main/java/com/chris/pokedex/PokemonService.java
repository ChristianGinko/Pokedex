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


}