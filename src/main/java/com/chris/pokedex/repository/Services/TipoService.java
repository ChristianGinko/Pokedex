package com.chris.pokedex.repository.Services;

import com.chris.pokedex.model.Tipos;
import com.chris.pokedex.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoService {

    private TipoRepository repository;

    @Autowired
    public TipoService(TipoRepository repository){
        this.repository = repository;
    }

    public List<Tipos> getAllTipos(){
        return repository.findAll();
    }

    public Tipos getTipoCompleto(Long id_tipo){
        Tipos tipos = repository.findTipoById(id_tipo)
                .orElseThrow(()-> new RuntimeException("Tipo no encontrado"));

        tipos.setDobleDanioDe(repository.findDobleDanioDeByTipo(id_tipo));
        tipos.setDobleDanioA(repository.findDobleDanioAByTipo(id_tipo));
        tipos.setMitadDanioDe(repository.findMitadDanioDeByTipo(id_tipo));
        tipos.setMitadDanioA(repository.findMitadDanioAByTipo(id_tipo));
        tipos.setSinDanioDe(repository.findSinDanioDeByTipo(id_tipo));
        tipos.setSinDanioA(repository.findSinDanioAByTipo(id_tipo));
        tipos.setPokemons(repository.findPokeByTipo(id_tipo));
        return tipos;
    }

}
