package com.filmees.backend.repository;

import com.filmees.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// TipoUtilizador Repository
@Repository
public interface TipoUtilizadorRepository extends JpaRepository<TipoUtilizador, Integer> {
}
