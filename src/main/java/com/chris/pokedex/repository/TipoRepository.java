package com.chris.pokedex.repository;

import com.chris.pokedex.model.Tipos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRepository extends JpaRepository<Tipos, Long> {
}
