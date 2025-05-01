package com.filmees.backend.repository;

import com.filmees.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Filme Repository
@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {
}
