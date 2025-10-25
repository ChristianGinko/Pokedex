package com.chris.pokedex.repository.Services;

import com.chris.pokedex.model.Ligas;
import com.chris.pokedex.repository.LigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LigaService {

    private LigaRepository repository;

    @Autowired
    public LigaService(LigaRepository repository) {
        this.repository = repository;
    }

    public List<Ligas> getAllLigas(){
        return repository.findAll();
    }

    public Ligas getLigaCompleta(Long id_liga) {
        Ligas liga = repository.findLigaById(id_liga)
                .orElseThrow(()-> new RuntimeException("Liga no encontrada"));

        liga.setPokemons(repository.findPokemonsByLiga(id_liga));
        return liga;
    }
}
