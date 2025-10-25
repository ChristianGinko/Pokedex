package com.chris.pokedex.repository.Services;

import com.chris.pokedex.model.Habilidades;
import com.chris.pokedex.repository.HabilidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabilidadService {

    private HabilidadRepository repository;

    @Autowired
    public HabilidadService(HabilidadRepository repository){
        this.repository = repository;
    }

    public List<Habilidades> getAllHabilidades(){
        return repository.findAll();
    }

    public Habilidades getHabilidadCompleta(Long id_habilidad){
        Habilidades habilidades = repository.findHabilidadById(id_habilidad)
                .orElseThrow(()-> new RuntimeException("Habilidad no encontrada"));

        habilidades.setPokemons(repository.findPokemonsByHabilidad(id_habilidad));
        return habilidades;
    }

}
