package com.chris.pokedex;

import com.chris.pokedex.DTO.Habilidades.HabilidadesDTO;
import com.chris.pokedex.DTO.Habilidades.HabilidadesResumenDTO;
import com.chris.pokedex.DTO.Ligas.LigasDTO;
import com.chris.pokedex.DTO.Ligas.LigasResumenDTO;
import com.chris.pokedex.DTO.Pokemon.PokemonDTO;
import com.chris.pokedex.DTO.Pokemon.PokemonResumenDTO;
import com.chris.pokedex.DTO.Tipos.TiposDTO;
import com.chris.pokedex.DTO.Tipos.TiposResumenDTO;
import com.chris.pokedex.model.Habilidades;
import com.chris.pokedex.model.Ligas;
import com.chris.pokedex.model.Pokeapi;
import com.chris.pokedex.model.Tipos;
import com.chris.pokedex.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
                        .collect(Collectors.toList()),
                pokemon.getLiga() != null
                        ? new PokemonResumenDTO(pokemon.getLiga().getId_liga(), pokemon.getLiga().getNombre())
                        :null
        );
    }

    public LigasDTO toDTO(Ligas ligas) {
        return new LigasDTO(
                ligas.getId_liga(),
                ligas.getNombre(),
                ligas.getPokemons().stream()
                        .map(l -> new LigasResumenDTO(l.getId_pokemon(), l.getNombre()))
                        .collect(Collectors.toList())
        );
    }

    public HabilidadesDTO toDTO(Habilidades habilidades) {
        return new HabilidadesDTO(
                habilidades.getId_habilidad(),
                habilidades.getNombre(),
                habilidades.getEfecto(),
                habilidades.getEfecto_corto(),
                habilidades.getPokemons().stream()
                        .map(h-> new HabilidadesResumenDTO(h.getId_pokemon(), h.getNombre()))
                        .collect(Collectors.toList())
        );
    }

    public TiposDTO toDTO(Tipos tipos) {
        return new TiposDTO(
                tipos.getId_tipo(),
                tipos.getNombre(),
                tipos.getDobleDañoDe().stream()
                        .map(t-> new TiposResumenDTO(t.getId_tipo(), t.getNombre()))
                        .collect(Collectors.toList()),
                tipos.getDobleDañoA().stream()
                        .map(t-> new TiposResumenDTO(t.getId_tipo(), t.getNombre()))
                        .collect(Collectors.toList()),
                tipos.getMitadDañoDe().stream()
                        .map(t-> new TiposResumenDTO(t.getId_tipo(), t.getNombre()))
                        .collect(Collectors.toList()),
                tipos.getMitadDañoA().stream()
                        .map(t-> new TiposResumenDTO(t.getId_tipo(), t.getNombre()))
                        .collect(Collectors.toList()),
                tipos.getSinDañoDe().stream()
                        .map(t-> new TiposResumenDTO(t.getId_tipo(), t.getNombre()))
                        .collect(Collectors.toList()),
                tipos.getSinDañoA().stream()
                        .map(t-> new TiposResumenDTO(t.getId_tipo(), t.getNombre()))
                        .collect(Collectors.toList()),
                tipos.getPokemons().stream()
                        .map(t -> new TiposResumenDTO(t.getId_pokemon(), t.getNombre()))
                        .collect(Collectors.toList())
        );
    }

}