package com.filmees.backend.repository;

import com.filmees.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// CarrinhoFilme Repository
@Repository
public interface CarrinhoFilmeRepository extends JpaRepository<CarrinhoFilme, Integer> {
}
