package com.chris.pokedex.repository;

import com.chris.pokedex.model.Pokeapi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokeapi, Long> {

}
