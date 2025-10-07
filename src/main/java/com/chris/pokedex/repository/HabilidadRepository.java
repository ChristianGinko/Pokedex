package com.chris.pokedex.repository;

import com.chris.pokedex.model.Habilidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabilidadRepository extends JpaRepository<Habilidades, Long> {
}
